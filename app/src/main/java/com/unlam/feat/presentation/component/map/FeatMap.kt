package com.unlam.feat.presentation.component.map

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.unlam.feat.R

@Composable
fun FeatMap(
    setLocation: (LatLng) -> Unit
) {
    val markerPosition = LatLng(-34.671137, -58.5664745)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 16f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                setLocation(cameraPositionState.position.target)
            },
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.logotipo),
                contentDescription = "marker",
            )
        }
    }


}

@Composable
fun FeatMapWhitMaker(
    markers: List<Marker> = listOf(
        com.unlam.feat.presentation.component.map.Marker(
            LatLng(
                -34.671137,
                -58.5664745
            ),
        ),
        com.unlam.feat.presentation.component.map.Marker(
            LatLng(
                -34.123,
                -58.5142123664745
            ),
        )
    ),
    onClick: @Composable (() -> Unit)? = null
) {
    var testing = remember {
        mutableStateOf(Marker())
    }
    val markerPosition = LatLng(-34.671137, -58.5664745)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 16f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        markers.forEach { maker ->
            Marker(
                position = maker.position!!,
                onClick = { it ->
                    return@Marker true
                }
            )
        }
    }

}