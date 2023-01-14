package org.d3ifcool.beratbadankita.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.databinding.FragmentTambahBeratBinding
import java.text.SimpleDateFormat
import java.util.*

class TambahBeratFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTambahBeratBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tambah_berat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTambahBeratBinding.bind(view)
        val currentTime = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        val tanggal = Date(currentTime)
        val time = simpleDateFormat.format(tanggal)
        binding.tvNow.text = time
        simpanDataHarian()
    }

    private fun simpanDataHarian() {

        binding.btnSimpan.setOnClickListener {
            it?.apply { isEnabled = false; postDelayed({ isEnabled = true }, 400) }
            var beratBadan = binding.inputBeratBadan.text.toString()
            val catatan = binding.inputCatatan.text.toString()

            if (beratBadan.isEmpty()) {
                binding.tilBeratBadan.error = getString(R.string.error_berat_kosong)
            } else if (beratBadan.toFloat() < 6.0) {
                binding.tilBeratBadan.error = getString(R.string.error_berat_kurang)
            } else if (beratBadan.toFloat() > 200.0) {
                binding.tilBeratBadan.error = getString(R.string.error_berat_lebih)
            } else if (beratBadan.isNotEmpty()) {
                binding.tilBeratBadan.error = null
                database = FirebaseDatabase.getInstance().getReference("History")

                beratBadan = String.format("%.1f", beratBadan.toFloat())

                val beratId = database.push().key!!
                val userId = FirebaseAuth.getInstance().currentUser?.uid

                val time: Long = System.currentTimeMillis()

                if (beratBadan.contains(",") || beratBadan.contains(",")) {
                    beratBadan = beratBadan.replace(",", ".")
                }

                val riwayat = History(userId, beratId, beratBadan, catatan, time)

                if (userId != null) {
                    database.child(beratId).setValue(riwayat).addOnSuccessListener {
                        binding.inputBeratBadan.text?.clear()
                        binding.inputCatatan.text?.clear()
                        dismiss()
                    }
                    Firebase.database.getReference("User/$userId/")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {}

                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val tujuan = snapshot.child("tujuan").value.toString()
                                    if (tujuan == beratBadan) {
                                        sudahSelesai()
                                    }
                                }
                            }
                        })
                }
            } else {
                binding.tilBeratBadan.error = null
            }

        }

    }

    private fun sudahSelesai() {
        Toast.makeText(
            context, R.string.selamat_tujuan_sama,
            Toast.LENGTH_SHORT
        ).show()
    }

}