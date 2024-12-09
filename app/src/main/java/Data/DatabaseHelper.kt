// DatabaseHelper.kt
package com.example.gymtracker.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "gym_tracker.db"
        const val DATABASE_VERSION = 1

        const val TABLE_EXERCISES = "exercises"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_REPS = "reps"
        const val COLUMN_SETS = "sets"
        const val COLUMN_TYPE = "type"
        const val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_EXERCISES_TABLE = ("CREATE TABLE $TABLE_EXERCISES ("
                + "$COLUMN_ID TEXT PRIMARY KEY,"
                + "$COLUMN_NAME TEXT,"
                + "$COLUMN_REPS INTEGER,"
                + "$COLUMN_SETS INTEGER,"
                + "$COLUMN_TYPE TEXT,"
                + "$COLUMN_IMAGE BLOB"
                + ")")
        db?.execSQL(CREATE_EXERCISES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_EXERCISES")
        onCreate(db)
    }
}
