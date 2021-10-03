package com.virtualtimetours.pixelmixer.ui.main.fragments

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pixelmixer.databinding.FragmentGameBinding
import com.virtualtimetours.pixelmixer.ui.main.viewmodels.GameViewModel
import com.virtualtimetours.pixelmixer.ui.main.viewmodels.ImageSelectionViewModel

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment(), View.OnLongClickListener, View.OnDragListener {
    private val imageViewModel: ImageSelectionViewModel by activityViewModels()
    private val gameViewModel: GameViewModel by activityViewModels()

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater)
        binding.viewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // First row click/drag
        binding.rowOneColumnOne.setOnDragListener(this)
        binding.rowOneColumnOne.setOnLongClickListener(this)

        binding.rowOneColumnTwo.setOnDragListener(this)
        binding.rowOneColumnTwo.setOnLongClickListener(this)

        binding.rowOneColumnThree.setOnDragListener(this)
        binding.rowOneColumnThree.setOnLongClickListener(this)

        binding.rowOneColumnFour.setOnDragListener(this)
        binding.rowOneColumnFour.setOnLongClickListener(this)

        // Second row click/drag
        binding.rowTwoColumnOne.setOnDragListener(this)
        binding.rowTwoColumnOne.setOnLongClickListener(this)

        binding.rowTwoColumnTwo.setOnDragListener(this)
        binding.rowTwoColumnTwo.setOnLongClickListener(this)

        binding.rowTwoColumnThree.setOnDragListener(this)
        binding.rowTwoColumnThree.setOnLongClickListener(this)

        binding.rowTwoColumnFour.setOnDragListener(this)
        binding.rowTwoColumnFour.setOnLongClickListener(this)

        // Third row click/drag
        binding.rowThreeColumnOne.setOnDragListener(this)
        binding.rowThreeColumnOne.setOnLongClickListener(this)

        binding.rowThreeColumnTwo.setOnDragListener(this)
        binding.rowThreeColumnTwo.setOnLongClickListener(this)

        binding.rowThreeColumnThree.setOnDragListener(this)
        binding.rowThreeColumnThree.setOnLongClickListener(this)

        binding.rowThreeColumnFour.setOnDragListener(this)
        binding.rowThreeColumnFour.setOnLongClickListener(this)

        // Forth row click/drag
        binding.rowFourColumnOne.setOnDragListener(this)
        binding.rowFourColumnOne.setOnLongClickListener(this)

        binding.rowFourColumnTwo.setOnDragListener(this)
        binding.rowFourColumnTwo.setOnLongClickListener(this)

        binding.rowFourColumnThree.setOnDragListener(this)
        binding.rowFourColumnThree.setOnLongClickListener(this)

        binding.rowFourColumnFour.setOnDragListener(this)
        binding.rowFourColumnFour.setOnLongClickListener(this)

        imageViewModel.imageBitmap.observe(viewLifecycleOwner) {
            if(it != null) {
                gameViewModel.fractureImage(imageViewModel.imageBitmap.value!!, 4, 4)
            }
        }
        return binding.root
    }

    override fun onLongClick(targetView: View?): Boolean {
        if(targetView != null) {
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val tag = "targetView.tag.toString()"
            Log.i(TAG, "starting drag on $tag")
            val item = ClipData.Item(tag)
            val data = ClipData(tag, mimeTypes, item)
            val dragShadowBuilder = View.DragShadowBuilder(targetView)
            targetView.startDragAndDrop(data, dragShadowBuilder, targetView, 0)
            return true
        }
       return false
    }
    companion object {
        val TAG = GameFragment::javaClass.name
    }

    override fun onDrag(v: View?, event: DragEvent): Boolean {
        when(event.action) {
            DragEvent.ACTION_DRAG_STARTED, DragEvent.ACTION_DRAG_ENDED -> return true
            DragEvent.ACTION_DROP -> {
                return true
            }
            else -> return false
        }
    }
}