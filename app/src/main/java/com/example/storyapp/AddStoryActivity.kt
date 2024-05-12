package com.example.storyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.storyapp.databinding.ActivityAddStoryBinding
import com.example.storyapp.databinding.ActivityMainBinding

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var db: StoriesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = StoriesDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val story = Story(0,title,content)
            db.insertStory(story)
            finish()
            Toast.makeText(this,"Story Saved",Toast.LENGTH_SHORT).show()

        }

    }
}