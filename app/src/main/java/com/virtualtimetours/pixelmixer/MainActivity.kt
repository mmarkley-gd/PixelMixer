package com.virtualtimetours.pixelmixer

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pixelmixer.R
import com.example.pixelmixer.databinding.MainActivityBinding
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.UnsplashPickerActivity
import com.virtualtimetours.pixelmixer.ui.main.fragments.ImageSelectionFragment
import com.virtualtimetours.pixelmixer.ui.main.viewmodels.ImageSelectionViewModel

/**
 * This is the only Activity that is created. It uses AndroidX Navigation to control
 * which part of the UX is visible
 */
class MainActivity : AppCompatActivity() {

    private var imageViewModel: ImageSelectionViewModel? = null
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private val imagePickerLauncher = registerForActivityResult(
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

    /**
     * move the UX to the game screen.
     */
    fun navigateToGameScreen() {
        navController.navigate(R.id.gameFragment)
    }

    /**
     * Method used by the [ImageSelectionFragment] to get the Activity to launch
     * the Unsplash Image Picker. We don't allow multiple selection, just a single
     * selection for creating puzzles
     */
    fun launchImagePicker(viewModel: ImageSelectionViewModel) {
        imageViewModel = viewModel
        val intent = UnsplashPickerActivity.getStartingIntent(
            this, // context
            false
        )

        imagePickerLauncher.launch(intent)
    }
}