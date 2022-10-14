package com.example.myapp

class Programmer(var name:String,
                 var age:Int,
                 var languages:Array<String>,
                 var SOs:Array<SO>) {

    enum class SO{
        ANDROID,
        IOS,
        WINDOWS,
        LINUX
    }

    fun code(){
        for(language in languages){
            println("Programo $language")
        }
    }
}