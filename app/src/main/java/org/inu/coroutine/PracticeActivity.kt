package org.inu.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.*
import org.inu.coroutine.databinding.ActivityPracticeBinding
import java.lang.AssertionError
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
            supervisorJob()
        }
    }

    private suspend fun supervisorJob() {
        val supervisor = SupervisorJob()

        CoroutineScope(Dispatchers.IO).launch {
            val firstChildJob = launch(Dispatchers.IO+ supervisor){
                throw AssertionError("첫 째 Job이 AssertionError로 인해 취소됩니다.")
            }
            val secondChildJob = launch(Dispatchers.Default){
                delay(1000)
                println("둘 째 Job이 살아있습니다")
            }
            firstChildJob.join()
            secondChildJob.join()
        }.join()
    }
}


