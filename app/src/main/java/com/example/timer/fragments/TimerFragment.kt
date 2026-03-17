package com.example.timer.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timer.R
import com.example.timer.databinding.FragmentTimerBinding
import java.util.concurrent.TimeUnit

class TimerFragment : Fragment() {
    
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    
    private var isTimerRunning = false
    private var timerDuration = 0L
    private val handler = Handler(Looper.getMainLooper())
    
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateTimerDisplay()
            handler.postDelayed(this, 1000)
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        
        setupTimerControls()
        
        return binding.root
    }
    
    private fun setupTimerControls() {
        binding.btnStartPause.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }
        
        binding.btnReset.setOnClickListener {
            resetTimer()
        }
    }
    
    private fun startTimer() {
        isTimerRunning = true
        binding.btnStartPause.text = "暂停"
        handler.post(updateRunnable)
    }
    
    private fun pauseTimer() {
        isTimerRunning = false
        binding.btnStartPause.text = "开始"
        handler.removeCallbacks(updateRunnable)
    }
    
    private fun resetTimer() {
        isTimerRunning = false
        binding.btnStartPause.text = "开始"
        binding.tvTimerDisplay.text = "00:00:00"
        handler.removeCallbacks(updateRunnable)
    }
    
    private fun updateTimerDisplay() {
        // 简化版显示
        binding.tvTimerDisplay.text = "00:00:00"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateRunnable)
        _binding = null
    }
}
