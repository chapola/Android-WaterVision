package com.matrixvision.watervision.feature_auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.matrixvision.watervision.R
import com.matrixvision.watervision.core.presentation.component.StandardTextField
import com.matrixvision.watervision.core.presentation.util.AuthError
import com.matrixvision.watervision.core.presentation.util.UiEvent
import com.matrixvision.watervision.core.presentation.util.asString
import com.matrixvision.watervision.core.util.Screens
import com.matrixvision.watervision.ui.theme.SpaceMedium
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    onLogin: () -> Unit={},
    viewModel: LoginViewModel = hiltViewModel()
){

    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val loginState = viewModel.loginState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest {event ->
            when(event){
                is UiEvent.ShowSnackbar ->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context = context)
                    )
                }
                is UiEvent.OnLogin ->{

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {

            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextField(
                text = emailState.text,
                onValueChange ={
                    viewModel.onEvent(LoginEvent.EnteredEmail(it))
                },
                keyboardType = KeyboardType.Email,
               hint =  stringResource(id = R.string.login_hint),
                error = when(emailState.error){
                    is AuthError.FieldEmpty ->{
                        stringResource(id = R.string.error_field_empty)
                    }
                    else -> ""
                }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextField(
                text = passwordState.text,
                onValueChange = {
                                viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                hint = stringResource(id = R.string.password_hint),
                keyboardType = KeyboardType.Password,
                error = when(passwordState.error){
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                isPasswordVisible = loginState.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }

            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            Button(
                onClick = {
                    viewModel.onEvent(LoginEvent.Login)
                },
                enabled= !loginState.isLoading,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
            }
            if (loginState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable(!loginState.isLoading) {
                    onNavigate(Screens.RegisterScreen.route)
                }


        )
    }

}