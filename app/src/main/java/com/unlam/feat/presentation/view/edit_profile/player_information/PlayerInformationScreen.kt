package com.unlam.feat.presentation.view.edit_profile.player_information

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.feat.R
import com.unlam.feat.model.player_detail.PlayerDetail
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.ui.theme.card
import com.unlam.feat.presentation.ui.theme.text


@Composable
fun PlayerInformationScreen(
    player: PlayerDetail
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
                                text = "Mis datos deportivos",
                                style = MaterialTheme.typography.h5
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Posici??n",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                                Text(
                                    text = player.position.description,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = MaterialTheme.colors.text,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )

                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Nivel",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = player.level.description,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )

                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Valoraci??n",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = player.valuation.description,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )

                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Habilidades",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Gray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = player.abilities,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = MaterialTheme.colors.text,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )

                        }
                    }
                }
            }
        }
    }
}

