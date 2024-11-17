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

    fun removeExercise(id: String) {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = _context.getString(R.string.Exercise_Not_Found)
            throw Exception(message)
        }
        dbManager.remove(id)
    }


    fun updateExercise(exercise: Exercise) {
        val existingExercise = MemoryManager.getById(exercise.id)
        if (existingExercise != null) {
            existingExercise.name = exercise.name
            existingExercise.reps = exercise.reps
            existingExercise.sets = exercise.sets
            existingExercise.imageByteArray = exercise.imageByteArray // Actualizar la imagen
            MemoryManager.update(existingExercise)
        }
    }
}
