package de.markbenz.sayit

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Adapter(val elemente : ArrayList<DataThema>, val context: Context) : RecyclerView.Adapter<Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.themen_layout, parent,false)

        return Holder(mView, context)
    }

    override fun getItemCount(): Int {
        return elemente.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.verbinden(elemente.get(position))
    }

}

class Holder (itemView: View,val context: Context): RecyclerView.ViewHolder(itemView){

    fun verbinden(element : DataThema){
        val content : TextView = itemView.findViewById(R.id.textViewContent)
        content.text = element.content
        content.setTextColor(Color.rgb(0, 0, 0))
        content.setBackgroundColor(Color.rgb(getRand(), getRand(), getRand()))
        itemView.setOnClickListener({
            Log.i("Test", "Geglickt wurde ${element.content})")

            val intent = Intent(context, DialogActivity::class.java)
            intent.putExtra("id", element.id)
            context.startActivity(intent)
        })
    }

    fun getRand (): Int{
        val rand = Math.random() * 255
        val randint : Int = rand.toInt()
        return randint
    }

}