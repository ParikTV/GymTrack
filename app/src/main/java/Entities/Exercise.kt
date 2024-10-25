package com.example.gymtracker.entities

class Exercise {
    private var _id: String = ""
    private var _name: String = ""
    private var _reps: Int = 0
    private var _sets: Int = 0
    private var _type: String = ""

    constructor()

    constructor(id: String, name: String, reps: Int, sets: Int, type: String) {
        this._id = id
        this._name = name
        this._reps = reps
        this._sets = sets
        this._type = type
    }

    var id: String
        get() = this._id
        set(value) { this._id = value }

    var name: String
        get() = this._name
        set(value) { this._name = value }

    var reps: Int
        get() = this._reps
        set(value) { this._reps = value }

    var sets: Int
        get() = this._sets
        set(value) { this._sets = value }

    var type: String
        get() = this._type
        set(value) { this._type = value }

    val fullName get() = "$name - $type"
}
