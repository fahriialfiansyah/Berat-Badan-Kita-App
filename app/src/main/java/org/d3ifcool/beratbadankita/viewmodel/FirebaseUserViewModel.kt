package org.d3ifcool.beratbadankita.viewmodel

import androidx.lifecycle.ViewModel
import org.d3ifcool.beratbadankita.livedata.FirebaseUserLiveData

class FirebaseUserViewModel: ViewModel() {
    val authState = FirebaseUserLiveData()
}