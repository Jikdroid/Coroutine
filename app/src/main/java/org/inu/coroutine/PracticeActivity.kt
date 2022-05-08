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
            jobStatus()
        }
    }

    private suspend fun jobStatus(){
        val job = CoroutineScope(Dispatchers.IO).launch(start = CoroutineStart.LAZY) {
           delay(1000)
        }
        println(job.isActive)    // 실행중인지 여부 표시
        println(job.isCancelled) // cancel 요청되었는지 여부 표시
        println(job.isCompleted) // 실행이 완료 되었거나 cancel 이 완료 되었는지를 표시
        job.cancel()
        println(job.isActive)    // 실행중인지 여부 표시
        println(job.isCancelled) // cancel 요청되었는지 여부 표시
        println(job.isCompleted) // 실행이 완료 되었거나 cancel 이 완료 되었는지를 표시
    }
}


