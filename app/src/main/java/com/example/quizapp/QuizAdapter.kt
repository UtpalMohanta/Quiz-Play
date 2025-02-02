package com.example.quizapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.CardBinding


class QuizAdapter(private val quizModelList:List<Quizmodel>):RecyclerView.Adapter<QuizAdapter.MyViewHolder>(){

    class MyViewHolder(private val binding: CardBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(model:Quizmodel){
            binding.apply {

                titleTXt.text=model.title
                subtitleTXt.text=model.subtitle
                timeTXt.text= model.time +" min"
                root.setOnClickListener{
                    val intent=Intent(root.context,Quiz::class.java)
                    Quiz.questionModelList=model.questionList
                    Quiz.time=model.time
                    root.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding=CardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizModelList[position])
    }
}