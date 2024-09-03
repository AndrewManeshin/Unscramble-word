package com.github.andrewmaneshin.unscrambleword

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.andrewmaneshin.unscrambleword.databinding.ActivityMainBinding
import com.github.andrewmaneshin.unscrambleword.databinding.GameOverBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val fragmentManager = MyCustomFragmentManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (savedInstanceState == null) {
            navigateToGameScreen()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragmentManager.save(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fragmentManager.restore(savedInstanceState, this)
    }

    fun navigateToGameOverScreen() {
        fragmentManager.show(GameOverCustomScreen(), this)
    }

    fun navigateToGameScreen() {
        fragmentManager.show(GameCustomScreen(), this)
    }
}

class MyCustomFragmentManager {

    private var lastScreen: MyCustomScreen? = null

    fun save(outState: Bundle) {
        outState.putSerializable("screen", lastScreen)
        lastScreen?.detach()
    }

    fun restore(savedInstanceState: Bundle, activity: MainActivity) {
        lastScreen = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            savedInstanceState.getSerializable(
                "screen",
                MyCustomScreen::class.java
            ) as MyCustomScreen
        } else {
            savedInstanceState.getSerializable("screen") as MyCustomScreen
        }
        lastScreen?.attach(activity)
        lastScreen?.show(savedInstanceState)
    }

    fun show(screen: MyCustomScreen, activity: MainActivity) {
        if (lastScreen != null && screen::class.simpleName == lastScreen!!::class.simpleName) {
            lastScreen!!.attach(activity)
            lastScreen!!.show(null)
        } else {
            lastScreen = screen
            lastScreen!!.attach(activity)
            lastScreen!!.show(null)
        }
    }
}

interface MyCustomScreen : Serializable {
    fun attach(activity: MainActivity)
    fun detach()
    fun show(savedInstanceState: Bundle?)
}

class GameCustomScreen : MyCustomScreen {
    private var activity: MainActivity? = null
    private lateinit var uiState: GameUiState
    private lateinit var viewModel: GameViewModel
    private lateinit var textWatcher: TextWatcher
    private lateinit var binding: ActivityMainBinding

    override fun attach(activity: MainActivity) {
        this.activity = activity
    }

    override fun detach() {
        this.activity = null
    }

    override fun show(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(activity!!.layoutInflater)
        activity?.setContentView(binding.root)

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

        viewModel = (activity?.application as UGApp).gameViewModel

        binding.nextButton.setOnClickListener {
            activity?.navigateToGameOverScreen()
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
}

class GameOverCustomScreen : MyCustomScreen {

    private var activity: MainActivity? = null

    override fun attach(activity: MainActivity) {
        this.activity = activity
    }

    override fun detach() {
        activity = null
    }

    override fun show(savedInstanceState: Bundle?) {
        val gameOverViewModel = (activity?.application as UGApp).gameOverViewModel

        val gameOverBinding = GameOverBinding.inflate(activity!!.layoutInflater)
        activity?.setContentView(gameOverBinding.root)

        gameOverBinding.statsTextView.updateUiState(gameOverViewModel.statsUiState)

        gameOverBinding.newGameButton.setOnClickListener {
            activity?.navigateToGameScreen()
        }
    }
}