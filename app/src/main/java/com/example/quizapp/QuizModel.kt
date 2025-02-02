package com.example.quizapp

data class Quizmodel(

    val id:String,
    val title:String,
    val subtitle:String,
    val time:String,
    val questionList:List<Questionmodel>,
){
    //Empty constructor
    constructor():this("","","","", emptyList())
}
data class Questionmodel(
    val question:String,
    val options:List<String>,
    val correct:String,
){
    constructor():this("", emptyList(),"")
}