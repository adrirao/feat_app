package com.unlam.feat.presentation.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.unlam.feat.R
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.*
import com.unlam.feat.presentation.view.register.RegisterEvent
import com.unlam.feat.presentation.view.register.RegisterState

@Preview(showSystemUi = true)
@Composable
fun LoginScreen(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    setMessages(viewModel,navController)

    if (state.isAuthenticate) {
        LaunchedEffect(true) {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
        }
    }

    Content(
        viewModel,
        navigateToRegister = {
            navController.popBackStack()
            navController.navigate(Screen.Register.route)
        }
    )

}

@Composable
private fun Content(
    viewModel: LoginViewModel,
    navigateToRegister: () -> Unit
) {
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
                text = stringResource(R.string.welcome_to_feat),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.height(25.dp)
            )
            FeatText(
                text = stringResource(R.string.how_do_you_want_to_continue),
                fontSize = 15.sp,
            )
            FeatTextField(
                text = viewModel.state.value.emailOrPhoneText,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredEmailOrPhone(it))
                },
                textLabel = stringResource(R.string.email_or_numberphone)
            )
            FeatTextField(
                text = viewModel.state.value.passwordText,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                keyboardType = KeyboardType.Password,
                textLabel = stringResource(R.string.password),
                isPasswordVisible = viewModel.state.value.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )
            FeatText(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.forgot_password)
            )
            FeatButton(
                textButton = stringResource(R.string.login),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
                colorText = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                onClick = {
                    viewModel.onEvent(LoginEvent.Login)
                }
            )
            FeatText(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.no_account)
            )
            FeatButton(
                textButton = stringResource(R.string.register),
                colors = ButtonDefaults.buttonColors(Color.White),
                colorText = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.5f)
                    .padding(10.dp)
                    .height(60.dp),
                onClick = {
                    navigateToRegister()
                }
            )
            Divider(
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 50.dp).fillMaxWidth().height(1.dp)
            )
            Box {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FeatButtonRounded(
                        drawable = R.drawable.google,
                        colors = ButtonDefaults.buttonColors(Color(0xFFD35451)),
                    )
                    FeatButtonRounded(
                        drawable = R.drawable.facebook,
                    )
                }
            }
        }
    }
}

@Composable
fun setMessages(viewModel: LoginViewModel,navController: NavController){
    val state = viewModel.state.value



    if (state.passwordError != null) {
        var title: String = stringResource(R.string.error_login)
        var description: String
        when (state.passwordError) {
            LoginState.PasswordError.InvalidPassword -> {
                description =
                    stringResource(R.string.error_invalid_password)
            }
            LoginState.PasswordError.InputTooShort -> {
                description = stringResource(R.string.error_input_to_shoort)
            }
            LoginState.PasswordError.FieldEmpty -> {
                description = stringResource(R.string.error_field_empty)
            }
        }
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                viewModel.onEvent(LoginEvent.DismissDialog)
            }
        )
    }

    if (state.emailError != null) {
        var title: String = stringResource(R.string.error_email)
        var description: String
        when (state.emailError) {
            LoginState.EmailError.FieldEmpty -> {
                description =
                    stringResource(R.string.error_field_empty)
            }
            LoginState.EmailError.InvalidEmail -> {
                description = stringResource(R.string.error_invalid_email)
            }
        }
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                viewModel.onEvent(LoginEvent.DismissDialog)
            }
        )
    }

    if(state.authenticateError != null){
        var title: String = stringResource(R.string.error_auth)
        var description: String
        when(state.authenticateError){
            is LoginState.AuthenticateError.InvalidCredentials -> {
                description = stringResource(R.string.error_invalid_credential)
            }
            is LoginState.AuthenticateError.UserNotExist -> {
                description = stringResource(R.string.error_user_not_exist)
            }
            is LoginState.AuthenticateError.VerifyEmail -> {
                description = stringResource(R.string.error_verify_email)
            }
        }
        FeatAlertDialog(
            title = title,
            descriptionContent = description,
            onDismiss = {
                viewModel.onEvent(LoginEvent.DismissDialog)
            }
        )
    }
}