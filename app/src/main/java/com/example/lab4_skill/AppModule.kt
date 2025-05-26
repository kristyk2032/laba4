package com.example.lab4_skill

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { SkillDatabase.getDatabase(androidContext()) }
    single { get<SkillDatabase>().skillDao() }
    single { get<SkillDatabase>().progressDao() }
    single { SkillRepository(get(), get()) }

    viewModel { SkillViewModel() }
}