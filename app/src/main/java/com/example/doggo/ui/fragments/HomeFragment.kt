package com.example.doggo.ui.fragments

import android.os.Bundle
import android.text.Layout.Directions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.doggo.R
import com.example.doggo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.generateDogButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_generateDogFragment
            )
        }
        binding.dogGallery.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_galleryFragment
            )
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}