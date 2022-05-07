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

    fun launch(){
        with(CoroutineScope(Dispatchers.IO)) {
            val job:Job = launch { println(1) }
        }
    }

    fun async(){
        CoroutineScope(Dispatchers.Main).launch {
            val deferredInt: Deferred<Int> = async {
                println(2)
                1
            }
            val value = deferredInt.await()
            println(value)
        }
    }

    fun switchDispatcher(){
        CoroutineScope(Dispatchers.Main).launch {

            // 1. 데이터 입출력을 해야 하므로 IO Dispatcher에 배분
            val deferredInt: Deferred<Array<Int>> = async(Dispatchers.IO){
                println(1)
                arrayOf(3,1,2,4,5)
            }

            // 2. Sort해야 하므로 CPU작업을 많이 해야하는 Default Dispatcher에 배분
            val sortedDeferred = async(Dispatchers.Default) {
                val value  = deferredInt.await()
                value.sortedBy { it }
            }

            // 3. 설정하지 않으면 기본 Dispatcher인 Main Dispatcher에 보내진다.
            // 3. TextView에 세팅하는 것은 UI 작업이므로 Main Dispatcher에 배분
            val textviewSettingJob = launch {
                val sortedArray = sortedDeferred.await()
                binding.btn.text = sortedArray.toString()
            }
        }
    }

}

