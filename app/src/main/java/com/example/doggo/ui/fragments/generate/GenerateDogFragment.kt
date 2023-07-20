package com.example.doggo.ui.fragments.generate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.doggo.data.cache.DogImageCache
import com.example.doggo.databinding.FragmentGenerateDogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GenerateDogFragment : Fragment() {

    private var _binding: FragmentGenerateDogBinding? = null
    private val binding get() = _binding!!
    private var dogImageCache: DogImageCache? = null
    private val viewModel: GenerateDogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenerateDogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dogImageCache = DogImageCache(requireContext());
        setupObserver()
        binding.generateDogButton.setOnClickListener {
            viewModel.getPartyName()
        }
    }

    private fun setupObserver() {
        viewModel.generateDog.observe(viewLifecycleOwner) {
            when (it) {
                GenerateDogViewModel.GenerateDogEvent.Loading -> {
                    binding.generateDogImageView.visibility = View.INVISIBLE
                    binding.progressBar.isVisible = true
                }

                is GenerateDogViewModel.GenerateDogEvent.RandomDogImageError -> {
                    Toast.makeText(requireContext(), "Something went Wrong", Toast.LENGTH_SHORT)
                        .show()
                }

                is GenerateDogViewModel.GenerateDogEvent.RandomDogImageSuccess -> {
                    Glide.with(this).load(it.url).into(binding.generateDogImageView)
                    binding.progressBar.isVisible = false
                    binding.generateDogImageView.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        dogImageCache?.saveImageUrlToCache(it.url)
                    }
                }
            }
        }
    }


}