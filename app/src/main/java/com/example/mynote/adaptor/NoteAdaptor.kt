package com.example.mynote.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.databinding.ItemLayoutBinding
import com.example.mynote.entity.NoteEntity

class NoteAdaptor(var context: Context, var noteList: MutableList<NoteEntity>) : RecyclerView.Adapter<NoteAdaptor.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)
    }


    fun updateNotes(notes: MutableList<NoteEntity>) {
        this.noteList = notes
        notifyDataSetChanged()  // Notify RecyclerView that data has changed
    }

    inner class ViewHolder(private val binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            if(position!=RecyclerView.NO_POSITION){
                val note = noteList[position]
                binding.apply {
                    title.text = note.title
                    text.text = note.desc
                }
            }
        }
    }
}