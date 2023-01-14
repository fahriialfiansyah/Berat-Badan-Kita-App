package org.d3ifcool.beratbadankita.ui

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.beratbadankita.MainActivity
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestingDatabase {
    companion object{
        private val Data1 = History("","","","Ini berat saya",0)
        private val Data2 = History("","","50","",0)
        private val Data3 = History("","","5","",0)
        private val Data4 = History("","","250","",0)
        private val Data5 = History("","","2300","",0)
        private val Data6 = History("","","10000","",0)
        private val Data7 = History("","","68","Ini beratku dua tahun lalu",0)
        private val Data8 = History("","","55","Waduh, turun beratku yaaa",0)
        private val Data9 = History("","","100","Wah, harus kontrol beratku nih",0)
        private val Data10 = History("","","","",0)
    }

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
        // DATA 1
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data1.saatIni)
        )
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data1.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)
        pressBack()

        // DATA 2
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data2.saatIni)
        )
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data2.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)

        // DATA 3
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data3.saatIni)
        )
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data3.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)
        pressBack()

        // DATA 4
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data4.saatIni)
        )
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data4.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)
        pressBack()

        // DATA 5
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data5.saatIni)
        )
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data5.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)
        pressBack()

        // DATA 6
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data6.saatIni)
        )
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data6.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)
        pressBack()

        // DATA 7
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data7.saatIni)
        ).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data7.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)

        // DATA 8
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data8.saatIni)
        ).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data8.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)

        // DATA 9
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data9.saatIni)
        ).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data9.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)

        // DATA 10
        onView(withId(R.id.ib_tambah_berat)).perform(ViewActions.click())
        onView(withId(R.id.input_berat_badan)).perform(
            ViewActions.typeText(Data10.saatIni)
        )
        onView(withId(R.id.input_catatan)).perform(
            ViewActions.typeText(Data10.catatan)
        ).perform(ViewActions.closeSoftKeyboard())

        onView(withText(R.string.simpan)).perform(ViewActions.click())
        SystemClock.sleep(2000)
    }
}