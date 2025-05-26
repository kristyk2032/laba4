package com.example.lab4_skill.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillDao {
    @Query("SELECT * FROM skills ORDER BY name ASC")
    fun getSkills(): Flow<List<Skill>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(skill: Skill)

    @Update
    suspend fun update(skill: Skill)

    @Delete
    suspend fun delete(skill: Skill)

    @Query("SELECT * FROM skills WHERE id = :id")
    fun getSkill(id: Int): Flow<Skill>

    @Query("SELECT skills.* FROM skills INNER JOIN (SELECT skillId, MAX(level) AS maxLevel FROM progress GROUP BY skillId) AS maxProgress ON skills.id = maxProgress.skillId WHERE maxProgress.maxLevel = :level")
    fun getSkillsByLevel(level: Int): Flow<List<Skill>>
}