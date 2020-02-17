package com.liad.radiosavta.di

import com.liad.radiosavta.RadioSavtaApplication
import org.koin.dsl.module

val appModule = module {

    single { RadioSavtaApplication.instance }

    //single { RetrofitImpl.getRetrofit() }
}