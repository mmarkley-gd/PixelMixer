package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    val totalMoves = MutableLiveData("0")
    val elapsedTime = MutableLiveData("1/1/1970")
    val fragments = MutableLiveData<List<List<Bitmap>>>(listOf())

    /**
     * Take the original bitmap and break it into N pieces
     */
    fun fractureImage(bitmap: Bitmap, rows: Int, columns: Int) {
        val width = bitmap.width
        val height = bitmap.height
        val horizontalStep = width / columns
        val verticalStep = height / rows
        val resultsArray = mutableListOf<List<Bitmap>>()
        try {
            for(x in 0..width step horizontalStep) {
                var columnPosition = 0
                val targetList = mutableListOf<Bitmap>()
                for(y in 0..height step verticalStep) {
                    val smallBitmap = Bitmap.createBitmap(bitmap, x, y,horizontalStep, verticalStep)
                    targetList.add(smallBitmap)
                    columnPosition++
                }
                resultsArray[x] = targetList.toList()
            }
        } catch (e: Exception) {
            Log.e(TAG, "failed to split bitmap ${e.message}")
        }
        fragments.postValue(resultsArray)
    }

    companion object {
        val TAG = GameViewModel::javaClass.name
    }
}