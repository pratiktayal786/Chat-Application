package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, private val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 2
    private val ITEM_SENT = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == 1) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            SentViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.received, parent, false)
            ReceivedViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SentViewHolder::class.java)
        {
            val viewHolder = holder as SentViewHolder
            viewHolder.tvSentMessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceivedViewHolder
            viewHolder.tvReceivedMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE


        }
    }
    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvSentMessage: TextView = itemView.findViewById<TextView>(R.id.tv_send_message)
    }
    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvReceivedMessage: TextView = itemView.findViewById<TextView>(R.id.tv_received_message)
    }
}
