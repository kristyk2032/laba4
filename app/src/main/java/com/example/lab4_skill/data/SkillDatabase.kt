package com.example.lab4_skill.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Skill::class, Progress::class], version = 4, exportSchema = false)
abstract class SkillDatabase : RoomDatabase() {

    abstract fun skillDao(): SkillDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile
        private var INSTANCE: SkillDatabase? = null

        fun getDatabase(context: Context): SkillDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SkillDatabase::class.java,
                    "skill_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}