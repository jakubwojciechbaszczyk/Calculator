package com.example.kalkulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.kalkulator.calculator.HistoryDialogFragment
import com.example.kalkulator.calculator.KeyboardFragment
import com.example.kalkulator.calculator.KeyboardViewModel
import com.example.kalkulator.calculator.Repository

class MainActivity : AppCompatActivity() {
    //private val keyboardViewModel: KeyboardViewModel by viewModels()
    private val repository = Repository
    private val keyboardViewModel: KeyboardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, KeyboardFragment.newInstance(), "keyboardFragment")
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_history -> {
//                val fragment = supportFragmentManager
//                    .findFragmentByTag("keyboardFragment") as? KeyboardFragment
//                val str = fragment?.historyList
                // Obsługa kliknięcia opcji "Settings"
                // Możesz dodać tutaj kod, który ma być wykonany po kliknięciu opcji "Settings"
//                HistoryDialogFragment(str)
//                    .show(
                //val arr = ArrayList(str ?: listOf("asd"))
                //val viewModel = ViewModelProvider(this).get(KeyboardViewModel::class.java)
                val historyDialogFragment = HistoryDialogFragment()
                historyDialogFragment.show(supportFragmentManager, "historyFragment")
//                        supportFragmentManager.beginTransaction()
//                        .add(R.id.container, HistoryDialogFragment(str), "historyFragment")
//                            .commit()

                   // )
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}