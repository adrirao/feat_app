package com.unlam.feat.presentation.view.configProfile


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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.io.IOException
import java.time.format.DateTimeFormatter



@Composable
fun ConfigProfileScreen(
    navController: NavHostController,
    state:ConfigProfileState
) {
    ConfigProfileContent(state, navigateToConfigSport = {
//        navController.popBackStack()
//        navController.navigate(Screen.ConfigSport.route)
    })
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ConfigProfileContent(state: ConfigProfileState, navigateToConfigSport: () -> Unit) {
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
                locationResult?.locations.let{

                    if (it != null) {
                        addrees = getAddress(it.last(), context)
                        if(addrees != null){
                            viewModel.onEvent(
                                ConfigProfileEvent.EnteredAddressStreet(
                                    addrees!!.thoroughfare
                                )
                            )
                            viewModel.onEvent(
                                ConfigProfileEvent.EnteredAddressNumber(
                                    addrees!!.featureName
                                )
                            )
                            viewModel.onEvent(
                                ConfigProfileEvent.EnteredAddressTown(
                                    addrees!!.locality
                                )
                            )
                            viewModel.onEvent(
                                ConfigProfileEvent.EnteredAddressZipCode(
                                    addrees!!.postalCode
                                )
                            )
                            viewModel.onEvent(
                                ConfigProfileEvent.EnteredAddressLatitude(
                                    addrees!!.latitude.toString()
                                )
                            )
                            viewModel.onEvent(
                                ConfigProfileEvent.EnteredAddressLongitude(
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
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.logotipo),
                contentDescription = stringResource(R.string.feat_logo)
            )
            FeatText(
                text = "Es tu primer inicio de sesión  en Feat, necesitaremos algunos datos para configurar tu perfil.",
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false),
                verticalArrangement = Arrangement.Center,

                ) {

                FeatTextField(
                    text = viewModel.state.value.lastName,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredLastName(it))
                    },
                    textLabel = "Apellido"
                )

                FeatTextField(
                    text = viewModel.state.value.name,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredName(it))
                    },
                    textLabel = "Nombres"
                )


                val dialogState = rememberMaterialDialogState()
                FeatTextField(
                    text = if (viewModel.state.value.dateOfBirth != null) {
                        viewModel.state.value.dateOfBirth!!.format(
                            DateTimeFormatter.ofPattern(
                                "dd LLLL yyyy"
                            )
                        )
                    } else {
                        " "
                    },
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { dialogState.show() },
                    enabled = false,
                    textLabel = "Fecha de nacimiento"

                )
                MaterialDialog(
                    dialogState = dialogState,
                    buttons = {
                        positiveButton("Ok")
                        negativeButton("Cancel")
                    },

                    ) {
                    datepicker() { date ->
                        viewModel.onEvent(ConfigProfileEvent.EnteredDateOfBirth(date))

                    }
                }

                FeatTextField(
                    text = viewModel.state.value.nickname,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredNickname(it))
                    },
                    textLabel = "Apodo"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    FeatText(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(), text = "Sexo"
                    )
                    val sexs = listOf("", "Hombre", "Mujer", "Otro")
                    val currentSelection = remember { mutableStateOf(sexs.first()) }
                    Row {
                        FeatRadioGroup(
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            items = sexs,
                            selection = currentSelection.value,
                            onItemClick = { clickedItem ->
                                currentSelection.value = clickedItem
                                viewModel.onEvent(ConfigProfileEvent.EnteredSex(clickedItem))
                            }
                        )
                    }


                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    FeatText(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(), text = "Direccion"
                    )




                    Row {
                        Column {
                            FeatButton(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .height(60.dp),
                                drawable = R.drawable.gps_locator,
                                textButton = "Mi ubicación actual",
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                                colorText = MaterialTheme.colors.primary,
                                textAlign = TextAlign.Center,
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                                enabled = enabled.value,
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
                                        viewModel.onEvent(
                                            ConfigProfileEvent.ShowAlertPermission(
                                                true,
                                                "Permisos de ubicacion necesarios",
                                                "Se necesitan los permisos de ubicacion para acceder a su ubicacion actual"
                                            )
                                        )
                                    } else if (permissionState.revokedPermissions.size != permissionState.permissions.size) {
                                        viewModel.onEvent(
                                            ConfigProfileEvent.ShowAlertPermission(
                                                true,
                                                "Se necesitan permisos de ubicacion exacta",
                                                "Se necesitan los permisos de ubicacion exacta para acceder a su ubicacion actual"
                                            )
                                        )
                                    } else if (permissionState.revokedPermissions.size == permissionState.permissions.size &&
                                        permissionState.permissionRequested
                                    ) {
                                        viewModel.onEvent(
                                            ConfigProfileEvent.ShowAlertPermission(
                                                true,
                                                "Se denegaron los permisos de ubicacion",
                                                "Se han denegado los permisos de ubicacion, por favor acceder a configuraciones de la applicacion y otorgar los permisos"
                                            )
                                        )

                                    }
                                }
                            )

                            if (viewModel.state.value.showAlertPermission) {
                                FeatAlertDialog(
                                    title = viewModel.state.value.titleAlert,
                                    descriptionContent = viewModel.state.value.descriptionAlert,
                                    onDismiss = {
                                        viewModel.onEvent(ConfigProfileEvent.DismissDialog)
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
                    text = viewModel.state.value.addressAlias,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredAddressAlias(it))
                    },
                    textLabel = "Alias"
                )
                FeatTextField(
                    text = viewModel.state.value.addressStreet,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredAddressStreet(it))
                    },
                    textLabel = "Calle"
                )
                FeatTextField(
                    text = viewModel.state.value.addressNumber,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredAddressNumber(it))
                    },
                    textLabel = "Numero"
                )
                FeatTextField(
                    text = viewModel.state.value.addressTown,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredAddressTown(it))
                    },
                    textLabel = "Ciudad"
                )
                FeatTextField(
                    text = viewModel.state.value.addressZipCode,
                    onValueChange = {
                        viewModel.onEvent(ConfigProfileEvent.EnteredAddressZipCode(it))
                    },
                    textLabel = "Codigo Postal"
                )
                FeatButton(modifier = Modifier
                    .padding(10.dp)
                    .height(60.dp),
                    textButton = "Confirma dirección",
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    colorText = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    onClick = {

                        val geocoder = Geocoder(context)
                        val addresses: List<Address>?
                        val addressText: String =
                            viewModel.state.value.addressStreet + " " + viewModel.state.value.addressNumber + " " + viewModel.state.value.addressTown + " " + viewModel.state.value.addressZipCode
                        val latitude: Double
                        val longitude: Double
                        try {

                            addresses = geocoder.getFromLocationName(addressText, 1)

                            if (addresses != null && addresses.isNotEmpty()) {
                                latitude = addresses[0].latitude
                                longitude = addresses[0].longitude

                                Log.e("MapsActivity", "$latitude + $longitude ")
                            }
                            Log.e("MapsActivity", addressText)
                            Log.e("AdrresLine", addresses[0].getAddressLine(0))
                            Log.e("AdrresMaxLine", addresses[0].maxAddressLineIndex.toString())

                        } catch (e: IOException) {
                            Log.e("MapsActivity", e.localizedMessage)
                        }

                    }
                )

                FeatButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(60.dp),
                    textButton = "Siguiente",
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                    colorText = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    onClick = {
                        navigateToConfigSport()
                        //persistir en la base
                    }
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








