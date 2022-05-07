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

        // exampleSuspend2()
        // suspend fun는 (코루틴이)일시중단 가능한 함수를 지칭하는 것이기 때문에, 해당 함수는 무조건 코루틴 내부에서 수행되어야 하기 때문에 오류 발생
        CoroutineScope(Dispatchers.Main).launch {
            exampleSuspend2()
        }

    }

    // 일시중단 함수(suspend fun)는 당연히 일시중단 함수(suspend fun) 내부에서 사용될 수 있다.
    // 당연히 가장 바깥에 있는 일시중단 함수(아래 코드에서는 exampleSuspend2)는 코루틴 내부에서 실행되어야 한다.
    suspend fun exampleSuspend4(){
        exampleSuspend3()
    }

    suspend fun exampleSuspend3() {
        val job3 = CoroutineScope(Dispatchers.IO).async {
            (1..10000).sortedByDescending { it }
        }

        job3.await()
    }



    suspend fun exampleSuspend2(){

        val job3 = CoroutineScope(Dispatchers.Default).async {
            // 2. IO Thread에서 작업3를 수행한다.
            (1..1000).sortedByDescending { it }
        }

        job3.await()

        val job1 = CoroutineScope(Dispatchers.Main).launch {
            // 1. Main Thread에서 작업1을 수행한다.
            println(1)

            // 3. 작업1의 남은 작업을 위해 작업3로부터 결과값이 필요하기 때문에 Main Thread는 작업1을 일시중단한다.
            val job3Result = job3.await()
            // 6. 작업3로부터 결과를 전달받는다.

            // 7. 작업1이 재개된다.
            job3Result.forEach {
                println(it)
            }
        }

        // 4. Main Thread에서 작업2이 수행되고 완료된다.
        val job2 = CoroutineScope(Dispatchers.Main).launch {
            println("job2 수행완료")
        }
    }


    fun exampleSuspend1(){

        val job3 = CoroutineScope(Dispatchers.Default).async {
            // 2. IO Thread에서 작업3를 수행한다.
            (1..1000).sortedByDescending { it }
        }

        //job3.await()    // await 정지하고 job3의 결과값을 받아오는데 그럴려면 코루틴 내부에 있어야하거나 일시정지가 가능한 함수(suspend fun)
                          // 안에 있어햐한다.(코루틴이 일시중단이 되려면 수행되는 위치 또한 코루틴 내부여야 하기 때문)

        val job1 = CoroutineScope(Dispatchers.Main).launch {
            // 1. Main Thread에서 작업1을 수행한다.
            println(1)

            // 3. 작업1의 남은 작업을 위해 작업3로부터 결과값이 필요하기 때문에 Main Thread는 작업1을 일시중단한다.
            val job3Result = job3.await()
            // 6. 작업3로부터 결과를 전달받는다.

            // 7. 작업1이 재개된다.
            job3Result.forEach {
                println(it)
            }
        }

        // 4. Main Thread에서 작업2이 수행되고 완료된다.
        val job2 = CoroutineScope(Dispatchers.Main).launch {
            println("job2 수행완료")
        }
    }

}

