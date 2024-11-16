package com.example.gymtracker

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gymtracker.util.Util

class Show_Exercises : AppCompatActivity() {

    private lateinit var exerciseListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_exercises)

        exerciseListLayout = findViewById(R.id.llExerciseList)

        val exerciseNames = intent.getStringExtra("EXERCISE_LIST")?.split(",") ?: emptyList()

        displayExercises(exerciseNames)

        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            Util.openActivity(this, Show_Routines::class.java)
        }
    }

    private fun displayExercises(exerciseNames: List<String>) {
        exerciseListLayout.removeAllViews() // Clear previous views
        if (exerciseNames.isEmpty()) {
            val noExerciseView = TextView(this).apply {
                text = getString(R.string.No_Exercises)
                textSize = 18f
                setPadding(16, 8, 16, 8)
            }
            exerciseListLayout.addView(noExerciseView)
        } else {
            for (exerciseName in exerciseNames) {
                val exerciseView = TextView(this).apply {
                    text = exerciseName
                    textSize = 18f
                    setPadding(16, 8, 16, 8)
                }
                exerciseListLayout.addView(exerciseView)
            }
        }
    }
}
