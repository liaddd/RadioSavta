package com.liad.radiosavta.di

import com.liad.radiosavta.RadioSavtaApplication
import com.liad.radiosavta.database.RadioSavtaDatabase
import com.liad.radiosavta.repositories.RadioRepository
import com.liad.radiosavta.viewmodels.ProgramsViewModel
import org.koin.dsl.module

val appModule = module {

    // single instance of RadioSavtaApplication
    single { RadioSavtaApplication.instance }

    // single instance of RadioSavtaDatabase
    single { RadioSavtaDatabase.getDatabase(RadioSavtaApplication.instance) }

    // single instance of Retrofit
    single { RetrofitImpl.getRetrofit() }

    // single instance of SavtaRepository
    single { RadioRepository(get(), get()) }


    factory { ProgramsViewModel(get()) }
}