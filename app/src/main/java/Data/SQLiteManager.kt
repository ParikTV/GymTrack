// SQLiteManager.kt
package com.example.gymtracker.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.interfaces.IExerciseRepository

class SQLiteManager(context: Context) : IExerciseRepository {

    private val dbHelper = DatabaseHelper(context)

    override fun add(exercise: Exercise) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_ID, exercise.id)
            put(DatabaseHelper.COLUMN_NAME, exercise.name)
            put(DatabaseHelper.COLUMN_REPS, exercise.reps)
            put(DatabaseHelper.COLUMN_SETS, exercise.sets)
            put(DatabaseHelper.COLUMN_TYPE, exercise.type)
            put(DatabaseHelper.COLUMN_IMAGE, exercise.imageByteArray)
        }

        db.insert(DatabaseHelper.TABLE_EXERCISES, null, values)
        db.close()
    }

    override fun update(exercise: Exercise) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, exercise.name)
            put(DatabaseHelper.COLUMN_REPS, exercise.reps)
            put(DatabaseHelper.COLUMN_SETS, exercise.sets)
            put(DatabaseHelper.COLUMN_TYPE, exercise.type)
            put(DatabaseHelper.COLUMN_IMAGE, exercise.imageByteArray)
        }

        db.update(DatabaseHelper.TABLE_EXERCISES, values, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(exercise.id))
        db.close()
    }

    override fun remove(id: String) {
        val db = dbHelper.writableDatabase

        db.delete(DatabaseHelper.TABLE_EXERCISES, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(id))
        db.close()
    }

    override fun getAll(): List<Exercise> {
        val db = dbHelper.readableDatabase
        val exerciseList = mutableListOf<Exercise>()

        val selectQuery = "SELECT * FROM ${DatabaseHelper.TABLE_EXERCISES}"

        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val exercise = Exercise(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                    reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPS)),
                    sets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SETS)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE)),
                    imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE))
                )
                exerciseList.add(exercise)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return exerciseList
    }

    override fun getById(id: String): Exercise? {
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            DatabaseHelper.TABLE_EXERCISES,
            null,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id),
            null, null, null
        )

        var exercise: Exercise? = null
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exercise = Exercise(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                    reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPS)),
                    sets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SETS)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE)),
                    imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE))
                )
            }
            cursor.close()
        }
        db.close()
        return exercise
    }

    override fun getByFullName(fullName: String): Exercise? {
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            DatabaseHelper.TABLE_EXERCISES,
            null,
            "${DatabaseHelper.COLUMN_NAME} || ' (' || ${DatabaseHelper.COLUMN_TYPE} || ')' = ?",
            arrayOf(fullName),
            null, null, null
        )

        var exercise: Exercise? = null
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exercise = Exercise(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                    reps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPS)),
                    sets = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SETS)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE)),
                    imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE))
                )
            }
            cursor.close()
        }
        db.close()
        return exercise
    }
}
