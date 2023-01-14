package org.d3ifcool.beratbadankita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.d3ifcool.beratbadankita.ui.HomeFragment
import org.d3ifcool.beratbadankita.ui.ProfileFragment
import org.d3ifcool.beratbadankita.ui.RiwayatFragment
import org.d3ifcool.beratbadankita.databinding.FragmentBottomNavBinding

class BottomNavFragment : Fragment() {

    private lateinit var binding: FragmentBottomNavBinding
    private lateinit var bottomNavView : BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomNavBinding.inflate(layoutInflater)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragment(HomeFragment())

        bottomNavView = binding.bottomNavView

        val homeFragment = HomeFragment()
        val riwayatFragment = RiwayatFragment()
        val profileFragment = ProfileFragment()

        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    setFragment(homeFragment)
                }
                R.id.riwayatFragment -> {
                    setFragment(riwayatFragment)
                }
                R.id.profileFragment -> {
                    setFragment(profileFragment)
                }
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
}