package com.ispace.mycar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ispace.mycar.databinding.ActivityMainBinding
import com.ispace.mycar.fragments.entry.FuelFragmentDirections
import com.ispace.mycar.fragments.list.CarDetailsFragmentArgs
import com.ispace.mycar.viewmodel.MyCarViewModel
import com.ispace.mycar.fragments.list.ListFragment
import com.ispace.mycar.fragments.entry.FuelFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MyCarViewModel

    private lateinit var navController: NavController

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MyCarViewModel::class.java]

        bottomNavigationView = mainBinding.bottomNav

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment


        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.carEntryFragment, R.id.fuelFragment, R.id.listFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)



    }


    override fun onResume() {
        super.onResume()

        //setting up badges to show the size of the list and fuel entries
        bottomNavigationView.getOrCreateBadge(R.id.listFragment).apply {
            var itemCount = 0
            mainViewModel.readAllCarData.observe(this@MainActivity, Observer {
                itemCount = it.size
            })
            number = itemCount
            isVisible = itemCount > 0

        }

        bottomNavigationView.getOrCreateBadge(R.id.fuelFragment).apply {
            var count = 0
            mainViewModel.readAllFuelData.observe(this@MainActivity) {
                count = it.size
            }
            number = count

            isVisible = count > 0
        }



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



  /*  //create a fun to start the fragment on a button clicked event
     fun onClick(v: View) {
       val entryFrag = CarEntryFragment()
       val transaction = supportFragmentManager.beginTransaction()
       transaction.replace(R.id.frag_container, entryFrag)
        transaction.commit()
    }*/
}