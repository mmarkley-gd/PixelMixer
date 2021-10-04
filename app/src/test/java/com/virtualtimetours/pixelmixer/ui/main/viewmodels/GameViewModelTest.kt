package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import android.graphics.Bitmap
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class GameViewModelTest {
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModelUnderTest: GameViewModel
    @Before
    fun setUp() {
        viewModelUnderTest = GameViewModel()
    }

    @Test
    fun basicTest() {
        assertNotNull(viewModelUnderTest)
    }

    @Test
    fun validateFractureBitmap() {
        assertNull(viewModelUnderTest.positionFourFourBitmap.value)
        assertNull(viewModelUnderTest.positionOneOneBitmap.value)
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        viewModelUnderTest.fractureComplete.observeForever {
            if(it) {
                assertNotNull(viewModelUnderTest.positionOneOneBitmap.value)
                val gameTile = viewModelUnderTest.positionEightTagValue.value
                assertNotNull(gameTile)
                assertEquals(8, gameTile!!.position)
                assertNotNull(gameTile!!.bitmap)
                assertEquals(16, viewModelUnderTest.gameTiles.size)
                assertEquals(View.VISIBLE, viewModelUnderTest.gameUIVisible.value)
                assertEquals(View.GONE, viewModelUnderTest.gameWonUIVisible.value)
            }
        }

        viewModelUnderTest.fractureImage(bitmap, 4, 4)
    }

}