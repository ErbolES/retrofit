package com.example.lessons11.ui.home

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.example.lessons11.R
import com.example.lessons11.databinding.FragmentThreadsBinding
import com.example.lessons11.ui.home.service.MAIN_SERVICE_STRING_EXTRA
import com.example.lessons11.ui.home.service.MainServise
import java.util.Date
import java.util.concurrent.TimeUnit

class ThreadsFragment : Fragment() {

    private var _binding:FragmentThreadsBinding? = null
    private val binding get() = _binding!!
    private var counterThread = 0

    companion object {
        fun newInstance() = ThreadsFragment()
    }

    private val viewModel: ThreadsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentThreadsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{
            binding.textView.text = startCalculation(binding.editText.text.toString().toInt())
            binding.mainConteiner.addView(AppCompatTextView(it.context).apply {
                text =  "In main thread"
                textSize = resources.getDimension(R.dimen.main_conteiner_text_size)
            })
        }
       val handlerThread = HandlerThread("handler thread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        binding.calcThreadHandler.setOnClickListener{
            binding.mainConteiner.addView(AppCompatTextView(it.context).apply {
                text =  String.format(
                    "in thread",
                    handlerThread.name
                )
                textSize = resources.getDimension(R.dimen.main_conteiner_text_size)
            })

            handler.post(Runnable {
                startCalculation(binding.editText.text.toString().toInt())
                binding.mainConteiner.post{
                    binding.mainConteiner.addView(AppCompatTextView(it.context).apply {
                        text =  String.format(
                            "in thread",
                            Thread.currentThread().name
                        )
                        textSize = resources.getDimension(R.dimen.main_conteiner_text_size)
                    })
                }
            })
        }

        binding.calcThreadBtn.setOnClickListener{
            Thread{
                counterThread ++
                val calculatedText = startCalculation(binding.editText.text.toString().toInt())

                activity?.runOnUiThread{
                    binding.textView.text = calculatedText
                    binding.mainConteiner.addView(AppCompatTextView(it.context).apply {
                        text = "from threads: " + calculatedText
                        textSize = resources.getDimension(R.dimen.main_conteiner_text_size)
                    })
                }

            }.start()
        }

        intentServiseButton()
    }
    /*
    1. Activity.runOnUiThread(Runnable) — выполняет метод run() в UI-потоке
    незамедлительно, а если поток занят, выполнение ставится в очередь.
    2. View.post(Runnable) ставит метод run() в очередь UI-потока.
    3. View.postDelayed(Runnable, long) ставит метод run() в очередь UI-потока. Метод будет
    выполняться после задержки (указывается в миллисекундах).
    4. Handler.post(Runnable) запоминает поток, в котором создан Handler, после чего метод
    run() будет вызываться в этом потоке.
     */


    private fun startCalculation(second: Int):String{
        val date = Date()
        var diffInSec:Long
        do {
            val currentDate = Date()
            val diffInMs:Long = currentDate.time- date.time
            diffInSec= TimeUnit.MILLISECONDS.toSeconds(diffInMs)
        }while (diffInSec<second)

        return diffInSec.toString()
    }


    private fun intentServiseButton(){
        binding.serviseButton.setOnClickListener{
            context?.let {
                it.startService(Intent(it, MainServise::class.java).apply {
                    putExtra(
                        MAIN_SERVICE_STRING_EXTRA,
                        getString(R.string.hello_from_thread_fragment)
                    )
                })
            }
        }
    }
}