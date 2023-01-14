package org.d3ifcool.beratbadankita.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class HistoryLiveData(private val db: Query) : LiveData<List<History>>() {
    private val data = ArrayList<History>()

    init {
        value = data
    }

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val history = snapshot.getValue<History>() ?: return
            history.userId = snapshot.key ?: return
            data.add(history)
            value = data.toList()
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val history = snapshot.getValue<History>() ?: return
            history.userId = snapshot.key ?: return

            data.add(history)
            value = data.toList()
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    override fun onActive() {
        db.addChildEventListener(listener)
    }

    override fun onInactive() {
        db.removeEventListener(listener)
    }
}