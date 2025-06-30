package com.example.connect.ui.screen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.connect.view_model.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), onLoginSuccess: () -> Unit) {

    LaunchedEffect(viewModel.error) {
        Log.d("LoginScreen", "Error: $viewModel.error")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = "Login",
            style = TextStyle(fontSize = 24.sp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Input(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            placeholder = "Email"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Input(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            placeholder = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, Color.Gray)
                .padding(top = 16.dp)
                .clickable(enabled = (!viewModel.loading)) {
                    viewModel.login(onLoginSuccess)
                },
            contentAlignment = Alignment.Center
        ){
            BasicText(
                text = if (viewModel.loading) "Logging in..." else "Login",
                style = TextStyle(fontSize = 16.sp, color = Color.Black)
            )
        }

        viewModel.error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            BasicText(
                text = it,
                style = TextStyle(fontSize = 16.sp, color = Color.Red)
            )
        }
    }
}

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, Color.Gray)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
        )

        if (value.isEmpty()) {
            BasicText(
                text = placeholder,
                style = TextStyle(color = Color.Gray, fontSize = 16.sp)
            )
        }
    }
}