package com.github.andrewmaneshin.unscrambleword.load

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.github.andrewmaneshin.unscrambleword.R
import com.github.andrewmaneshin.unscrambleword.game.ButtonUi
import org.hamcrest.Matcher

class LoadPage {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.loadContainer))
    private val containerClassTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))
    private val progressUi = ProgressUi(
        id = R.id.progress,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )
    private val retryUi = ButtonUi(
        text = R.string.retry,
        id = R.id.retryButton,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )
    private val errorMessageUi = ErrorMessageUi(
        id = R.id.errorMessageTextView,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    fun assertProgressState() {
        progressUi.assertVisible()
        retryUi.assertNotVisible()
        errorMessageUi.assertNotVisible()
    }

    fun waitTillError() {
        errorMessageUi.waitTillVisible()
    }

    fun assertErrorState() {
        progressUi.assertNotVisible()
        retryUi.assertVisible()
        errorMessageUi.assertVisible()
    }

    fun clickRetry() {
        retryUi.click()
    }

    fun waitTillGone() {
        errorMessageUi.waitTillDoesNotExist()
    }
}