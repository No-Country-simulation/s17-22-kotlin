package com.nocountry.listmate.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Input(
    label: String,
    value: String,
    onValueChange: ((String) -> Unit)? = null
){
    Spacer(modifier = Modifier.height(16.dp))
    TextField (
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange ={ onValueChange?.invoke(it) },
        shape = RoundedCornerShape(15.dp),
        label =  { Text(text = label ) },
        maxLines = 1,
        singleLine = true
    )
}