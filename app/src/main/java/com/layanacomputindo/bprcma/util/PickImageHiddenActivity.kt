package com.layanacomputindo.bprcma.util

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.layanacomputindo.bprcma.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PickImageHiddenActivity : AppCompatActivity() {

    private val KEY_CAMERA_PICTURE_URL = "cameraPictureUrl"
    companion object {

        val IMAGE_SOURCE = "image_source"
        val ALLOW_MULTIPLE_IMAGES = "allow_multiple_images"

        private val SELECT_PHOTO = 100
        private val TAKE_PHOTO = 101
    }

    private var cameraPictureUrl: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            handleIntent(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_CAMERA_PICTURE_URL, cameraPictureUrl)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        cameraPictureUrl = savedInstanceState.getParcelable(KEY_CAMERA_PICTURE_URL)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handleIntent(intent)
        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SELECT_PHOTO -> handleGalleryResult(data!!)
                TAKE_PHOTO -> ImagePicker(this).onImagePicked(this.cameraPictureUrl!!)
            }
        }
        finish()
    }

    private fun handleGalleryResult(data: Intent) {
        if (intent.getBooleanExtra(ALLOW_MULTIPLE_IMAGES, false)) {
            val imageUris = ArrayList<Uri>()
            val clipData = data.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    imageUris.add(clipData.getItemAt(i).uri)
                }
            } else {
                imageUris.add(data.data)
            }
            ImagePicker(this).onImagesPicked(imageUris)
        } else {
            ImagePicker(this).onImagePicked(data.data)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (!checkPermission()) {
            return
        }

        val sourceType = Sources.values()[intent.getIntExtra(IMAGE_SOURCE, 0)]
        var chooseCode = 0
        var pictureChooseIntent: Intent? = null

        when (sourceType) {
            Sources.CAMERA -> {
                val fileimage = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")
                if (Build.VERSION.SDK_INT >= 23) {
                    cameraPictureUrl = FileProvider.getUriForFile(baseContext,
                            BuildConfig.APPLICATION_ID + ".provider",
                            fileimage)
                } else {
                    cameraPictureUrl = Uri.fromFile(fileimage)
                }
                pictureChooseIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                pictureChooseIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUrl)
                chooseCode = TAKE_PHOTO
            }
            Sources.GALLERY -> {
                pictureChooseIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pictureChooseIntent.type = "image/*"
                chooseCode = SELECT_PHOTO
            }
        }

        startActivityForResult(pictureChooseIntent, chooseCode)
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0)
            return false
        } else {
            return true
        }
    }

    private fun createImageUri(): Uri? {
        val contentResolver = contentResolver
        val cv = ContentValues()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        cv.put(MediaStore.Images.Media.TITLE, timeStamp)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
    }
}
