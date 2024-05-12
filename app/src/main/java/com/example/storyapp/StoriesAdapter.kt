package com.example.storyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StoriesAdapter(private var stories:List<Story>,context: Context) : RecyclerView.Adapter<StoriesAdapter.StoryViewHolder>() {

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
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
    }

    fun refreshData(newStories : List<Story>){
        stories = newStories
        notifyDataSetChanged()
    }
}