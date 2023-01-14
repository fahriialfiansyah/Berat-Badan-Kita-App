package org.d3ifcool.beratbadankita.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.databinding.FragmentCekIdealBinding

class CekIdealFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCekIdealBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCekIdealBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.btnCek.setOnClickListener {
            val tinggi = binding.inputTinggi.text.toString()


            when {
                tinggi.isEmpty() -> {
                    binding.tinggiBadanSaatIni.error = getString(R.string.error_tinggi_kosong)
                }
                tinggi.toFloat() < 20.0 -> {
                    binding.tvRekomendasiBerat.text = null
                    binding.tinggiBadanSaatIni.error = getString(R.string.error_tinggi_kurang)
                }
                tinggi.toFloat() > 200.0 -> {
                    binding.tvRekomendasiBerat.text = null
                    binding.tinggiBadanSaatIni.error = getString(R.string.error_tinggi_lebih)
                }
                tinggi.isNotEmpty() -> {
                    binding.tinggiBadanSaatIni.isErrorEnabled = false

                    var beratMinim = (18.5 * tinggi.toFloat() * tinggi.toFloat() / 10000).toString()
                    var beratMax = (24.9 * tinggi.toFloat() * tinggi.toFloat() / 10000).toString()

                    beratMinim = String.format("%.1f", beratMinim.toFloat())
                    beratMax = String.format("%.1f", beratMax.toFloat())

                    binding.tvRekomendasiBerat.visibility = View.VISIBLE
                    "Rekomendasi berat badan kamu: $beratMinim - $beratMax kg".also { binding.tvRekomendasiBerat.text = it }
                }
            }

        }
    }
}