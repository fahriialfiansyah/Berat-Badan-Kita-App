package org.d3ifcool.beratbadankita.ui.onboarding

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.beratbadankita.R
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class OnBoardingFragmentTest {

    @Test
    fun testInsert() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val titleScenario =
            launchFragmentInContainer<OnBoardingFragment>(themeResId = R.style.Theme_BeratBadanKita)

        titleScenario.onFragment { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    mockNavController.setGraph(R.navigation.navigation)
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }

        onView(withId(R.id.input_current))
            .check(matches(isDisplayed()))
            .perform(replaceText("50"))
            .check(matches(withText("50")))

        onView(withId(R.id.input_goal))
            .check(matches(isDisplayed()))
            .perform(replaceText("60"))
            .check(matches(withText("60")))

        onView(withId(R.id.mulai))
            .check(matches(isDisplayed()))
            .perform(click())
    }
}