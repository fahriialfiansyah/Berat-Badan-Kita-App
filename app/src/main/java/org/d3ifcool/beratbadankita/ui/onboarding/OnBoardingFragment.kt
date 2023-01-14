package org.d3ifcool.beratbadankita.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.data.User
import org.d3ifcool.beratbadankita.databinding.FragmentOnBoardingBinding


class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var database: DatabaseReference
    private lateinit var databaseHistory: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
        binding.tvKlikDisini.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_cekIdealFragment)
        }



        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            })

        val rootRef = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val uidRef = rootRef.child("User").child(uid)
        uidRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                val userId = snapshot.child("userId").getValue(String::class.java)
                if (uid == userId) {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_bottomNavFragment)
                } else if (userId == null) {
                    simpanDataUser()
                }
            } else {
                Log.d("TAG", task.exception!!.message!!) //Don't ignore potential errors!
            }
        }
        return binding.root
    }

    private fun simpanDataUser() {

        binding.mulai.setOnClickListener {
            it?.apply { isEnabled = false; postDelayed({ isEnabled = true }, 400) } //400 ms
            var saatIni = binding.inputCurrent.text.toString()
            var tujuan = binding.inputGoal.text.toString()

            if (saatIni.isEmpty()) {
                binding.beratBadanSaatIni.error = getString(R.string.error_berat_kosong)
            } else if (saatIni.toFloat() < 6.0) {
                binding.beratBadanSaatIni.error = getString(R.string.error_berat_kurang)
            } else if (saatIni.toFloat() > 200.0) {
                binding.beratBadanSaatIni.error = getString(R.string.error_berat_lebih)
            } else if (saatIni.isNotEmpty() && saatIni == tujuan) {
                binding.beratBadanSaatIni.error = null
                binding.beratBadanTujuan.error = null
                binding.beratBadanTujuan.error =
                    getString(R.string.error_berat_beda)
            } else if (saatIni.toFloat() in 6.0..200.0) {
                binding.beratBadanSaatIni.error = null
            }

            if (tujuan.isEmpty()) {
                binding.beratBadanTujuan.error = getString(R.string.error_berat_tujuan_kosong)
            } else if (tujuan.toFloat() < 6.0) {
                binding.beratBadanTujuan.error = getString(R.string.error_berat_kurang)
            } else if (tujuan.toFloat() > 200.0) {
                binding.beratBadanTujuan.error = getString(R.string.error_berat_lebih)
            } else if (tujuan.isNotEmpty() && saatIni == tujuan) {
                binding.beratBadanSaatIni.error = null
                binding.beratBadanTujuan.error = null
                binding.beratBadanTujuan.error =
                    getString(R.string.error_berat_beda)
            } else if (tujuan.toFloat() in 6.0..200.0) {
                binding.beratBadanTujuan.error = null
            }
            if (saatIni.isNotEmpty() && tujuan.isNotEmpty() && saatIni.toFloat() in 6.0..200.0 && tujuan.toFloat() in 6.0..200.0 && saatIni != tujuan) {
                binding.beratBadanTujuan.error = null
                database = FirebaseDatabase.getInstance().getReference("User")
                databaseHistory = FirebaseDatabase.getInstance().getReference("History")

                saatIni = String.format("%.1f", saatIni.toFloat())
                tujuan = String.format("%.1f", tujuan.toFloat())

                val beratId = database.push().key!!
                val userId = FirebaseAuth.getInstance().currentUser?.uid

                val time: Long = System.currentTimeMillis()

                if (saatIni.contains(",") || tujuan.contains(",")) {
                    saatIni = saatIni.replace(",", ".")
                    tujuan = tujuan.replace(",", ".")
                }

                val onBoarding = User(userId, beratId, saatIni, tujuan, time)
                val riwayat = History(userId, beratId, saatIni, catatan = null, time)

                if (userId != null) {
                    database.child(userId).setValue(onBoarding).addOnSuccessListener {
                        binding.inputCurrent.text?.clear()
                        binding.inputGoal.text?.clear()
                    }
                    databaseHistory.child(beratId).setValue(riwayat).addOnSuccessListener {
                        binding.inputCurrent.text?.clear()
                        binding.inputGoal.text?.clear()
                    }
                }

                Toast.makeText(context, R.string.data_berhasil_masuk, Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_onBoardingFragment_to_bottomNavFragment)
            }


        }
    }
}