package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virtualtimetours.pixelmixer.PixelMixerApplication

class GameViewModel : ViewModel() {

    val totalMoves = MutableLiveData("0")
    val elapsedTime = MutableLiveData("1/1/1970")
    val fragments = MutableLiveData<List<List<BitmapDrawable>>>(listOf())
    val positionOneOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionOneTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionOneThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionOneFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwoOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwoTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwoThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwoFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThreeOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThreeTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThreeThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThreeFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourFourBitmap = MutableLiveData<BitmapDrawable>(null)

    /**
     * Take the original bitmap and break it into N pieces
     */
    fun fractureImage(bitmap: Bitmap, rows: Int, columns: Int) {
        val width = bitmap.width
        val height = bitmap.height
        val resultsArray = mutableListOf<List<BitmapDrawable>>()
        try {
            var count = 0
            var horizontalStep = width / columns
            var verticalStep = height / rows
            for (y in 0..height step verticalStep) {
                if(y == height) {
                    continue
                }
                val targetList = mutableListOf<BitmapDrawable>()
                for (x in 0..width step horizontalStep) {
                    if(x == width) {
                        continue
                    }
                    // TODO: Fix exception splitting bitmap
                    if (x + horizontalStep > width) {
                        horizontalStep = width - x
                    }
                    if (y + verticalStep > height) {
                        verticalStep = height - y
                    }
                    val smallBitmap =
                        Bitmap.createBitmap(bitmap, x, y, horizontalStep, verticalStep)
                    val drawable = BitmapDrawable(PixelMixerApplication.context.resources, smallBitmap)
                    targetList.add(drawable)
                }
                when(count) {
                    0-> updateRowOne(targetList)
                    1-> updateRowTwo(targetList)
                    2-> updateRowThree(targetList)
                    3->updateRowFour(targetList)
                }
                resultsArray.add(targetList.toList())
                horizontalStep = width / columns
                verticalStep = height / rows
                count++
            }
        } catch (e: Exception) {
            Log.e(TAG, "failed to split bitmap ${e.message}")
        }
        fragments.postValue(resultsArray)
    }

    private fun updateRowOne(bitmaps: List<BitmapDrawable>) {
        positionOneOneBitmap.postValue(bitmaps[0])
        positionOneTwoBitmap.postValue(bitmaps[1])
        positionOneThreeBitmap.postValue(bitmaps[2])
        positionOneFourBitmap.postValue(bitmaps[3])
    }

    private fun updateRowTwo(bitmaps: List<BitmapDrawable>) {
        positionTwoOneBitmap.postValue(bitmaps[0])
        positionTwoTwoBitmap.postValue(bitmaps[1])
        positionTwoThreeBitmap.postValue(bitmaps[2])
        positionTwoFourBitmap.postValue(bitmaps[3])
    }

    private fun updateRowThree(bitmaps: List<BitmapDrawable>) {
        positionThreeOneBitmap.postValue(bitmaps[0])
        positionThreeTwoBitmap.postValue(bitmaps[1])
        positionThreeThreeBitmap.postValue(bitmaps[2])
        positionThreeFourBitmap.postValue(bitmaps[3])
    }

    private fun updateRowFour(bitmaps: List<BitmapDrawable>) {
        positionFourOneBitmap.postValue(bitmaps[0])
        positionFourTwoBitmap.postValue(bitmaps[1])
        positionFourThreeBitmap.postValue(bitmaps[2])
//        positionFourFourBitmap.postValue(bitmaps[3])
    }

    companion object {
        val TAG = GameViewModel::javaClass.name
    }
}