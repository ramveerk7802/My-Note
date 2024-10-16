package com.example.mynote.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int?=null,

    @ColumnInfo(name = "title")
    var title:String,

    @ColumnInfo(name = "desc")
    var desc:String,

    @ColumnInfo(name ="date")
    var date:Date
)
