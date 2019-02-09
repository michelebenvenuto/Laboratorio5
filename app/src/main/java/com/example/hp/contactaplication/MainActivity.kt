package com.example.hp.contactaplication

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.hp.contactaplication.Models.Contact
import com.example.hp.contactaplication.Views.ContactInfoActivity
import com.example.hp.contactaplication.Views.NewContactActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyApplication.Contacts.clear()
        //sirve para el adapter
        val contacts = ArrayList<String>()
        val URL = "content://com.example.hp.contactaplication.ContactsProvider"
        val contact= Uri.parse(URL)
        val c = contentResolver.query(contact, null, null, null, "name")
        //logica para poner toda la informacion necesaria en la list view y ademas agregar la necesaria a my application
        if (c.moveToFirst()){
            do {
                contacts.add(c.getString(c.getColumnIndex(ContactsProvider.NAME))+" "+c.getString(c.getColumnIndex(ContactsProvider.TEL)))
                val newContact = Contact(c.getString(c.getColumnIndex(ContactsProvider.TEL)),c.getString(c.getColumnIndex(ContactsProvider.NAME)),c.getString(c.getColumnIndex(ContactsProvider.MAIL)))
                MyApplication.Contacts.add(newContact)
            }while (c.moveToNext())
        }
        c.close()


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
        ContactView.onItemLongClickListener= object: AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                contentResolver.delete(ContactsProvider.CONTET_URI,position.toString(),null)
                contacts.remove(contacts.get(position))
                MyApplication.Contacts.removeAt(position)
                adapter.notifyDataSetChanged()
                return true
            }
        }

    }
    fun showNewContactActivity(view: View){
        val intent:Intent=Intent(this, NewContactActivity::class.java)
        startActivity(intent)
    }

}
