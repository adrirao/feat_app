package com.unlam.feat.presentation.view.events.add_event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.presentation.component.FeatButton
import com.unlam.feat.presentation.component.FeatHeader
import com.unlam.feat.presentation.component.FeatTextField
import com.unlam.feat.presentation.ui.theme.Shapes
import com.unlam.feat.presentation.ui.theme.SpaceMedium
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text

@Composable
fun AddNewEventScreen(
    navigateToEvents: () -> Unit
) {
    Column {
        FeatHeader("Creacion Evento")
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(MaterialTheme.colors.card)
        ) {
            Column(
            ) {
                FeatTextField(
                    text = "",
                    textLabel = stringResource(R.string.input_name_event),
                    onValueChange = {}
                )
                FeatTextField(
                    text = "",
                    textLabel = "Dia",
                    onValueChange = {}
                )
                FeatTextField(
                    text = "",
                    textLabel = "Horario",
                    onValueChange = {}
                )
                FeatTextField(
                    text = "",
                    textLabel = "Perioricidad",
                    onValueChange = {}
                )
                FeatTextField(
                    text = "",
                    textLabel = "Lugar",
                    onValueChange = {}
                )
                FeatTextField(
                    text = "",
                    textLabel = "Descripcion",
                    onValueChange = {}
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
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
                    )
                }
            }

        }
    }
}