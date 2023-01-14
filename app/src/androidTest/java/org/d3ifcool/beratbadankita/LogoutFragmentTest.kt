package org.d3ifcool.beratbadankita

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class LogoutFragmentTest {
    @Test
    fun logoutTest() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val scenario =
            launchFragmentInContainer<BottomNavFragment>(themeResId = R.style.Theme_BeratBadanKita)

        scenario.onFragment { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    mockNavController.setGraph(R.navigation.navigation)
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }

        onView(withId(R.id.profileFragment))
            .check(matches(isDisplayed()))
            .perform(click())
    }
}