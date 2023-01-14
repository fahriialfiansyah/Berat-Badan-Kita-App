package org.d3ifcool.beratbadankita.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.d3ifcool.beratbadankita.R
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest{
    @Test
    fun loginTest(){
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        val mockNavController = Mockito.mock(NavController::class.java)

        val titleScenario =
            launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_BeratBadanKita)

        titleScenario.onFragment { fragment ->
            fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    mockNavController.setGraph(R.navigation.navigation)
                    Navigation.setViewNavController(fragment.requireView(), mockNavController)
                }
            }
        }

        onView(withId(R.id.login)).perform(click())
        val mText: UiObject = device.findObject(UiSelector().text("fahrialfiansyah2002@gmail.com"))
        mText.click()
        onView(withId(R.id.navigation)).check(matches(isDisplayed()))
    }
}