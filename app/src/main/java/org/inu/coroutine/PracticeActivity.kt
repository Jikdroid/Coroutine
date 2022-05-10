package org.inu.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import org.inu.coroutine.databinding.ActivityPracticeBinding
import java.lang.IllegalArgumentException

class PracticeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice)
        binding.activity = this


    }

    fun executeSuspendFun(){
        CoroutineScope(Dispatchers.Main).launch {
            deferredException()
        }
    }

    private suspend fun deferredException(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            when (throwable){
                is IllegalArgumentException -> println("More Argument Needed To Process Job")
                is InterruptedException -> println("Job Interrupted")
            }
        }


        val deferred = CoroutineScope(Dispatchers.IO).async(exceptionHandler) {
            throw IllegalArgumentException()
            arrayOf(1,2,3)
        }
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            deferred.await()
        }.join()
    }
}


