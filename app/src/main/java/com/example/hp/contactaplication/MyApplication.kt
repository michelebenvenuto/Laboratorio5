package com.example.hp.contactaplication

import android.app.Application
import com.example.hp.contactaplication.Models.Contact
import java.util.*


class MyApplication: Application() {
   companion object {
        val Contacts: ArrayList<Contact> = ArrayList()
    }
}