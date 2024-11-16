package com.example.gymtracker.data

import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.interfaces.IExerciseRepository

object MemoryManager : IExerciseRepository {
    private var exerciseList = mutableListOf<Exercise>()

    override fun add(exercise: Exercise) {
        exerciseList.add(exercise)
    }

    override fun update(exercise: Exercise) {
        remove(exercise.id)
        exerciseList.add(exercise)
    }

    override fun remove(id: String) {
        exerciseList.removeIf { it.id.trim() == id.trim() }
    }

    override fun getAll(): List<Exercise> = exerciseList.toList()

    override fun getById(id: String): Exercise? {
        return try {
            val result = exerciseList.filter { it.id == id }
            if (result.isEmpty()) null else result[0]
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getByFullName(fullName: String): Exercise? {
        return try {
            val result = exerciseList.filter { it.fullName == fullName }
            if (result.isEmpty()) null else result[0]
        } catch (e: Exception) {
            throw e
        }
    }
}
