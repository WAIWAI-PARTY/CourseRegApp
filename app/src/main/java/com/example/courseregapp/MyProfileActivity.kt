package com.example.courseregapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MyProfileActivity : AppCompatActivity() {
    private val REQUEST_CODE_PICK_IMAGE = 1
    private lateinit var profile_picture: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        profile_picture = findViewById(R.id.profile_picture)
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val imagePath = sharedPreferences.getString("profile_image_path", null)

        // Load and display the image if path is available
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            profile_picture.setImageBitmap(bitmap)
        }        // Set up click listener for profile picture
        profile_picture.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            uri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                profile_picture.setImageBitmap(bitmap)

                // Save image to internal storage and store the path in SharedPreferences
                val imagePath = saveImageToInternalStorage(bitmap, this)
                val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                Toast.makeText(this, "Image saved at: $imagePath", Toast.LENGTH_LONG).show()

                val editor = sharedPreferences.edit()
                editor.putString("profile_image_path", imagePath)
                editor.apply()
            }
        }
    }
    private fun saveImageToInternalStorage(bitmap: Bitmap, context: Context): String {
        val filename = "profile_image.png"
        val file = File(context.filesDir, filename)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        return file.absolutePath
    }
}