package com.example.hp.contactaplication

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils

import java.lang.IllegalArgumentException
import java.util.*

class ContactsProvider: ContentProvider() {
    private var db: SQLiteDatabase? = null

    companion object {
        internal val PROVIDER_NAME="com.example.hp.contactaplication.ContactsProvider"
        internal val URL = "content://$PROVIDER_NAME/contacts"
        internal val CONTET_URI= Uri.parse(URL)

        internal val ID= "_id"
        internal val NAME = "name"
        internal val TEL = "tel"
        internal val MAIL = "mail"

        private val CONTACT_PROJECTION_MAP: HashMap<String, String>?=null

        internal val CONTACTS= 1
        internal val CONTACT_ID= 2
        internal val uriMatcher: UriMatcher
        init {
            uriMatcher= UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher.addURI(PROVIDER_NAME,"contacts", CONTACTS)
            uriMatcher.addURI(PROVIDER_NAME,"contacts/#", CONTACT_ID)
        }
        internal val DATABASE_NAME = "Contacts"
        internal val CONTACTS_TABLE_NAME="contactos"
        internal val DATABASE_VERSION=1
        internal val CREATE_DB_TABLE = " CREATE TABLE " + CONTACTS_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL, " + "tel TEXT NOT NULL," + "mail TEXT NOT NULL);"

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
        val rowId= db!!.insert(CONTACTS_TABLE_NAME,"", values)

        if(rowId>0){
            val _uri= ContentUris.withAppendedId(CONTET_URI, rowId)
            context!!.contentResolver.notifyChange(_uri,null)
            return _uri
        }
        throw SQLException("Failed to add a record into $uri")
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = CONTACTS_TABLE_NAME
        when(uriMatcher.match(uri)){
            CONTACTS -> qb.setProjectionMap(CONTACT_PROJECTION_MAP)
            CONTACT_ID -> qb.appendWhere(ID + "=" + uri.pathSegments[1])
        }
        val c = qb.query(
            db, projection, selection, selectionArgs, null,null, sortOrder?: NAME
        )
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    override fun onCreate(): Boolean {
        val context= context
        val dbHelper= DatabaseHelper(context)
        db = dbHelper.writableDatabase
        return db!=null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        var counter =0
        when(uriMatcher.match(uri)){
            CONTACTS -> counter = db!!.update(CONTACTS_TABLE_NAME, values,selection, selectionArgs)
            CONTACT_ID-> {counter=db!!.update(CONTACTS_TABLE_NAME, values, ID + "=" + uri.pathSegments[1] +
                    if (!TextUtils.isEmpty(selection))" AND ($selection)" else "",selectionArgs )
            }
            else ->throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return counter
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var count = 0
        when (uriMatcher.match(uri)) {
            CONTACTS -> count = db!!.delete(CONTACTS_TABLE_NAME, selection, selectionArgs)

            CONTACT_ID -> {
                val id = uri.pathSegments[1]
                count = db!!.delete(
                    CONTACTS_TABLE_NAME, ID + " = " + id +
                            if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "", selectionArgs
                )
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        when(uriMatcher.match(uri)){
            CONTACTS -> return "vnd.android.cursor.dir/vnd.example.contacts"
            CONTACT_ID -> return "vnd.android.cursor.item/vnd.example.contacts"
            else-> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

}