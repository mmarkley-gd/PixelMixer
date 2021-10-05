package com.virtualtimetours.pixelmixer.ui.main.fragments

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pixelmixer.databinding.FragmentGameBinding
import com.virtualtimetours.pixelmixer.data.DragData
import com.virtualtimetours.pixelmixer.ui.main.GameTile
import com.virtualtimetours.pixelmixer.ui.main.viewmodels.GameViewModel
import com.virtualtimetours.pixelmixer.ui.main.viewmodels.ImageSelectionViewModel

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment(), View.OnTouchListener, View.OnDragListener,
    View.OnLongClickListener {
    private val imageViewModel: ImageSelectionViewModel by activityViewModels()
    private val gameViewModel: GameViewModel by activityViewModels()

    // Used to hold the GameTile for the item that is clicked on. This is initialized
    // in the [onTouch] handler
    private var sourceTile: GameTile? = null
    private lateinit var binding: FragmentGameBinding

    private var puzzleIsSolved: Boolean = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater)
        binding.viewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.root.setOnLongClickListener(this)

        // First row click/drag
        binding.rowOneColumnOne.setOnDragListener(this)
        binding.rowOneColumnOne.setOnTouchListener(this)

        binding.rowOneColumnTwo.setOnDragListener(this)
        binding.rowOneColumnTwo.setOnTouchListener(this)

        binding.rowOneColumnThree.setOnDragListener(this)
        binding.rowOneColumnThree.setOnTouchListener(this)

        binding.rowOneColumnFour.setOnDragListener(this)
        binding.rowOneColumnFour.setOnTouchListener(this)

        // Second row click/drag
        binding.rowTwoColumnOne.setOnDragListener(this)
        binding.rowTwoColumnOne.setOnTouchListener(this)

        binding.rowTwoColumnTwo.setOnDragListener(this)
        binding.rowTwoColumnTwo.setOnTouchListener(this)

        binding.rowTwoColumnThree.setOnDragListener(this)
        binding.rowTwoColumnThree.setOnTouchListener(this)

        binding.rowTwoColumnFour.setOnDragListener(this)
        binding.rowTwoColumnFour.setOnTouchListener(this)

        // Third row click/drag
        binding.rowThreeColumnOne.setOnDragListener(this)
        binding.rowThreeColumnOne.setOnTouchListener(this)

        binding.rowThreeColumnTwo.setOnDragListener(this)
        binding.rowThreeColumnTwo.setOnTouchListener(this)

        binding.rowThreeColumnThree.setOnDragListener(this)
        binding.rowThreeColumnThree.setOnTouchListener(this)

        binding.rowThreeColumnFour.setOnDragListener(this)
        binding.rowThreeColumnFour.setOnTouchListener(this)

        // Forth row click/drag
        binding.rowFourColumnOne.setOnDragListener(this)
        binding.rowFourColumnOne.setOnTouchListener(this)

        binding.rowFourColumnTwo.setOnDragListener(this)
        binding.rowFourColumnTwo.setOnTouchListener(this)

        binding.rowFourColumnThree.setOnDragListener(this)
        binding.rowFourColumnThree.setOnTouchListener(this)

        binding.rowFourColumnFour.setOnDragListener(this)
        binding.rowFourColumnFour.setOnTouchListener(this)

        imageViewModel.imageBitmap.observe(viewLifecycleOwner) {
            if (it != null) {
                gameViewModel.fractureImage(imageViewModel.imageBitmap.value!!, 4, 4)
            }
        }
        return binding.root
    }

    enum class Direction {
        HORIZONTAL, VERTICAL
    }

    class MyShadowBuilder(
        private val target: View,
        private val tiles: List<GameTile>,
        private val direction: Direction
    ) : View.DragShadowBuilder(target) {

        override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
            super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
            when (direction) {
                Direction.HORIZONTAL -> outShadowSize?.set(target.width * tiles.size, target.height)
                Direction.VERTICAL -> outShadowSize?.set(target.width, target.height * tiles.size)
            }
            when (direction) {
                Direction.HORIZONTAL -> {
                    outShadowTouchPoint?.set(
                        outShadowTouchPoint.x * tiles.size,
                        outShadowTouchPoint.y
                    )
                }
                Direction.VERTICAL -> {
                    outShadowTouchPoint?.set(
                        outShadowTouchPoint.x,
                        outShadowTouchPoint.y * tiles.size
                    )
                }
            }
        }

        override fun onDrawShadow(canvas: Canvas?) {
            val width = when (direction) {
                Direction.HORIZONTAL -> tiles.size * view.width.toFloat()
                Direction.VERTICAL -> view.width.toFloat()
            }
            val height = when (direction) {
                Direction.HORIZONTAL -> view.height.toFloat()
                Direction.VERTICAL -> view.height.toFloat() * tiles.size
            }
            canvas!!.scale(width, height)
            view.draw(canvas)
        }
    }

    var currentDragData : DragData? = null

    override fun onTouch(targetView: View, event: MotionEvent): Boolean {
        targetView.performClick()
        if (puzzleIsSolved) {
            return false
        }
        val tile: GameTile = targetView.tag as GameTile
        if (event.action == MotionEvent.ACTION_DOWN) {
            val dragData = gameViewModel.tileCanBeDragged(tile)
            if (null != dragData) {
                currentDragData = dragData
                sourceTile = tile
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val tag = targetView.tag.toString()
                Log.i(TAG, "starting drag on $tag")
                val item = ClipData.Item(tag)
                val data = ClipData(tag, mimeTypes, item)
                val dragShadowBuilder =
                    MyShadowBuilder(targetView, dragData.list, dragData.direction)
                targetView.startDragAndDrop(data, dragShadowBuilder, targetView, 0)
                return true
            } else {
                ToneGenerator(
                    AudioManager.STREAM_MUSIC,
                    100
                ).startTone(ToneGenerator.TONE_PROP_BEEP, 200)
            }
        }
        return false
    }

    private var startX = -1
    private var startY = -1

    /**
     * Handle DragEvents.
     * returns true if the DragEvent is handled, false otherwise
     */
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                startX = v.left
                startY = v.top
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                return true
            }
            DragEvent.ACTION_DROP -> {
                if (null == sourceTile || null == currentDragData)
                    return false
                gameViewModel.swapMultipleTiles(currentDragData!!)
//                val targetTile = v.tag as GameTile
//                Log.i(TAG, "onDrag DROP $sourceTile on $targetTile")
                gameViewModel.incrementMoveCount()
//                // The target of the drop is NOT the source of the drag. We've saved the source
//                // GameTile in the [MotionEvent.ACTION_DONE] handler
//                Log.i(TAG, "swapping $sourceTile")
//                gameViewModel.swapTiles(sourceTile!!, targetTile)
                if (gameViewModel.isSolved()) {
                    puzzleIsSolved = true
                    ToneGenerator(
                        AudioManager.STREAM_MUSIC,
                        100
                    ).startTone(ToneGenerator.TONE_PROP_BEEP, 2000)
                }
                sourceTile = null
                return true
            }
            else -> return false
        }
    }

    companion object {
        val TAG = GameFragment::javaClass.name
    }

    override fun onLongClick(v: View?): Boolean {
        gameViewModel.toggleTileHints()
        return false
    }
}