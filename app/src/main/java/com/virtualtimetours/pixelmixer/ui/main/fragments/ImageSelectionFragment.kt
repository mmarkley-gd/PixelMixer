package com.virtualtimetours.pixelmixer.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pixelmixer.databinding.ImageSelectionFragmentBinding
import com.virtualtimetours.pixelmixer.MainActivity
import com.virtualtimetours.pixelmixer.ui.main.viewmodels.ImageSelectionViewModel

class ImageSelectionFragment : Fragment() {
    val viewModel : ImageSelectionViewModel by activityViewModels()

    private lateinit var binding: ImageSelectionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImageSelectionFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.getImagesButton.setOnClickListener {
            (activity as MainActivity).launchImagePicker(viewModel)
        }

        binding.imageView.setOnClickListener {
            if(null != viewModel.imageBitmap.value) {
                (activity as MainActivity).navigateToGameScreen()
            }
        }

        viewModel.imageBitmap.observe(viewLifecycleOwner) {
            if (null != it) {
                binding.imageView.setImageBitmap(it)
            }
        }

        return binding.root
    }

}