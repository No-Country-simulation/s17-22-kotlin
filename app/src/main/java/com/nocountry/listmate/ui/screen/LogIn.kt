package com.nocountry.listmate.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nocountry.listmate.componentes.TopBar
import androidx.compose.material3.*
import androidx.compose.ui.text.style.TextAlign

@Composable
@Preview
fun LogInPreview(){
    LogIn()

}
@Composable

fun LogIn(){
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xffF0F2F5)

            )

    ){
        TopBar(
            titulo = "Log In",

        )
        Spacer(modifier = Modifier.height(20.dp))
       Column(
           modifier = Modifier.fillMaxHeight(1f).padding(20.dp),
           verticalArrangement = Arrangement.Center,


       ) {
            TextField (
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,  // Línea cuando el campo está enfocado
                    unfocusedIndicatorColor = Color.Transparent // Línea cuando el campo no está enfocado
                ),

                modifier = Modifier
                    .fillMaxWidth(),
                value = "",
                onValueChange ={
//                if (it.length <= 20){
//                    movimientoViewModel.setNombre(it)
//                }
                },
                shape = RoundedCornerShape(15.dp),
                label =  { Text(text = "Email" ) },

                maxLines = 1,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField (
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,  // Línea cuando el campo está enfocado
                    unfocusedIndicatorColor = Color.Transparent // Línea cuando el campo no está enfocado
                ),

                modifier = Modifier
                    .fillMaxWidth(),
                value = "",
                onValueChange ={},
                shape = RoundedCornerShape(15.dp),
                label =  { Text(text = "Password" ) },

                maxLines = 1,
                singleLine = true
            )
           Spacer(modifier = Modifier.height(25.dp))

            HyperlinkText(text = "¿Forgot password?", modifier = Modifier.align(alignment = Alignment.Start)) {  }

            Spacer(modifier = Modifier.height(25.dp))


            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff31628D))
                ,
                shape = RoundedCornerShape(10.dp)
                ,
                onClick = {

                }
            ) {
                Text(
                    text = "Log in",
                    fontSize = 23.sp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "¿New user?")
                Spacer(modifier = Modifier.width(10.dp))
                HyperlinkText(
                    text = "Sign Up",
                    color = Color.Black) {  }

            }
       }


    }
}

@Composable
fun HyperlinkText(
    text: String,
    modifier: Modifier = Modifier,
    color:Color = Color.Gray,
    onClick: (() -> Unit)? = null
){
    val annotatedString = with(AnnotatedString.Builder()) {
        pushStringAnnotation(
            tag = "LINK",
            annotation = text
        )
        append(text)
        pop()
        toAnnotatedString()
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "LINK",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onClick?.invoke()
            }
        },
        style = TextStyle(color = color, textDecoration = TextDecoration.None)
    )
}