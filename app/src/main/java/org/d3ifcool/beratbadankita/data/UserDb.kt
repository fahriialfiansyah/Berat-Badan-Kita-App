package org.d3ifcool.beratbadankita.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase

class UserDb private constructor() {

    private val database = FirebaseDatabase.getInstance()

    val dao = object : UserDao {
        override fun insertData(user: User) {
            database.getReference(USER_PATH).push().setValue(user)
        }

        override fun getUserData(userId: String): LiveData<User> {
            return UserLiveData(
                database.getReference(USER_PATH)
                    .orderByChild("userId")
                    .equalTo(userId)
            )
        }

        override fun getData(userId: String): LiveData<List<History>> {
            return HistoryLiveData(
                database.getReference(HISTORY_PATH)
                    .orderByChild("userId")
                    .equalTo(userId)
            )
        }
    }


    companion object {
        private const val USER_PATH = "User"
        private const val HISTORY_PATH = "History"

        @Volatile
        private var INSTANCE: UserDb? = null

        fun getInstance(): UserDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = UserDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}