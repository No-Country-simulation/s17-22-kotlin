package com.nocountry.listmate.ui.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.componentes.TopBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.nocountry.listmate.ui.components.Input

@Composable
@Preview
fun SignUpPreview(){
    SignUpScreen(rememberNavController())

}
@Composable

fun SignUpScreen(
    navHostController: NavHostController
){
    var username by remember { mutableStateOf("sara@mail.com") }
    var password by remember { mutableStateOf("123456") }
    var passwordRepeat by remember { mutableStateOf("123456") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xffF0F2F5)

            )

    ){
        TopBar(titulo = "Sig up" )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Input(label = "Name", value =""){
//                if (it.length <= 20){
//                    movimientoViewModel.setNombre(it)
//                }
            }
            Input(label = "Lastname", value =""){
//                if (it.length <= 20){
//                    movimientoViewModel.setNombre(it)
//                }
            }
            Input(label = "Email", value = username){
                username = it
//                if (it.length <= 20){
//                    movimientoViewModel.setNombre(it)
//                }
            }
            Input(label = "Password", value =password){
                password = it
//                if (it.length <= 20){
//                    movimientoViewModel.setNombre(it)
//                }
            }
            Input(label = "Repeat Passsword", value = passwordRepeat){
                passwordRepeat = it
//                if (it.length <= 20){
//                    movimientoViewModel.setNombre(it)
//                }
            }
            Spacer(modifier = Modifier.height(25.dp))

            HyperlinkText(text = "¿Forgot password?", modifier = Modifier.align(alignment = Alignment.Start)) {  }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff31628D))
                ,
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if(username.isNotBlank()
                        && password.isNotBlank()
                        && passwordRepeat.isNotBlank()
                        && password == passwordRepeat
                    ){
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(username , password)
                            .addOnCompleteListener{
                                if(it.isSuccessful){
//                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
//                                    val user = User(
//                                        name = name,
//                                        lastName = lastname,
//                                        birthDate = birthdate,
//                                        phone = phone,
//                                        e_mail = e_mail,
//                                        address = address,
//                                        uid = FirebaseAuth.getInstance().currentUser?.uid?:""
//                                    )
//
//                                    UserManager().addUser(user)
//
//                                    navigation.navigate(MainDestinations.HOME_ROUTE)
                                }
                                else{
//                                    displayAlert = true
                                }
                            }
                    }
                }
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,

                    )
            }

        }


    }
}

