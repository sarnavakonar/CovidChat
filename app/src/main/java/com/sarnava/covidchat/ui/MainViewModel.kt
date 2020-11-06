package com.sarnava.covidchat.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sarnava.covidchat.R
import com.sarnava.covidchat.model.ChatItem
import com.sarnava.covidchat.model.CovidData
import com.sarnava.covidchat.model.CovidResponse
import com.sarnava.covidchat.repository.NetworkRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var userMessage: MutableLiveData<String> = MutableLiveData()
    private val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    private var data: CovidResponse? = null
    private val chats: MutableList<ChatItem> = ArrayList()
    private val searchedData: HashMap<String, CovidData> = HashMap()

    fun observeUserMessage(): MutableLiveData<String> {
        return userMessage
    }

    private fun setUserMessage(message: String) {
        userMessage.value = message
    }

    private fun setProgress(inProgress: Boolean) {
        showProgress.value = inProgress
    }

    fun observeProgress(): MutableLiveData<Boolean> = showProgress

    private fun setData(data: CovidResponse) {
        this.data = data
    }

    fun getData(): CovidResponse? = data

    fun setChat(chat: ChatItem) {
        this.chats.add(chat)
    }

    fun getChats(): MutableList<ChatItem> = chats

    fun setSearchedData(key: String, value: CovidData) {
        searchedData[key] = value
    }

    fun getSearchedData(): HashMap<String, CovidData> = searchedData

    fun getCovidData() {

        setProgress(inProgress = true)
        NetworkRepository.getData(
            success = {
                setProgress(inProgress = false)
                setData(it.body()!!)
            },
            error = {
                setProgress(inProgress = false)
                setUserMessage(getApplication<Application>().getString(R.string.please_try_again))
            },
            failure = {
                setProgress(inProgress = false)
                val message =
                    it.message ?: getApplication<Application>().getString(R.string.please_try_again)
                setUserMessage(message)
            }
        )
    }
}