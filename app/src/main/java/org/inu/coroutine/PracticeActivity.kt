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
        CoroutineScope(Dispatchers.IO).launch { // 부모 코루틴은 supervisorScope 외부에 있어 SupervisorJob() 적용 안됨

            supervisorScope {
                val firstChildJob = launch(Dispatchers.IO) { // 자식 코루틴은 supervisorScope 내부에 있어 SupervisorJob 적용됨
                    throw AssertionError("첫 째 Job이 AssertionError로 인해 취소됩니다.")
                }
                val secondChildJob = launch(Dispatchers.Default) { // 자식 코루틴은 supervisorScope 내부에 있어 SupervisorJob 적용됨
                    delay(1000)
                    println("둘 째 Job이 살아있습니다")
                }
                firstChildJob.join()
                secondChildJob.join()
            }
        }.join()
    }
}


