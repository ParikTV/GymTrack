package com.example.gymtracker

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gymtracker.entities.Exercise
import com.example.gymtracker.model.ExerciseModel
import com.example.gymtracker.util.EXTRA_MESSAGE_EXERCISE_ID
import java.io.ByteArrayOutputStream

class Edit_Exercise_Activity : AppCompatActivity() {

    private lateinit var etNombreEjercicio: EditText
    private lateinit var etRepeticiones: EditText
    private lateinit var etSeries: EditText
    private lateinit var ivExercisePhoto: ImageView
    private lateinit var btnChooseImage: Button
    private lateinit var btnAceptar: Button
    private lateinit var btnEliminar: Button
    private lateinit var exerciseModel: ExerciseModel
    private var exerciseId: String? = null
    private var exercise: Exercise? = null
    private var exerciseBitmap: Bitmap? = null

    companion object {
        private const val REQUEST_CAMERA = 100
        private const val REQUEST_GALLERY = 200
        private const val PERMISSION_REQUEST_CODE = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_exercise)
        exerciseModel = ExerciseModel(this)

        etNombreEjercicio = findViewById(R.id.etNombreEjercicio)
        etRepeticiones = findViewById(R.id.etRepeticiones)
        etSeries = findViewById(R.id.etSeries)
        ivExercisePhoto = findViewById(R.id.ivExercisePhoto)
        btnChooseImage = findViewById(R.id.btnChooseImage)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnEliminar = findViewById(R.id.btnEliminar)

        exerciseId = intent.getStringExtra(EXTRA_MESSAGE_EXERCISE_ID)

        if (exerciseId != null) {
            exercise = exerciseModel.getExercise(exerciseId!!)
            if (exercise != null) {
                etNombreEjercicio.setText(exercise!!.name)
                etRepeticiones.setText(exercise!!.reps.toString())
                etSeries.setText(exercise!!.sets.toString())

                if (exercise!!.imageByteArray != null) {
                    val bitmap = BitmapFactory.decodeByteArray(
                        exercise!!.imageByteArray,
                        0,
                        exercise!!.imageByteArray!!.size
                    )
                    ivExercisePhoto.setImageBitmap(bitmap)
                    exerciseBitmap = bitmap
                }
            } else {
                Toast.makeText(this, getString(R.string.Exercise_Not_Found), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        } else {
            Toast.makeText(this, getString(R.string.Error_No_Internet), Toast.LENGTH_SHORT).show()
            finish()
        }

        btnChooseImage.setOnClickListener {
            checkPermissions()
        }

        btnAceptar.setOnClickListener {
            updateExercise()
        }

        btnEliminar.setOnClickListener {
            deleteExercise()
        }
    }

    private fun checkPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toTypedArray(), PERMISSION_REQUEST_CODE)
        } else {
            showImagePickerDialog()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                showImagePickerDialog()
            } else {
                Toast.makeText(this, getString(R.string.Permission_Denied), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showImagePickerDialog() {
        val options = arrayOf(
            getString(R.string.Take_Photo),
            getString(R.string.Choose_From_Gallery)
        )
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.Add_Images))
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .create()
            .show()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    private fun openGallery() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    val extras = data?.extras
                    val imageBitmap = extras?.get("data") as Bitmap
                    val resizedBitmap = getResizedBitmap(imageBitmap, 500)
                    exerciseBitmap = resizedBitmap
                    ivExercisePhoto.setImageBitmap(resizedBitmap)
                }
                REQUEST_GALLERY -> {
                    val imageUri = data?.data
                    val imageBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    val resizedBitmap = getResizedBitmap(imageBitmap, 500)
                    exerciseBitmap = resizedBitmap
                    ivExercisePhoto.setImageBitmap(resizedBitmap)
                }
            }
        }
    }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    private fun updateExercise() {
        val name = etNombreEjercicio.text.toString()
        val reps = etRepeticiones.text.toString().toIntOrNull()
        val sets = etSeries.text.toString().toIntOrNull()

        if (name.isNotEmpty() && reps != null && sets != null) {
            exercise!!.name = name
            exercise!!.reps = reps
            exercise!!.sets = sets

            if (exerciseBitmap != null) {
                val stream = ByteArrayOutputStream()
                exerciseBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                exercise!!.imageByteArray = stream.toByteArray()
            }

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

    override fun onDestroy() {
        super.onDestroy()
        exerciseBitmap?.recycle()
        exerciseBitmap = null
    }
}
