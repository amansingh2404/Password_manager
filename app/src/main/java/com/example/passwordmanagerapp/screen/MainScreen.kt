package com.example.passwordmanagerapp.screen

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanagerapp.CryptoManager
import com.example.passwordmanagerapp.database.PasswordEntry
import com.example.passwordmanagerapp.ui.UIcomponents.DetailsTextBar
import com.example.passwordmanagerapp.ui.UIcomponents.PasswordBar
import com.example.passwordmanagerapp.viewmodel.PasswordDetailsViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainScreen():Application() {

     companion object{
         private val passwordConfiguration = RealmConfiguration.create(schema = setOf(PasswordEntry::class))
         var realm = Realm.open(
             passwordConfiguration
         )
     }


   @Composable
   fun MainScreenUI(){

     val  passwordViewModel = PasswordDetailsViewModel()
        val decrypt = CryptoManager()
       val savePasswordScreen = SavePasswordScreen(realm)

       var showDialog by remember {
           mutableStateOf(false)
       }
       var showDetailDialog by remember {
           mutableStateOf(false)
       }

       val uiState by passwordViewModel.uiState.collectAsState()
       Column(
           modifier = Modifier
               .fillMaxSize(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Box(
               modifier = Modifier
                   .fillMaxSize()
           ) {
               LazyColumn(
                   modifier = Modifier
                       .padding(top = 32.dp)
               ) {
                   items(uiState.passwordEntries){
                       var showDetailsScreen by remember {
                           mutableStateOf(false)
                       }
                       if(showDetailsScreen){
                           DetailsTextBar(accountType = it.accountName, userName = it.userName,
                               encryptedPassword = decrypt.decrypt(it.encryptedPasswordStore).decodeToString( ), deleteClick = { deleteEntry(it.userName) })
                       }
                       PasswordBar(onClick = {showDetailsScreen = true},
                           accountType = it.accountName)
                   }
               }

               Row(horizontalArrangement = Arrangement.End,
                   verticalAlignment = Alignment.Bottom,
                   modifier = Modifier
                       .fillMaxSize()
               ) {
                   FloatingActionButton(
                       onClick = { showDialog = !showDialog },
                       modifier = Modifier
                           .padding(24.dp)

                   ) {
                       Icon(imageVector = Icons.Filled.Add, contentDescription = "Add New Password")

                   }
               }
           }

       }
       if(showDialog){
           Column(
               verticalArrangement = Arrangement.Bottom,
               modifier = Modifier
                   .fillMaxWidth()
                   .height(360.dp)

           ) {
               Dialog(onDismissRequest = {showDialog = false}) {
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Bottom,
                       modifier = Modifier
                           .width(390.dp)
                           .height(360.dp)

                   ) {

                       savePasswordScreen.SavePassword()
                   }

               }
           }

       }

   }



    private fun deleteEntry(userName:String){
        val realm = realm
       try {
           realm.writeBlocking {
               val findEntryWithUserName= realm.query<PasswordEntry>("userName == $0", userName).find()
               if(findEntryWithUserName.isNotEmpty()){
                   delete(findEntryWithUserName)
               }
           }
       }catch (e:Exception){
           Log.e("RealmError", "${e.message}")
       }

    }

}
