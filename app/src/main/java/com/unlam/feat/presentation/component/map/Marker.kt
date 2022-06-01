package com.unlam.feat.presentation.component.map

import com.google.android.gms.maps.model.LatLng

data class Marker(
    var position: LatLng? = null,
    val title : String = "",
    val snippet : String = ""
)