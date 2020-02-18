package com.liad.radiosavta.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import co.climacell.statefulLiveData.core.*
import com.liad.radiosavta.database.RadioSavtaDao
import com.liad.radiosavta.database.RadioSavtaDatabase
import com.liad.radiosavta.models.Program
import com.liad.radiosavta.server_connection.ApiRequest
import retrofit2.Retrofit
import java.util.concurrent.Executors

class RadioRepository(radioSavtaDatabase: RadioSavtaDatabase, retrofit: Retrofit) {

    val statefulLiveDataPrograms: StatefulLiveData<List<Program>>
    private val apiRequest: ApiRequest
    private val executor = Executors.newSingleThreadExecutor()

    private val dao: RadioSavtaDao

    init {
        Log.d("Liad", "initialized $this")

        dao = radioSavtaDatabase.dao()
        apiRequest = retrofit.create(ApiRequest::class.java)

        statefulLiveDataPrograms = getProgramsStatefulLiveData()
    }

    private fun getProgramsStatefulLiveData(): StatefulLiveData<List<Program>> {
        val programsMutableLiveData = MutableStatefulLiveData<List<Program>>()

        programsMutableLiveData.putLoading()

        getProgramsFromDatabase().observeOnce(Observer { dbPrograms ->
            Log.d("Liad" , "getting Programs from Database")
            if (dbPrograms.isNullOrEmpty()) {
                getProgramsFromApi().observeOnce(Observer { apiPrograms ->
                    Log.d("Liad" , "getting Programs from Api")
                    if (apiPrograms is StatefulData.Success) {
                        Log.d("Liad", apiPrograms.data.toString())
                        programsMutableLiveData.putData(apiPrograms.data)
                        saveProgramInDatabase(apiPrograms.data)
                    }
                })
            } else {
                programsMutableLiveData.putData(dbPrograms)
            }
        })

        return programsMutableLiveData
    }

    private fun saveProgramInDatabase(data: List<Program>) {
        executor.submit {
            dao.insertPrograms(data)
            Log.d("Liad", "programs inserted to Database successfully!")
        }
    }

    private fun getProgramsFromApi() = apiRequest.getPrograms()

    private fun getProgramsFromDatabase(): LiveData<List<Program>> = dao.getPrograms()

    fun getProgramById(id: Int) : StatefulLiveData<Program>{
        val programMutableStateful = MutableStatefulLiveData<Program>()
        programMutableStateful.putLoading()

        getProgramByIdFromDatabase(id).observeOnce(Observer { dbProgram ->
            Log.d("Liad" , "getting Program $id from Database")
            if (dbProgram == null) {
                Log.d("Liad", "getting Program $id from Api")
                getProgramByIdFromApi(id).observeOnce(Observer { apiProgram ->
                    if (apiProgram is StatefulData.Success) {
                        programMutableStateful.putData(apiProgram.data)
                    }
                })
            } else {
                programMutableStateful.putData(dbProgram)
            }
        })

        return programMutableStateful
    }

    private fun getProgramByIdFromDatabase(id : Int) = dao.getProgramById(id)

    private fun getProgramByIdFromApi(id : Int) = apiRequest.getProgramById(id)
}