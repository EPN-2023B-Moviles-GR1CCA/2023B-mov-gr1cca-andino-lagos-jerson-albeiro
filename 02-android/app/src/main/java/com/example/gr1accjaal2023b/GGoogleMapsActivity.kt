package com.example.gr1accjaal2023b

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class GGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
    }
    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermiso = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat.checkSelfPermission(
            contexto,
            nombrePermiso
        )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this, //Contexto
                arrayOf( // Arreglo permisos
                    nombrePermiso,nombrePermisoCoarse
                ),
                1
            )
        }
    }
    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{ googleMap ->
            with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                moverQuicentro()
                //anadirPoligono()
                anadirPolilinea()
                escucharListeners()
            }
        }
    }
    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat.checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos){
                mapa.isMyLocationEnabled = true //tenemos permisos
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled=true
        }
    }
    fun moverQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(
            -0.18021490689877717, -78.4972281882293
        )
        val titulo = "San Gabriel"
        val markquicentro = anadirMarcador(quicentro, titulo)
        markquicentro.tag = titulo
        moverCamaraConZoom(quicentro, zoom)
    }
    fun anadirMarcador(latLng: LatLng, title: String): Marker{
        return mapa.addMarker(
            MarkerOptions().position(latLng).title(title)
        )!!
    }
    fun moverCamaraConZoom(latLng: LatLng, zoom: Float=10f){
        mapa.moveCamera(
            CameraUpdateFactory.newLatLngZoom(latLng,zoom)
        )
    }
    fun anadirPolilinea(){
        with(mapa){
            val poliLineaUno = mapa.addPolyline(
                PolylineOptions().clickable(true)
                    .add(LatLng(-0.1759187040647396,
                        -78.48506472421384),
                        LatLng(-0.17632468492901104,
                            -78.48265589308046),
                        LatLng(-0.17746143130181483,
                            -78.4770533307815))
            )
            poliLineaUno.color= -0xc771c4
        }
    }
    fun anadirPoligono(){
        with(mapa){
            val poligonoUno=mapa.addPolygon(
                PolygonOptions()
                    .clickable(true)
                    .add(LatLng(-0.1759187040647396,
                        -78.48506472421384),
                        LatLng(-0.17632468492901104,
                            -78.48265589308046),
                        LatLng(-0.17746143130181483,
                            -78.4770533307815))
            )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-2"// <- ID
        }
    }
    fun escucharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa","setOnPolygonClickListener ${it}")
            it.tag
        }
        mapa.setOnPolylineClickListener {
            Log.i("mapa","setOnPolylineClickListener ${it}")
            it.tag
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa","setOnMarkerClickListener ${it}")
            it.tag
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            Log.i("mapa","setOnCameraMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa","setOnCameraMoveStartedListener")
        }
        mapa.setOnCameraIdleListener {
            Log.i("mapa","setOnCameraIdleListener")
        }
    }
}