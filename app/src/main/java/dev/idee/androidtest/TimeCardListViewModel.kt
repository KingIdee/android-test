package dev.idee.androidtest

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

class TimeCardListViewModel: ViewModel(){

    val timeCardListLiveData = MediatorLiveData<List<TimeCardModel>>()
    val timeCardList = ArrayList<TimeCardModel>()


    fun saveTimeCardList(timeCardModel: TimeCardModel) {
        timeCardList.add(timeCardModel)
        timeCardListLiveData.postValue(timeCardList)
    }

    fun updateTimeCardList(timeCardModel: TimeCardModel){
        val index = timeCardList.indexOfFirst { it.id == timeCardModel.id }
        timeCardList[index] = timeCardModel
    }

    fun deleteItemFromList(id: String) {
        val index = timeCardList.indexOfFirst { it.id == id }
        timeCardList.remove(timeCardList[index])
    }

}