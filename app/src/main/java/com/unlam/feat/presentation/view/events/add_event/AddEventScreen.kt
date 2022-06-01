package com.unlam.feat.presentation.view.events.add_event

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.model.Periodicity
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.view.register.RegisterEvent


@Composable
fun AddNewEventScreen(
    navigateToEvents: () -> Unit,
    state: AddEventState,
    onValueChange: (AddEventEvent) -> Unit,
    navigateToHome: () -> Unit,
    createEvent: () -> Unit
) {
    var openMap by remember {
        mutableStateOf(false)
    }

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


                val periodicityList = remember { mutableListOf<Periodicity>() }
                periodicityList.add(Periodicity(0, " "))
                periodicityList.add(Periodicity(1, "Unica Vez"))
                periodicityList.add(Periodicity(2, "Semanal"))
                periodicityList.add(Periodicity(3, "Quincenal"))
                periodicityList.add(Periodicity(4, "Mensual"))

                val periodicityListString = remember {
                    mutableListOf<String>()
                }

                periodicityList.forEach {
                    if (!periodicityListString.contains(it.description)) {
                        periodicityListString.add(it.description)
                    }
                }

                FeatDropDown(
                    label = "Periodicidad",
                    options = periodicityListString,
                    selectedText = { value ->
                        state.periodicityList.forEach {
                            if (it.description == value) onValueChange(
                                AddEventEvent.EnteredPeriodicity(
                                    it.id.toString()
                                )

                            )
                        }
                    }
                )


                FeatTextField(
                    text = state.address,
                    textLabel = "Lugar",
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.GpsFixed,
                            contentDescription = "Ubicacion",
                            modifier = Modifier.clickable {
                                openMap = true
                            },
                            tint = Color.Black
                        )
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

    if (openMap) {
        FeatMap(
            setLocation = {
                onValueChange(
                    AddEventEvent.EnteredLatLong(
                        lat = it.latitude.toString(),
                        long = it.longitude.toString()
                    )
                )

                openMap = false
            }
        )
    }

    if (state.latitude.isNotEmpty() && state.longitude.isNotEmpty()) {
        val address = Geocoder(LocalContext.current).getFromLocation(
            state.latitude.toDouble(),
            state.longitude.toDouble(),
            1
        )
        onValueChange(AddEventEvent.EnteredAddress(address[0].getAddressLine(0)))
    }


    if (state.isCreatedMessage?.isNotEmpty() == true) {
        val title = "Evento creado con exito"
        val description = "Tu evento ha sido creado con exito!!"
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                onValueChange(AddEventEvent.DismissDialog)
                navigateToHome()
            }
        )
    } else if (state.error.isNotBlank()) {
        val title = "Error al crear evento"
        val description =
            "Se produjo un error al crear el evento. Por favor verifique los datos ingresados."
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                onValueChange(AddEventEvent.DismissDialog)
                navigateToHome()
            }
        )
    }
}
