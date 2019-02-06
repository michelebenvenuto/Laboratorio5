package com.example.hp.contactaplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.hp.contactaplication.Views.ContactInfoActivity
import com.example.hp.contactaplication.Views.NewContactActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contacts = MyApplication.Contacts
        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1,contacts)
        ContactView.adapter= adapter

        ContactView.onItemClickListener= object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent: Intent = Intent(this@MainActivity, ContactInfoActivity::class.java)
                val itemToGet=position
                intent.putExtra("itemToGet",itemToGet)
                startActivity(intent)
            }
        }

    }
    fun showNewContactActivity(view: View){
        val intent:Intent=Intent(this, NewContactActivity::class.java)
        startActivity(intent)
    }

}
