package com.virtualtimetours.pixelmixer.ui.main.fragments

import android.graphics.Bitmap
import android.graphics.Color
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ImageSelectionFragmentTest {

    lateinit var scenario: FragmentScenario<ImageSelectionFragment>
    @Before
    fun setUp() {
        scenario = launchFragment<ImageSelectionFragment>()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun getViewModel() {
        scenario.onFragment { fragment ->
            assertNotNull(fragment.viewModel)
        }
    }

    @Test
    fun validateViewModel() {
        scenario.onFragment { fragment ->
            val viewModel = fragment.viewModel
            val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.WHITE)
            viewModel.onBitmapLoaded(bitmap, null)
            assertNotNull(viewModel.imageBitmap.value)
        }
    }
}