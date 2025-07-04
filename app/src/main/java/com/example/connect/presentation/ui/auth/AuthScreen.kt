package com.example.connect.presentation.ui.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.connect.presentation.ui.components.Input

@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getServerPublicKey()
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
            value = viewModel.state.mobileNumber,
            onValueChange = { viewModel.onMobileNumberChange(it) },
            placeholder = "Mobile Number"
        )

        Spacer(modifier = Modifier.height(16.dp))

        if(viewModel.state.isOTPSent) {
            Input(
                value = viewModel.state.otp,
                onValueChange = { viewModel.onOTPChange(it) },
                placeholder = "OTP"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, Color.Gray)
                .padding(top = 16.dp)
                .clickable(enabled = (!viewModel.state.isLoading)) {
                    when(viewModel.state.stateOfAuth){
                        StateOfAuth.MOBILE_NUMBER -> viewModel.onRequestOTP()
                        StateOfAuth.OTP -> viewModel.onVerifyOTP()
                        null -> {}
                    }
                },
            contentAlignment = Alignment.Center
        ){
            BasicText(
                text = when(viewModel.state.stateOfAuth){
                    StateOfAuth.MOBILE_NUMBER -> if (viewModel.state.isLoading) "Sending OTP..." else "Send OTP"
                    StateOfAuth.OTP -> if (viewModel.state.isLoading) "Verifying OTP..." else "Verify OTP"
                    else -> ""
                },
                style = TextStyle(fontSize = 16.sp, color = Color.Black)
            )
        }

        viewModel.state.error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            BasicText(
                text = it,
                style = TextStyle(fontSize = 16.sp, color = Color.Red)
            )
        }
    }
}