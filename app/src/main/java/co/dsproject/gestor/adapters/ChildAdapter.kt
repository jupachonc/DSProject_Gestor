package co.dsproject.gestor.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.dsproject.gestor.R
import co.dsproject.gestor.models.TaskModel
import kotlinx.android.synthetic.main.child_recycler.view.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit



class ChildAdapter(private val children : List<TaskModel>)
    : RecyclerView.Adapter<ChildAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        val v =  LayoutInflater.from(parent.context)
                .inflate(R.layout.child_recycler,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val child = children[position]
        //holder.imageView.setImageResource(child.image)
        holder.textView.text = child.placa

        val days = LocalDate.now().until(child.fecha, ChronoUnit.DAYS)

        holder.tv.text = "$days\nDías Restantes"




        when(child.tipo){
            0 -> holder.textView.setBackgroundResource(R.drawable.lightred)
            1 -> holder.textView.setBackgroundResource(R.drawable.lightindigo)
            2 -> holder.textView.setBackgroundResource(R.drawable.lightblue)
            3 -> holder.textView.setBackgroundResource(R.drawable.lightgreen)
            4 -> holder.textView.setBackgroundResource(R.drawable.lightamber)
        }

    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.child_textView
        val tv = itemView.child_days
        //val imageView: ImageView = itemView.child_imageView

    }
}