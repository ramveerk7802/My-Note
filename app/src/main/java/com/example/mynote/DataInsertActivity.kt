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
import androidx.lifecycle.ViewModelProvider
import com.example.mynote.databinding.ActivityDataInsertBinding
import com.example.mynote.entity.NoteEntity
import com.example.mynote.viewModel.NoteViewModel
import java.util.Date

class DataInsertActivity : AppCompatActivity() {
    private val binding:ActivityDataInsertBinding by lazy {
        ActivityDataInsertBinding.inflate(layoutInflater)
    }
    private lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow)
        // Ensure light status bar icons if needed
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        // initialize view model

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]




        val type = intent?.getStringExtra("type")?:""
        Log.d("intent",type)

        if(type == "edit"){
            binding.text.text = "Edit your note"
            val id = intent?.getIntExtra("id",-1)
            var title = intent?.getStringExtra("title")
            var desc = intent?.getStringExtra("desc")

            binding.titleText.setText(title!!)
            binding.descText.setText(desc!!)

            binding.submitBtn.setText("Update")
            binding.submitBtn.setOnClickListener {
                if(id!=-1){
                      title = binding.titleText.text.toString().trim()
                      desc = binding.descText.text.toString().trim()
                    if(title!!.isBlank() || desc!!.isBlank()){
                        Toast.makeText(this,"Incomplete information !!",Toast.LENGTH_SHORT).show()
                    }else{
                        val note = NoteEntity(id=id, title = title!!, desc = desc!!, date = Date())
                        noteViewModel.update(note)
                        startActivity(Intent(this,MainActivity::class.java))
                        finishAffinity()
                    }
                }

            }

        }else{
            binding.text.text = "Add new note"
            binding.submitBtn.setOnClickListener {
                val title = binding.titleText.text.toString().trim()
                val desc = binding.descText.text.toString().trim()
                if(title.isBlank() || desc.isBlank()){
                    Toast.makeText(this,"Incomplete information !!",Toast.LENGTH_SHORT).show()
                }else{
                    val note = NoteEntity(title = title, desc = desc, date = Date())
                    noteViewModel.insert(note)
                    Toast.makeText(this,"Data inserted succesfully!!",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finishAffinity()
                }
            }

        }


    }
}