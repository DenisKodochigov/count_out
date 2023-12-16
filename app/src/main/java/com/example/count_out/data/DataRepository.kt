package com.example.count_out.data

import com.example.count_out.entity.PluginTrainings
import com.example.count_out.entity.Training
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository  @Inject constructor(){

    fun getTrainings(): List<Training>{ return PluginTrainings.list }
    fun getTraining(id: Long): Training { return PluginTrainings.item(id) }
    fun changeNameTraining(id: Long): List<Training>{ return emptyList() }
    fun deleteTraining(id: Long): List<Training>{
        PluginTrainings.list.remove(PluginTrainings.list.find { it.idTraining ==id })
        return PluginTrainings.list
    }
    fun copyTraining(id: Long): List<Training>
    {
        val item = PluginTrainings.list.find { it.idTraining ==id }
        val idLast = PluginTrainings.list.maxBy { it.idTraining }.idTraining

        item?.let {
            val itemC = item.copy()
            itemC.idTraining = idLast + 1
            PluginTrainings.list.add(itemC) }
        return PluginTrainings.list
    }
    fun addTraining(name: String): List<Training>{ return emptyList() }
}