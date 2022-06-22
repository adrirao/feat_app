package com.unlam.feat.presentation.view.edit_profile.address

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
import androidx.core.content.ContextCompat
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
import com.unlam.feat.presentation.ui.theme.card
import java.io.IOException

@Composable
fun EditProfileAddressScreen(
    navController: NavHostController,
    state: EditProfileAddressState,
    onValueChange: (EditProfileAddressEvent) -> Unit
) {

    SetMessagesAddress(
        state = state,
        navController = navController,
        onValueChange = onValueChange
    )

    EditProfileAddressScreenContent(state, navigateToConfigProfileAvailability = {
        navController.popBackStack()
        navController.navigate(Screen.Profile.route)
    }, onValueChange)
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun EditProfileAddressScreenContent(
    state: EditProfileAddressState,
    navigateToConfigProfileAvailability: () -> Unit,
    onValueChange: (EditProfileAddressEvent) -> Unit
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
                                EditProfileAddressEvent.EnteredAddressStreet(
                                    addrees!!.thoroughfare
                                )
                            )
                            onValueChange(
                                EditProfileAddressEvent.EnteredAddressNumber(
                                    addrees!!.featureName
                                )
                            )
                            onValueChange(
                                EditProfileAddressEvent.EnteredAddressTown(
                                    addrees!!.locality
                                )
                            )
                            onValueChange(
                                EditProfileAddressEvent.EnteredAddressZipCode(
                                    addrees!!.postalCode
                                )
                            )
                            onValueChange(
                                EditProfileAddressEvent.EnteredAddressLatitude(
                                    addrees!!.latitude.toString()
                                )
                            )
                            onValueChange(
                                EditProfileAddressEvent.EnteredAddressLongitude(
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FeatHeader(text = "Agregar Dirección")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colors.card),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

                    Row {
                        Column {

                            if (state.showAlertPermission) {
                                FeatAlertDialog(
                                    title = state.titleAlert,
                                    descriptionContent = state.descriptionAlert,
                                    onDismiss = {
                                        onValueChange(EditProfileAddressEvent.DismissDialog)
                                        permissionState.launchMultiplePermissionRequest()
                                    },
                                    onClick = {
                                        ContextCompat.startActivity(
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
                        onValueChange(EditProfileAddressEvent.EnteredAddressAlias(it))
                    },
                    textLabel = "Alias"
                )
                FeatTextField(
                    text = state.addressStreet,
                    onValueChange = {
                        onValueChange(EditProfileAddressEvent.EnteredAddressStreet(it))
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
                                            EditProfileAddressEvent.ShowAlertPermission(
                                                true,
                                                "Permisos de ubicacion necesarios",
                                                "Se necesitan los permisos de ubicacion para acceder a su ubicacion actual"
                                            )
                                        )
                                    } else if (permissionState.revokedPermissions.size != permissionState.permissions.size) {
                                        onValueChange(
                                            EditProfileAddressEvent.ShowAlertPermission(
                                                true,
                                                "Se necesitan permisos de ubicacion exacta",
                                                "Se necesitan los permisos de ubicacion exacta para acceder a su ubicacion actual"
                                            )
                                        )

                                    } else if (permissionState.revokedPermissions.size == permissionState.permissions.size &&
                                        permissionState.permissionRequested
                                    ) {
                                        onValueChange(
                                            EditProfileAddressEvent.ShowAlertPermission(
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
                        onValueChange((EditProfileAddressEvent.EnteredAddressNumber(it)))
                    },
                    textLabel = "Numero"
                )
                FeatTextField(
                    text = state.addressTown,
                    onValueChange = {
                        onValueChange(EditProfileAddressEvent.EnteredAddressTown(it))
                    },
                    textLabel = "Ciudad"
                )
                FeatTextField(
                    text = state.addressZipCode,
                    onValueChange = {
                        onValueChange(EditProfileAddressEvent.EnteredAddressZipCode(it))
                    },
                    textLabel = "Codigo Postal"
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.End),
                    verticalAlignment = Alignment.Bottom
                ) {
                    FeatButton(
                        modifier = Modifier
                            .padding(10.dp),
                        textButton = "Agregar dirección",
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        onClick = {

                            val geocoder = Geocoder(context)
                            val addresses: List<Address>?
                            val addressText: String =
                                state.addressStreet + " " + state.addressNumber + " " + state.addressTown + " " + state.addressZipCode
                            try {
                                addresses = geocoder.getFromLocationName(addressText, 1)

                                if (addresses != null && addresses.isNotEmpty()) {
                                    onValueChange(
                                        EditProfileAddressEvent.EnteredAddressLatitude(
                                            addresses[0].latitude.toString()
                                        )
                                    )
                                    onValueChange(
                                        EditProfileAddressEvent.EnteredAddressLongitude(
                                            addresses[0].longitude.toString()
                                        )
                                    )
                                }

                                onValueChange(EditProfileAddressEvent.SubmitData)
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
}


}


private fun getAddress(location: Location, context: Context): Address {
    val geocoder = Geocoder(context)
    val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
    return list[0]
}


@Composable
private fun SetMessagesAddress(
    state: EditProfileAddressState,
    navController: NavController,
    onValueChange: (EditProfileAddressEvent) -> Unit
) {
    if (state.error != null || state.personError != "" ) {
        Log.d("ERROR", state.error.toString())
        FeatAlertDialog(
            title = "Error de conexión",
            descriptionContent = "No se ha podido conectar con el servidor, vuelva a intentarlo.",
            onDismiss = {
                onValueChange(EditProfileAddressEvent.DismissDialog)
                navController.popBackStack()
                navController.navigate(Screen.Profile.route)
            }
        )
    }

    var countFieldEmptys: List<String> = state.fieldEmpty.split (',')


    if (state.fieldEmpty != "" && countFieldEmptys.size > 2) {
        FeatAlertDialog(
            title = "Hay campos vacios",
            descriptionContent = "Por favor, verifica que los siguientes campos no esten vacios ${state.fieldEmpty}",
            onDismiss = {onValueChange(EditProfileAddressEvent.DismissDialog)
            }
        )
    }else {
        if (state.addressStreetError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese una calle en el campo",
                onDismiss = {
                    onValueChange(EditProfileAddressEvent.DismissDialog)
                }
            )
        }

        if (state.addressNumberError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese un numero en el campo",
                onDismiss = {
                    onValueChange(EditProfileAddressEvent.DismissDialog)
                }
            )
        }

        if (state.addressTownError != null) {
            FeatAlertDialog(
                title = "Debe seleccionar un sexo",
                descriptionContent = "Por favor, ingrese una ciudad en el campo",
                onDismiss = {
                    onValueChange(EditProfileAddressEvent.DismissDialog)
                }
            )
        }

        if (state.addressZipCodeError != null) {
            FeatAlertDialog(
                title = "El campo no puede estar vacío",
                descriptionContent = "Por favor, ingrese un numero en el campo",
                onDismiss = {
                    onValueChange(EditProfileAddressEvent.DismissDialog)
                }
            )
        }
    }
}