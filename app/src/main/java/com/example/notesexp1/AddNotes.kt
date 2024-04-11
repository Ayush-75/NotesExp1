package com.example.notesexp1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesexp1.databinding.ActivityAddNotesBinding
import com.example.notesexp1.models.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNotes : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding

    private lateinit var note: Note
    private lateinit var oldNote:Note
    var isUpdated = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try{
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNotes.setText(oldNote.note)
            isUpdated = true
        }catch (e:Exception){
            e.printStackTrace()
        }

        binding.checkNotes.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNotes.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MM yyy HH:MM a",Locale.getDefault(Locale.Category.FORMAT))

                if (isUpdated){
                    note = Note(
                        oldNote.id,title,note_desc,formatter.format(Date()))
                }else{
                    note = Note(
                        null,title,note_desc,formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this, "Please enter some notes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.backArrow.setOnClickListener{
            onBackPressed()
            onBackPressedDispatcher
        }
    }
}