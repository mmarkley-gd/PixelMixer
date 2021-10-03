package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virtualtimetours.pixelmixer.PixelMixerApplication
import com.virtualtimetours.pixelmixer.ui.main.GameTile
import java.util.concurrent.ScheduledThreadPoolExecutor
import kotlin.random.Random

class GameViewModel : ViewModel() {

    var totalMoves = 0
    val totalMovesText = MutableLiveData("0")
    val elapsedTime = MutableLiveData("1/1/1970")
    private val gameGrid = MutableLiveData<List<List<GameTile>>>(listOf())

    val positionOneOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionOneTagValue = MutableLiveData("1")
    val positionOneTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwoTagValue = MutableLiveData("2")
    val positionOneThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThreeTagValue = MutableLiveData("3")
    val positionOneFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourTagValue = MutableLiveData("4")
    val positionTwoOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFiveTagValue = MutableLiveData("5")
    val positionTwoTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionSixTagValue = MutableLiveData("6")
    val positionTwoThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionSevenTagValue = MutableLiveData("7")
    val positionTwoFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionEightTagValue = MutableLiveData("8")
    val positionThreeOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionNineTagValue = MutableLiveData("9")
    val positionThreeTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTenTagValue = MutableLiveData("10")
    val positionThreeThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionElevenTagValue = MutableLiveData("11")
    val positionThreeFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwelveTagValue = MutableLiveData("12")
    val positionFourOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThirteenTagValue = MutableLiveData("13")
    val positionFourTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourteenTagValue = MutableLiveData("14")
    val positionFourThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFifteenTagValue = MutableLiveData("15")
    val positionFourFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionSixteenTagValue = MutableLiveData("16")

    val debugDisplayTextVisibility = MutableLiveData(View.VISIBLE)

    private var gameTiles: MutableList<GameTile> = mutableListOf()

    /**
     * Take the original bitmap and break it into N pieces. Do it in a background
     * thread to not block the UI
     */
    fun fractureImage(bitmap: Bitmap, rows: Int, columns: Int) {
        executor.execute {
            val width = bitmap.width.toDouble()
            val height = bitmap.height.toDouble()
            val resultsArray = mutableListOf<List<GameTile>>()
            try {
                var count = 0
                var rowCount = 0
                var tileCount = 1
                var horizontalStep = width / columns
                var verticalStep = height / rows

                var y = 0.0
                while (y < height) {
                    if (y >= height) {
                        continue
                    }
//                    Log.i(TAG, "processing row $rowCount")
                    rowCount++
                    var columnCount = 0
                    val targetList = mutableListOf<BitmapDrawable>()
                    var x = 0.0
                    while (x < width) {
                        if (x >= width) {
                            continue
                        }
//                        Log.i(TAG, "processing column $columnCount")
                        columnCount++
                        if (x + horizontalStep > width) {
                            horizontalStep = width - x.toInt()
                        }
                        if (y + verticalStep > height) {
                            verticalStep = height - y
                        }
                        val xCoord = x.toInt()
                        val yCoord = y.toInt()
                        val copyWidth = horizontalStep.toInt()
                        val copyHeight = verticalStep.toInt()
//                        Log.i(TAG, "Creating tile: $tileCount")
                        val smallBitmap =
                            Bitmap.createBitmap(bitmap, xCoord, yCoord, copyWidth, copyHeight)
                        val drawable =
                            BitmapDrawable(PixelMixerApplication.context.resources, smallBitmap)
                        val gameTile = GameTile(drawable, tileCount++)
                        gameTiles.add(gameTile)
                        targetList.add(drawable)
                        x += horizontalStep
                    }
                    horizontalStep = width / columns
                    verticalStep = height / rows
                    y += verticalStep
                    count++
                }
            } catch (e: Exception) {
                Log.e(TAG, "failed to split bitmap ${e.message}")
            }
            var shuffled = shuffleTiles(gameTiles)
            while (!newShuffleIsSolvable(shuffled)) {
                shuffled = shuffleTiles(gameTiles)
            }
            val rowOne = shuffled.subList(0, 3)
            val rowTwo = shuffled.subList(4,7)
            val rowThree = shuffled.subList(8, 11)
            val rowFour = shuffled.subList(12,15)
            resultsArray.clear()
            resultsArray.add(rowOne)
            resultsArray.add(rowTwo)
            resultsArray.add(rowThree)
            resultsArray.add(rowFour)
            updateRowOne(shuffled)
            updateRowTwo(shuffled)
            updateRowThree(shuffled)
            updateRowFour(shuffled)
            gameGrid.postValue(resultsArray)
        }
    }

    private fun updateRowOne(tiles: List<GameTile>) {
        positionOneOneBitmap.postValue(tiles[0].bitmap)
        positionOneTagValue.postValue(tiles[0].position.toString())
        positionOneTwoBitmap.postValue(tiles[1].bitmap)
        positionTwoTagValue.postValue(tiles[1].position.toString())
        positionOneThreeBitmap.postValue(tiles[2].bitmap)
        positionThreeTagValue.postValue(tiles[2].position.toString())
        positionOneFourBitmap.postValue(tiles[3].bitmap)
        positionFourTagValue.postValue(tiles[3].position.toString())
    }

    private fun updateRowTwo(tiles: List<GameTile>) {
        positionTwoOneBitmap.postValue(tiles[4].bitmap)
        positionFiveTagValue.postValue(tiles[4].position.toString())
        positionTwoTwoBitmap.postValue(tiles[5].bitmap)
        positionSixTagValue.postValue(tiles[5].position.toString())
        positionTwoThreeBitmap.postValue(tiles[6].bitmap)
        positionSevenTagValue.postValue(tiles[6].position.toString())
        positionTwoFourBitmap.postValue(tiles[7].bitmap)
        positionEightTagValue.postValue(tiles[7].position.toString())
    }

    private fun updateRowThree(tiles: List<GameTile>) {
        positionThreeOneBitmap.postValue(tiles[8].bitmap)
        positionNineTagValue.postValue(tiles[8].position.toString())
        positionThreeTwoBitmap.postValue(tiles[9].bitmap)
        positionTenTagValue.postValue(tiles[9].position.toString())
        positionThreeThreeBitmap.postValue(tiles[10].bitmap)
        positionElevenTagValue.postValue(tiles[10].position.toString())
        positionThreeFourBitmap.postValue(tiles[11].bitmap)
        positionTwelveTagValue.postValue(tiles[11].position.toString())
    }

    private fun updateRowFour(tiles: List<GameTile>) {
        positionFourOneBitmap.postValue(tiles[12].bitmap)
        positionThirteenTagValue.postValue(tiles[12].position.toString())
        positionFourTwoBitmap.postValue(tiles[13].bitmap)
        positionFourteenTagValue.postValue(tiles[13].position.toString())
        positionFourThreeBitmap.postValue(tiles[14].bitmap)
        positionFifteenTagValue.postValue(tiles[14].position.toString())
    }

    fun incrementMoveCount() {
        totalMoves++

        totalMovesText.postValue(totalMoves.toString())
    }

    private fun shuffleTiles(listToShuffle: MutableList<GameTile>): List<GameTile> {
        val result = mutableListOf<GameTile>()
        result.addAll(listToShuffle)
        var n = result.size

        while (n > 1) {
            val r: Int = Random.nextInt(n--)
            val tmp = result[r]

            result[r] = result[n]
            result[n] = tmp
        }

        return result
    }

    private fun newShuffleIsSolvable(listToCheck: List<GameTile>): Boolean {
        var countInversions = 0

        for (i in 0 until listToCheck.size) {
            for (j in 0 until i) {
                if (listToCheck[j].position > listToCheck[i].position)
                    countInversions++
            }
        }

        return countInversions % 2 == 0
    }


    private fun isSolved(listToCheck: List<GameTile>): Boolean {
        if (listToCheck[listToCheck.size - 1].position != 0) // if blank tile is not in the solved position ==> not solved
            return false
        for (i in listToCheck.size - 1 downTo 0) {
            if (listToCheck[i].position != (i + 1)) return false
        }
        return true
    }

    companion object {
        val TAG = GameViewModel::javaClass.name
        val executor = ScheduledThreadPoolExecutor(2)
    }
}