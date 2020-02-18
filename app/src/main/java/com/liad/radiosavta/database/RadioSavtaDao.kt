package com.liad.radiosavta.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.liad.radiosavta.models.Program


@Dao
interface RadioSavtaDao {

    @Query("SELECT * FROM programs")
    fun getPrograms(): LiveData<List<Program>>

    @Insert
    fun insertPrograms(programs: List<Program>)

    @Query("SELECT * from programs WHERE id = :id")
    fun getProgramById(id: Int) : LiveData<Program>
}