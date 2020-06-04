package com.liad.radiosavta.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import co.climacell.statefulLiveData.core.*
import com.liad.radiosavta.database.RadioSavtaDao
import com.liad.radiosavta.database.RadioSavtaDatabase
import com.liad.radiosavta.models.Program
import com.liad.radiosavta.models.StreamTitle
import com.liad.radiosavta.models.User
import com.liad.radiosavta.server_connection.ApiRequest
import retrofit2.Retrofit
import java.util.concurrent.Executors

class RadioRepository(radioSavtaDatabase: RadioSavtaDatabase, retrofit: Retrofit) {

    val statefulLiveDataPrograms: StatefulLiveData<List<Program>>
    val statefulLiveDataUsers: StatefulLiveData<List<User>>
    val statefulLiveDataCurrentSong: StatefulLiveData<String>

    private val apiRequest: ApiRequest
    private val executor = Executors.newSingleThreadExecutor()

    private val dao: RadioSavtaDao

    init {
        dao = radioSavtaDatabase.dao()
        apiRequest = retrofit.create(ApiRequest::class.java)

        statefulLiveDataPrograms = getProgramsStatefulLiveData()
        statefulLiveDataUsers = getUsersStatefulLiveData()
        statefulLiveDataCurrentSong = getCurrentPlayingSongTitle()
    }

    // Get Programs StatefulLiveData
    private fun getProgramsStatefulLiveData(): StatefulLiveData<List<Program>> {
        val programsMutableLiveData = MutableStatefulLiveData<List<Program>>()

        programsMutableLiveData.putLoading()

        getProgramsFromDatabase().observeOnce(Observer { dbPrograms ->
            if (dbPrograms.isNullOrEmpty()) {
                getProgramsFromApi().observeOnce(Observer { apiPrograms ->
                    if (apiPrograms is StatefulData.Success) {
                        programsMutableLiveData.putData(apiPrograms.data)
                        saveProgramsInDatabase(apiPrograms.data)
                    }
                })
            } else {
                programsMutableLiveData.putData(dbPrograms)
            }
        })

        return programsMutableLiveData
    }

    // Save Programs in DB
    private fun saveProgramsInDatabase(data: List<Program>) {
        executor.submit {
            dao.insertPrograms(data)
        }
    }

    // Get Programs from API
    private fun getProgramsFromApi() = apiRequest.getPrograms()

    // Get Programs from DB
    private fun getProgramsFromDatabase(): LiveData<List<Program>> = dao.getPrograms()

    // Get Program By Id StatefulLiveData
    fun getProgramById(id: Int): StatefulLiveData<Program> {
        val programMutableStateful = MutableStatefulLiveData<Program>()
        programMutableStateful.putLoading()

        getProgramByIdFromDatabase(id).observeOnce(Observer { dbProgram ->
            if (dbProgram == null || dbProgram.recorded_shows.isNullOrEmpty()) {
                getProgramByIdFromApi(id).observeOnce(Observer { apiProgram ->
                    if (apiProgram is StatefulData.Success) {
                        programMutableStateful.putData(apiProgram.data)
                        updateProgramInDatabase(apiProgram.data)
                    }
                })
            } else {
                programMutableStateful.putData(dbProgram)
            }
        })

        return programMutableStateful
    }

    private fun updateProgramInDatabase(program: Program) {
        executor.submit {
            dao.insertProgram(program)
        }
    }

    // Get Program By Id from DB
    private fun getProgramByIdFromDatabase(id: Int) = dao.getProgramById(id)

    // Get Program By Id from API
    private fun getProgramByIdFromApi(id: Int) = apiRequest.getProgramById(id)

    // Get Users StatefulLiveData
    private fun getCurrentPlayingSongTitle(): StatefulLiveData<String> {

        val mutableSongTitle = MutableStatefulLiveData<String>()
        mutableSongTitle.putLoading()

        getCurrentPlayingSongFromApi().observeForever { songTitle ->
            if (songTitle is StatefulData.Success) {
                Log.d("Liad", songTitle.data.title)
                mutableSongTitle.putData(songTitle.data.title)
            } else if (songTitle is StatefulData.Error) {
                Log.d("Liad", songTitle.throwable.localizedMessage)
            }
        }

        return mutableSongTitle
    }

    // Get Users StatefulLiveData
    private fun getUsersStatefulLiveData(): StatefulLiveData<List<User>> {
        val mutableUsersLiveData = MutableStatefulLiveData<List<User>>()

        mutableUsersLiveData.putLoading()

        getUsersFromDatabase().observeOnce(Observer { dbUsers ->
            if (dbUsers.isNullOrEmpty()) {
                getUsersFromApi().observeOnce(Observer { apiUsers ->
                    if (apiUsers is StatefulData.Success) {
                        mutableUsersLiveData.putData(apiUsers.data)
                        saveUsersInDatabase(apiUsers.data)
                    }
                })
            } else {
                mutableUsersLiveData.putData(dbUsers)
            }
        })

        return mutableUsersLiveData
    }

    // Save Users in DB
    private fun saveUsersInDatabase(users: List<User>) {
        executor.submit {
            try {
                dao.insertUsers(users)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Get users from API
    private fun getUsersFromApi(): StatefulLiveData<List<User>> = apiRequest.getUsers()

    // Get users from DB
    private fun getUsersFromDatabase(): LiveData<List<User>> = dao.getUsers()

    // Get Current Song playing from API
    private fun getCurrentPlayingSongFromApi(): StatefulLiveData<StreamTitle> = apiRequest.getCurrentPlayingSongTitle()

}