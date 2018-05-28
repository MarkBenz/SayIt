package de.markbenz.sayit

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AdapterDialog(val elemente : ArrayList<DataAntwort>) : RecyclerView.Adapter<HolderDialog>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDialog {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.themen_layout, parent,false)

        return HolderDialog(mView)
    }

    override fun getItemCount(): Int {
        return elemente.size
    }

    override fun onBindViewHolder(holder: HolderDialog, position: Int) {
        holder.verbinden(elemente.get(position))
    }

}

class HolderDialog (itemView: View): RecyclerView.ViewHolder(itemView){

    fun verbinden(element : DataAntwort){
        val content : TextView = itemView.findViewById(R.id.textViewContent)
        content.text = element.content
        content.setTextColor(Color.rgb(0, 0, 0))
        content.setBackgroundColor(Color.rgb(getRand(), getRand(), getRand()))
        itemView.setOnClickListener({
            Log.i("Test", "Geglickt wurde ${element.content})")
        })
    }

    fun getRand (): Int{
        val rand = Math.random() * 255
        val randint : Int = rand.toInt()
        return randint
    }

}