package com.example.mynote.repo

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.mynote.dao.NoteDao
import com.example.mynote.database.NoteDb
import com.example.mynote.entity.NoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepo(application: Application) {

    // get database instance
    private val noteDatabase= NoteDb.getInstance(application)
    private val noteDao = noteDatabase.getNoteDao()


    // getAll data
    val noteList: LiveData<MutableList<NoteEntity>> = noteDao.getAllNote()




    // Insert a note
    fun insertNote(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insertNote(noteEntity)
        }
    }

    // Update a note
    fun updateNote(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.updateNote(noteEntity)
        }
    }

    // Delete a note
    fun deleteNote(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteNote(noteEntity)
        }
    }






}