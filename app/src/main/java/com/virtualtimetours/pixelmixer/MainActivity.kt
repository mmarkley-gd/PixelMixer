package com.virtualtimetours.pixelmixer

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pixelmixer.R
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.UnsplashPickerActivity
import com.virtualtimetours.pixelmixer.ui.main.fragments.ImageSelectionFragment
import com.virtualtimetours.pixelmixer.ui.main.viewmodels.ImageSelectionViewModel

class MainActivity : AppCompatActivity() {

    var imageViewModel: ImageSelectionViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ImageSelectionFragment())
                .commitNow()
        }
    }

    val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        if (Activity.RESULT_OK == it.resultCode) {
            val photos: ArrayList<UnsplashPhoto> =
                it.data?.getParcelableArrayListExtra(UnsplashPickerActivity.EXTRA_PHOTOS)!!
            if (!photos.isNullOrEmpty()) {
                val photo = photos.first()
                Log.i("MainActivity", "${photo.id} ${photo.description} ${photo.user.name}")
                Log.i("MainActivity", "${photo.urls.full}")
                imageViewModel?.setImage(photo)
            }
        } else {
            Toast.makeText(this, getString(R.string.load_failure), Toast.LENGTH_LONG).show()
        }
    }

    public fun launchImagePicker(viewModel: ImageSelectionViewModel) {
        imageViewModel = viewModel
        val intent = UnsplashPickerActivity.getStartingIntent(
            this, // context
            false
        )

        imagePickerLauncher.launch(intent)
    }
}