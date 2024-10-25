package com.example.gymtracker.interfaces

import com.example.gymtracker.entities.Exercise

interface IExerciseRepository {
    fun add(exercise: Exercise)
    fun update(exercise: Exercise)
    fun remove(id: String)
    fun getAll(): List<Exercise>
    fun getById(id: String): Exercise?
    fun getByFullName(fullName: String): Exercise?
}
