package com.layanacomputindo.bprcma.util

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import com.layanacomputindo.bprcma.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraActivity : AppCompatActivity() {
    var mCamera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_camera)
        mCamera = getCameraInstance()
        val mPreview = Preview(this, mCamera)
        val preview = findViewById<View>(R.id.camera_preview) as FrameLayout
        preview.addView(mPreview)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val screenCenterX = size.x / 2
        val screenCenterY = size.y / 2
        val mDraw = DrawOnTop(this, screenCenterX, screenCenterY)
        addContentView(mDraw, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

        //Adding listener
        val captureButton = findViewById<View>(R.id.button_capture) as Button
        captureButton.setOnClickListener{
            mCamera!!.takePicture(null, null, mPicture)
        }
    }

    private fun getCameraInstance(): Camera? {
        var camera: Camera? = null

        try {
            camera = Camera.open()
        } catch (e: Exception) {
            // cannot get camera or does not exist
        }

        return camera
    }

    var mPicture: PictureCallback = PictureCallback { data, camera ->
        val pictureFile = getOutputMediaFile() ?: return@PictureCallback
        try {
            val fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()
            Toast.makeText(this, "Photo saved to folder \"Pictures\\MyCameraApp\"", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {

        } catch (e: IOException) {

        }
    }

    private fun getOutputMediaFile(): File? {
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp")
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory")
                return null
            }
        }
        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val mediaFile: File
        mediaFile = File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg")

        return mediaFile
    }
}

internal class DrawOnTop(context: Context, screenCenterX: Int, screenCenterY: Int) : View(context) {
    var screenCenterX = 0
    var screenCenterY = 0
    val radius = 50

    init {
        this.screenCenterX = screenCenterX
        this.screenCenterY = screenCenterY
    }

    override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        val p = Paint()
        p.color = Color.RED
        val dashPath = DashPathEffect(floatArrayOf(5f, 5f), 1.0.toFloat())
        p.pathEffect = dashPath
        p.style = Style.STROKE
        canvas.drawCircle(screenCenterX.toFloat(), screenCenterY.toFloat(), radius.toFloat(), p)
        invalidate()
        super.onDraw(canvas)
    }
}



