package com.github.andrewmaneshin.unscrambleword.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.andrewmaneshin.unscrambleword.UGApp
import com.github.andrewmaneshin.unscrambleword.databinding.FragmentGameOverBinding
import com.github.andrewmaneshin.unscrambleword.game.NavigateToGame

class GameOverFragment : Fragment() {

    private var _binding: FragmentGameOverBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (requireActivity().application as UGApp).gameOverViewModel

        binding.newGameButton.setOnClickListener {
            (requireActivity() as NavigateToGame).navigateToGame()
        }
        val uiState = viewModel.init(savedInstanceState == null)
        binding.statsTextView.updateUiState(uiState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}