package com.example.mynote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.adaptor.NoteAdaptor
import com.example.mynote.databinding.ActivityMainBinding
import com.example.mynote.entity.NoteEntity
import com.example.mynote.viewModel.NoteViewModel

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private lateinit var adaptor1: NoteAdaptor

    private lateinit var viewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow)

        // Ensure light status bar icons if needed
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true




        // initialize adaptor and view model
        adaptor1 = NoteAdaptor(this@MainActivity, mutableListOf())

        viewModel = ViewModelProvider(this)[NoteViewModel::class]


        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adaptor1

          viewModel.getAllNoteList().observe(this, Observer { note ->
              note?.let {
                  Log.d("MainActivity", "Notes retrieved: ${it.size}")
                  adaptor1.updateNotes(it)
              }
          })


        binding.addButton.setOnClickListener{
            startActivity(Intent(this@MainActivity,DataInsertActivity::class.java))
        }




        // delete the swipe on Right direction
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = adaptor1.noteList[position]
                viewModel.delete(note)

                Toast.makeText(this@MainActivity,"Note is deleted.",Toast.LENGTH_SHORT).show()
            }

        }


        // Attach the ItemTouchHelper to the RecyclerView
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recycleView)



    }
}