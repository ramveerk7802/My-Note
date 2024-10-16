package com.example.mynote.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.DataInsertActivity
import com.example.mynote.MainActivity
import com.example.mynote.ReadMoreActivity
import com.example.mynote.databinding.ItemLayoutBinding
import com.example.mynote.entity.NoteEntity
import com.example.mynote.viewModel.NoteViewModel
import java.text.SimpleDateFormat

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
                    val temp = note.title
                    val x = temp[0].uppercaseChar()+temp.substring(1)
                    title.text = x
                    val temp2 = note.desc
                    val formatter = SimpleDateFormat("dd/MM/yy")
                    val currDate =  formatter.format(note.date)
                    date.text = currDate

                    text.text = buildString {
                        append(temp2[0].uppercaseChar())
                            .append(temp2.substring(1))
                    }




                    root.setOnClickListener{
                        val intent = Intent(context,ReadMoreActivity::class.java)
                            .apply {
                                putExtra("date",currDate)
                                putExtra("title",note.title)
                                putExtra("desc",note.desc)
                            }
                        context.startActivity(intent)
                    }

                    editButton.setOnClickListener {
                        val intent = Intent(context,DataInsertActivity::class.java)
                            .apply {
                                putExtra("id",note.id!!)
                                putExtra("title",note.title)
                                putExtra("desc",note.desc)
                                putExtra("type","edit");
                            }
                        context.startActivity(intent)
                    }


                    root.setOnLongClickListener {

                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("Delete")
                            .setMessage("Are you sure want to remove note ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes"){ dialog,_ ->
                                val viewModel = ViewModelProvider(context as MainActivity)[NoteViewModel::class]
                                viewModel.delete(note)
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){dialog,_ ->
                                dialog.dismiss()
                            }
                        builder.show()
                        true
                    }
                }


            }
        }
    }
}