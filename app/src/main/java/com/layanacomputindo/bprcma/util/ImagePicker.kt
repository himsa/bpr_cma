package com.layanacomputindo.bprcma.util

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import rx.Observable
import rx.subjects.PublishSubject

class ImagePicker(val context: Context) {
    private lateinit var instance: ImagePicker

    @Synchronized
    fun with(context: Context): ImagePicker {
        if (false) {
            instance = ImagePicker(context.applicationContext)
        }
        return instance
    }
    private var publishSubject: PublishSubject<Uri>? = null
    private var publishSubjectMultipleImages: PublishSubject<List<Uri>>? = null

    fun getActiveSubscription(): Observable<Uri>? {
        return publishSubject
    }

    fun requestImage(imageSource: Sources): Observable<Uri>? {
        publishSubject = PublishSubject.create()
        startImagePickHiddenActivity(imageSource.ordinal, false)
        return publishSubject
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun requestMultipleImages(): Observable<List<Uri>>? {
        publishSubjectMultipleImages = PublishSubject.create()
        startImagePickHiddenActivity(Sources.GALLERY.ordinal, true)
        return publishSubjectMultipleImages
    }

    internal fun onImagePicked(uri: Uri) {
        if (publishSubject != null) {
            publishSubject!!.onNext(uri)
            publishSubject!!.onCompleted()
        }
    }

    internal fun onImagesPicked(uris: List<Uri>) {
        if (publishSubjectMultipleImages != null) {
            publishSubjectMultipleImages!!.onNext(uris)
            publishSubjectMultipleImages!!.onCompleted()
        }
    }

    private fun startImagePickHiddenActivity(imageSource: Int, allowMultipleImages: Boolean) {
        val intent = Intent(context, PickImageHiddenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(PickImageHiddenActivity.ALLOW_MULTIPLE_IMAGES, allowMultipleImages)
        intent.putExtra(PickImageHiddenActivity.IMAGE_SOURCE, imageSource)
        context.startActivity(intent)
    }
}