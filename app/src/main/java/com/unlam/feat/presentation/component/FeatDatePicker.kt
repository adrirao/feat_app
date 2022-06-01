package com.unlam.feat.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun FeatDatePicker(
    date: LocalDate?,
    onValueChange: (LocalDate) -> Unit,
    label:String = "",
    titlePicker:String = "Default"

){
    val dialogState = rememberMaterialDialogState()
    FeatTextField(
        text = date?.toString()?.format(
            DateTimeFormatter.ofPattern(
                "dd LLLL yyyy"
            )
        )
            ?: " ",
        onValueChange = {},
        modifier = Modifier
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
        datepicker(
            title = titlePicker
        ) { date ->
            onValueChange(date)

        }
    }
}