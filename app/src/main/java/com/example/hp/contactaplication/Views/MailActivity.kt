package com.example.hp.contactaplication.Views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hp.contactaplication.R
import kotlinx.android.synthetic.main.activity_mail.*

class MailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)

        val message = intent.getStringExtra(Intent.EXTRA_TEXT)
        val to =intent.getStringArrayExtra(Intent.EXTRA_EMAIL)

        toView.setText(to[0])
        messageView.setText(message)
    }
    fun send(view: View){
        Toast.makeText(applicationContext,"Se envio el mensaje a ${intent.getStringArrayExtra(Intent.EXTRA_EMAIL)[0]}",Toast.LENGTH_SHORT).show()
    }
}
