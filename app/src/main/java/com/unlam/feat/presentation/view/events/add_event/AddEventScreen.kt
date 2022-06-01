package com.unlam.feat.presentation.view.events.add_event

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.model.Periodicity
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card


@Composable
fun AddNewEventScreen(
    navigateToEvents: () -> Unit,
    state: AddEventState,
    onValueChange: (AddEventEvent) -> Unit,
    createEvent: () -> Unit
) {
    Column {
        FeatHeader("Creacion Evento")
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(MaterialTheme.colors.card)
        ) {

            val context: Context = LocalContext.current

            Column(
            ) {
                FeatTextField(
                    text = state.name,
                    textLabel = stringResource(R.string.input_name_event),
                    onValueChange = { onValueChange(AddEventEvent.EnteredName(it)) }
                )

                FeatDatePicker(
                    date = state.date,
                    label = "Día",
                    onValueChange = { onValueChange(AddEventEvent.EnteredDay(it)) },
                    titlePicker = "Seleccione una fecha"
                )

                FeatTimePicker(
                    time = state.startTime,
                    label = "Horario de inicio",
                    onValueChange = { onValueChange(AddEventEvent.EnteredStartTime(it)) },
                    titlePicker = "Seleccione una hora de inicio"
                )

                FeatTimePicker(
                    time = state.endTime,
                    label = "Horario de finalizacion",
                    onValueChange = { onValueChange(AddEventEvent.EnteredEndTime(it)) },
                    titlePicker = "Seleccione una hora de fin"
                )


                val periodicityList = mutableListOf<Periodicity>()
                periodicityList.add(Periodicity(0, " "))
                periodicityList.add(Periodicity(1, "Unica Vez"))
                periodicityList.add(Periodicity(2, "Semanal"))
                periodicityList.add(Periodicity(3, "Quincenal"))
                periodicityList.add(Periodicity(4, "Mensua"))

                val periodicityListString = mutableListOf<String>()

                periodicityList.forEach {
                    periodicityListString.add(it.description)
                }

                FeatDropDown(
                    options = periodicityListString,
                    label = "Perioricidad",
                    onValueChange = { periodicityText ->

                        state.periodicityList.forEach {

                            if (it.description == periodicityText) {
                                onValueChange(AddEventEvent.EnteredPeriodicity(it.id.toString()))
                            }
                        }
                    }
                )


                FeatTextField(
                    text = state.address,
                    textLabel = "Lugar",
                    onValueChange = {

                        AddEventEvent.EnteredAddress(it)

                        val address: List<Address> = getAddress(it, context)

                        if (address != null && address.isNotEmpty()) {
                            onValueChange(
                                AddEventEvent.EnteredLatLong(
                                    address.first().latitude.toString(),
                                    address.first().longitude.toString()
                                )
                            )
                            Log.e("MapsActivity",   address.first().latitude.toString() + address.first().longitude.toString())
                        }

                    }
                )
                FeatTextField(
                    text = state.description,
                    textLabel = "Descripcion",
                    onValueChange = { onValueChange(AddEventEvent.EnteredDescription(it)) }
                )
                Row(

                ) {
                    FeatButton(
                        textButton = "Cancelar",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        onClick = {
                            navigateToEvents()
                        }
                    )
                    FeatButton(
                        textButton = "Crear",
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                        onClick = {
                            createEvent()
                        }
                    )
                }
            }

        }
    }
    if (state.isCreatedMessage?.isNotEmpty() == true) {
        Text(state.isCreatedMessage)
    }
}


private fun getAddress(addressText: String, context: Context): List<Address> {
    val geocoder = Geocoder(context)
    return geocoder.getFromLocationName(addressText, 1)
}