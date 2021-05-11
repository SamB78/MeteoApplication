package com.example.meteoapplication.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.meteoapplication.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.lifecycleOwner = this


        viewModel.navigation.observe(viewLifecycleOwner, { navigation ->
            when (navigation) {
                MainViewModel.Navigation.CLICK_WEATHER -> {
                    val action = MainFragmentDirections.actionMainFragmentToWeatherFragment()
                    findNavController().navigate(action)
                    viewModel.onButtonClicked()
                }
                else -> {
                    Timber.e("Error Navigation: $navigation")
                }
            }

        })


        return binding.root
    }
}