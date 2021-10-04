package com.virtualtimetours.pixelmixer.ui.main.viewmodels


import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImageSelectionViewModelTest {
    @Rule
    @JvmField
    public var rule: TestRule = InstantTaskExecutorRule()

    val initialString = "PixelMixer is an implementation of a 15 puzzle using a user selected image. Click Get Image to select the image that you want to use."

    lateinit var viewModelUnderTest : ImageSelectionViewModel
    @Before
    fun setup() {
        viewModelUnderTest = ImageSelectionViewModel()
    }

    @Test
    fun testOne() {
        assertNotNull(viewModelUnderTest.gameInfoText.value)
    }

    @Test
    fun testSetImage() {
        assertEquals(initialString, viewModelUnderTest.gameInfoText.value)
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        viewModelUnderTest.onBitmapLoaded(bitmap, null)
        Thread.sleep(1000)
        assertNotEquals(initialString, viewModelUnderTest.gameInfoText.value)
    }
}