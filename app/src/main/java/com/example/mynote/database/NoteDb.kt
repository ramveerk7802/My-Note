package com.example.mynote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynote.converter.DateConverter
import com.example.mynote.dao.NoteDao
import com.example.mynote.entity.NoteEntity


@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class NoteDb : RoomDatabase() {


    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile

        private var INSTANCE: NoteDb? = null

        fun getInstance(context: Context): NoteDb {
            // Double-checked locking to ensure only one instance is created
//            return INSTANCE ?: synchronized(this) {
//                val instance = INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    NoteDb::class.java,
//                    "db"
//                ).build()
//                INSTANCE = instance
//                INSTANCE!!
            if(NoteDb.INSTANCE==null){
                synchronized(this){
                    NoteDb.INSTANCE = Room.databaseBuilder(context.applicationContext,NoteDb::class.java,"db").build()
                }
            }
            return INSTANCE!!
        }


    }

}