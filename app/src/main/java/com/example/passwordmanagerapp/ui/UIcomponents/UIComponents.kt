package com.example.passwordmanagerapp.ui.UIcomponents

import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanagerapp.CryptoManager
import com.example.passwordmanagerapp.R
import com.example.passwordmanagerapp.database.PasswordEntry
import kotlinx.coroutines.launch

@Composable
fun PasswordBar(onClick:  ()->Unit, accountType: String){
    Button(onClick = onClick,
        modifier = Modifier
            .padding(top = 32.dp)
            .padding(start = 15.dp)
            .height(66.19.dp)
            .width(345.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White),) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$accountType ********",
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Expand key",
                modifier = Modifier.padding(end = 16.dp),
            )
        }
    }


}

@Composable
fun myTextField(labelValue: String): String{
    var textValue by rememberSaveable{
        mutableStateOf("")
    }
    TextField(
        value = textValue,

        onValueChange = { textValue = it },
        label = {
            Text(
                labelValue,
                color = Color.Black.copy(alpha = 0.5f)
            )
        },
        singleLine = true,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .height(55.dp)
            .width(313.dp),
    )
    return  textValue
}

@Composable
fun DetailsTextBar(accountType:String, userName:String, encryptedPassword:String, deleteClick:()->Unit){
   val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(16.dp)
            .height(380.dp)
            .width(375.dp)

    ) {
        Surface {
            Text(text = "Account Details",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp))

        }
        Surface {
           Column(

           ) {
               Text(
                   text = "Account type",
                   fontSize = 16.sp,
                   color = Color.Black.copy(alpha = 0.3f))
               Text(
                   text = accountType,
                   style = TextStyle(
                       fontSize = 24.sp,
                       fontWeight = FontWeight.Bold
                   )

               )
           }
        }
        Surface {
            Column(

            ) {
                Text(
                    text = "Username/Email",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.3f))
                Text(
                    text = userName,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                )
            }
        }
        Surface {
            Column {
                Text(
                    text = "Password",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.3f))
                Text(
                    text = encryptedPassword,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                )
            }
        }
        Surface(modifier = Modifier
            .padding(top = 18.dp)
            .fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(100.dp)
                        .height(44.dp),
                       colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                    Text("Edit",
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
                Button(
                    onClick =  deleteClick,
                    modifier = Modifier
                        .width(110.dp)
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.delete_button_color))
                    ) {
                    Text(
                        "Delete",
                        style = TextStyle(
                            fontSize = 20.sp
                        ))
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UIPreview(){
    DetailsTextBar(accountType = "Google", userName ="amansingh" , encryptedPassword ="123456" ){}
}