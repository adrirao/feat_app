package com.unlam.feat.presentation.view.events.add_event

import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import com.unlam.feat.model.Periodicity
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.component.map.FeatMap
import com.unlam.feat.presentation.ui.theme.card


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
            Column {
                FeatTextField(
                    text = state.name,
                    textLabel = stringResource(R.string.input_name_event),
                    onValueChange = { onValueChange(AddEventEvent.EnteredName(it)) }
                )

                FeatDatePicker(
                    date = state.date,
                    label = stringResource(R.string.text_day),
                    onValueChange = { onValueChange(AddEventEvent.EnteredDay(it)) },
                    titlePicker = stringResource(R.string.text_select_date)
                )

                FeatTimePicker(
                    time = state.startTime,
                    label = stringResource(R.string.text_start_time),
                    onValueChange = { onValueChange(AddEventEvent.EnteredStartTime(it)) },
                    titlePicker = stringResource(R.string.text_select_start_time)
                )

                FeatTimePicker(
                    time = state.endTime,
                    label = stringResource(R.string.text_ending_time),
                    onValueChange = { onValueChange(AddEventEvent.EnteredEndTime(it)) },
                    titlePicker = stringResource(R.string.text_select_ending_time)
                )

                val periodicityList = mutableListOf<String>()
                state.periodicityList.map {
                    periodicityList.add(it.description)
                }

                FeatDropDown(
                    label = stringResource(R.string.text_priodicity),
                    options = periodicityList,
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
                    textLabel = stringResource(R.string.text_location),
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.GpsFixed,
                            contentDescription = stringResource(R.string.text_location),
                            modifier = Modifier.clickable {
                                openMap = true
                            },
                            tint = Color.Black
                        )
                    }
                )
                FeatTextField(
                    text = state.description,
                    textLabel = stringResource(R.string.text_description),
                    onValueChange = { onValueChange(AddEventEvent.EnteredDescription(it)) })
                Row {
                    FeatButton(
                        textButton = stringResource(R.string.text_cancel),
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
                        textButton = stringResource(R.string.text_create),
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
        val title = stringResource(R.string.text_event_created)
        val description = stringResource(R.string.text_description_event_created)
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                onValueChange(AddEventEvent.DismissDialog)
                navigateToHome()
            }
        )
    } else if (state.error.isNotBlank()) {
        val title = stringResource(R.string.error_created_event)
        val description =
            stringResource(R.string.error_description_event_created)
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
