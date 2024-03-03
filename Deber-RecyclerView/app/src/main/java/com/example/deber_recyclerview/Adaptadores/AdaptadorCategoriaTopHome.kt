package com.example.deber_recyclerview.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber_recyclerview.MainActivity
import com.example.deber_recyclerview.R

class AdaptadorCategoriaTopHome(
    private val contexto: MainActivity,
    private val lista: ArrayList<String>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AdaptadorCategoriaTopHome.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val btnCategoria: Button

        init {
            btnCategoria = view.findViewById<Button>(R.id.btn_rv_vista_categoria_top_home)
        }
        /*fun anadirLike(){
            numeroLikes = numeroLikes+1
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }

         */
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.rv_vista_categorias_top_home,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoriaActual = this.lista[position]
        holder.btnCategoria.text = categoriaActual.toString()
    }
}