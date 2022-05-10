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
            jobException()
        }
    }

    private suspend fun jobException(){

        val deferred:Deferred<String> = CoroutineScope(Dispatchers.IO).async {
            "Deferred Result"
        }
        println(deferred.await())

        val result : String = withContext(Dispatchers.IO){
            "Deferred Result"
        }
        println(result)
        // 1. withContext 블록은 IO Thread의 작업이 끝나기 전까지 Main Thread에서 수행되는 코루틴을 일시중단되도록 만든다.

        // 2. IO Thread의 작업이 끝나면 "Async Result"가 반환되며 이는 result에 세팅된다.

        // 3. result가 프린트된다.

        // 한계점
        // withContext를 너무 많이 사용하는 것은 좋지 않아 보인다.
        // 비동기 작업을 하는 이유 자체가 순차적인 처리를 했을 때 연산 시간을 줄이기 위함인데,
        // 순차적으로 처리하도록 만들어버리면 비동기 작업의 장점이 사라지기 때문이다.
    }
}


