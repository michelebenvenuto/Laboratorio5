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
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val currentName= intent.getStringExtra("currentName")
        val currentTel= intent.getStringExtra("currentTel")
        val currentMail= intent.getStringExtra("currentMail")

        //le pone los datos actuales al momento de ingresar a la vista
        newName.setText(currentName)
        newEmail.setText(currentMail)
        newTel.setText(currentTel)
    }
    fun retrun(view: View){
        val intent: Intent = Intent(this, ContactInfoActivity::class.java)
        startActivity(intent)
    }
    fun edit(view:View){
        val values=ContentValues()
        values.put(ContactsProvider.NAME, newName.text.toString())
        values.put(ContactsProvider.TEL, newTel.text.toString())
        values.put(ContactsProvider.MAIL, newEmail.text.toString())
        contentResolver.update(ContactsProvider.CONTET_URI,values,(intent.getIntExtra("item",0)-1).toString(),null)
        MyApplication.Contacts.set(intent.getIntExtra("item",0), Contact(newTel.text.toString(),newName.text.toString(),newEmail.text.toString()))
        Toast.makeText(applicationContext,"Se ha editado el contacto de manera exitosa", Toast.LENGTH_SHORT).show()
        newName.setText("")
        newTel.setText("")
        newEmail.setText("")
    }
}
