package com.example.hp.contactaplication.Views

import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hp.contactaplication.ContactsProvider
import com.example.hp.contactaplication.MainActivity
import com.example.hp.contactaplication.Models.Contact
import com.example.hp.contactaplication.MyApplication
import com.example.hp.contactaplication.R

import kotlinx.android.synthetic.main.activity_new_contact.*

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)
    }
    fun retrun(view: View){
        val intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun addContact(view: View){
        val values = ContentValues()
        values.put(ContactsProvider.NAME, nameInput.text.toString())
        values.put(ContactsProvider.TEL, phoneInput.text.toString())
        values.put(ContactsProvider.MAIL, emailInput.text.toString())
        if (nameInput.text.toString() !="" && phoneInput.text.toString()!=""&& emailInput.text.toString()!=""){
            val uri = contentResolver.insert(ContactsProvider.CONTET_URI,values)
            Toast.makeText(applicationContext,"Se ha agregado el contacto de manera exitosa",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(applicationContext,"No se ha podido agregar el contacto",Toast.LENGTH_SHORT).show()
        }

        nameInput.setText("")
        phoneInput.setText("")
        emailInput.setText("")
    }
}
