package com.example.gymtracker

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WorkOut : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out)

        // Obtener la rutina seleccionada del Intent
        val selectedRoutine = intent.getStringExtra("SELECTED_ROUTINE") ?: "PUSH"

        // Configurar el título de la rutina seleccionada
        val tvRoutineType: TextView = findViewById(R.id.tvRoutineType)
        tvRoutineType.text = "Rutina: $selectedRoutine"

        // Mostrar ejercicios según la rutina seleccionada
        val llExerciseList: LinearLayout = findViewById(R.id.llExerciseList)
        displayExercises(selectedRoutine, llExerciseList)
    }

    private fun displayExercises(routine: String, layout: LinearLayout) {
        // Limpiar la lista de ejercicios antes de mostrar los nuevos
        layout.removeAllViews()

        // Crear una lista de ejercicios de ejemplo según la rutina seleccionada
        val exercises = when (routine) {
            "PUSH" -> listOf("Press banca", "Flexiones", "Dips", "Press militar")
            "PULL" -> listOf("Dominadas", "Remo con barra", "Curl de bíceps", "Encogimientos")
            "LEG" -> listOf("Sentadillas", "Prensa", "Peso muerto", "Extensiones de pierna")
            else -> listOf("Ejercicio desconocido")
        }

        // Añadir los ejercicios al layout
        for (exercise in exercises) {
            val exerciseView = TextView(this).apply {
                text = exercise
                textSize = 18f
                setPadding(16, 8, 16, 8)
            }
            layout.addView(exerciseView)
        }
    }
}
