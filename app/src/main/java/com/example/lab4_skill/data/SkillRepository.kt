package com.example.lab4_skill.data

import kotlinx.coroutines.flow.Flow

class SkillRepository(private val skillDao: SkillDao, private val progressDao: ProgressDao) {

    val allSkills: Flow<List<Skill>> = skillDao.getSkills()

    suspend fun insertSkill(skill: Skill) {
        skillDao.insert(skill)
    }

    suspend fun updateSkill(skill: Skill) {
        skillDao.update(skill)
    }

    suspend fun deleteSkill(skill: Skill) {
        skillDao.delete(skill)
    }

    fun getSkill(id: Int): Flow<Skill> {
        return skillDao.getSkill(id)
    }

    fun getProgressForSkill(skillId: Int): Flow<List<Progress>> {
        return progressDao.getProgressForSkill(skillId)
    }

    fun getSkillsByLevel(level: Int): Flow<List<Skill>> {
        return skillDao.getSkillsByLevel(level)
    }

    suspend fun insertProgress(progress: Progress) {
        progressDao.insert(progress)
    }

    suspend fun updateProgress(progress: Progress) {
        progressDao.update(progress)
    }

    suspend fun deleteProgress(progress: Progress) {
        progressDao.delete(progress)
    }
}