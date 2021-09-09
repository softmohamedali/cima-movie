package com.example.graduateproject.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.graduateproject.R
import com.example.graduateproject.databinding.ActivityMainBinding
import com.example.graduateproject.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,NavController.OnDestinationChangedListener {

    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!
    private  val userViewModel: UserViewModel by viewModels<UserViewModel>()
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=findNavController(R.id.fragment2)
        binding.bottomNavigationView.setupWithNavController(navController)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment
        val myNavhost=navHostFragment.navController
        myNavhost.addOnDestinationChangedListener(this)

        imgb_option_mainactiv.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                imgb_option_mainactiv.performLongClick()
            }

        } )
        registerForContextMenu(imgb_option_mainactiv)




    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.option_meanue,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.option_contact ->{

            }
            R.id.option_logout ->{
                userViewModel.logOut()
                startActivity(Intent(this,UserActivity::class.java))
                finish()
            }
            else -> return false
        }
        return super.onContextItemSelected(item)
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()||navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id==R.id.detailsFragment){
            bottomNavigationView.visibility=View.VISIBLE
        }
        else{
            bottomNavigationView.visibility=View.VISIBLE
        }

    }
}