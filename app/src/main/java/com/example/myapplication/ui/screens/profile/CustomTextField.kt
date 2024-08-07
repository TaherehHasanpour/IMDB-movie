package com.example.myapplication.ui.screens.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.imdbYellow
import com.example.myapplication.ui.theme.darkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: @Composable () -> Unit,
    value: MutableState<String>,
    onValueCallback: (String) -> Unit,
    isPasswordTextField: Boolean = false,
) {


    OutlinedTextField(
        value = value.value,
        onValueChange = {
            value.value = it
            onValueCallback(value.value)
        },
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = MaterialTheme.colorScheme.darkText,
            focusedBorderColor = MaterialTheme.colorScheme.imdbYellow,
            cursorColor = MaterialTheme.colorScheme.imdbYellow,
            errorBorderColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = MaterialTheme.colorScheme.imdbYellow,
            unfocusedLabelColor = MaterialTheme.colorScheme.darkText,
        ),
        shape = Shapes().medium,
        maxLines = 1,
        label = {
            label()
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        visualTransformation = if (isPasswordTextField) PasswordVisualTransformation()
        else VisualTransformation.None

    )

}