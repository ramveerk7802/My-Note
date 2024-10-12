package com.example.mynote.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mynote.entity.NoteEntity
import com.example.mynote.repo.NoteRepo

class NoteViewModel(private val application: Application) : AndroidViewModel(application){
    private var noteRepo :NoteRepo = NoteRepo(application)
    private var noteList = noteRepo.noteList


    fun insert(noteEntity: NoteEntity){
        noteRepo.insertNote(noteEntity)
    }
    fun update(noteEntity: NoteEntity){
        noteRepo.updateNote(noteEntity)
    }
    fun delete(noteEntity: NoteEntity){
        noteRepo.deleteNote(noteEntity)
    }
    fun  getAllNoteList():LiveData<MutableList<NoteEntity>>{
        return this.noteList
    }




}