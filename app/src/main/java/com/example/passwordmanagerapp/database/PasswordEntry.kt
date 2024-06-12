package com.example.passwordmanagerapp.database

import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID


/**
 * The PasswordEntry class provides a structure for storing sensitive
 * password data in a Realm database. It includes fields for a unique
 * identifier, account name, username, and the encrypted password
 * itself. The secondary constructor offers flexibility in setting a
 * custom ID during object creation
 */

open class PasswordEntry() : RealmObject{
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var accountName: String = ""
    var userName: String = ""
    var encryptedPasswordStore: ByteArray = byteArrayOf()

    constructor(ownerId: String = "") : this() {
        id = ownerId
    }

}








