package com.example.ivanjuradom08practica.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ivanjuradom08practica.Entidad.Marcas



@Dao
interface MarcasDao {
    @Query("SELECT * FROM  Marcas")
     fun getAll(): List<Marcas>
    @Query("SELECT * FROM  Marcas")
    fun findAll():LiveData<List<Marcas>>

    @Update
     fun update(marca: Marcas)

    @Insert(onConflict= OnConflictStrategy.REPLACE)
     fun insertar(marcas: List<Marcas>)
}