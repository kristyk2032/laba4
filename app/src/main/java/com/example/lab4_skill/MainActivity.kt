package com.example.lab4_skill

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val skillViewModel: SkillViewModel by viewModel()
    private lateinit var skillRecyclerView: RecyclerView
    private lateinit var addSkillFab: FloatingActionButton
    private lateinit var noSkillsTextView: TextView
    private lateinit var adapter: SkillListAdapter
    private lateinit var levelFilterSpinner: Spinner
    private var currentFilterLevel: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        skillRecyclerView = findViewById(R.id.skillRecyclerView)
        addSkillFab = findViewById(R.id.addSkillFab)
        noSkillsTextView = findViewById(R.id.noSkillsTextView)
        levelFilterSpinner = findViewById(R.id.levelFilterSpinner)

        adapter = SkillListAdapter(this, object : SkillListAdapter.OnItemClickListener {
            override fun onItemClick(skillId: Int) {
                val intent = Intent(this@MainActivity, SkillProgressActivity::class.java)
                intent.putExtra("skillId", skillId)
                startActivity(intent)
            }

            override fun onDeleteClick(skill: Skill) {
                val confirmDialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Delete Skill?")
                    .setMessage("Are you sure you want to delete this skill?")
                    .setPositiveButton("Delete") { _, _ ->
                        // Launch a coroutine to call deleteSkill
                        skillViewModel.delete(skill)
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                    }
                    .create()
                confirmDialog.show()
            }
        })
        skillRecyclerView.adapter = adapter
        skillRecyclerView.layoutManager = LinearLayoutManager(this)

        val levelOptions = resources.getStringArray(R.array.level_filter_options)
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, levelOptions)
        levelFilterSpinner.adapter = adapterSpinner

        levelFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentFilterLevel = if (position == 0) {
                    null
                } else {
                    position
                }
                saveLevelFilter(currentFilterLevel)
                loadSkills()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                currentFilterLevel = null
                saveLevelFilter(null)
                loadSkills()
            }
        }

        addSkillFab.setOnClickListener {
            val intent = Intent(this, AddSkillActivity::class.java)
            startActivity(intent)
        }

        currentFilterLevel = loadLevelFilter()
        val spinnerPosition = currentFilterLevel ?: 0
        levelFilterSpinner.setSelection(spinnerPosition)

        loadSkills()
    }

    private fun loadSkills() {
        if (currentFilterLevel == null) {
            skillViewModel.allSkills.observe(this, Observer { skills ->
                adapter.submitList(skills)
                noSkillsTextView.visibility = if (skills.isEmpty()) View.VISIBLE else View.GONE
                skillRecyclerView.visibility = if (skills.isEmpty()) RecyclerView.GONE else RecyclerView.VISIBLE
            })
        } else {
            skillViewModel.getSkillsByLevel(currentFilterLevel!!).observe(this, Observer { skills ->
                adapter.submitList(skills)
                noSkillsTextView.visibility = if (skills.isEmpty()) View.VISIBLE else View.GONE
                skillRecyclerView.visibility = if (skills.isEmpty()) RecyclerView.GONE else RecyclerView.VISIBLE
            })
        }
    }

    private fun saveLevelFilter(level: Int?) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            if (level == null) {
                putInt("levelFilter", 0)
            } else {
                putInt("levelFilter", level)
            }
            apply()
        }
    }

    private fun loadLevelFilter(): Int? {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val level = sharedPref.getInt("levelFilter", 0)
        val result = if (level == 0) null else level
        return result
    }
}