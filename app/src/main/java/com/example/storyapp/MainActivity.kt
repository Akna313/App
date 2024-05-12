package com.example.storyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db :StoriesDatabaseHelper
    private lateinit var storiesAdapter: StoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = StoriesDatabaseHelper(this)
        storiesAdapter = StoriesAdapter(db.getAllStories(),this)

        binding.storiesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.storiesRecyclerView.adapter = storiesAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this,AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        storiesAdapter.refreshData(db.getAllStories())
    }
}