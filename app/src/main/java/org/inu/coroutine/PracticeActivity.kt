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
            coroutineContext()
        }
    }

    private fun coroutineContext() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable -> }
        val coroutineContext = Dispatchers.IO + exceptionHandler

        val exceptionHandlerFromContext = coroutineContext[exceptionHandler.key]
        if (exceptionHandler == exceptionHandlerFromContext){
            println(true)
        }
        val minusContext  = coroutineContext.minusKey(exceptionHandler.key)
        println(minusContext)
    }
}


