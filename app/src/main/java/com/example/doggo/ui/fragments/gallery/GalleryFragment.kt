package com.example.doggo.ui.fragments.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.doggo.data.cache.DogImageCache
import com.example.doggo.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private var dogImageCache: DogImageCache? = null
    private var cachedImages: List<String>? = null
    private var dogsAdapter: ImageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dogImageCache = DogImageCache(requireContext());
        cachedImages = dogImageCache?.getCacheImage() ?: listOf()
        toggleEmptyView(displayEmptyView = cachedImages?.isEmpty() == true)
        dogsAdapter = ImageAdapter(cachedImages!!.toMutableList());
        setupViews()
    }

    private fun toggleEmptyView(displayEmptyView: Boolean) {
        binding.recyclerView.visibility = if (displayEmptyView) View.INVISIBLE else View.VISIBLE
        binding.emptyTextView.isVisible = displayEmptyView
    }

    private fun setupViews() {
        binding.recyclerView.apply {
            adapter = dogsAdapter
        }
        binding.clearCacheButtpn.setOnClickListener {
            dogImageCache?.clearCache()
            dogsAdapter?.updateList(listOf())
            toggleEmptyView(displayEmptyView = true)
        }
    }

}