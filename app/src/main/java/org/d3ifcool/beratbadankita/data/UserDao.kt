package org.d3ifcool.beratbadankita.data

import androidx.lifecycle.LiveData
import androidx.room.Dao

@Dao
interface UserDao {

    fun insertData(user: User)

    fun getUserData(userId: String): LiveData<User>

    fun getData(userId: String) : LiveData<List<History>>

}