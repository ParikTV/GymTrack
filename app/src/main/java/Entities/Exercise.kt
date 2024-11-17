package com.example.gymtracker.entities

data class Exercise(
    val id: String,
    var name: String,
    var reps: Int,
    var sets: Int,
    var type: String,
    var imageByteArray: ByteArray? = null
) {
    val fullName: String
        get() = "$name ($type)"
}
