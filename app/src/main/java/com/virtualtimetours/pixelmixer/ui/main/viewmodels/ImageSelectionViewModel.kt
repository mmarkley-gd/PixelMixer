package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.virtualtimetours.pixelmixer.ui.main.fragments.ImageSelectionFragment
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ImageSelectionViewModel : ViewModel(), Target {

    val imageBitmap = MutableLiveData<Bitmap?>(null)

    fun setImage(uri: String?) {
        if(null != uri) {
            Picasso.get().load(uri).into(this)
        }
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        Log.i(TAG, "loaded bitmap")
        imageBitmap.postValue(bitmap)
    }

    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
        Log.i(TAG, "failed to load bitmap ${e?.message}")
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        Log.i(TAG, "prepar to load")
    }

    companion object {
        val TAG: String = ImageSelectionFragment::javaClass.name
    }
}
