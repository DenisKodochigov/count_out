package com.example.count_out.data

import com.example.count_out.entity.Workout
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository  @Inject constructor(){

    fun getWorkouts(): List<Workout>{ return emptyList() }
    fun changeNameWorkout(workout: Workout): List<Workout>{ return emptyList() }
    fun deleteWorkout(id: Long): List<Workout>{ return emptyList() }
    fun addWorkout(name: String): List<Workout>{ return emptyList() }
}