package com.virtualtimetours.pixelmixer.ui.main.fragments

import android.os.Bundle
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
class GameFragment : Fragment() {
    val imageViewModel: ImageSelectionViewModel by activityViewModels()
    val gameViewModel: GameViewModel by activityViewModels()

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater)
        binding.viewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        gameViewModel.fragments.observe(viewLifecycleOwner) {
            val count = 0
            for (list in it) {
                when (count) {
                    0 -> {
                        val bitmap = list[0]
                        binding.rowOneColumnOne.setImageBitmap(bitmap)
                    }
                    1 -> {
                        val bitmap = list[1]
                        binding.rowOneColumnTwo.setImageBitmap(bitmap)
                    }
                    2 -> {
                        val bitmap = list[2]
                        binding.rowOneColumnThree.setImageBitmap(bitmap)
                    }
                    3 -> {
                        val bitmap = list[3]
                        binding.rowOneColumnFour.setImageBitmap(bitmap)
                    }
                }
            }
        }
        gameViewModel.fractureImage(imageViewModel.imageBitmap.value!!, 4, 4)
        return binding.root
    }
}