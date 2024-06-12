package com.example.passwordmanagerapp.screen

import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.passwordmanagerapp.database.DatabaseOperation
import com.example.passwordmanagerapp.ui.UIcomponents.myTextField
import io.realm.kotlin.Realm
import kotlinx.coroutines.launch

class SavePasswordScreen(val realm: Realm) {
    var accountType:String = ""
    var userName:String = ""
    var password:String = ""
 val savePassword: DatabaseOperation = DatabaseOperation()


    @Composable
   fun SavePassword(){
        val coroutineScope = rememberCoroutineScope()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(333.dp)
                .width(375.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
           accountType =  myTextField(labelValue = "Account Name")
            userName = myTextField(labelValue = "Username/Email")
            password = myTextField(labelValue = "Password")
            Button(
                onClick = {
                    coroutineScope.launch {
                        savePassword.savePassword(realm, accountType,userName, password)
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(300.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)

            ) {
                Text("Add New Account")
            }
        }
    }



}
