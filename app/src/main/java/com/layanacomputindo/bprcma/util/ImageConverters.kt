package com.layanacomputindo.bprcma.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class ImageConverters {
    companion object {
        fun uriToFile(context: Context, uri: Uri, file: File): Observable<File> {
            return Observable.create(Observable.OnSubscribe<File> { subscriber ->
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    copyInputStreamToFile(inputStream!!, file)
                    subscriber.onNext(file)
                    subscriber.onCompleted()
                } catch (e: Exception) {
                    Log.e(ImageConverters::class.java.simpleName, "Error converting uri", e)
                    subscriber.onError(e)
                }
            })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun uriToBitmap(context: Context, uri: Uri): Observable<Bitmap> {
            return Observable.create(Observable.OnSubscribe<Bitmap> { subscriber ->
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    subscriber.onNext(bitmap)
                    subscriber.onCompleted()
                } catch (e: IOException) {
                    Log.e(ImageConverters::class.java.simpleName, "Error converting uri", e)
                    subscriber.onError(e)
                }
            })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        @Throws(IOException::class)
        private fun copyInputStreamToFile(inpt: InputStream, file: File) {
            val out = FileOutputStream(file)
            val buf = ByteArray(10 * 1024)
            val input: Int = inpt.read(buf)
            while (input > 0) {
                out.write(buf, 0, input)
            }
            out.close()
            inpt.close()
        }
    }
}