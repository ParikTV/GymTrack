package com.example.gymtracker.model

import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.interfaces.IExerciseRepository
import android.content.Context
import com.example.gymtracker.R
import com.example.gymtracker.data.SQLiteManager

class ExerciseModel(context: Context) {
    private var dbManager: IExerciseRepository = SQLiteManager(context)
    private val _context: Context = context

    fun addExercise(exercise: Exercise) {
        dbManager.add(exercise)
    }

    fun getExercises() = dbManager.getAll()

    fun getExercise(id: String): Exercise {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = _context.getString(R.string.Exercise_Not_Found)
            throw Exception(message)
        }
        return result
    }

    fun removeExercise(id: String) {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = _context.getString(R.string.Exercise_Not_Found)
            throw Exception(message)
        }
        dbManager.remove(id)
    }

    fun updateExercise(exercise: Exercise) {
        val existingExercise = dbManager.getById(exercise.id)
        if (existingExercise != null) {
            dbManager.update(exercise)
        } else {
            val message = _context.getString(R.string.Exercise_Not_Found)
            throw Exception(message)
        }
    }
}
