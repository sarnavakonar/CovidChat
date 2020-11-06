package com.sarnava.covidchat.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sarnava.covidchat.R
import com.sarnava.covidchat.model.ChatItem
import kotlinx.android.synthetic.main.chat_item_me.view.*
import kotlinx.android.synthetic.main.chat_item_other.view.*

class MainAdapter(
    private var chats: MutableList<ChatItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == 0){
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.chat_item_me, parent, false)
            return MyViewHolder(view)
        }
        else{
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.chat_item_other, parent, false)
            return OtherViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(chats[position].sentFromMe) 0 else 1
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val chat = chats[position]
        if(chat.sentFromMe){
            (holder as MyViewHolder).myText.text = chat.text
        }
        else{
            (holder as OtherViewHolder).otherText.text = chat.text
        }
    }

    override fun getItemCount(): Int = chats.size

    fun setChats(chats: List<ChatItem>) {
        this.chats.clear()
        this.chats.addAll(chats)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myText: TextView = itemView.findViewById(R.id.txt_me)
    }

    inner class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val otherText: TextView = itemView.findViewById(R.id.txt_other)
    }
}