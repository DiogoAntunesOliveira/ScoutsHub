package com.mindoverflow.scoutshub

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle
import com.google.android.gms.maps.model.MarkerOptions
import com.mindoverflow.scoutshub.ApiLogicBackend.ActivitiesLocation
import java.net.CacheRequest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // Location
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback:  LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportActionBar!!.hide()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val options = GoogleMapOptions()
        val newLatitude = intent.getDoubleExtra("LOCATION_LATITUDE", 0.0)
        val newLongitude = intent.getDoubleExtra("LOCATION_LONGITUDE", 0.0)

        // Add a marker
        val activitiesLocation = ActivitiesLocation(newLatitude, newLongitude)
        mMap.addMarker(MarkerOptions().position(activitiesLocation).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(activitiesLocation))
        mMap.cameraPosition
        mMap.uiSettings.isZoomControlsEnabled = true
        try {
            val sucess = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.uber_maps_style))
            if (!sucess)
                    println("MPAS ERROR -> erro ao adicionar estilo")
        }catch (e:Resources.NotFoundException){
            println("SUCESSO")
        }
        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(false)
            .tiltGesturesEnabled(false)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(activitiesLocation, 18f))
    }
}