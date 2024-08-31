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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nocountry.listmate.R
import com.nocountry.listmate.componentes.TopBar
import com.nocountry.listmate.ui.navigation.Destinations

@Composable
@Preview
fun LogInPreview(){
    LoginScreen(rememberNavController())

}
@Composable

fun LoginScreen(navHostController: NavHostController){
    var email by remember { mutableStateOf("maria@mail.com")}
    var password by remember { mutableStateOf("8056161") }
    var passwordVisible by rememberSaveable { mutableStateOf(true) }
    //var displayAlert by remember { mutableStateOf(false) }

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
           modifier = Modifier
               .fillMaxHeight(1f)
               .padding(20.dp),
           verticalArrangement = Arrangement.Center,


       ) {
            TextField (
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,  // Línea cuando el campo está enfocado
                    unfocusedIndicatorColor = Color.Transparent // Línea cuando el campo no está enfocado
                ),

                modifier = Modifier
                    .fillMaxWidth(),
                value = email,
                onValueChange ={email = it
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
                value = password,
                onValueChange ={password = it},
                shape = RoundedCornerShape(15.dp),
                label =  { Text(text = "Password" ) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                maxLines = 1,
                singleLine = true,
                visualTransformation =
                if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    if (password.isNotBlank()) {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            val icon = if(passwordVisible) R.drawable.eye_slash else R.drawable.eye

                            Icon(
                                painter = painterResource(id = icon),
                                modifier = Modifier.width(15.dp),
                                contentDescription = ""
                            )
                        }
                    }
                }
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
                    if (email.isNotBlank() && password.isNotBlank()){
                        navHostController.navigate(Destinations.HOME)
                    }

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