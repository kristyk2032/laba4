package com.example.lab4_skill

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SkillViewModel : ViewModel(), KoinComponent {

    private val repository: SkillRepository by inject()

    val allSkills: LiveData<List<Skill>> = repository.allSkills.asLiveData()

    fun insert(skill: Skill) = viewModelScope.launch {
        repository.insertSkill(skill)
    }

    fun update(skill: Skill) = viewModelScope.launch {
        repository.updateSkill(skill)
    }

    fun delete(skill: Skill) = viewModelScope.launch {
        repository.deleteSkill(skill)
    }

    fun getSkill(id: Int): LiveData<Skill> {
        return repository.getSkill(id).asLiveData()
    }

    fun getProgressForSkill(skillId: Int): LiveData<List<Progress>> {
        return repository.getProgressForSkill(skillId).asLiveData()
    }

    fun getSkillsByLevel(level: Int): LiveData<List<Skill>> {
        return repository.getSkillsByLevel(level).asLiveData()
    }


    fun insertProgress(progress: Progress) = viewModelScope.launch {
        repository.insertProgress(progress)
    }

    fun updateProgress(progress: Progress) = viewModelScope.launch {
        repository.updateProgress(progress)
    }

    fun deleteProgress(progress: Progress) = viewModelScope.launch {
        repository.deleteProgress(progress)
    }
}