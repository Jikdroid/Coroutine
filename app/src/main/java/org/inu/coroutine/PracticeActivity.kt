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
    fun startSuspendFun(){
        CoroutineScope(Dispatchers.Main).launch {
            jobCancel()
        }

    }

    private suspend fun jobCancel(){
        val job = CoroutineScope(Dispatchers.IO).launch {
           delay(1000)
        }

        job.invokeOnCompletion { throwable ->       // Job 이 취소나 실행 완료 될 때 호출된다.
            when(throwable){
                is CancellationException -> println("Cancelled")
                null -> println("Completed with no error")
            }
        }

        job.cancel("Job Cancelled by user",InterruptedException("Cancelled Forcibly"))  // Job 취소(메세지, 원인)

        delay(3000)
    }
}


