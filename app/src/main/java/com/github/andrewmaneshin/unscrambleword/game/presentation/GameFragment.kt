package com.github.andrewmaneshin.unscrambleword.game.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.andrewmaneshin.unscrambleword.core.AbstractFragment
import com.github.andrewmaneshin.unscrambleword.databinding.FragmentGameBinding
import com.github.andrewmaneshin.unscrambleword.di.ProvideViewModel
import com.github.andrewmaneshin.unscrambleword.stats.presentation.NavigateToGameOver

class GameFragment : AbstractFragment<GameUiState, GameViewModel>() {

    private var _binding: FragmentGameBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var textWatcher: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override val update: (GameUiState) -> Unit = { uiState ->
        with(binding) {
            uiState.update(
                scrambledWordTextView,
                inputView,
                checkButton,
                skipButton,
                nextButton
            )
        }
        uiState.navigate(requireActivity() as NavigateToGameOver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                viewModel.handleUserInput(text = s.toString())
            }
        }

        viewModel =
            (requireActivity().application as ProvideViewModel).makeViewModel(GameViewModel::class.java)

        binding.nextButton.setOnClickListener {
            viewModel.next()
        }

        binding.skipButton.setOnClickListener {
            viewModel.skip()
        }

        binding.checkButton.setOnClickListener {
            viewModel.check(text = binding.inputView.text())
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onStart() {
        super.onStart()
        binding.inputView.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.inputView.removeTextChangedListener(textWatcher)
        _binding = null
    }
}