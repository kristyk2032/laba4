package com.example.lab4_skill

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider

class EditProgressDialogFragment : DialogFragment() {

    private lateinit var skillViewModel: SkillViewModel
    private var progress: Progress? = null

    companion object {
        fun newInstance(progress: Progress): EditProgressDialogFragment {
            val fragment = EditProgressDialogFragment()
            fragment.progress = progress
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        skillViewModel = ViewModelProvider(requireActivity())[SkillViewModel::class.java]

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit Progress")

        val rootView = requireActivity().layoutInflater.inflate(R.layout.dialog_edit_progress, null)
        val levelEditText = rootView.findViewById<EditText>(R.id.levelEditText)
        val notesEditText = rootView.findViewById<EditText>(R.id.notesEditText)

        progress?.let {
            levelEditText.setText(it.level.toString())
            notesEditText.setText(it.notes)
        }

        builder.setView(rootView)
        builder.setPositiveButton("Save") { _, _ ->
            val level = levelEditText.text.toString().toIntOrNull() ?: 1
            val notes = notesEditText.text.toString()

            progress?.let {
                val updatedProgress = it.copy(level = level, notes = notes, date = System.currentTimeMillis())
                skillViewModel.updateProgress(updatedProgress)
            }
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            dismiss()
        }

        return builder.create()
    }
}