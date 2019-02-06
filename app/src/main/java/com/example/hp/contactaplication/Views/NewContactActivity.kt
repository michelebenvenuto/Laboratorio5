package com.example.hp.contactaplication.Views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        val name =nameInput.text.toString()
        val phone=phoneInput.text.toString()
        val email=emailInput.text.toString()
        val contactToAdd= Contact(phone,name,email)
        if (name!="" && phone!=""&& email!=""){
            MyApplication.Contacts.add(contactToAdd)
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
