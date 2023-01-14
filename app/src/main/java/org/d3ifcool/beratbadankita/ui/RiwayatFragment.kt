package org.d3ifcool.beratbadankita.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.adapter.HistoryAdapter
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.databinding.FragmentRiwayatBinding

class RiwayatFragment : Fragment(), HistoryAdapter.RecyclerViewClickListener {

    private lateinit var binding: FragmentRiwayatBinding
    private var historyAdapter: HistoryAdapter? = null
    private var mHistory: List<History>? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRiwayatBinding.inflate(layoutInflater, container, false)

        historyAdapter?.listener = this

        binding.ibTambahBerat.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavFragment_to_tambahBeratFragment)
        }

        recyclerView = binding.rvWeightHistory
        recyclerView!!.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        retrieveAllHistory()
        mHistory = ArrayList()
        if ((mHistory as ArrayList<History>).isEmpty()) {
            binding.rvWeightHistory.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.rvWeightHistory.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
        return binding.root
    }

    override fun onItemClicked(view: View, history: History) {
        Toast.makeText(context,
            "Berat ${history.saatIni} kg berhasil di klik",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun retrieveAllHistory(){
        val mAuth = FirebaseAuth.getInstance()
        val refHistory = FirebaseDatabase.getInstance().reference.child("History").orderByChild("userId").equalTo(mAuth.currentUser!!.uid)
        refHistory.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (mHistory as ArrayList<History>).clear()
                for (data in snapshot.children){
                    val history: History? = data.getValue(History::class.java)
                    if (history != null) {
                        binding.rvWeightHistory.visibility = View.VISIBLE
                        binding.emptyView.visibility = View.GONE
                        (mHistory as ArrayList<History>).add(history)
                    } else {
                        binding.rvWeightHistory.visibility = View.GONE
                        binding.emptyView.visibility = View.VISIBLE
                    }
                }
                historyAdapter = HistoryAdapter(context!!, mHistory!!)
                recyclerView!!.adapter = historyAdapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}