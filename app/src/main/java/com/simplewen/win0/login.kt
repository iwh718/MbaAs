package com.simplewen.win0

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Timer().schedule(object :TimerTask(){
            override fun run() {
                startActivity(Intent(this@login,MainActivity::class.java))
            }
        },2000)
    }
}
