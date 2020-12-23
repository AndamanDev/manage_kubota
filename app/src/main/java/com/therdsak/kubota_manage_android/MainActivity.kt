package com.therdsak.kubota_manage_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.therdsak.kubota_manage_android.fragment.MainFragment
import com.therdsak.kubota_manage_android.fragment.ScanFragment
import com.therdsak.kubota_manage_android.fragment.SettingFragment
import com.vanstone.appsdk.client.ISdkStatue
import com.vanstone.trans.api.SystemApi
import com.vanstone.trans.api.constants.GlobalConstants
import com.vanstone.utils.CommonConvert
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity  : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener , ScanFragment.ScanListener {

    var token : String = ""
    var type : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        initSystem()
        token = intent.getStringExtra("token")!!
        type = intent.getStringExtra("type")!!


        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)




//        if (type == ""){
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commit()
//        }else if (type == "in"){
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, ScanFragment.newInstance("in"))
//                .commit()
//        }else{
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, ScanFragment.newInstance("out"))
//                .commit()
//        }

       // displayScreen(-1)
    }


    private fun initSystem() {
        SystemApi.SystemInit_Api(
            0,
            CommonConvert.StringToBytes(GlobalConstants.CurAppDir + "/" + "\u0000"),
            this@MainActivity,
            object : ISdkStatue {
                override fun sdkInitSuccessed() {
                    Log.d("!!!", "sdkInitSuccess")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ScanFragment.newInstance("in"))
                        .commit()
                }

                override fun sdkInitFailed() {
                    Log.d("!!!", "sdkInitFailed")
                }
            })
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    fun displayScreen(id: Int){

        // val fragment =  when (id){

        when (id){
//            R.id.nav_check_in -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, ScanFragment.newInstance("in"))
//                    .commit()
//              //  supportFragmentManager.beginTransaction().replace(R.id.relativelayout, HomeFragment()).commit()
//            }
//
//            R.id.nav_check_out -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, ScanFragment.newInstance("out"))
//                    .commit()
//                //supportFragmentManager.beginTransaction().replace(R.id.relativelayout, PhotosFragment()).commit()
//            }
//
//            R.id.nav_setting -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, SettingFragment.newInstance())
//                    .commit()
//               // supportFragmentManager.beginTransaction().replace(R.id.relativelayout, MoviesFragment()).commit()
//            }

            R.id.nav_logout -> {
                onSignOut()
               // Toast.makeText(this, "Clicked Logout", Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                // Handle navigation view item clicks here.

        displayScreen(p0.itemId)

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCheckIn(code : String) {
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("code",code)
        intent.putExtra("type","in")
        intent.putExtra("token",token)
        startActivity(intent)
       // finish()
    }

    override fun onCheckOut(code : String) {
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("code",code)
        intent.putExtra("type","out")
        intent.putExtra("token",token)
        startActivity(intent)
        // finish()
    }


     private fun onSignOut() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }




}