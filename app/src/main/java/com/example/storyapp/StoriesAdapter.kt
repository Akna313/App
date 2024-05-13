package com.example.storyapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class StoriesAdapter(private var stories:List<Story>,context: Context) : RecyclerView.Adapter<StoriesAdapter.StoryViewHolder>() {

    private val db: StoriesDatabaseHelper = StoriesDatabaseHelper(context)

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
    }
    override fun onCreateViewHolder(parent:ViewGroup,viewType: Int):StoryViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.story_item,parent,false)
        return StoryViewHolder(view)
    }
    override fun getItemCount(): Int = stories.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int){
        val story = stories[position]
        holder.titleTextView.text = story.title
        holder.contentTextView.text = story.content
        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply{
                putExtra("story_id",story.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            db.deleteStory(story.id)
            refreshData(db.getAllStories())
            Toast.makeText(holder.itemView.context,"Story deleted Successfully",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newStories : List<Story>){
        stories = newStories
        notifyDataSetChanged()
    }
}