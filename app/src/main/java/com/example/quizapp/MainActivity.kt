package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var QuizmodelList:MutableList<Quizmodel>
    lateinit var adapter: QuizAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        QuizmodelList= mutableListOf()
        getDataFromFirebase()
    }
    private fun setupRecycleeView() {
        binding.progressBar.visibility=View.GONE
        adapter= QuizAdapter(QuizmodelList)
        binding.recyclurView.layoutManager=LinearLayoutManager(this)
        binding.recyclurView.adapter=adapter
    }
    private fun getDataFromFirebase(){

        binding.progressBar.visibility=View.VISIBLE
        //dummy data
       // val listQuestionModel = mutableListOf<Questionmodel>()
       // listQuestionModel.add(Questionmodel("What is android", mutableListOf("Language","OS","Product","None"),"os"))
      //  QuizmodelList.add(Quizmodel("1","Programming","All the basic programm","10",listQuestionModel))

        //farebase
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapshot ->
                if(dataSnapshot.exists()){
                  for(snapshot in dataSnapshot.children){
                      val quizModel=snapshot.getValue(Quizmodel::class.java)

                      if(quizModel!=null){
                         QuizmodelList.add(quizModel)
                      }

                  }
                }
                setupRecycleeView()
            }


    }

}