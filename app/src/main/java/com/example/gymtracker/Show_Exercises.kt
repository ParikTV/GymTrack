package com.example.gymtracker

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

        val exerciseData = intent.getSerializableExtra("EXERCISE_DATA") as? HashMap<String, Triple<Int, Int, ByteArray?>>

        displayExercises(exerciseData)

        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            Util.openActivity(this, Show_Routines::class.java)
        }
    }

    private fun displayExercises(exerciseData: Map<String, Triple<Int, Int, ByteArray?>>?) {
        exerciseListLayout.removeAllViews()
        if (exerciseData.isNullOrEmpty()) {
            val noExerciseView = TextView(this).apply {
                text = getString(R.string.No_Exercises)
                textSize = 18f
                setPadding(16, 8, 16, 8)
            }
            exerciseListLayout.addView(noExerciseView)
        } else {
            for ((exerciseName, data) in exerciseData) {
                val (reps, sets, imageByteArray) = data

                val exerciseRow = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(16, 8, 16, 8)
                }

                val exerciseImageView = ImageView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(150, 150)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    if (imageByteArray != null) {
                        val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                        setImageBitmap(bitmap)
                    } else {
                        setImageResource(R.drawable.ic_placeholder)
                    }
                    setOnClickListener {
                        showExerciseImage(exerciseName, imageByteArray)
                    }
                }

                val exerciseText = TextView(this).apply {
                    text = "$exerciseName\nReps: $reps - Sets: $sets"
                    textSize = 18f
                    setPadding(16, 0, 16, 0)
                }

                exerciseRow.addView(exerciseImageView)
                exerciseRow.addView(exerciseText)

                exerciseListLayout.addView(exerciseRow)
            }
        }
    }

    private fun showExerciseImage(exerciseName: String, imageByteArray: ByteArray?) {
        val dialogView = ImageView(this).apply {
            if (imageByteArray != null) {
                val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                setImageBitmap(bitmap)
            } else {
                setImageResource(R.drawable.ic_placeholder)
            }
            adjustViewBounds = true
        }

        AlertDialog.Builder(this)
            .setTitle(exerciseName)
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}
