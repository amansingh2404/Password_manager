package com.example.passwordmanagerapp.database

import com.example.passwordmanagerapp.CryptoManager
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DatabaseOperation {

    private val cryptoManager = CryptoManager()
    suspend fun savePassword(realm: Realm, siteName: String, username: String, password: String) {

        try {
            withContext(Dispatchers.IO) {
                val encryptedPassword = cryptoManager.encrypt(password.toByteArray())
                realm.writeBlocking {
                    copyToRealm(PasswordEntry().apply {
                        accountName = siteName
                        userName = username
                        encryptedPasswordStore = encryptedPassword
                    })
                }
            }
        } catch (e: Exception) {
            // Handle the exception (e.g., log or display an error message)
            e.printStackTrace()
        }
    }

    fun loadPasswords(realm: Realm):RealmResults<PasswordEntry> {
        val queryAllDetails = realm.query<PasswordEntry>()
        return queryAllDetails.find()
    }









}
