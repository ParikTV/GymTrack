package com.example.gymtracker

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.model.ExerciseModel
import com.example.gymtracker.util.EXTRA_MESSAGE_EXERCISE_ID
import com.example.gymtracker.util.Util

class Show_Routines : AppCompatActivity() {

    private lateinit var btnPush: Button
    private lateinit var btnPull: Button
    private lateinit var btnLeg: Button
    private lateinit var selectedRoutine: String
    private lateinit var exerciseModel: ExerciseModel
    private var exercisesForRoutine: List<Exercise> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_routines)

        exerciseModel = ExerciseModel(this)

        btnPush = findViewById(R.id.btnPush)
        btnPull = findViewById(R.id.btnPull)
        btnLeg = findViewById(R.id.btnLeg)

        btnPush.setOnClickListener { selectRoutine("PUSH", btnPush) }
        btnPull.setOnClickListener { selectRoutine("PULL", btnPull) }
        btnLeg.setOnClickListener { selectRoutine("LEG", btnLeg) }

        findViewById<Button>(R.id.btnTrain).setOnClickListener { startTraining() }

        selectRoutine("PUSH", btnPush)
    }

    private fun selectRoutine(routine: String, selectedButton: Button) {
        resetButtonColors()
        selectedButton.setBackgroundColor(Color.BLUE)
        selectedRoutine = routine
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

        exercisesForRoutine = exerciseModel.getExercises().filter { it.type == routine }

        if (exercisesForRoutine.isEmpty()) {
            val noExercisesView = TextView(this).apply {
                text = getString(R.string.No_Exercises)
                textSize = 18f
                setPadding(16, 8, 16, 8)
            }
            llExerciseList.addView(noExercisesView)
        } else {
            for (exercise in exercisesForRoutine) {
                val exerciseView = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(16, 8, 16, 8)
                }

                val imageView = ImageView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(100, 100)
                    if (exercise.imageByteArray != null) {
                        val bitmap = BitmapFactory.decodeByteArray(
                            exercise.imageByteArray,
                            0,
                            exercise.imageByteArray!!.size
                        )
                        setImageBitmap(bitmap)
                    } else {
                        setImageResource(R.drawable.ic_placeholder)
                    }
                }

                val textView = TextView(this).apply {
                    text =
                        "${exercise.name} - ${Util.formatRepsAndSets(exercise.reps, exercise.sets)}"
                    textSize = 18f
                    setPadding(16, 0, 0, 0)
                }

                exerciseView.addView(imageView)
                exerciseView.addView(textView)

                exerciseView.setOnClickListener { openEditExercise(exercise) }

                llExerciseList.addView(exerciseView)
            }
        }
    }

    private fun openEditExercise(exercise: Exercise) {
        if (exercise.id.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.Exercise_Not_Found), Toast.LENGTH_SHORT).show()
        } else {
            Util.openActivity(
                this,
                Edit_Exercise_Activity::class.java,
                EXTRA_MESSAGE_EXERCISE_ID,
                exercise.id
            )
        }
    }

    private fun startTraining() {
        if (exercisesForRoutine.isNotEmpty()) {
            val exerciseData = exercisesForRoutine.associate { exercise ->
                exercise.name to Triple(exercise.reps, exercise.sets, exercise.imageByteArray)
            }

            val intent = Intent(this, Show_Exercises::class.java).apply {
                putExtra("EXERCISE_DATA", HashMap(exerciseData))
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.No_Exercises), Toast.LENGTH_SHORT).show()
        }
    }
}
