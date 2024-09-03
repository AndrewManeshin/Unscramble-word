package com.github.andrewmaneshin.unscrambleword.game

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.andrewmaneshin.unscrambleword.GameUiState
import com.github.andrewmaneshin.unscrambleword.GameViewModel
import com.github.andrewmaneshin.unscrambleword.Navigate
import com.github.andrewmaneshin.unscrambleword.UGApp
import com.github.andrewmaneshin.unscrambleword.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var uiState: GameUiState
    private lateinit var viewModel: GameViewModel
    private lateinit var textWatcher: TextWatcher


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val update: () -> Unit = {
            with(binding) {
                uiState.update(
                    scrambledWordTextView,
                    inputView,
                    checkButton,
                    skipButton,
                    nextButton
                )
            }
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                uiState = viewModel.handleUserInput(text = s.toString())
                update.invoke()
            }
        }
        binding.inputView.addTextChangedListener(textWatcher)

        viewModel = (requireActivity().application as UGApp).gameViewModel

        binding.nextButton.setOnClickListener {
            (requireActivity() as Navigate).navigateToGameOver()
            uiState = viewModel.init(true)
            update.invoke()
        }

        binding.skipButton.setOnClickListener {
            uiState = viewModel.skip()
            update.invoke()
        }

        binding.checkButton.setOnClickListener {
            uiState = viewModel.check(text = binding.inputView.text())
            update.invoke()
        }

        uiState = viewModel.init(true)
        update.invoke()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}