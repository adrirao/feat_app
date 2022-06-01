package com.unlam.feat.presentation.view.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.FeatAlertDialog
import com.unlam.feat.presentation.component.FeatButton
import com.unlam.feat.presentation.component.FeatText
import com.unlam.feat.presentation.component.FeatTextField

@Composable
fun Register(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    setMessages(viewModel, navController)
    Content(
        viewModel,
        navigateToLogin = {
            navController.popBackStack()
            navController.navigate(Screen.Login.route)
        }
    )
}

@Composable
private fun Content(viewModel: RegisterViewModel, navigateToLogin: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.logotipo),
                contentDescription = stringResource(R.string.feat_logo)
            )
            FeatText(
                text = "A continuacion te pedimos algunos datos para que formes parte de nuetro equipo.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            FeatTextField(
                text = viewModel.state.value.emailText,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredEmail(it))
                },
                textLabel = "Correo electronico"
            )
            FeatTextField(
                text = viewModel.state.value.confirmationEmailText,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredConfirmEmail(it))
                },
                textLabel = "Vuelve a introducir tu correo electronico"
            )
            Spacer(
                modifier = Modifier.height(25.dp)
            )
            FeatTextField(
                text = viewModel.state.value.passwordText,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredPassword(it))
                },
                textLabel = "Contraseña",
                keyboardType = KeyboardType.Password,
                isPasswordVisible = viewModel.state.value.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)
                }
            )
            FeatTextField(
                text = viewModel.state.value.confirmationPasswordText,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredConfirmPassword(it))
                },
                textLabel = "Vuelve a introducir tu contraseña",
                keyboardType = KeyboardType.Password,
                isPasswordVisible = viewModel.state.value.isConfirmPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(RegisterEvent.ToggleConfirmPasswordVisibility)
                }
            )
            FeatButton(
                textButton = stringResource(R.string.register),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                colorText = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                onClick = {
                    viewModel.onEvent(RegisterEvent.Register)
                }
            )
        }
        Text(
            text = buildAnnotatedString {
                append("Ya tenes cuenta?")
                append(" ")
                val signUpText = "Inicia sesion."
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navigateToLogin()
                }
        )
    }
}

@Composable
fun setMessages(viewModel: RegisterViewModel, navController: NavController) {
    val state = viewModel.state.value
    if (state.registrationMessage != null) {
        var title: String
        var description: String
        var isRegistered: Boolean = false
        when (state.registrationMessage) {
            RegisterState.RegisterMessage.UserCreated -> {
                title = "Regristro con exito!"
                description =
                    "Por favor verificar tu correo electronico e ingresar a la confirmacion para darse de alta."
                isRegistered = true
            }
            RegisterState.RegisterMessage.AlreadyExistUser -> {
                title = "Registro con error!"
                description = "El usuario ya se encuentra creado."
            }
        }
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                viewModel.onEvent(RegisterEvent.DismissDialog)
                if (isRegistered) {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            }
        )
    }

    if (state.passwordError != null) {
        var title: String = "Error Registro"
        var description: String
        when (state.passwordError) {
            RegisterState.PasswordError.InvalidPassword -> {
                description =
                    "Contraseña invalida."
            }
            RegisterState.PasswordError.InputTooShort -> {
                description = "Contaseña demasiado corta."
            }
            RegisterState.PasswordError.FieldEmpty -> {
                description = "Campos de contraseña incompletos."
            }
        }
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                viewModel.onEvent(RegisterEvent.DismissDialog)
            }
        )
    }
    if (state.emailError != null) {
        var title: String = "Error Email"
        var description: String
        when (state.emailError) {
            RegisterState.EmailError.FieldEmpty -> {
                description =
                    "Email vacio."
            }
            RegisterState.EmailError.InvalidEmail -> {
                description = "Email invalido."
            }
        }
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                viewModel.onEvent(RegisterEvent.DismissDialog)
            }
        )
    }
}