package org.inu.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import org.inu.coroutine.databinding.ActivityPracticeBinding

class PracticeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice)
        binding.activity = this


    }

    fun executeSuspendFun(){
        CoroutineScope(Dispatchers.Main).launch {
            jobException()
        }
    }

    private suspend fun jobException(){
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler : $exception")

            when(exception){
                is IllegalArgumentException -> println("More Arguement Needed To Process Job")
                is InterruptedException -> println("Job Interrupted")
            }
        }

        val job1 = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) { // root coroutine, running in GlobalScope
            throw IllegalArgumentException()
        }

        val job2 = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            throw InterruptedException()
        }

        delay(1000)
    }
}


