package com.nocountry.listmate.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nocountry.listmate.R
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.singleton.GlobalUser
import com.nocountry.listmate.ui.components.Input
import com.nocountry.listmate.ui.screen.LoginScreen
import org.w3c.dom.Text

@Composable
@Preview
fun ProfileScreenPreview(){

    ProfileScreen(rememberNavController())

}
@Composable
fun  ProfileScreen(navHostController: NavHostController){
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF0F2F5)),

        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopProfile(){navHostController.popBackStack()}
        Spacer(modifier = Modifier.height(25.dp))

        Box(

            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
        ) {
            Image(
                painter = painterResource(R.drawable.profile_icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

           Text(text = GlobalUser.name)
            Spacer(modifier = Modifier.height(15.dp))
           Text(text = GlobalUser.lastName )

            Input(label = "Name", value = name){
                name = it
            }
            Input(label = "Lastname", value = lastname){
               lastname = it
            }

        }
        Button(
            onClick = {  },
            modifier = Modifier.fillMaxWidth(.7f)
            ) {
            Text(text = "Save")

        }

    }


}
@Composable
fun TopProfile(
    onBack: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable { onBack() }
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Profile",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
 @Composable
 fun TextProfile(

 ){
     Column() {

     }

 }

}
