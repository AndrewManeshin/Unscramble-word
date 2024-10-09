package com.github.andrewmaneshin.unscrambleword.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.andrewmaneshin.unscrambleword.core.AbstractFragment
import com.github.andrewmaneshin.unscrambleword.databinding.FragmentLoadBinding
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel
import com.github.andrewmaneshin.unscrambleword.game.presentation.NavigateToGame

class LoadFragment : AbstractFragment<LoadUiState, LoadViewModel>() {

    private var _binding: FragmentLoadBinding? = null
    private val binding
        get() = _binding!!

    override val update: (LoadUiState) -> Unit = { uiState ->
        uiState.show(
            binding.progress,
            binding.retryButton,
            binding.errorMessageTextView
        )
        uiState.navigate(requireActivity() as NavigateToGame)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).makeViewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.load(isFirstRun = savedInstanceState == null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}