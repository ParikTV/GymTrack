package com.example.gymtracker.entities

data class Exercise(
    var id: String = "",
    var name: String = "",
    var reps: Int = 0,
    var sets: Int = 0,
    var type: String = ""
) {
    val fullName get() = "$name - $type"
}
