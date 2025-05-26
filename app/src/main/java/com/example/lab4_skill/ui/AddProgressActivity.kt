package com.example.lab4_skill.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4_skill.R
import com.example.lab4_skill.SkillViewModel
import com.example.lab4_skill.data.Progress
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProgressActivity : AppCompatActivity() {

    private val skillViewModel: SkillViewModel by viewModel()
    private lateinit var levelEditText: EditText
    private lateinit var notesEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private var skillId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_progress)

        levelEditText = findViewById(R.id.levelEditText)
        notesEditText = findViewById(R.id.notesEditText)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)

        skillId = intent.getIntExtra("skillId", -1)
        val progressId = intent.getIntExtra("progressId", 0)

        saveButton.setOnClickListener {
            val level = levelEditText.text.toString().toIntOrNull() ?: 1
            val notes = notesEditText.text.toString()

            if (skillId != -1 && level in 1..5) {
                val progress = Progress(skillId = skillId, level = level, notes = notes, date = System.currentTimeMillis())
                skillViewModel.insertProgress(progress)
                finish()
            } else {
                levelEditText.error = "Level should be a number from 1 to 5\n"
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}