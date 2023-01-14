package org.d3ifcool.beratbadankita.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.data.UserDb
import org.d3ifcool.beratbadankita.databinding.FragmentHomeBinding
import org.d3ifcool.beratbadankita.ui.onboarding.OnBoardingViewModel
import org.d3ifcool.beratbadankita.ui.onboarding.OnBoardingViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: DatabaseReference

    private val viewModel: OnBoardingViewModel by lazy {
        val db = UserDb.getInstance()
        val factory = OnBoardingViewModelFactory(db.dao)
        ViewModelProvider(requireActivity(), factory)[OnBoardingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.ibTambahBerat.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavFragment_to_tambahBeratFragment)

        }
        binding.tvCurrent.text = "-"
        binding.tvStart.text = "-"

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        viewModel.authState.observe(viewLifecycleOwner) { firebaseUser ->

            if (firebaseUser != null) {
                viewModel.getUserData(firebaseUser.uid).observe(viewLifecycleOwner) {
                    if (userId != null) {
                        readData(userId)
                    }
                }

                viewModel.getHistory(firebaseUser.uid).observe(viewLifecycleOwner) {
                    if (it != null) {
                        if (it.isNotEmpty()) {
                            binding.tvCurrent.text = it[it.size-1].saatIni.toString()
                            binding.tvStart.text = it[0].saatIni.toString()
                        }
                        createChart(it)
                    }
                }
            }
        }
        return binding.root
    }

    private fun createChart(histories: List<History>) {
        val entries: ArrayList<Entry> = ArrayList()
        var index = 1f
        for (history in histories) {
            entries.add(Entry(index, history.saatIni!!.toFloat()))
            index++
        }
        val dataset = LineDataSet(entries, "Berat Harian")
        dataset.color = ContextCompat.getColor(requireContext(), R.color.green)
        dataset.fillColor = dataset.color
        dataset.setDrawFilled(false)
        dataset.setDrawCircles(true)
        dataset.setCircleColor(Color.BLACK)
        binding.chart.data = LineData(dataset)
        binding.chart.description = null
        binding.chart.legend.isEnabled = false
        binding.chart.invalidate()
        binding.chart.axisRight.isEnabled = false
        val xAxis = binding.chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    private fun readData(userId: String) {

        database = FirebaseDatabase.getInstance().getReference("User")

        database.child(userId).get().addOnSuccessListener {
            val tujuan = it.child("tujuan").value
            binding.tvGoal.text = tujuan.toString()
        }

        val userQueryLast =
            FirebaseDatabase.getInstance().reference.child("History").orderByChild("userId")
                .limitToLast(1)

        val userQueryFirst =
            FirebaseDatabase.getInstance().reference.child("History").orderByChild("userId")
                .limitToFirst(1)

        userQueryLast.equalTo(userId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val saatIni = snapshot.child("saatIni").value
                        if (saatIni == null) {
                            binding.tvCurrent.text = null
                        } else {
                            binding.tvCurrent.text = saatIni.toString()
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }
        )

        userQueryFirst.equalTo(userId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val mulai = snapshot.child("saatIni").value
                        binding.tvStart.text = mulai.toString()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }
        )
    }

}