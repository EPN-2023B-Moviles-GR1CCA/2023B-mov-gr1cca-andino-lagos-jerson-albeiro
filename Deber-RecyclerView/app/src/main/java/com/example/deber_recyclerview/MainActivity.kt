package com.example.deber_recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.deber_recyclerview.Adaptadores.AdaptadorCategoriaTopHome
import com.example.deber_recyclerview.Adaptadores.AdaptadorVideosRecomendadosHome
import com.example.deber_recyclerview.Adaptadores.AdaptadorVolverEscuchar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv_horizontal_top_categoria = findViewById<RecyclerView>(R.id.rv_categoria_top_home)

        val dataSourceCategoria = ArrayList<String>()
        dataSourceCategoria.add("Energía")
        dataSourceCategoria.add("Relajación")
        dataSourceCategoria.add("Para sentirte bien")
        dataSourceCategoria.add("Entrenamiento")
        dataSourceCategoria.add("Viaje diario")
        dataSourceCategoria.add("Concentración")
        dataSourceCategoria.add("Triste")

        val linearLayoutManagerCategoria = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapterCategoriaTopHome = AdaptadorCategoriaTopHome(this, dataSourceCategoria, rv_horizontal_top_categoria)
        rv_horizontal_top_categoria.setLayoutManager(linearLayoutManagerCategoria)
        rv_horizontal_top_categoria.setAdapter(adapterCategoriaTopHome)

        val rv_horizontal_grid_vovler_escuchar = findViewById<RecyclerView>(R.id.rv_volver_escuchar_home)

        val dataSourceVolverEscuchar = ArrayList<String>()
        dataSourceVolverEscuchar.add("LA PRIMERA")
        dataSourceVolverEscuchar.add("L-Gante RKT()")
        dataSourceVolverEscuchar.add("WOS")
        dataSourceVolverEscuchar.add("ALTO VISTO")
        dataSourceVolverEscuchar.add("ANDRÓMEDA")
        dataSourceVolverEscuchar.add("wiggy")
        dataSourceVolverEscuchar.add("DANCE CRIP")
        dataSourceVolverEscuchar.add("MORFEO")
        dataSourceVolverEscuchar.add("Remember The Time")
        dataSourceVolverEscuchar.add("Tamam Tamam")
        dataSourceVolverEscuchar.add("DILUVIO")
        dataSourceVolverEscuchar.add("Snow Tha Product")
        dataSourceVolverEscuchar.add("Dueles Tan Bien")
        dataSourceVolverEscuchar.add("Tú")
        dataSourceVolverEscuchar.add("La Dueña Del Swing")
        dataSourceVolverEscuchar.add("El Bolero")
        dataSourceVolverEscuchar.add("Tiburón")

        val gridLinearLayoutManagerVolverEscuchar = GridLayoutManager(this,2,LinearLayoutManager.HORIZONTAL, false)
        val adapterVolverEscucharHome = AdaptadorVolverEscuchar(this, dataSourceVolverEscuchar,rv_horizontal_grid_vovler_escuchar)
        rv_horizontal_grid_vovler_escuchar.setLayoutManager(gridLinearLayoutManagerVolverEscuchar)
        rv_horizontal_grid_vovler_escuchar.setAdapter(adapterVolverEscucharHome)

        val rv_horizontal_videos_recomendados = findViewById<RecyclerView>(R.id.rv_videos_recomendados_home)

        val dataSourceVideosRecomendados = ArrayList<String>()
        dataSourceVideosRecomendados.add("LA PRIMERA")
        dataSourceVideosRecomendados.add("L-Gante RKT()")
        dataSourceVideosRecomendados.add("WOS")
        dataSourceVideosRecomendados.add("ALTO VISTO")
        dataSourceVideosRecomendados.add("ANDRÓMEDA")
        dataSourceVideosRecomendados.add("wiggy")
        dataSourceVideosRecomendados.add("DANCE CRIP")
        dataSourceVideosRecomendados.add("MORFEO")
        dataSourceVideosRecomendados.add("Remember The Time")

        val linearLayoutManagerVideosRecomendados = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        val adapterVideosRecomendados = AdaptadorVideosRecomendadosHome(this, dataSourceVideosRecomendados,rv_horizontal_videos_recomendados)
        rv_horizontal_videos_recomendados.setLayoutManager(linearLayoutManagerVideosRecomendados)
        rv_horizontal_videos_recomendados.setAdapter(adapterVideosRecomendados)
    }
}