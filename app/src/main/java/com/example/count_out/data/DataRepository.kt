package com.example.count_out.data

import com.example.count_out.entity.PluginTrainings
import com.example.count_out.entity.Training
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository  @Inject constructor(){

    fun getTrainings(): List<Training>{ return PluginTrainings.list }
    fun getTraining(id: Long): Training { return PluginTrainings.item(id) }
    fun changeNameWorkout(id: Long): List<Training>{ return emptyList() }
    fun deleteWorkout(id: Long): List<Training>{ return emptyList() }
    fun addWorkout(name: String): List<Training>{ return emptyList() }
}