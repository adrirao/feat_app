package com.unlam.feat.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun FeatTimePicker(
    modifier: Modifier = Modifier,
    time: LocalTime?,
    onValueChange: (LocalTime) -> Unit,
    label: String = "",
    titlePicker:String = "Default"

) {
    val dialogState = rememberMaterialDialogState()
    FeatTextField(
        text = time?.toString()?.format(
            DateTimeFormatter.ofPattern(
                "h:mm"
            )
        )
            ?: " ",
        onValueChange = {},
        modifier = modifier
            .fillMaxWidth()
            .clickable { dialogState.show() },
        enabled = false,
        textLabel = label

    )
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Aceptar")
            negativeButton("Cancelar")
        },

        ) {
        timepicker(
            title = titlePicker
        ) { time ->
            onValueChange(time)

        }
    }
}