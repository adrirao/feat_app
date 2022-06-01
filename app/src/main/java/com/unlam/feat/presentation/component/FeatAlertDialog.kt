package com.unlam.feat.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

//Custom AlertDialog
@Composable
fun FeatAlertDialog(
    title:String,
    descriptionContent: String,
    onDismiss: () -> Unit,
    onClick: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            securePolicy = SecureFlagPolicy.Inherit
        ),
        title = {
            Text(title, fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = descriptionContent)
        },
        buttons = {
//            OutlinedButton(
//                shape = RoundedCornerShape(percent = 30),
//                onClick = onDismiss,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(text = "Cancel")
//            }
//            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                shape = RoundedCornerShape(percent = 30),
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color(0xff8BC34A),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "OK")
            }

            if(onClick != null) {
                OutlinedButton(
                    shape = RoundedCornerShape(percent = 30),
                    onClick = onClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color(0xff8BC34A),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Abrir Config")
                }
            }
        }
    )
}

