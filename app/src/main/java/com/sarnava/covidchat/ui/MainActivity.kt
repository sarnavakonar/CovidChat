package com.sarnava.covidchat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.sarnava.covidchat.R
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarnava.covidchat.model.ChatItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private val viewModel by viewModels<MainViewModel>()
  private lateinit var adapter: MainAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModel.getCovidData()

    setUI()
    setObservers()
    setClickListeners()
  }

  private fun setUI(){
    val linearLayoutManager = LinearLayoutManager(
      this, RecyclerView.VERTICAL, false
    )
    rv.layoutManager = linearLayoutManager
    adapter = MainAdapter(chats = ArrayList())
    rv.adapter = adapter
  }

  private fun setObservers(){
    viewModel.observeProgress().observe(this, Observer {
      if (it)
        pb.visibility = VISIBLE
      else
        pb.visibility = GONE
    })
    viewModel.observeUserMessage().observe(this, Observer {
      it?.let {
        Toast.makeText(this, it,Toast.LENGTH_SHORT).show()
      }
    })
  }

  private fun setClickListeners(){
    img_send.setOnClickListener {
      var text = et.text.toString()
      if(!text.isBlank()){

        if(text.contains("Cases", true)){

          viewModel.setChat(ChatItem(text, true))
          adapter.setChats(viewModel.getChats())

          if (text.equals("CASES TOTAL", true)){
            val active = viewModel.getData()?.Global?.TotalConfirmed
            viewModel.setChat(ChatItem("Total Active Cases $active", false))
            adapter.setChats(viewModel.getChats())
            rv.smoothScrollToPosition(viewModel.getChats().size)
          }
          else{
            text = text.replace("Cases","",true).trim().toUpperCase()

            if(viewModel.getSearchedData().containsKey(text)){
              val active = viewModel.getSearchedData().get(text)!!.TotalConfirmed
              viewModel.setChat(ChatItem("$text Active Cases $active", false))
              adapter.setChats(viewModel.getChats())
              rv.smoothScrollToPosition(viewModel.getChats().size)
              Log.e("lala", "from hash")
              return@setOnClickListener
            }

            val countries = viewModel.getData()?.Countries
            countries?.forEach {
              if(text.equals(it.CountryCode, true) || text.equals(it.Country, true)){
                viewModel.setChat(ChatItem("$text Active Cases ${it.TotalConfirmed}", false))
                adapter.setChats(viewModel.getChats())
                rv.smoothScrollToPosition(viewModel.getChats().size)
                viewModel.setSearchedData(it.CountryCode!!.toUpperCase(), it)
                viewModel.setSearchedData(it.Country!!.toUpperCase(), it)
                countries.remove(it)
                Log.e("lala", "from loop")
                return@setOnClickListener
              }
            }
            Toast.makeText(this, "Not found",Toast.LENGTH_SHORT).show()
            rv.smoothScrollToPosition(viewModel.getChats().size)
          }
        }
        else if(text.contains("Deaths", true)){

          viewModel.setChat(ChatItem(text, true))
          adapter.setChats(viewModel.getChats())

          if(text.equals("DEATHS TOTAL", true)){
            val deaths = viewModel.getData()?.Global?.TotalDeaths
            viewModel.setChat(ChatItem("Total Deaths $deaths", false))
            adapter.setChats(viewModel.getChats())
            rv.smoothScrollToPosition(viewModel.getChats().size)
          }
          else {
            text = text.replace("Deaths","",true).trim().toUpperCase()

            if(viewModel.getSearchedData().containsKey(text)){
              val deaths = viewModel.getSearchedData().get(text)!!.TotalDeaths
              viewModel.setChat(ChatItem("$text Deaths $deaths", false))
              adapter.setChats(viewModel.getChats())
              rv.smoothScrollToPosition(viewModel.getChats().size)
              Log.e("lala", "from hash")
              return@setOnClickListener
            }

            val countries = viewModel.getData()?.Countries
            countries?.forEach {
              if(text.equals(it.CountryCode, true) || text.equals(it.Country, true)){
                viewModel.setChat(ChatItem("$text Deaths ${it.TotalDeaths}", false))
                adapter.setChats(viewModel.getChats())
                rv.smoothScrollToPosition(viewModel.getChats().size)
                viewModel.setSearchedData(it.CountryCode!!.toUpperCase(), it)
                viewModel.setSearchedData(it.Country!!.toUpperCase(), it)
                countries.remove(it)
                Log.e("lala", "from loop")
                return@setOnClickListener
              }
            }
            Toast.makeText(this, "Not found",Toast.LENGTH_SHORT).show()
            rv.smoothScrollToPosition(viewModel.getChats().size)
          }
        }
        else{
          Toast.makeText(this, "Wrong format",Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

}