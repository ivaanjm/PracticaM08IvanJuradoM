package com.example.ivanjuradom08practica.Fragmentos

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isGone
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ivanjuradom08practica.Dao.MarcasDao
import com.example.ivanjuradom08practica.Dao.MarcasDaoDB
import com.example.ivanjuradom08practica.Entidad.Marcas
import com.example.ivanjuradom08practica.Interfaz.Instancia
import com.example.ivanjuradom08practica.Interfaz.JsonApi
import com.example.ivanjuradom08practica.R

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel() : ViewModel() {
    lateinit var marcasDao: MarcasDao
    lateinit var marcas: List<Marcas>

    fun callGetMarcas(view: View, context: Context?, refrezcar: Boolean) {
        val interfaz: JsonApi = Instancia.getinstancia().create(JsonApi::class.java)
        val resultados = interfaz.getDataFromJson()
        marcasDao = MarcasDaoDB.getInstance(context!!).marcasDao()
        val conexion = view.findViewById<ImageView>(R.id.conexion)
        val loadingView = view.findViewById<ProgressBar>(R.id.progreso)
        val volver = view.findViewById<Button>(R.id.volver)

        if (marcasDao.getAll().isEmpty() || refrezcar) {
            resultados.enqueue(object : Callback<List<Marcas>> {
                override fun onFailure(call: Call<List<Marcas>>, t: Throwable) {
                    loadingView.isGone = true
                    conexion.isGone = false
                    volver.isGone = false
                    Log.d("INFO", "${t.message}")
                    Toast.makeText(context, "No se han podido cargar los datos ${t.message}", Toast.LENGTH_SHORT).show()
                    volver.setOnClickListener {
                        callGetMarcas(view,context,false)
                    }
                    if (!marcasDao.getAll().isEmpty())ocultar(loadingView, conexion, volver)
                }

                override fun onResponse(
                    call: Call<List<Marcas>>,
                    response: Response<List<Marcas>>
                ) {
                    conexion.isGone = true
                    loadingView.isGone = true
                    volver.isGone = true
                    Log.d("INFO", "marcas cogidas del ${response.raw()}")
                    marcas = response.body()!!
                    Log.d("INFO", "Insertando Marca en b")
                    marcasDao.insertar(marcas)

                }
            })
        } else ocultar(loadingView, conexion, volver)
    }

    fun findAllMarcas(): LiveData<List<Marcas>> {
        return marcasDao.findAll()
    }

    private fun ocultar(
        loadingView: ProgressBar,
        conexion: ImageView,
        volver: Button
    ) {
        loadingView.isGone = true
        conexion.isGone = true
        volver.isGone = true
    }
}