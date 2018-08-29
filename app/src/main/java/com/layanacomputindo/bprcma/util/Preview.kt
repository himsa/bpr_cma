package com.layanacomputindo.bprcma.util

import android.content.Context
import android.hardware.Camera
import android.view.View
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.IOException


internal class Preview(context: Context, var mCamera: Camera?) : SurfaceView(context), SurfaceHolder.Callback {
    var mHolder: SurfaceHolder

    init {
        // underlying surface is created and destroyed.
        mHolder = holder
        mHolder.addCallback(this)
        //this is a deprecated method, is not required after 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }// Install a SurfaceHolder.Callback so we get notified when

    override fun surfaceCreated(holder: SurfaceHolder) {
        // The Surface has been created, acquire the camera and tell
        // to draw.
        try {
            mCamera!!.setPreviewDisplay(holder)
            mCamera!!.startPreview()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Surface will be destroyed when we return, so stop the
        // Because the CameraDevice object is not a shared resource,
        // important to release it when the activity is paused.
        mCamera!!.stopPreview()
        mCamera!!.release()
        mCamera = null
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        // Now that the size is known, set up the camera parameters
        // the preview.
        val parameters = mCamera!!.getParameters()
        val previewSizes = parameters.getSupportedPreviewSizes()
        // You need to choose the most appropriate previewSize for your app
        val previewSize = previewSizes.get(0)
        parameters.setPreviewSize(previewSize.width, previewSize.height)
        mCamera!!.setParameters(parameters)
        mCamera!!.startPreview()
    }


}