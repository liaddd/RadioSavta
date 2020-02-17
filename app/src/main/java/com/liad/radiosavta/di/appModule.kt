package com.liad.radiosavta.di

import com.liad.radiosavta.RadioSavtaApplication
import com.liad.radiosavta.database.RadioSavtaDatabase
import org.koin.dsl.module

val appModule = module {

    single { RadioSavtaApplication.instance }

    single { RadioSavtaDatabase.getDatabase(get()) }

    //single { RetrofitImpl.getRetrofit() }
}