package org.d3ifcool.beratbadankita.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.data.User
import org.d3ifcool.beratbadankita.data.UserDao
import org.d3ifcool.beratbadankita.livedata.FirebaseUserLiveData

class OnBoardingViewModel(private val db: UserDao) : ViewModel() {

    val authState = FirebaseUserLiveData()

    fun getUserData(userId: String): LiveData<User> {
        return db.getUserData(userId)
    }

    fun getHistory(userId: String) : LiveData<List<History>> {
        return db.getData(userId)
    }
}