package com.unlam.feat.presentation.view.config_profile.address


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import java.io.IOException

@Composable
fun ConfigProfileAddressScreen(
    navController: NavHostController,
    state: ConfigProfileAddressState,
    onValueChange: (ConfigProfileAddressEvent) -> Unit
) {

    SetMessagesAddress(
        state = state,
        navController = navController,
        onValueChange = onValueChange
    )

    ConfigProfileAddressScreenContent(state, navigateToConfigProfileAvailability = {
        navController.popBackStack()
        navController.navigate(Screen.ConfigProfileAvailability.route)
    }, onValueChange)
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ConfigProfileAddressScreenContent(
    state: ConfigProfileAddressState,
    navigateToConfigProfileAvailability: () -> Unit,
    onValueChange: (ConfigProfileAddressEvent) -> Unit
) {

    if (state.isSuccessSubmitData) {
        LaunchedEffect(true) {
            navigateToConfigProfileAvailability()
        }
    }

    if (state.isLoading) {
        FeatCircularProgress()
    }


    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val enabled = remember {
        mutableStateOf(true)
    }

    var addrees: Address?
    val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    val gpsSensorManage =
        LocationServices.getFusedLocationProviderClient(context)

    val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult?.locations.let {

                    if (it != null) {
                        addrees = getAddress(it.last(), context)
                        if (addrees != null) {
                            onValueChange(
                                ConfigProfileAddressEvent.EnteredAddressStreet(
                                    addrees!!.thoroughfare
                                )
                            )
                            onValueChange(
                                ConfigProfileAddressEvent.EnteredAddressNumber(
                                    addrees!!.featureName
                                )
                            )
                            onValueChange(
                                ConfigProfileAddressEvent.EnteredAddressTown(
                                    addrees!!.locality
                                )
                            )
                            onValueChange(
                                ConfigProfileAddressEvent.EnteredAddressZipCode(
                                    addrees!!.postalCode
                                )
                            )
                            onValueChange(
                                ConfigProfileAddressEvent.EnteredAddressLatitude(
                                    addrees!!.latitude.toString()
                                )
                            )
                            onValueChange(
                                ConfigProfileAddressEvent.EnteredAddressLongitude(
                                    addrees!!.longitude.toString()
                                )
                            )
                            gpsSensorManage.removeLocationUpdates(this).addOnCompleteListener {
                                enabled.value = true
                            }
                        }
                    }

                }
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 0.15f, fill = false)
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(200.dp, 60.dp)
                        .padding(bottom = 10.dp),
                    painter = painterResource(R.drawable.ic_isologotype_2),
                    contentDescription = stringResource(R.string.feat_logo)
                )
                FeatText(
                    text = "Agregar direcciones. 2/5",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 0.8f, fill = true)
                    .align(Alignment.CenterHorizontally),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    FeatText(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 2.dp)
                            .fillMaxWidth(), text = "Direccion"
                    )

                    Row {
                        Column {

                            if (state.showAlertPermission) {
                                FeatAlertDialog(
                                    title = state.titleAlert,
                                    descriptionContent = state.descriptionAlert,
                                    onDismiss = {
                                        onValueChange(ConfigProfileAddressEvent.DismissDialog)
                                        permissionState.launchMultiplePermissionRequest()
                                    },
                                    onClick = {
                                        startActivity(
                                            context,
                                            Intent(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts(
                                                    "package",
                                                    "com.unlam.feat",
                                                    null
                                                )
                                            ),
                                            null
                                        )
                                    }
                                )

                            }

                        }

                    }
                }

                FeatTextField(
                    text = state.addressAlias,
                    onValueChange = {
                        onValueChange(ConfigProfileAddressEvent.EnteredAddressAlias(it))
                    },
                    textLabel = "Alias"
                )
                FeatTextField(
                    text = state.addressStreet,
                    onValueChange = {
                        onValueChange(ConfigProfileAddressEvent.EnteredAddressStreet(it))
                    },
                    textLabel = "Calle",
                    trailingIcon = {
                        val icon: @Composable () -> Unit = {
                            IconButton(
                                onClick = {
                                    if (permissionState.allPermissionsGranted) {
                                        enabled.value = false
                                        gpsSensorManage.requestLocationUpdates(
                                            locationRequest,
                                            locationCallback,
                                            Looper.getMainLooper()
                                        )

                                    } else if (!permissionState.permissionRequested) {
                                        permissionState.launchMultiplePermissionRequest()

                                    } else if (permissionState.shouldShowRationale) {
                                        onValueChange(
                                            ConfigProfileAddressEvent.ShowAlertPermission(
                                                true,
                                                "Permisos de ubicacion necesarios",
                                                "Se necesitan los permisos de ubicacion para acceder a su ubicacion actual"
                                            )
                                        )
                                    } else if (permissionState.revokedPermissions.size != permissionState.permissions.size) {
                                        onValueChange(
                                            ConfigProfileAddressEvent.ShowAlertPermission(
                                                true,
                                                "Se necesitan permisos de ubicacion exacta",
                                                "Se necesitan los permisos de ubicacion exacta para acceder a su ubicacion actual"
                                            )
                                        )

                                    } else if (permissionState.revokedPermissions.size == permissionState.permissions.size &&
                                        permissionState.permissionRequested
                                    ) {
                                        onValueChange(
                                            ConfigProfileAddressEvent.ShowAlertPermission(
                                                true,
                                                "Se denegaron los permisos de ubicacion",
                                                "Se han denegado los permisos de ubicacion, por favor acceder a configuraciones de la applicacion y otorgar los permisos"
                                            )
                                        )
                                    }
                                },
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.gps_locator),
                                    tint = Color.Black,
                                    contentDescription = ""
                                )
                            }
                        }
                        icon()
                    }
                )
                FeatTextField(
                    text = state.addressNumber,
                    onValueChange = {
                        onValueChange((ConfigProfileAddressEvent.EnteredAddressNumber(it)))
                    },
                    textLabel = "Numero"
                )
                FeatTextField(
                    text = state.addressTown,
                    onValueChange = {
                        onValueChange(ConfigProfileAddressEvent.EnteredAddressTown(it))
                    },
                    textLabel = "Ciudad"
                )
                FeatTextField(
                    text = state.addressZipCode,
                    onValueChange = {
                        onValueChange(ConfigProfileAddressEvent.EnteredAddressZipCode(it))
                    },
                    textLabel = "Codigo Postal"
                )

            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .weight(0.1f, false),
                verticalAlignment = Alignment.Bottom
            ) {
                FeatButtonRounded(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(60.dp),
                    drawable = R.drawable.arrow_next,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    onClick = {

                            val geocoder = Geocoder(context)
                            val addresses: List<Address>?
                            val addressText: String =
                                state.addressStreet + " " + state.addressNumber + " " + state.addressTown + " " + state.addressZipCode
                            try {
                                addresses = geocoder.getFromLocationName(addressText, 1)

                                if (addresses != null && addresses.isNotEmpty()) {
                                   onValueChange( ConfigProfileAddressEvent.EnteredAddressLatitude(
                                        addresses[0].latitude.toString()
                                    ))
                                    onValueChange(
                                    ConfigProfileAddressEvent.EnteredAddressLongitude(
                                        addresses[0].longitude.toString()
                                    ))
                                }

                                onValueChange(ConfigProfileAddressEvent.SubmitData)
                            } catch (e: IOException) {
                                Log.e("MapsActivity", e.localizedMessage)
                            }



                    },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }

        }


    }


}


private fun getAddress(location: Location, context: Context): Address {
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
    return list[0]
}


@Composable
 private fun SetMessagesAddress(
    state: ConfigProfileAddressState,
    navController: NavController,
    onValueChange: (ConfigProfileAddressEvent) -> Unit
) {
    if (state.error != null || state.personError != "" ) {
        FeatAlertDialog(
            title = "Error de conexión",
            descriptionContent = "No se ha podido conectar con el servidor, vuelva a intentarlo.",
            onDismiss = {
                onValueChange(ConfigProfileAddressEvent.DismissDialog)
                onValueChange(ConfigProfileAddressEvent.SingOutUser)
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )
    }
    var countFieldEmptys: List<String>

    countFieldEmptys = state.fieldEmpty.split (',')


    if (state.fieldEmpty != "" && countFieldEmptys.size > 2) {
        FeatAlertDialog(
            title = "Hay campos vacios",
            descriptionContent = "Por favor, verifica que los siguientes campos no esten vacios ${state.fieldEmpty}",
            onDismiss = {onValueChange(ConfigProfileAddressEvent.DismissDialog)
            }
        )
    }else {
        if (state.addressStreetError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese una calle en el campo",
                onDismiss = {
                    onValueChange(ConfigProfileAddressEvent.DismissDialog)
                }
            )
        }

        if (state.addressNumberError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese un numero en el campo",
                onDismiss = {
                    onValueChange(ConfigProfileAddressEvent.DismissDialog)
                }
            )
        }

        if (state.addressTownError != null) {
            FeatAlertDialog(
                title = "Debe seleccionar un sexo",
                descriptionContent = "Por favor, ingrese una ciudad en el campo",
                onDismiss = {
                    onValueChange(ConfigProfileAddressEvent.DismissDialog)
                }
            )
        }

        if (state.addressZipCodeError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese un numero en el campo",
                onDismiss = {
                    onValueChange(ConfigProfileAddressEvent.DismissDialog)
                }
            )
        }
    }
}














