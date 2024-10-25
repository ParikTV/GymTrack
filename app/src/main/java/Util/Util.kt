package com.example.gymtracker.util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

const val EXTRA_MESSAGE_EXERCISE_ID = "com.example.gymtracker.exerciseID"

class Util {
    companion object {
        fun openActivity(context: Context, objClass: Class<*>, keyName: String = "", value: String? = "") {
            val intent = Intent(context, objClass).apply { putExtra(keyName, value) }
            ContextCompat.startActivity(context, intent, null)
        }

        fun formatRepsAndSets(reps: Int, sets: Int): String {
            return "${sets}x$reps"
        }
    }
}
