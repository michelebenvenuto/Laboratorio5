package com.example.hp.contactaplication.Views

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.example.hp.contactaplication.MainActivity
import com.example.hp.contactaplication.MyApplication
import com.example.hp.contactaplication.R
import kotlinx.android.synthetic.main.activity_contact_info.*
import java.lang.reflect.Array
import java.util.jar.Manifest

class ContactInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)
        val item = intent.getIntExtra("itemToGet",0)
        val itemToShow= MyApplication.Contacts[item]
        nameView.setText(itemToShow.name)
        phoneNumberView.setText(itemToShow.phoneNumber)
        emailView.setText(itemToShow.email)

        phoneNumberView.setOnClickListener {
            val phoneIntent: Intent= Intent(Intent.ACTION_DIAL)
            phoneIntent.setData(Uri.parse("tel:${phoneNumberView.text}"))
            startActivity(phoneIntent)
        }
        //Codigo para enviar la informacion a un app para poder mandar un correo
        emailView.setOnClickListener {
            val to = arrayOf(itemToShow.email)
            val mailIntent: Intent= Intent(Intent.ACTION_SEND)
            mailIntent.setData(Uri.parse("mailto:"))
            mailIntent.setType("text/plain")
            mailIntent.putExtra(Intent.EXTRA_EMAIL, to )
            mailIntent.putExtra(Intent.EXTRA_CC,"De")
            mailIntent.putExtra(Intent.EXTRA_TEXT,"Hola mi nombre es {su nombre aqui} y mi telefono es {su telefono aqui}")
            startActivity(mailIntent)

            try {
                startActivity(Intent.createChooser(mailIntent,"Send mail..."))
                finish()
            }catch (ex: android.content.ActivityNotFoundException){
                Toast.makeText(this,"there is no email client installed", Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun retrun(view: View){
        val intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}
