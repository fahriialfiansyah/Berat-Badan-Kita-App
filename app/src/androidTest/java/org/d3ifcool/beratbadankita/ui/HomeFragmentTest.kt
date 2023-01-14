package org.d3ifcool.beratbadankita.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.beratbadankita.MainActivity
import org.d3ifcool.beratbadankita.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeratBadanTest {

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        activityScenario.scenario.onActivity {
            it.supportFragmentManager.beginTransaction()
        }
    }

    @Test
    fun insertData() {
        onView(withId(R.id.ib_tambah_berat)).perform(click())
        onView(withId(R.id.input_berat_badan)).perform(
            typeText("60")).perform(closeSoftKeyboard())
        onView(withId(R.id.input_catatan)).perform(
            typeText("catatan")).perform(closeSoftKeyboard())
        onView(withText(R.string.simpan)).perform(click())
    }
}