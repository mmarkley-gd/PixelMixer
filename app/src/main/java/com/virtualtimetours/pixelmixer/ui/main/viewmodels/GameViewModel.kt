package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virtualtimetours.pixelmixer.PixelMixerApplication
import com.virtualtimetours.pixelmixer.ui.main.GameTile
import java.util.concurrent.ScheduledThreadPoolExecutor
import kotlin.random.Random

/**
 * A [ViewModel] to hold information related to game play
 */
class GameViewModel : ViewModel() {

    private var totalMoves = 0
    val totalMovesText = MutableLiveData("0")
    val elapsedTime = MutableLiveData("1/1/1970")
    private val gameGrid = MutableLiveData<List<List<GameTile>>>(listOf())

    val positionOneOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionOneTagValue = MutableLiveData<GameTile>(null)
    val positionOneDebugTextValue = MutableLiveData("1")
    val positionOneTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwoTagValue = MutableLiveData<GameTile>(null)
    val positionTwoDebugTextValue = MutableLiveData("2")
    val positionOneThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThreeTagValue = MutableLiveData<GameTile>(null)
    val positionThreeDebugTextValue = MutableLiveData("3")
    val positionOneFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourTagValue = MutableLiveData<GameTile>(null)
    val positionFourDebugTextValue = MutableLiveData("4")
    val positionTwoOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFiveTagValue = MutableLiveData<GameTile>(null)
    val positionFiveDebugTextValue = MutableLiveData("5")
    val positionTwoTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionSixTagValue = MutableLiveData<GameTile>(null)
    val positionSixDebugTextValue = MutableLiveData("6")
    val positionTwoThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionSevenTagValue = MutableLiveData<GameTile>(null)
    val positionSevenDebugTextValue = MutableLiveData("7")
    val positionTwoFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionEightTagValue = MutableLiveData<GameTile>(null)
    val positionEightDebugTextValue = MutableLiveData("8")
    val positionThreeOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionNineTagValue = MutableLiveData<GameTile>(null)
    val positionNineDebugTextValue = MutableLiveData("9")
    val positionThreeTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTenTagValue = MutableLiveData<GameTile>(null)
    val positionTenDebugTextValue = MutableLiveData("10")
    val positionThreeThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionElevenTagValue = MutableLiveData<GameTile>(null)
    val positionElevenDebugTextValue = MutableLiveData("11")
    val positionThreeFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionTwelveTagValue = MutableLiveData<GameTile>(null)
    val positionTwelveDebugTextValue = MutableLiveData("12")
    val positionFourOneBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionThirteenTagValue = MutableLiveData<GameTile>(null)
    val positionThirteenDebugTextValue = MutableLiveData("13")
    val positionFourTwoBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFourteenTagValue = MutableLiveData<GameTile>(null)
    val positionFourteenDebugTextValue = MutableLiveData("14")
    val positionFourThreeBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionFifteenTagValue = MutableLiveData<GameTile>(null)
    val positionFifteenDebugTextValue = MutableLiveData("15")
    val positionFourFourBitmap = MutableLiveData<BitmapDrawable>(null)
    val positionSixteenTagValue = MutableLiveData<GameTile>(null)
    val positionSixteenDebugTextValue = MutableLiveData("16")

    val debugDisplayTextVisibility = MutableLiveData(View.VISIBLE)

    private var gameTiles: MutableList<GameTile> = mutableListOf()

    private val gameBoardRowOne: MutableList<GameTile> = mutableListOf()
    private val gameBoardRowTwo: MutableList<GameTile> = mutableListOf()
    private val gameBoardRowThree: MutableList<GameTile> = mutableListOf()
    private val gameBoardRowFour: MutableList<GameTile> = mutableListOf()

    private var emptyTile: GameTile? = null

    /**
     * Take the original [bitmap] and break it into N based on [rows] and [columns] pieces. Do it
     * in a background thread to not block the UI
     */
    fun fractureImage(bitmap: Bitmap, rows: Int, columns: Int) {
        executor.execute {
            val width = bitmap.width.toDouble()
            val height = bitmap.height.toDouble()
            val resultsArray = mutableListOf<List<GameTile>>()
            var emptyBitmapDrawable: BitmapDrawable? = null
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
                        if(null == emptyBitmapDrawable) {
                            smallBitmap.eraseColor(Color.WHITE)
                            emptyBitmapDrawable = BitmapDrawable(PixelMixerApplication.context.resources, smallBitmap)
                        }
                        val gameTile = when(tileCount) {
                            16 -> {
                                emptyTile = GameTile(emptyBitmapDrawable, tileCount++)
                                emptyTile
                            }
                            else -> GameTile(drawable, tileCount++)
                        }
                        Log.i(TAG, "created tile $gameTile for index $tileCount-1")
                        gameTiles.add(gameTile!!)
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

            emptyTile = GameTile(emptyBitmapDrawable!!, 16)

            var shuffled = shuffleTiles(gameTiles)
            while (!newShuffleIsSolvable(shuffled)) {
                shuffled = shuffleTiles(gameTiles)
            }
            gameBoardRowOne.clear()
            gameBoardRowOne.addAll(shuffled.subList(0, 4))

            gameBoardRowTwo.clear()
            gameBoardRowTwo.addAll(shuffled.subList(4, 8))

            gameBoardRowThree.clear()
            gameBoardRowThree.addAll(shuffled.subList(8, 12))

            gameBoardRowFour.clear()
            gameBoardRowFour.addAll(shuffled.subList(12, 16))

            resultsArray.clear()
            resultsArray.add(gameBoardRowOne)
            resultsArray.add(gameBoardRowTwo)
            resultsArray.add(gameBoardRowThree)
            resultsArray.add(gameBoardRowFour)
            updateRowOne(gameBoardRowOne)
            updateRowTwo(gameBoardRowTwo)
            updateRowThree(gameBoardRowThree)
            updateRowFour(gameBoardRowFour)
            gameGrid.postValue(resultsArray)
        }
    }

    private fun updateRowOne(tiles: List<GameTile>) {
        positionOneOneBitmap.postValue(tiles[0].bitmap)
        positionOneTagValue.postValue(tiles[0])
        positionOneDebugTextValue.postValue(tiles[0].position.toString())

        positionOneTwoBitmap.postValue(tiles[1].bitmap)
        positionTwoTagValue.postValue(tiles[1])
        positionTwoDebugTextValue.postValue(tiles[1].position.toString())

        positionOneThreeBitmap.postValue(tiles[2].bitmap)
        positionThreeTagValue.postValue(tiles[2])
        positionThreeDebugTextValue.postValue(tiles[2].position.toString())

        positionOneFourBitmap.postValue(tiles[3].bitmap)
        positionFourTagValue.postValue(tiles[3])
        positionFourDebugTextValue.postValue(tiles[3].position.toString())
    }

    private fun updateRowTwo(tiles: List<GameTile>) {
        positionTwoOneBitmap.postValue(tiles[0].bitmap)
        positionFiveTagValue.postValue(tiles[0])
        positionFiveDebugTextValue.postValue(tiles[0].position.toString())

        positionTwoTwoBitmap.postValue(tiles[1].bitmap)
        positionSixTagValue.postValue(tiles[1])
        positionSixDebugTextValue.postValue(tiles[1].position.toString())

        positionTwoThreeBitmap.postValue(tiles[2].bitmap)
        positionSevenTagValue.postValue(tiles[2])
        positionSevenDebugTextValue.postValue(tiles[2].position.toString())

        positionTwoFourBitmap.postValue(tiles[3].bitmap)
        positionEightTagValue.postValue(tiles[3])
        positionEightDebugTextValue.postValue(tiles[3].position.toString())
    }

    private fun updateRowThree(tiles: List<GameTile>) {
        positionThreeOneBitmap.postValue(tiles[0].bitmap)
        positionNineTagValue.postValue(tiles[0])
        positionNineDebugTextValue.postValue(tiles[0].position.toString())

        positionThreeTwoBitmap.postValue(tiles[1].bitmap)
        positionTenTagValue.postValue(tiles[1])
        positionTenDebugTextValue.postValue(tiles[1].position.toString())

        positionThreeThreeBitmap.postValue(tiles[2].bitmap)
        positionElevenTagValue.postValue(tiles[2])
        positionElevenDebugTextValue.postValue(tiles[2].position.toString())

        positionThreeFourBitmap.postValue(tiles[3].bitmap)
        positionTwelveTagValue.postValue(tiles[3])
        positionTwelveDebugTextValue.postValue(tiles[3].position.toString())
    }

    /**
     * Updates row four of the game board using the values in [tiles]
     */
    private fun updateRowFour(tiles: List<GameTile>) {
        positionFourOneBitmap.postValue(tiles[0].bitmap)
        positionThirteenTagValue.postValue(tiles[0])
        positionThirteenDebugTextValue.postValue(tiles[0].position.toString())

        positionFourTwoBitmap.postValue(tiles[1].bitmap)
        positionFourteenTagValue.postValue(tiles[1])
        positionFourteenDebugTextValue.postValue(tiles[1].position.toString())

        positionFourThreeBitmap.postValue(tiles[2].bitmap)
        positionFifteenTagValue.postValue(tiles[2])
        positionFifteenDebugTextValue.postValue(tiles[2].position.toString())

        positionSixteenDebugTextValue.postValue(tiles[3].position.toString())
        positionSixteenTagValue.postValue(tiles[3])
        positionFourFourBitmap.postValue(tiles[3].bitmap)
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

    /**
     * Validates that the shuffled [listToCheck] can actually be solved in a 15-square puzzle
     * Returns true if solvable, false otherwise
     */
    private fun newShuffleIsSolvable(listToCheck: List<GameTile>): Boolean {
        var countInversions = 0

        for (i in listToCheck.indices) {
            for (j in 0 until i) {
                if (listToCheck[j].position > listToCheck[i].position)
                    countInversions++
            }
        }

        return countInversions % 2 == 0
    }

    /**
     * Check the given [listToCheck] and determine if it meets the criteria for a completed
     * game. Returns true if the game is solved, false otherwise
     */
    private fun isSolved(listToCheck: List<GameTile>): Boolean {
        if (listToCheck[listToCheck.size - 1].position != 0) // if blank tile is not in the solved position ==> not solved
            return false
        for (i in listToCheck.size - 1 downTo 0) {
            if (listToCheck[i].position != (i + 1)) return false
        }
        return true
    }

    /**
     * Given the specified [tile], can that [GameTile] be moved? This is based on whether or not
     * Tile 16 is either in the same row as the specified tile, or the same column
     * Returns true if the [GameTile] can be dragged, false otherwise
     */
    fun tileCanBeDragged(tile: GameTile): Boolean {
        val tileRowIndex: Int = getRowIndex(tile)
        val tileColumnIndex: Int = getColumnIndex(tile)
        val emptyTileRowIndex: Int = getRowIndex(emptyTile)
        val emptyTileColumnIndex: Int = getColumnIndex(emptyTile)

        return (tileRowIndex == emptyTileRowIndex
                || tileColumnIndex == emptyTileColumnIndex)
    }

    fun swapTiles(sourceTile: GameTile, destinationTile: GameTile) {
        if(destinationTile != emptyTile) {
            return
        }
        val sourceTileRowIndex: Int = getRowIndex(sourceTile)
        val sourceTileColumnIndex: Int = getColumnIndex(sourceTile)
        val destinationTileRowIndex: Int = getRowIndex(destinationTile)
        val destinationTileColumnIndex: Int = getColumnIndex(destinationTile)
        if (sourceTileRowIndex == destinationTileRowIndex) {
            when (sourceTileRowIndex) {
                1 -> {
                    Log.i(TAG, "switching row 1 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex")
                    gameBoardRowOne[destinationTileColumnIndex] = sourceTile
                    gameBoardRowOne[sourceTileColumnIndex] = destinationTile
                    updateRowOne(gameBoardRowOne)
                }
                2 -> {
                    Log.i(TAG, "switching row 2 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex")
                    gameBoardRowTwo[destinationTileColumnIndex] = sourceTile
                    gameBoardRowTwo[sourceTileColumnIndex] = destinationTile
                    updateRowTwo(gameBoardRowTwo)
                }
                3 -> {
                    Log.i(TAG, "switching row 3 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex")
                    gameBoardRowThree[destinationTileColumnIndex] = sourceTile
                    gameBoardRowThree[sourceTileColumnIndex] = destinationTile
                    updateRowThree(gameBoardRowThree)
                }
                4 -> {
                    Log.i(TAG, "switching row 4 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex")
                    gameBoardRowFour[destinationTileColumnIndex] = sourceTile
                    gameBoardRowFour[sourceTileColumnIndex] = destinationTile
                    updateRowFour(gameBoardRowFour)
                }
            }
        } else {
            val emptyRow = when (destinationTileRowIndex) {
                1 -> gameBoardRowOne
                2 -> gameBoardRowTwo
                3 -> gameBoardRowThree
                4 -> gameBoardRowFour
                else -> null
            }
            val targetRow = when (sourceTileRowIndex) {
                1 -> gameBoardRowOne
                2 -> gameBoardRowTwo
                3 -> gameBoardRowThree
                4 -> gameBoardRowFour
                else -> null
            }
            if (emptyRow != null && targetRow != null) {
                Log.i(TAG, "moving tile from row $sourceTileRowIndex to row $destinationTileRowIndex")
                emptyRow[destinationTileColumnIndex] = sourceTile
                targetRow[sourceTileColumnIndex] = destinationTile
                when (destinationTileRowIndex) {
                    1 -> updateRowOne(gameBoardRowOne)
                    2 -> updateRowTwo(gameBoardRowTwo)
                    3 -> updateRowThree(gameBoardRowThree)
                    4 -> updateRowFour(gameBoardRowFour)
                    else -> {
                    }
                }

                when (sourceTileRowIndex) {
                    1 -> updateRowOne(gameBoardRowOne)
                    2 -> updateRowTwo(gameBoardRowTwo)
                    3 -> updateRowThree(gameBoardRowThree)
                    4 -> updateRowFour(gameBoardRowFour)
                    else -> {
                    }
                }
            }
        }

    }

    private fun getRowIndex(tile: GameTile?): Int {
        if (gameBoardRowOne.contains(tile)) {
            return 1
        }
        if (gameBoardRowTwo.contains(tile)) {
            return 2
        }
        if (gameBoardRowThree.contains(tile)) {
            return 3
        }
        if (gameBoardRowFour.contains(tile)) {
            return 4
        }
        return -1
    }

    private fun getColumnIndex(tile: GameTile?): Int {
        if (gameBoardRowOne.contains(tile)) {
            return gameBoardRowOne.indexOf(tile)
        }
        if (gameBoardRowTwo.contains(tile)) {
            return gameBoardRowTwo.indexOf(tile)
        }
        if (gameBoardRowThree.contains(tile)) {
            return gameBoardRowThree.indexOf(tile)
        }
        if (gameBoardRowFour.contains(tile)) {
            return gameBoardRowFour.indexOf(tile)
        }
        return -1
    }

    companion object {
        val TAG = GameViewModel::javaClass.name
        val executor = ScheduledThreadPoolExecutor(2)
    }
}