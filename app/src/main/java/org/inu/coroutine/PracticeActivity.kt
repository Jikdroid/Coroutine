package org.inu.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

    fun job(){
        val job = CoroutineScope(Dispatchers.IO).launch {   // 별도의 옵션없이 launch 하게 되면 생성된 비동기 작업은 생성 후 바로 실행
            println(1)
        }
    }

    suspend fun jobLazy(){
        val job = CoroutineScope(Dispatchers.IO).launch(start = CoroutineStart.LAZY) {  // Job 이 수행되지 않고 대기 상태로 있는다.
            println(1)
        }
        job.start()     // 인텔리제이 main 함수에서 실행하면 println(1) 이 찍히지 않는다.
                        // 왜냐면 start 는 그저 실행하는데 job 이 IO 스레드에서 실행하기에 IO 스레드에서의 코루틴 작업이 끝나기전에
                        // main 스레드에서 실행되는 main 함수가 끝나버린다.

        delay(100L)     // delay 를 해준다면 IO 스레드가 끝나고 main 이 끝난다.
    }

    suspend fun jobLazy2(){
        val job = CoroutineScope(Dispatchers.IO).launch(start = CoroutineStart.LAZY) {
            println(1)
        }

        job.join()      // join 을 사용하면 IO 스레드의 코루틴(job) 이 끝날때까지 Main 스레드가 일시중단되어진다.
    }
}


