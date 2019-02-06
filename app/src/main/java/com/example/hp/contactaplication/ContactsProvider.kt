package com.example.hp.contactaplication

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri

class ContactsProvider: ContentProvider() {
    private var db: SQLiteDatabase? = null

    companion object {
        internal val PROVIDER_NAME="com.example.hp.contactaplication.ContactsProvider"
        internal val URL = "content://$PROVIDER_NAME/contacts"
        internal val CONTET_URI= Uri.parse(URL)

        internal val NAME = "name"
        internal val TEL = "tel"
        internal val MAIL = "mail"

        private val CONTACT_PROJECTION_MAP: HashMap<String,String>?=null

        internal val CONTACTS= 1
        internal val CONTACT_ID=2
        internal val uriMatcher: UriMatcher
        init {
            uriMatcher= UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(PROVIDER_NAME,"contacts", CONTACTS)
            uriMatcher.addURI(PROVIDER_NAME,"contacts/#", CONTACT_ID)
        }
        internal val DATABASE_NAME = "Contacts"
        internal val CONTACTS_TABLE_NAME="contacts"
        internal val DATABASE_VERSION=1
        internal val CREATE_DB_TABLE = " CREATE TABLE "+ CONTACTS_TABLE_NAME +" name TEXT NOT NULL, " + "tel TEXT NOT NUll" + "mail TEXT NOT NULL"

    }


    private class DatabaseHelper internal constructor(context: Context):
        SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL(" DROP TABLE IF EXISTS $CONTACTS_TABLE_NAME")
            onCreate(db)
        }
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}