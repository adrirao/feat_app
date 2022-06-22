package com.unlam.feat.presentation.view.edit_profile.personal_information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unlam.feat.R
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent
import com.unlam.feat.presentation.view.edit_profile.ProfileEvent
import com.unlam.feat.presentation.view.edit_profile.ProfileState
import kotlin.math.roundToInt

@Composable
fun PersonalInformation(
    navController: NavHostController,
    state: EditPersonalInformationState,
    onValueChange: (EditProfilePersonalInformationEvent) -> Unit,
    updatePerson: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FeatHeader(text = stringResource(R.string.title_my_profile))
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
            {

                Card(
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    elevation = 6.dp,
                    backgroundColor = MaterialTheme.colors.card,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    )
                    {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Mis datos personales",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FeatTextField(
                                text = state.names,
                                textLabel = "Nombres",
                                onValueChange = { onValueChange(EditProfilePersonalInformationEvent.EnteredNames(it))}
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FeatTextField(
                                text = state.lastname,
                                textLabel = "Apellido",
                                onValueChange = { onValueChange(EditProfilePersonalInformationEvent.EnteredLastNames(it))}
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FeatTextField(
                                text = state.nickname,
                                textLabel = "Apodo",
                                onValueChange = { onValueChange(EditProfilePersonalInformationEvent.EnteredNickname(it))}
                            )
                        }
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
                                    .fillMaxWidth(), text = "Sexo"
                            )
                            val sexs = listOf("", "Hombre", "Mujer", "Otro")
                            val currentSelection = remember { mutableStateOf(sexs.first()) }
                            Row {
                                FeatRadioGroup(
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp),
                                    items = sexs,
                                    selection = currentSelection.value,
                                    onItemClick = { clickedItem ->
                                        currentSelection.value = clickedItem
                                        onValueChange(EditProfilePersonalInformationEvent.EnteredSex(clickedItem))
                                    }
                                )
                            }


                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FeatDatePicker(
                                date = state.birth_date,
                                label = "Fecha de nacimiento",
                                onValueChange = { onValueChange(EditProfilePersonalInformationEvent.EnteredBirthDate(it))},
                                titlePicker = stringResource(R.string.text_select_date)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                                FeatButton(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .height(60.dp),
                                    textButton = "Modificar datos",
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                                    colorText = MaterialTheme.colors.primary,
                                    textAlign = TextAlign.Center,
                                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                                    onClick = {
                                        updatePerson()
                                    }
                                )
                        }
                    }

                }
            }
        }
    }

}