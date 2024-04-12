package com.example.count_out.service.stopwatch

//class StopWatch {
//    private var duration: Duration = Duration.ZERO
//    private var timer: Timer = Timer()
//
//    fun onStart(pause: MutableStateFlow<StateRunning>,onTick: (TickTime) -> Unit) {
//        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
//            if (pause.value != StateRunning.Paused) {
//                duration = duration.plus(1.seconds)
//                duration.toComponents { h, m, s, _ ->
//                    onTick(TickTime(hour = h.toInt().pad(), min = m.pad(), sec = s.pad()))
//                }
//            } else if (pause.value == StateRunning.Stopped){
//                onStop()
//            }
//        }
//    }
//    fun onStop() {
//        duration = Duration.ZERO
//        timer.cancel()
//    }
//}