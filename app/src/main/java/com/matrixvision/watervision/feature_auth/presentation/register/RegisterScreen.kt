package com.matrixvision.watervision.feature_auth.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.matrixvision.watervision.R
import com.matrixvision.watervision.core.presentation.component.StandardTextField
import com.matrixvision.watervision.core.presentation.util.AuthError
import com.matrixvision.watervision.core.presentation.util.UiEvent
import com.matrixvision.watervision.core.presentation.util.asString
import com.matrixvision.watervision.core.util.Constants
import com.matrixvision.watervision.ui.theme.SpaceMedium
import com.matrixvision.watervision.ui.theme.SpaceSmall
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    onPopBackStack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()

) {

    val usernameState = viewModel.usernameState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val registerState = viewModel.registerState.value
    val acceptedTermsState = viewModel.acceptTerms.value
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.onRegister.collect {
            onPopBackStack()
        }
    }

    LaunchedEffect(key1 = keyboardController){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar ->{
                    keyboardController?.hide()
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )

                }
            }
        }
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp),
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = stringResource(id = R.string.register),
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(SpaceMedium))

        StandardTextField(
            text = emailState.text,
            onValueChange ={
                viewModel.onEvent(RegisterEvent.EnteredEmail(it))
            },
            error = when(emailState.error){
                is AuthError.FieldEmpty ->{
                    stringResource(id = R.string.error_field_empty)
                }
                is AuthError.InvalidEmail ->{
                    stringResource(id = R.string.not_a_valid_email)
                }
                else -> ""
            },
            keyboardType = KeyboardType.Email,
            hint = stringResource(id = R.string.email)
        )

        Spacer(modifier = Modifier.height(SpaceMedium))

        StandardTextField(
            text = usernameState.text,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.EnteredUsername(it))
            },
            error = when(usernameState.error){
                is AuthError.FieldEmpty ->{
                    stringResource(id = R.string.error_field_empty)
                }
                is AuthError.InputTooShort ->{
                    stringResource(id = R.string.input_too_short, Constants.MIN_USERNAME_LENGTH)
                }
                else -> ""
            },
            hint = stringResource(id = R.string.username)

        )
        Spacer(modifier = Modifier.height(SpaceMedium))

        StandardTextField(
            text = passwordState.text,
            onValueChange = {
                viewModel.onEvent(RegisterEvent.EnteredPassword(it))
            },
            hint = stringResource(id = R.string.password_hint),
            keyboardType = KeyboardType.Password,
            error = when (passwordState.error) {
                is AuthError.FieldEmpty -> {
                    stringResource(id = R.string.error_field_empty)
                }
                is AuthError.InputTooShort -> {
                    stringResource(id = R.string.input_too_short, Constants.MIN_PASSWORD_LENGTH)
                }
                is AuthError.InvalidPassword -> {
                    stringResource(id = R.string.invalid_password)
                }
                else -> ""
            },
            isPasswordVisible = passwordState.isPasswordVisible,
            onPasswordToggleClick = {
                viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)
            }

        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = acceptedTermsState.isChecked,
                onCheckedChange = {
                    viewModel.onEvent(RegisterEvent.AcceptTermsAndCondition(it))
                },
               
            )
            Spacer(modifier = Modifier.width(SpaceSmall))
            Text(
                text = stringResource(id = R.string.accept_terms),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

        }
        if (acceptedTermsState.error != null){
            Text(
                text = stringResource(id = R.string.accept_terms_error),
                color = MaterialTheme.colors.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }

        Button(
            onClick = {
                viewModel.onEvent(RegisterEvent.Register)
            },
            enabled = !registerState.isLoading,
            modifier = Modifier
                .align(Alignment.End)

        ) {
            Text(
                text = stringResource(id = R.string.register),
                color = MaterialTheme.colors.onPrimary
            )
        }
        if (registerState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(CenterHorizontally)
            )
        }

    }
}

