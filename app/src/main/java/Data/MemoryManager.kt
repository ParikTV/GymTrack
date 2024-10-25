package com.example.gymtracker.data

import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.interfaces.IExerciseRepository

object MemoryManager : IExerciseRepository {
    private var exerciseList = mutableListOf<Exercise>()

    // Agregar un ejercicio
    override fun add(exercise: Exercise) {
        exerciseList.add(exercise)
    }

    // Actualizar un ejercicio
    override fun update(exercise: Exercise) {
        remove(exercise.id)
        exerciseList.add(exercise)
    }

    // Eliminar un ejercicio
    override fun remove(id: String) {
        exerciseList.removeIf { it.id.trim() == id.trim() }
    }

    // Ver todos los ejercicios
    override fun getAll(): List<Exercise> = exerciseList.toList()

    // Ver ejercicio por ID
    override fun getById(id: String): Exercise? {
        return try {
            val result = exerciseList.filter { it.id == id }
            if (result.isEmpty()) null else result[0]
        } catch (e: Exception) {
            throw e
        }
    }

    // Ver ejercicio por nombre completo
    override fun getByFullName(fullName: String): Exercise? {
        return try {
            val result = exerciseList.filter { it.fullName == fullName }
            if (result.isEmpty()) null else result[0]
        } catch (e: Exception) {
            throw e
        }
    }
}
