package org.d3ifcool.beratbadankita

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.beratbadankita.viewmodel.FirebaseUserViewModel


class MainActivity : AppCompatActivity() {

    private var pressedTime: Long = 0
    private lateinit var navHostFragment: NavHostFragment

    private val viewModel: FirebaseUserViewModel by lazy {
        ViewModelProvider(this)[FirebaseUserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val content: View = findViewById(android.R.id.content)

        viewModel.authState.observe(this)  {
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (it != null) {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            val rootRef = FirebaseDatabase.getInstance().reference
                            val uid = FirebaseAuth.getInstance().currentUser!!.uid
                            val uidRef = rootRef.child("User").child(uid)
                            uidRef.get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val snapshot = task.result
                                    val userId = snapshot.child("userId").getValue(String::class.java)
                                    if (uid == userId) {
                                        content.viewTreeObserver.removeOnPreDrawListener(this)
                                    } else if (userId == null){
                                        Navigation.findNavController(
                                            this@MainActivity, R.id.fragmentContainerView
                                        ).navigate(R.id.onBoardingFragment)
                                    }
                                } else {
                                    Log.d("TAG", task.exception!!.message!!) //Don't ignore potential errors!
                                }
                            }
                            true
                        } else {
                            Navigation.findNavController(
                                this@MainActivity, R.id.fragmentContainerView
                            ).navigate(R.id.loginFragment)

                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            false
                        }
                    }
                }
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                navHostFragment.navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(baseContext, "Tekan sekali lagi untuk keluar ", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }
}
