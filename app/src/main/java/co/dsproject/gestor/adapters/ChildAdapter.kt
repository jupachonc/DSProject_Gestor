package co.dsproject.gestor.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.dsproject.gestor.Car
import co.dsproject.gestor.Lista
import co.dsproject.gestor.R
import co.dsproject.gestor.models.TaskModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.child_recycler.view.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ChildAdapter(private val children : MutableList<TaskModel>, private val cars: Lista<Car>, private  val uid: String)
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

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val child = children[position]
        //holder.imageView.setImageResource(child.image)
        holder.textView.text = child.placa

        val days = LocalDate.now().until(LocalDate.parse(child.fecha), ChronoUnit.DAYS)

        holder.tv.text = "$days\nDías Restantes"




        when(child.tipo){
            0 -> holder.textView.setBackgroundResource(R.drawable.lightred)
            1 -> holder.textView.setBackgroundResource(R.drawable.lightindigo)
            2 -> holder.textView.setBackgroundResource(R.drawable.lightblue)
            3 -> holder.textView.setBackgroundResource(R.drawable.lightgreen)
            4 -> holder.textView.setBackgroundResource(R.drawable.lightamber)
        }

        holder.container.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(holder.container.context, 16974374)
            builder.setTitle("Confirmar")
            val message = "Realizó " + getType(child) + " en " + holder.textView.text.toString()
            builder.setMessage(message)
            builder.setPositiveButton("Confirmar"){ dialog, _ ->
                updateCar(child)
                updateCarsDB()
                removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(holder.container.context, "Se ha actualizado la información de su vehículo",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar"){ dialog, _ ->
                    dialog.dismiss()
            }
            builder.show()

        }
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.child_textView
        val tv: TextView = itemView.child_days
        val container: LinearLayout = itemView.linearchild

    }

    private fun updateCarsDB(){
        val database = FirebaseDatabase.getInstance().getReference("Users/$uid/Cars")
        database.removeValue()
        var ptr = cars.head
        while(ptr != null){
            database.push().setValue(ptr.data)
            ptr = ptr.next
        }
    }

    private fun removeAt(position: Int) {
        children.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, children.size)
    }

    private fun updateCar(child: TaskModel){
        val car = cars.getItem(Car(child.placa))

        when(child.tipo){
            0 -> car.ultimo_mantenimiento = LocalDate.now().toString()
            1 -> car.soat = LocalDate.now().plusYears(1).toString()
            2 -> car.rtm = LocalDate.now().plusYears(1).toString()
            3 -> car.poliza = LocalDate.now().plusYears(1).toString()
            4 -> car.impuesto = LocalDate.parse(car.impuesto).plusYears(1).toString()
        }

        FirebaseDatabase.getInstance().getReference("Users/$uid/History").push()
                .setValue(TaskModel(child.tipo, child.placa, LocalDate.now()))


    }

    private fun getType(child: TaskModel) = when(child.tipo){
        0 -> "Mantenimiento"
        1 -> "Renovación SOAT"
        2 -> "Revisión Técnico-Mecánica"
        3 -> "Renovación Póliza"
        4 -> "Pago de Impuesto"
        else -> ""
    }


}