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
import com.unlam.feat.R
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.view.edit_profile.ProfileEvent
import com.unlam.feat.presentation.view.edit_profile.ProfileState
import kotlin.math.roundToInt

@Composable
fun PersonalInformation(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    onValueChange: (ProfileEvent) -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
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
                                onValueChange = { onValueChange(ProfileEvent.EnteredNames(it))}
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FeatTextField(
                                text = state.lastname,
                                textLabel = "Apellido",
                                onValueChange = { onValueChange(ProfileEvent.EnteredLastNames(it))}
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FeatTextField(
                                text = state.nickname,
                                textLabel = "Apodo",
                                onValueChange = { onValueChange(ProfileEvent.EnteredNickname(it))}
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Sexo",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Masculino",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Black,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            FeatDatePicker(
                                date = state.birth_date,
                                label = "Fecha de nacimiento",
                                onValueChange = { onValueChange(ProfileEvent.EnteredBirthDate(it))},
                                titlePicker = stringResource(R.string.text_select_date)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            if (state.buttonUpdatePersonalInformation) {
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

}