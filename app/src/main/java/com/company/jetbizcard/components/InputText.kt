package com.company.jetbizcard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun InputText (
    state: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType = KeyboardType.Number,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(15.dp, 5.dp, 15.dp, 5.dp),
        leadingIcon = {Icon(Icons.Rounded.AttachMoney, contentDescription = "Money icon")},
        value = state.value,
        onValueChange = {state.value = it},
        label = { Text(text = labelId) })
}
