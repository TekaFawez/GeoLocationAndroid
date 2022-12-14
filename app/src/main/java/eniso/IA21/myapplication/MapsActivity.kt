package eniso.IA21.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Criteria
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import eniso.IA21.myapplication.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sousse = LatLng(35.82, 10.64)
        mMap.addMarker(MarkerOptions().position(sousse).title("Marker in sousse"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sousse))
        val tunis = LatLng(36.8, 10.17)
        mMap.addMarker(MarkerOptions().position(tunis).title("Marker in tunis"))
        mMap.mapType=GoogleMap.MAP_TYPE_SATELLITE
        val options=PolylineOptions()
        options.add(sousse,tunis)
        mMap.addPolyline(options)
        mMap.setOnMapClickListener {
            Toast.makeText(this,"${it.latitude}\n ${it.longitude}",Toast.LENGTH_LONG ).show()
        }
        mMap.setOnMarkerClickListener {
            if (it.position==sousse){
                val uri= Uri.parse("http://fr.wikipedia.org/wiki/sousse")
                val i =Intent(Intent.ACTION_VIEW,uri)
                startActivity(i)
            }
            true
        }
        val ln=getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria=Criteria()
        criteria.accuracy=Criteria.ACCURACY_FINE
        criteria.isCostAllowed=true
        val best =ln.getBestProvider(criteria,true)
        Toast.makeText(this,best,Toast.LENGTH_LONG).show()
val location=ln.getLastKnownLocation(best!!)
if(location!=null){
    val lat=location.latitude
    val log=location.longitude
    Toast.makeText(this,"$lat \n $log",Toast.LENGTH_LONG).show()
}
    }
}