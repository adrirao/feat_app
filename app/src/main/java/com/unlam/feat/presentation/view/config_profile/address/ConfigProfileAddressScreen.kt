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
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.view.config_profile.ConfigProfileEvent
import java.io.IOException


@Composable
fun ConfigProfileAddressScreen(
    navController: NavHostController,
    state: ConfigProfileAddressState,
    onValueChange: (ConfigProfileAddressEvent) -> Unit
) {
    ConfigProfileAddressScreenContent(state, navigateToConfigAvailability = {
        navController.popBackStack()
        navController.navigate(Screen.ConfigProfileAvailability.route)
    }, onValueChange)
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ConfigProfileAddressScreenContent(
    state: ConfigProfileAddressState,
    navigateToConfigAvailability: () -> Unit,
    onValueChange: (ConfigProfileAddressEvent) -> Unit
) {
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
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.ic_isologotype_2),
                contentDescription = stringResource(R.string.feat_logo)
            )
            FeatText(
                text = "Agregar direcciones.",
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
                                }
                            )

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
                    textLabel = "Calle"
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
                            state.addressStreet + " " + state.addressNumber + " " + state.addressTown + " " + state.addressZipCode
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
                        navigateToConfigAvailability()
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




