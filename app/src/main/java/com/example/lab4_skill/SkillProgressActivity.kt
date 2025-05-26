package com.example.lab4_skill

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class SkillProgressActivity : AppCompatActivity() {

    private val skillViewModel: SkillViewModel by viewModel()
    private lateinit var skillNameEditText: EditText
    private lateinit var skillDescriptionEditText: EditText
    private lateinit var progressRecyclerView: RecyclerView
    private lateinit var addProgressButton: Button
    private lateinit var backButton: Button
    private lateinit var saveButton: Button
    private lateinit var adapter: SkillProgressListAdapter
    private var skillId: Int = -1
    private var skill: Skill? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_progress)

        skillNameEditText = findViewById(R.id.skillNameEditText)
        skillDescriptionEditText = findViewById(R.id.skillDescriptionEditText)
        progressRecyclerView = findViewById(R.id.progressRecyclerView)
        addProgressButton = findViewById(R.id.addProgressButton)
        backButton = findViewById(R.id.backButton)
        saveButton = findViewById(R.id.saveButton)

        skillId = intent.getIntExtra("skillId", -1)

        adapter = SkillProgressListAdapter(this, object : SkillProgressListAdapter.OnItemClickListener {
            override fun onItemClick(progress: Progress) {
                val dialog = EditProgressDialogFragment.newInstance(progress)
                dialog.show(supportFragmentManager, "EditProgressDialog")
            }

            override fun onDeleteClick(progress: Progress) {
                val confirmDialog = AlertDialog.Builder(this@SkillProgressActivity)
                    .setTitle("Delete Progress?")
                    .setMessage("Are you sure you want to delete this progress entry?")
                    .setPositiveButton("Delete") { _, _ ->
                        skillViewModel.deleteProgress(progress)
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                    }
                    .create()
                confirmDialog.show()
            }
        })

        progressRecyclerView.adapter = adapter
        progressRecyclerView.layoutManager = LinearLayoutManager(this)

        if (skillId != -1) {
            skillViewModel.getSkill(skillId).observe(this, Observer { skillData ->
                skill = skillData
                skill?.let {
                    skillNameEditText.setText(it.name)
                    skillDescriptionEditText.setText(it.description ?: "")
                }
            })
            skillViewModel.getProgressForSkill(skillId).observe(this, Observer { progressList ->
                progressList?.let {
                    adapter.submitList(it)
                }
            })
        }

        addProgressButton.setOnClickListener {
            val intent = Intent(this, AddProgressActivity::class.java)
            intent.putExtra("skillId", skillId)
            intent.putExtra("progressId", 0)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            skill?.let {
                val updatedSkill = it.copy(name = skillNameEditText.text.toString(), description = skillDescriptionEditText.text.toString(), lastEdited = System.currentTimeMillis())
                skillViewModel.update(updatedSkill)
            }
            finish()
        }
    }
}