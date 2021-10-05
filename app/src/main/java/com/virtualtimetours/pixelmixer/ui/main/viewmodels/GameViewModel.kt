package com.virtualtimetours.pixelmixer.ui.main.viewmodels

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virtualtimetours.pixelmixer.PixelMixerApplication
import com.virtualtimetours.pixelmixer.data.DragData
import com.virtualtimetours.pixelmixer.ui.main.GameTile
import com.virtualtimetours.pixelmixer.ui.main.fragments.GameFragment
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ScheduledThreadPoolExecutor
import kotlin.math.sign
import kotlin.random.Random

/**
 * A [ViewModel] to hold information related to game play
 */
class GameViewModel : ViewModel() {

    private var totalMoves = 0
    private var timer: Timer? = null
    private var elapsedMilliseconds: Long = 0

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

    val gameWonUIVisible = MutableLiveData(View.GONE)
    val gameUIVisible = MutableLiveData(View.VISIBLE)

    val tileHintsTextVisibility = MutableLiveData(View.GONE)

    var gameTiles: MutableList<GameTile> = mutableListOf()

    private val gameBoardRowOne: MutableList<GameTile> = mutableListOf()
    private val gameBoardRowTwo: MutableList<GameTile> = mutableListOf()
    private val gameBoardRowThree: MutableList<GameTile> = mutableListOf()
    private val gameBoardRowFour: MutableList<GameTile> = mutableListOf()

    private var emptyTile: GameTile? = null

    val fractureComplete = MutableLiveData(false)

    /**
     * Take the original [bitmap] and break it into N based on [rows] and [columns] pieces. Do it
     * in a background thread to not block the UI
     */
    fun fractureImage(bitmap: Bitmap, rows: Int, columns: Int) {
        executor.execute {
            gameWonUIVisible.postValue(View.GONE)
            gameUIVisible.postValue(View.VISIBLE)
            gameTiles.clear()
            val width = bitmap.width.toDouble()
            val height = bitmap.height.toDouble()
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
                    var x = 0.0
                    while (x < width) {
                        if (x >= width) {
                            continue
                        }
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
                        val smallBitmap =
                            Bitmap.createBitmap(bitmap, xCoord, yCoord, copyWidth, copyHeight)
                        val drawable =
                            BitmapDrawable(PixelMixerApplication.context.resources, smallBitmap)
                        if (null == emptyBitmapDrawable) {
                            val emptyBitmap = Bitmap.createBitmap(smallBitmap)
                            emptyBitmap.eraseColor(Color.WHITE)
                            emptyBitmapDrawable =
                                BitmapDrawable(PixelMixerApplication.context.resources, emptyBitmap)
                        }
                        val gameTile = when (tileCount) {
                            16 -> {
                                emptyTile = GameTile(emptyBitmapDrawable, tileCount++)
                                emptyTile
                                break
                            }
                            else -> GameTile(drawable, tileCount++)
                        }
                        Log.i(TAG, "created tile $gameTile for index ${tileCount - 1}")
                        gameTiles.add(gameTile)
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

            shuffleTilesToWinnableGame(gameTiles)

            fractureComplete.postValue(true)
        }
    }

    private fun getTimerTask(): TimerTask {
        return object : TimerTask() {
            override fun run() {
                elapsedMilliseconds += 1000
                val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
                val date = Date(elapsedMilliseconds)
                dateFormat.timeZone = TimeZone.getDefault()
                val timeString = dateFormat.format(date)
                elapsedTime.postValue(timeString)
            }
        }
    }

    private fun shuffleTilesToWinnableGame(tiles: List<GameTile>) {
        val tilesToShuffle = mutableListOf<GameTile>()
        val resultsArray = mutableListOf<List<GameTile>>()
        tilesToShuffle.addAll(tiles)
        var shuffled = shuffleTiles(tilesToShuffle)
        while (!newShuffleIsSolvable(shuffled)) {
            tilesToShuffle.clear()
            tilesToShuffle.addAll(tiles)
            shuffled = shuffleTiles(tilesToShuffle)
        }
        shuffled.add(emptyTile!!)
        gameBoardRowOne.clear()
        gameBoardRowOne.addAll(shuffled.subList(0, 4))

        gameBoardRowTwo.clear()
        gameBoardRowTwo.addAll(shuffled.subList(4, 8))

        gameBoardRowThree.clear()
        gameBoardRowThree.addAll(shuffled.subList(8, 12))

        gameBoardRowFour.clear()
        gameBoardRowFour.addAll(shuffled.subList(12, 16))

        Log.i(TAG, "fractureImage complete")
        debugDumpGameGrid()

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
        timer = Timer()
        timer?.scheduleAtFixedRate(getTimerTask(), 1000, 1000)
    }

    private fun shuffleTiles(listToShuffle: MutableList<GameTile>): MutableList<GameTile> {
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
     * Check and determine if the current puzzle has been solved
     * Returns true if the puzzle is solved, false otherwise
     */
    fun isSolved(): Boolean {
        val listToCheck = mutableListOf<GameTile>()
        listToCheck.addAll(gameBoardRowOne)
        listToCheck.addAll(gameBoardRowTwo)
        listToCheck.addAll(gameBoardRowThree)
        listToCheck.addAll(gameBoardRowFour)
        if (listToCheck[listToCheck.size - 1].position != 16) // if blank tile is not in the solved position ==> not solved
            return false
        for (i in listToCheck.size - 1 downTo 0) {
            if (listToCheck[i].position != (i + 1)) return false
        }
        gameWonUIVisible.postValue(View.VISIBLE)
        gameUIVisible.postValue(View.GONE)
        return true
    }

    /**
     * Given the specified [tile], can that [GameTile] be moved? This is based on whether or not
     * Tile 16 is either in the same row as the specified tile, or the same column
     * Returns true if the [GameTile] can be dragged, false otherwise
     * The current implementation only allows for sliding the tiles immediately adjacent to the
     * empty spot on the game grid
     */
    fun tileCanBeDragged(tile: GameTile): DragData? {
        val tileRowIndex: Int = getRowIndex(tile)
        val tileColumnIndex: Int = getColumnIndex(tile)
        val emptyTileRowIndex: Int = getRowIndex(emptyTile)
        val emptyTileColumnIndex: Int = getColumnIndex(emptyTile)

        if (tile == emptyTile!!) {
            return null
        }
        val result = when (tileRowIndex == emptyTileRowIndex
                || tileColumnIndex == emptyTileColumnIndex) {
            true -> {
                val tileSet = mutableListOf<GameTile>()
                when {
                    tileRowIndex == emptyTileRowIndex -> {
                        val targetRow = getRow(tileRowIndex)
                        val count = emptyTileColumnIndex - tileColumnIndex
                        when (count.sign) {
                            -1 -> {
                                for (i in (emptyTileColumnIndex + 1)..tileColumnIndex) {
                                    tileSet.add(targetRow?.get(i)!!)
                                }
                            }
                            else -> {
                                for (i in (emptyTileColumnIndex - 1) downTo tileColumnIndex) {
                                    tileSet.add(targetRow?.get(i)!!)
                                }
                            }
                        }
                        if (tileSet.size > 0) {
                            DragData(tileSet, GameFragment.Direction.HORIZONTAL)
                        } else {
                            null
                        }
                    }
                    tileColumnIndex == emptyTileColumnIndex -> {
                        val count = emptyTileRowIndex - tileRowIndex
                        when (count.sign) {
                            -1 -> {
                                for (i in (emptyTileRowIndex + 1)..tileRowIndex) {
                                    val row = getRow(i)
                                    tileSet.add(row?.get(tileColumnIndex)!!)
                                }
                            }
                            else -> {
                                for (i in (emptyTileRowIndex - 1) downTo tileRowIndex) {
                                    val row = getRow(i)
                                    tileSet.add(row?.get(tileColumnIndex)!!)
                                }

                            }
                        }
                        if (tileSet.size > 0) {
                            DragData(tileSet, GameFragment.Direction.VERTICAL)
                        } else {
                            null
                        }
                    }
                    else -> {
                        null
                    }
                }
            }
            false -> null
        }

        Log.i(
            TAG,
            "tileCanBeDragged stri: $tileRowIndex stci: $tileColumnIndex dtri: $emptyTileRowIndex dtci: $emptyTileColumnIndex ${null == result}"
        )
        return result
    }

    fun swapMultipleTiles(dragData: DragData) {
        for (tile in dragData.list) {
            val row = getRowIndex(tile)
            val column = getColumnIndex(tile)
            val emptyRow = getRowIndex(emptyTile!!)
            val emptyColumn = getColumnIndex(emptyTile!!)
            Log.i(
                TAG,
                "swap tile: row $row column $column empty: row $emptyRow column $emptyColumn"
            )
            swapTiles(tile, emptyTile!!)
        }
    }

    private fun swapTiles(sourceTile: GameTile, destinationTile: GameTile) {
        if (destinationTile != emptyTile) {
            return
        }
        val sourceTileRowIndex: Int = getRowIndex(sourceTile)
        val sourceTileColumnIndex: Int = getColumnIndex(sourceTile)
        val destinationTileRowIndex: Int = getRowIndex(destinationTile)
        val destinationTileColumnIndex: Int = getColumnIndex(destinationTile)
        Log.i(
            TAG,
            "swap stri: $sourceTileRowIndex stci: $sourceTileColumnIndex dtri: $destinationTileRowIndex dtci: $destinationTileColumnIndex"
        )
        if (sourceTileRowIndex == destinationTileRowIndex) {
            when (sourceTileRowIndex) {
                1 -> {
                    Log.i(
                        TAG,
                        "switching row 1 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex"
                    )
                    gameBoardRowOne[destinationTileColumnIndex] = sourceTile
                    gameBoardRowOne[sourceTileColumnIndex] = destinationTile
                    updateRowOne(gameBoardRowOne)
                }
                2 -> {
                    Log.i(
                        TAG,
                        "switching row 2 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex"
                    )
                    gameBoardRowTwo[destinationTileColumnIndex] = sourceTile
                    gameBoardRowTwo[sourceTileColumnIndex] = destinationTile
                    updateRowTwo(gameBoardRowTwo)
                }
                3 -> {
                    Log.i(
                        TAG,
                        "switching row 3 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex"
                    )
                    gameBoardRowThree[destinationTileColumnIndex] = sourceTile
                    gameBoardRowThree[sourceTileColumnIndex] = destinationTile
                    updateRowThree(gameBoardRowThree)
                }
                4 -> {
                    Log.i(
                        TAG,
                        "switching row 4 tile at $destinationTileColumnIndex with emptyTile at $sourceTileColumnIndex"
                    )
                    gameBoardRowFour[destinationTileColumnIndex] = sourceTile
                    gameBoardRowFour[sourceTileColumnIndex] = destinationTile
                    updateRowFour(gameBoardRowFour)
                }
            }
        } else {
            val destinationRow = when (destinationTileRowIndex) {
                1 -> gameBoardRowOne
                2 -> gameBoardRowTwo
                3 -> gameBoardRowThree
                4 -> gameBoardRowFour
                else -> null
            }
            val sourceRow = when (sourceTileRowIndex) {
                1 -> gameBoardRowOne
                2 -> gameBoardRowTwo
                3 -> gameBoardRowThree
                4 -> gameBoardRowFour
                else -> null
            }
            if (destinationRow != null && sourceRow != null) {
                Log.i(
                    TAG,
                    "moving tile from row $sourceTileRowIndex to row $destinationTileRowIndex"
                )
                destinationRow[destinationTileColumnIndex] = sourceTile
                sourceRow[sourceTileColumnIndex] = destinationTile
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
            } else {
                Log.w(TAG, "row error destinationRow: $destinationRow sourceRow: $sourceRow")
            }
        }


        Log.i(TAG, "swap tiles complete complete")
        debugDumpGameGrid()
    }

    private fun getRow(index: Int): MutableList<GameTile>? {
        return when (index) {
            1 -> gameBoardRowOne
            2 -> gameBoardRowTwo
            3 -> gameBoardRowThree
            4 -> gameBoardRowFour
            else -> null
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

    private fun debugDumpGameGrid() {
        Log.i(
            TAG,
            "Row 1 ${gameBoardRowOne[0].position} ${gameBoardRowOne[1].position} ${gameBoardRowOne[2].position} ${gameBoardRowOne[3].position}"
        )
        Log.i(
            TAG,
            "Row 2 ${gameBoardRowTwo[0].position} ${gameBoardRowTwo[1].position} ${gameBoardRowTwo[2].position} ${gameBoardRowTwo[3].position}"
        )
        Log.i(
            TAG,
            "Row 3 ${gameBoardRowThree[0].position} ${gameBoardRowThree[1].position} ${gameBoardRowThree[2].position} ${gameBoardRowThree[3].position}"
        )
        Log.i(
            TAG,
            "Row 4 ${gameBoardRowFour[0].position} ${gameBoardRowFour[1].position} ${gameBoardRowFour[2].position} ${gameBoardRowFour[3].position}"
        )
    }

    fun toggleTileHints() {
        when (tileHintsTextVisibility.value == View.VISIBLE) {
            true -> tileHintsTextVisibility.postValue(View.GONE)
            else -> tileHintsTextVisibility.postValue(View.VISIBLE)
        }
    }
}