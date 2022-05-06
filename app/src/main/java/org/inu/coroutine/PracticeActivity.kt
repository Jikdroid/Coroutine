package org.inu.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class PracticeActivity : AppCompatActivity() ,Runnable{
    val button : Button by lazy {
        findViewById(R.id.btn)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)


       button.setOnClickListener {
           Thread(this).start()
           Thread(this).start()
       }
    }

    override fun run() {
        Log.d(TAG, "Runnable Running")
        Log.d(TAG, Thread.currentThread().toString())
    }

    companion object{
        const val TAG = "aaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    }
}

