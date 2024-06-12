package com.example.passwordmanagerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.passwordmanagerapp.database.PasswordEntry
import com.example.passwordmanagerapp.screen.MainScreen
import com.example.passwordmanagerapp.ui.theme.PasswordManagerAppTheme
import com.example.passwordmanagerapp.viewmodel.PasswordDetailsViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {



        val mainScreenObject = MainScreen()


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    mainScreenObject.MainScreenUI()
                }
            }
        }
    }



}

