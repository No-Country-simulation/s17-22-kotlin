package com.nocountry.listmate.ui.screens.register

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.nocountry.listmate.data.UsuarioManager
import com.nocountry.listmate.data.local.SettingsDataStore
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.ui.components.Input
import com.nocountry.listmate.ui.components.TopBar
import com.nocountry.listmate.ui.navigation.Destinations
import com.nocountry.listmate.ui.screens.login.HyperlinkText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@Composable
//@Preview
//fun SignUpPreview() {
//    SignUpScreen(rememberNavController())
//
//}

@Composable

fun SignUpScreen(
    navHostController: NavHostController,
    settingsDataStore: SettingsDataStore,
    context: Context
) {
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }

    var displayAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xffF0F2F5)

            )

    ) {
        TopBar(titulo = "Sig up")
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Input(label = "Name", value = name) {
                name = it
            }
            Input(label = "Lastname", value = lastname) {
                lastname = it
            }
            Input(label = "Email", value = username) {
                username = it
            }
            Input(label = "Password", value = password, isPassword = true) {
                password = it
            }
            Input(label = "Repeat Passsword", value = passwordRepeat, isPassword = true) {
                passwordRepeat = it
            }
            Spacer(modifier = Modifier.height(25.dp))

            HyperlinkText(
                text = "¿Forgot password?",
                modifier = Modifier.align(alignment = Alignment.Start)
            ) { }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff31628D)),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if (username.isNotBlank()
                        && password.isNotBlank()
                        && passwordRepeat.isNotBlank()
                        && password == passwordRepeat
                    ) {
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener {
                                    task ->
                                if (task.isSuccessful) {
                                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                                    if (userId != null) {
                                        // Guardar el ID del usuario en DataStore
                                        CoroutineScope(Dispatchers.IO).launch {
                                            settingsDataStore.saveUserId(userId)
                                            Log.d("DataStore", "UserId saved: $userId")
                                        }

                                        // Crear usuario en la base de datos de la aplicación
                                        val user = User(
                                            name = name,
                                            lastName = lastname,
                                            email = username,
                                            uid = userId
                                        )
                                        UsuarioManager().guardarUsuario(user)
                                        // Navegar a la pantalla principal
                                        navHostController.navigate(Destinations.HOME)
                                    }
                                } else {
                                    displayAlert = true
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


    if (displayAlert) {
        AlertDialog(
            title = {
                Text(text = "No se pudo registrar")
            },
            text = {
                Text(text = "No se pudo registrar el usuario. Intente en unos minutos.")
            },
            onDismissRequest = {
            },
            confirmButton = {
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        displayAlert = false
                    }
                ) {
                    Text("Entendido")
                }
            }
        )
    }
}

