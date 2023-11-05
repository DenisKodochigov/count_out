package com.example.count_out.data.plug

import com.example.count_out.data.room.tables.CoordinateDB
import com.example.count_out.data.room.tables.CountDB
import com.example.count_out.data.room.tables.CountsDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.WorkoutDB
import com.example.count_out.entity.Count
import com.example.count_out.entity.Rainfall
import com.example.count_out.entity.Round
import com.example.count_out.entity.Workout
import java.util.Calendar

data class WorkoutsPlug(
    val workouts: List<Workout>
){
    fun init():  List<Workout>{
        return listOf<Workout>(
            WorkoutDB( name = "", listRound = funRounds(1), timeStart = 0, timeEnd = 0, temperature = 20.0,
                rainfall = Rainfall.MEDIUM, counts = funCountsDB(1), averagePace = 0.0, maxPace = 0.0, minPace = 0.0, 
                averageSpeed = 0.0, maxSpeed = 0.0, minSpeed = 0.0, averageHeartRate = 0.0,
                maxHeartRate= 0.0, minHeartRate = 0.0, resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0,
                resultAmount = 0.0, resultRange = 0.0
            ), 
            WorkoutDB( name = "", listRound = funRounds(2), timeStart = 0, timeEnd = 0, temperature = 20.0,
                rainfall = Rainfall.MEDIUM, counts = funCountsDB(1), averagePace = 0.0, maxPace = 0.0, minPace = 0.0,
                averageSpeed = 0.0, maxSpeed = 0.0, minSpeed = 0.0, averageHeartRate = 0.0,
                maxHeartRate= 0.0, minHeartRate = 0.0, resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0,
                resultAmount = 0.0, resultRange = 0.0
            ) ,
            WorkoutDB( name = "", listRound = funRounds(3), timeStart = 0, timeEnd = 0, temperature = 20.0,
                rainfall = Rainfall.MEDIUM, counts = funCountsDB(1), averagePace = 0.0, maxPace = 0.0, minPace = 0.0,
                averageSpeed = 0.0, maxSpeed = 0.0, minSpeed = 0.0, averageHeartRate = 0.0,
                maxHeartRate= 0.0, minHeartRate = 0.0, resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0,
                resultAmount = 0.0, resultRange = 0.0
            ),
            WorkoutDB( name = "", listRound = funRounds(4), timeStart = 0, timeEnd = 0, temperature = 20.0,
                rainfall = Rainfall.MEDIUM, counts = funCountsDB(1), averagePace = 0.0, maxPace = 0.0, minPace = 0.0,
                averageSpeed = 0.0, maxSpeed = 0.0, minSpeed = 0.0, averageHeartRate = 0.0,
                maxHeartRate= 0.0, minHeartRate = 0.0, resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0,
                resultAmount = 0.0, resultRange = 0.0
            ),
            WorkoutDB( name = "", listRound = funRounds(5), timeStart = 0, timeEnd = 0, temperature = 20.0,
                rainfall = Rainfall.MEDIUM, counts = funCountsDB(1), averagePace = 0.0, maxPace = 0.0, minPace = 0.0,
                averageSpeed = 0.0, maxSpeed = 0.0, minSpeed = 0.0, averageHeartRate = 0.0,
                maxHeartRate= 0.0, minHeartRate = 0.0, resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0,
                resultAmount = 0.0, resultRange = 0.0
            ),
            WorkoutDB( name = "", listRound = funRounds(6), timeStart = 0, timeEnd = 0, temperature = 20.0,
                rainfall = Rainfall.MEDIUM, counts = funCountsDB(1), averagePace = 0.0, maxPace = 0.0, minPace = 0.0,
                averageSpeed = 0.0, maxSpeed = 0.0, minSpeed = 0.0, averageHeartRate = 0.0,
                maxHeartRate= 0.0, minHeartRate = 0.0, resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0,
                resultAmount = 0.0, resultRange = 0.0
            ),
        )
    }
    private fun funCountsDB(number: Int): CountsDB{
        val calendar = Calendar.getInstance()
        var currentTime = calendar.timeInMillis
        val listCountDB = mutableListOf<CountDB>()
        for (i in 1..15){
            listCountDB.add(
                CountDB(timeL = currentTime + i * 1000,  heartRate = 80 + number * 5 + i,
                    coordinate = CoordinateDB( latitude = 59.938732 + i * 0.0001, longitude = 30.316229+ i * 0.0001)))
        }

        return CountsDB(counts = listCountDB as List<Count>)
    }
    private fun funRounds(number: Int): List<Round>{
        val listRound = mutableListOf<RoundDB>()
        for (i in 1..15){
            listRound.add(
                RoundDB(set = SetDB(), amount = 0, beforeTime = 5, afterTime = 5, restTime  = 30,))
        }
        
        return listRound
    }
}
