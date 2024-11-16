package com.example.gymtracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.model.ExerciseModel
import com.example.gymtracker.util.EXTRA_MESSAGE_EXERCISE_ID

class Edit_Exercise_Activity : AppCompatActivity() {

    private lateinit var etNombreEjercicio: EditText
    private lateinit var etRepeticiones: EditText
    private lateinit var etSeries: EditText
    private lateinit var btnAceptar: Button
    private lateinit var btnEliminar: Button
    private lateinit var exerciseModel: ExerciseModel
    private var exerciseId: String? = null
    private var exercise: Exercise? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_exercise)
        exerciseModel = ExerciseModel(this)

        etNombreEjercicio = findViewById(R.id.etNombreEjercicio)
        etRepeticiones = findViewById(R.id.etRepeticiones)
        etSeries = findViewById(R.id.etSeries)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnEliminar = findViewById(R.id.btnEliminar)

        exerciseId = intent.getStringExtra(EXTRA_MESSAGE_EXERCISE_ID)

        if (exerciseId != null) {
            exercise = exerciseModel.getExercise(exerciseId!!)
            if (exercise != null) {
                etNombreEjercicio.setText(exercise!!.name)
                etRepeticiones.setText(exercise!!.reps.toString())
                etSeries.setText(exercise!!.sets.toString())
            } else {
                Toast.makeText(this, getString(R.string.Exercise_Not_Found), Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            Toast.makeText(this, getString(R.string.Error_No_Internet), Toast.LENGTH_SHORT).show()
            finish()
        }

        btnAceptar.setOnClickListener {
            updateExercise()
        }

        btnEliminar.setOnClickListener {
            deleteExercise()
        }
    }

    private fun updateExercise() {
        val name = etNombreEjercicio.text.toString()
        val reps = etRepeticiones.text.toString().toIntOrNull()
        val sets = etSeries.text.toString().toIntOrNull()

        if (name.isNotEmpty() && reps != null && sets != null) {
            exercise!!.name = name
            exercise!!.reps = reps
            exercise!!.sets = sets
            exerciseModel.updateExercise(exercise!!)
            Toast.makeText(this, getString(R.string.Exercise_Updated), Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, getString(R.string.Error_Empty_Fields), Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteExercise() {
        exerciseModel.removeExercise(exerciseId!!)
        Toast.makeText(this, getString(R.string.Exercise_Deleted), Toast.LENGTH_SHORT).show()
        finish()
    }
}
