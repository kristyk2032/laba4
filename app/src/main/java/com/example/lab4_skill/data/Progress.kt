package com.example.lab4_skill.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "progress",
    foreignKeys = [ForeignKey(entity = Skill::class,
        parentColumns = ["id"],
        childColumns = ["skillId"],
        onDelete = ForeignKey.CASCADE)])
data class Progress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val skillId: Int,
    val level: Int,
    val notes: String?,
    var date: Long = System.currentTimeMillis()
)