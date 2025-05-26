package com.example.lab4_skill

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress WHERE skillId = :skillId ORDER BY date DESC")
    fun getProgressForSkill(skillId: Int): Flow<List<Progress>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(progress: Progress)

    @Update
    suspend fun update(progress: Progress)

    @Delete
    suspend fun delete(progress: Progress)
}