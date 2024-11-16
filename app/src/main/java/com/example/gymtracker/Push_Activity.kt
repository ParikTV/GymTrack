package com.example.gymtracker

import android.os.Bundle
import com.example.gymtracker.util.Util
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.model.ExerciseModel
import java.util.UUID

class Push_Activity : AppCompatActivity() {

    private lateinit var etNombreEjercicio: EditText
    private lateinit var etRepeticiones: EditText
    private lateinit var etSeries: EditText
    private lateinit var exerciseModel: ExerciseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_push)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        exerciseModel = ExerciseModel(this)

        etNombreEjercicio = findViewById(R.id.etNombreEjercicio)
        etRepeticiones = findViewById(R.id.etRepeticiones)
        etSeries = findViewById(R.id.etSeries)

        findViewById<Button>(R.id.btnAceptar).setOnClickListener { addExercise() }
        findViewById<Button>(R.id.btnVerEjercicios).setOnClickListener {
            Util.openActivity(this, Show_Exercises::class.java)
        }
        findViewById<Button>(R.id.btnVerRutinas).setOnClickListener {
            Util.openActivity(this, Show_Routines::class.java)
        }
    }

    private fun addExercise() {
        val name = etNombreEjercicio.text.toString()
        val reps = etRepeticiones.text.toString().toIntOrNull()
        val sets = etSeries.text.toString().toIntOrNull()

        if (name.isNotEmpty() && reps != null && sets != null) {
            val exercise = Exercise(UUID.randomUUID().toString(), name, reps, sets, "PUSH")
            exerciseModel.addExercise(exercise)

            etNombreEjercicio.text.clear()
            etRepeticiones.text.clear()
            etSeries.text.clear()

            Toast.makeText(this, getString(R.string.Exercise_Added), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.Error_Empty_Fields), Toast.LENGTH_SHORT).show()
        }
    }
}
