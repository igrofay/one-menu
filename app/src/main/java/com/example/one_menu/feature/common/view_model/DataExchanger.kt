package com.example.one_menu.feature.common.view_model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataExchanger @Inject constructor(){
    private val dataPackage = mutableMapOf<String,Any>()
    fun putData(key: String, data: Any){
        dataPackage[key] = data
    }

    fun getData(key: String) : Any{
        return dataPackage.remove(key)!!
    }
    fun getListData(key: String) : List<*>{
        return (dataPackage.remove(key) as List<*>)
    }
}