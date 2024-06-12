package com.example.passwordmanagerapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.passwordmanagerapp.database.DatabaseOperation
import com.example.passwordmanagerapp.database.PasswordEntry
import com.example.passwordmanagerapp.screen.MainScreen
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


data class PasswordUiState(
    val passwordEntries: List<PasswordEntry> = emptyList()
)

/**
 * This is ViewModel class to fetch data from database provide it to UI
 */
class PasswordDetailsViewModel():ViewModel() {
    private  val realm = MainScreen.realm
    private val fetchDataFromDatabase = DatabaseOperation()
    private val _uiState = MutableStateFlow(PasswordUiState())
    val uiState: StateFlow<PasswordUiState> = _uiState

    init {
        fetchPasswordEntries()
    }

    /**
     * fetchPasswordEntries function call the function loadPassword from DataBaseOperation class and update
     * _uiState
     */
    private fun fetchPasswordEntries(){
          val passwordDetails = fetchDataFromDatabase.loadPasswords(realm)
        _uiState.value = PasswordUiState(passwordDetails)
    }
}