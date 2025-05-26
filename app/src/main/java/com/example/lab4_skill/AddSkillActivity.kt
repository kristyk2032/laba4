package com.example.lab4_skill

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class AddSkillActivity : AppCompatActivity() {

    private lateinit var skillNameEditText: EditText
    private lateinit var skillDescriptionEditText: EditText
    private lateinit var addButton: Button
    private lateinit var cancelButton: Button
    private lateinit var skillViewModel: SkillViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_skill)

        skillNameEditText = findViewById(R.id.skillNameEditText)
        skillDescriptionEditText = findViewById(R.id.skillDescriptionEditText)
        addButton = findViewById(R.id.addButton)
        cancelButton = findViewById(R.id.cancelButton)

        skillViewModel = ViewModelProvider(this)[SkillViewModel::class.java]

        addButton.setOnClickListener {
            val name = skillNameEditText.text.toString()
            val description = skillDescriptionEditText.text.toString()

            if (name.isNotEmpty()) {
                val skill = Skill(name = name, description = description, lastEdited = System.currentTimeMillis())
                skillViewModel.insert(skill)
                finish()
            } else {
                skillNameEditText.error = "Skill name cannot be empty"
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}