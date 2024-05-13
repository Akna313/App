package com.example.storyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.storyapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: StoriesDatabaseHelper
    private var storyId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = StoriesDatabaseHelper(this)

        storyId = intent.getIntExtra("story_id",-1)
        if(storyId == -1){
            finish()
            return
        }

        val story = db.getStoryByID(storyId)
        binding.updateTitleEditText.setText(story.title)
        binding.updateContentEditText.setText(story.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle =  binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updateStory = Story(storyId,newTitle,newContent)
            db.updateStory(updateStory)
            finish()
            Toast.makeText(this,"Changes saved Successfully",Toast.LENGTH_SHORT).show()
        }
    }
}