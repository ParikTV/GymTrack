package com.example.gymtracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Show_Routines : AppCompatActivity() {

    private lateinit var btnPush: Button
    private lateinit var btnPull: Button
    private lateinit var btnLeg: Button
    private lateinit var selectedRoutine: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_routines)

        // Inicializar botones
        btnPush = findViewById(R.id.btnPush)
        btnPull = findViewById(R.id.btnPull)
        btnLeg = findViewById(R.id.btnLeg)

        // Inicializar rutina seleccionada
        selectedRoutine = "PUSH"

        // Configurar listeners de los botones
        btnPush.setOnClickListener { selectRoutine("PUSH", btnPush) }
        btnPull.setOnClickListener { selectRoutine("PULL", btnPull) }
        btnLeg.setOnClickListener { selectRoutine("LEG", btnLeg) }

        // Configurar el botón "Train"
        val btnTrain: Button = findViewById(R.id.btnTrain)
        btnTrain.setOnClickListener {
            startWorkout()
        }
    }

    private fun selectRoutine(routine: String, selectedButton: Button) {
        // Restablecer el color de todos los botones
        resetButtonColors()

        // Cambiar el color del botón seleccionado a azul
        selectedButton.setBackgroundColor(Color.BLUE)

        // Guardar la rutina seleccionada
        selectedRoutine = routine

        // Actualizar la lista de ejercicios (ejemplo)
        updateExerciseList(routine)
    }

    private fun resetButtonColors() {
        btnPush.setBackgroundColor(Color.BLACK)
        btnPull.setBackgroundColor(Color.BLACK)
        btnLeg.setBackgroundColor(Color.BLACK)
    }

    private fun updateExerciseList(routine: String) {
        val llExerciseList: LinearLayout = findViewById(R.id.llExerciseList)
        llExerciseList.removeAllViews()

        // Lista de ejercicios según la rutina seleccionada
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
            llExerciseList.addView(exerciseView)
        }
    }

    private fun startWorkout() {
        // Iniciar la actividad WorkOut con la rutina seleccionada
        val intent = Intent(this, WorkOut::class.java)
        intent.putExtra("SELECTED_ROUTINE", selectedRoutine)
        startActivity(intent)
    }
}
