package com.example.storyapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StoriesDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "storyapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allstories"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    fun insertStory(story: Story){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,story.title)
            put(COLUMN_CONTENT,story.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllStories(): List<Story> {
        val storiesList = mutableListOf<Story>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val story = Story(id, title, content)
            storiesList.add(story)

        }
        cursor.close()
        db.close()
        return storiesList

    }
    fun updateStory(story: Story){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE,story.title)
            put(COLUMN_CONTENT,story.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(story.id.toString())
        db.update(TABLE_NAME, values,whereClause,whereArgs)
        db.close()
    }

    fun getStoryByID(storyId: Int): Story{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $storyId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id =cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title =cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content =cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Story(id, title, content)
    }

    fun deleteStory(storyId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(storyId.toString())
        db.delete(TABLE_NAME,whereClause, whereArgs)
        db.close()
    }
}