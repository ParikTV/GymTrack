package com.example.gymtracker.model

import com.example.gymtracker.data.MemoryManager
import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.interfaces.IExerciseRepository
import android.content.Context
import com.example.gymtracker.R

class ExerciseModel(context: Context) {
    private var dbManager: IExerciseRepository = MemoryManager
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

    fun getExerciseNames(): List<String> {
        val names = mutableListOf<String>()
        dbManager.getAll().forEach { i -> names.add(i.fullName) }
        return names.toList()
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
        dbManager.update(exercise)
    }

    fun getExerciseByFullName(fullName: String): Exercise {
        val result = dbManager.getByFullName(fullName)
        if (result == null) {
            val message = _context.getString(R.string.Exercise_Not_Found)
            throw Exception(message)
        }
        return result
    }
}
