package com.example.empoweress

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.empoweress.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.getDefault())
        setSupportActionBar(binding.toolbar)

        binding.btnShowLocation.setOnClickListener{
            getAddress()
        }
        binding.btnSettings.setOnClickListener {
            var intent= Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun getAddress(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
        fusedLocationClient.lastLocation.addOnSuccessListener {
            geocoder=Geocoder(this, Locale.getDefault())
            try {
                val address=geocoder.getFromLocation(it.latitude,it.longitude,1)
                binding.tvAddress.text= address?.get(0)?.getAddressLine(0)
                binding.tvLatitude.text=it.latitude.toString()
                binding.tvLongitude.text=it.longitude.toString()

            }
            catch (e:Exception){
                Toast.makeText(this,"Unable to fetch the location",Toast.LENGTH_SHORT).show()
        }
        }

        }
    }
}
