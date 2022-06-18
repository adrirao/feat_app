package com.unlam.feat.presentation.view.edit_profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.unlam.feat.R
import com.unlam.feat.presentation.component.FeatHeader

@Composable
fun Profile(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit
) {
    var person = state.person;
    var players = state.players;

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        FeatHeader(text = stringResource(R.string.title_my_profile))
        Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(
                    bottom = 6.dp,
                    top = 6.dp,
                )
                .fillMaxWidth(),
            elevation = 8.dp,
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
                            text = "("+person.nickname+")",
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
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(
                    bottom = 6.dp,
                    top = 6.dp,
                )
                .fillMaxWidth(),
            elevation = 8.dp,
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
                ) {
                        Text(
                            text = "Mis direcciones",
                            style = MaterialTheme.typography.h5
                        )
                }

                state.addresses?.forEachIndexed { index, address ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                            Text(
                                text = address.street,
                                style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = " "+ address.number,
                                style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = ", "+ address.town,
                                style = MaterialTheme.typography.h6
                            )
                    }
                }
            }

        }

        // Mis Deportes
        Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(
                    bottom = 6.dp,
                    top = 6.dp,
                )
                .fillMaxWidth(),
            elevation = 8.dp,
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
                ) {
                    Text(
                        text = "Mis intereses deportivos",
                        style = MaterialTheme.typography.h5
                    )
                }

                state.players?.forEachIndexed { index, player ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                            Text(
                                text = player.sport.description,
                                style = MaterialTheme.typography.h6
                            )
                    }
                }
            }

        }
    }

}