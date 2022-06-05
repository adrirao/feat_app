package com.unlam.feat.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import java.time.LocalTime

@Composable
fun FeatAvailabilityCheckBoxPickerTime(
    checked: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit,
    label: String = "Day",
    starTime: LocalTime?,
    endTime: LocalTime?,
    onValueChangeStartTime: (LocalTime?) -> Unit,
    onValueChangeEndTime: (LocalTime?) -> Unit
) {


    FeatLabelledCheckbox(
        modifier = Modifier.fillMaxWidth(),
        checked = checked.value,
        onCheckedChange = { onCheckedChange(it) },
        label = label
    )

    Row(modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically

    ) {
        if (checked.value) {

            FeatTimePicker(
                modifier = Modifier.weight(0.5f),
                time = starTime,
                label = stringResource(R.string.text_start_time),
                onValueChange = { onValueChangeStartTime(it) },
                titlePicker = stringResource(R.string.text_select_start_time)
            )

            FeatTimePicker(
                modifier = Modifier.weight(0.5f),
                time = endTime,
                label = stringResource(R.string.text_ending_time),
                onValueChange = { onValueChangeEndTime(it) },
                titlePicker = stringResource(R.string.text_select_ending_time)
            )

        }else{
            onValueChangeStartTime(null)
            onValueChangeEndTime(null)

        }
    }


}