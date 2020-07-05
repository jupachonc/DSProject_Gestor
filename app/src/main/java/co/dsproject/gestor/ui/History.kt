package co.dsproject.gestor.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import co.dsproject.gestor.Car
import co.dsproject.gestor.Pila
import co.dsproject.gestor.R
import co.dsproject.gestor.models.TaskModel
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.Month


class History : Fragment(), View.OnClickListener {

    private var MainStack = Pila<TaskModel>()
    private var AuxStack = Pila<TaskModel>()
    private var MainTask: TaskModel? = null
    private lateinit var uid: String
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = activity?.getSharedPreferences("user", 0)
        uid = prefs!!.getString("user", "").toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        title = view.findViewById(R.id.Title_Task)
        description = view.findViewById(R.id.Description)
        btn = view.findViewById(R.id.button)

        btn.setOnClickListener(this)
        return view
    }

    override fun onStart() {
        super.onStart()
            val dialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
            val database = FirebaseDatabase.getInstance().getReference("Users/" + uid + "/History")
            readData(database, object : OnGetDataListener {
                override fun onSuccess(dataSnapshot: DataSnapshot?) {
                    for (snapshot in dataSnapshot!!.children) {
                        var task = snapshot.getValue(TaskModel::class.java)
                        AuxStack.push(task)
                    }
                    while(!AuxStack.empty()){
                        MainStack.push(AuxStack.pop())
                    }
                    if(!MainStack.empty()) MainTask = MainStack.pop()
                    if(MainTask != null) updateUI()
                    if (dialog.isShowing) {
                        dialog.dismiss();
                    }
                }

                override fun onStart() {
                    Log.d("ONSTART", "Started")
                    dialog.setMessage("Cargando los vehículos, por favor espere");
                    dialog.show()
                }

                override fun onFailure() {
                    Log.d("onFailure", "Failed")
                }
            })
    }

    interface OnGetDataListener {
        //Para los CallBacks
        fun onSuccess(dataSnapshot: DataSnapshot?)
        fun onStart()
        fun onFailure()
    }

    private fun readData(ref: DatabaseReference, listener: OnGetDataListener) {
        listener.onStart()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listener.onSuccess(dataSnapshot)
            }

            override fun onCancelled(databaserror: DatabaseError) {}
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        title.text = getType(MainTask!!) + "\n" + MainTask!!.placa.toString()
        description.text = "Realizado el " + FechaLarga(LocalDate.parse(MainTask!!.fecha))

    }

    override fun onClick(v: View?) {
        MainTask = MainStack.pop()
        if(MainTask != null) updateUI()
        else
            Toast.makeText(context, "No hay más tareas realizadas", Toast.LENGTH_SHORT).show()
    }

    fun getType(child: TaskModel): String{
        when(child.tipo){
            0 -> {
                return "Mantenimiento"
            }

            1 -> {
                return "Renovación SOAT"
            }

            2 -> {
                return "Revisión Técnico-Mecánica"
            }

            3 -> {
                return  "Renovación Póliza"
            }

            4 -> {
                return "Pago de Impuesto"
            }
        }

        return  ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun FechaLarga(date : LocalDate): String{
        return date.dayOfMonth.toString() + " de " + NombreMes(date.month) + " del " + date.year
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun NombreMes(m: Month) = when(m){
        Month.JANUARY -> "Enero"
        Month.FEBRUARY -> "Febrero"
        Month.MARCH -> "Marzo"
        Month.APRIL -> "Abril"
        Month.MAY -> "Mayo"
        Month.JUNE -> "Junio"
        Month.JULY -> "Julio"
        Month.AUGUST -> "Agosto"
        Month.SEPTEMBER -> "Septiembre"
        Month.OCTOBER -> "Octubre"
        Month.NOVEMBER -> "Noviembre"
        Month.DECEMBER -> "Diciembre"
    }


}