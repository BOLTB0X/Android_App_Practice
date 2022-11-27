package com.cranberryai.a01_stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //뒤로가기 버튼 누른 시각 저장
    var initTime = 0L

    //멈춘 시각 저장
    var pauseTime = 0L

    private var mBinding: ActivityMainBinding?= null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //바인딩 초기화
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        // 생성된 뷰 액티비티에 표시시
        setContentView(binding.root)

        // elapsedRealtime: 부팅 이후의 밀리초를 리턴 (절전 모드에서 보낸 시간 포함)
        // 사용자가 현재시간을 수정해도 영향 받지 않음
        binding.startBtn.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()

            //버튼 표시 여부 조정
            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
            binding.startBtn.isEnabled = false
        }

        binding.stopBtn.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            //버튼 표시 여부 조정
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
            binding.startBtn.isEnabled = true
        }


        binding.resetBtn.setOnClickListener {
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            //버튼 표시 여부 조정
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = false
            binding.startBtn.isEnabled = true
        }



    }

    // 뒤로가기 버튼 이벤트 핸들러
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 뒤로 가기 버튼 눌렀을 때 처리
        if(keyCode === KeyEvent.KEYCODE_BACK) {
            // 뒤로가기 버튼을 처음 눌렀거나 누른지 2초가 지났을 때 처리
            if(System.currentTimeMillis() - initTime > 2000) {
                Toast.makeText(this, "한번 더 누르면 종료 됩니다", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}