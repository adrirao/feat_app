package com.unlam.feat.presentation.view.edit_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.presentation.component.FeatButtonRounded
import com.unlam.feat.presentation.component.FeatHeader
import com.unlam.feat.presentation.component.FeatSportCard
import com.unlam.feat.presentation.ui.theme.card

@Composable
fun Profile(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit
) {
    var person = state.person;

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
                        if (person != null) {
                            Text(
                                text = person.names,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (person != null) {
                            Text(
                                text = person.lastname,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (person != null) {
                            Text(
                                text = "(" + person.nickname + ")",
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (person != null) {
                            Text(
                                text = person.sex,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (person != null) {
                            Text(
                                text = person.birthDate,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }

            }


            // Direcciones
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
                            text = "Mis direcciones",
                            style = MaterialTheme.typography.h5
                        )
                    }
                    FeatButtonRounded(
                        modifier = Modifier
                            .size(60.dp)
                            .fillMaxWidth(),
                        drawable = R.drawable.check,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                        onClick = {refreshData()},
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                    state.addresses?.forEachIndexed { index, address ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = address.street,
                                //style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = " " + address.number,
                                //style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = ", " + address.town,
                                //style = MaterialTheme.typography.h6
                            )
                        }
                    }

                }

            }

            // Mis Deportes
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
                            text = "Mis intereses deportivos",
                            style = MaterialTheme.typography.h5
                        )
                    }
                    state.players?.forEach { player ->

                        FeatSportCard(
                            sport = player.sport.description,
                            //sportDescription = player.sport.description,
                            idSport = player.sport.id,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            onClickCard = { refreshData() })
                        /*
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                            Text(
                                text = player.sport.description,
                                //style = MaterialTheme.typography.h6
                            )
                    }
                    */
                    }
                }

            }
        }
    }
    }

}