package co.dsproject.gestor.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.dsproject.gestor.*
import co.dsproject.gestor.R
import co.dsproject.gestor.adapters.ParentAdapter
import co.dsproject.gestor.models.ParentModel
import co.dsproject.gestor.models.TaskModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.time.LocalDate
import java.time.LocalDateTime

class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var root: View
    private lateinit var uid: String
    val mant = OrderedListTime()
    val soat = OrderedListTime()
    val rtm = OrderedListTime()
    val pol = OrderedListTime()
    val imp = OrderedListTime()
    val p0 = HashSet<String>()
    val p1 = HashSet<String>()
    val p2 = HashSet<String>()
    val p3 = HashSet<String>()
    val p4 = HashSet<String>()
    val p5 = HashSet<String>()
    val p6 = HashSet<String>()
    val p7 = HashSet<String>()
    val p8 = HashSet<String>()
    val p9 = HashSet<String>()
    val cars = Lista<Car>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = activity?.getSharedPreferences("user", 0)
        uid = prefs!!.getString("user", "").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        initRecycler()
        return root
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

    private fun initRecycler(){

        val dialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
        val database = FirebaseDatabase.getInstance().getReference("Users/" + uid + "/Cars")
        readData(database, object : OnGetDataListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                for (snapshot in dataSnapshot!!.children) {
                    var car = snapshot.getValue(Car::class.java)
                    addPlc(car!!)
                    cars.insert(car)
                    mant.add(TaskModel(0, car!!.placa,
                            LocalDate.parse(car!!.ultimo_mantenimiento).plusDays(car!!.frecuencia_mantenimiento.toLong())))
                    soat.add(TaskModel(1, car!!.placa, LocalDate.parse(car!!.soat)))
                    rtm.add(TaskModel(2, car!!.placa, LocalDate.parse(car!!.rtm)))
                    pol.add(TaskModel(3, car!!.placa, LocalDate.parse(car!!.poliza)))
                    imp.add(TaskModel(4, car!!.placa, LocalDate.parse(car!!.impuesto)))
                }


                val lista = arrayListOf(
                        ParentModel("Próximos Mantenimientos", toList(mant)),
                        ParentModel("Próximos Vencimientos SOAT", toList(soat)),
                        ParentModel("Próximas Revisiones", toList(rtm)),
                        ParentModel("Próximos Vencimientos Póliza", toList(pol)),
                        ParentModel("Próximos Pagos de Impuestos", toList(imp))
                )

                recyclerView = root.rv_parent
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context,
                            RecyclerView.VERTICAL, false)
                    adapter = ParentAdapter(lista, cars, uid)
                }

                if (dialog.isShowing) {
                    dialog.dismiss()
                    getRestrition()
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

    fun toList(Lista: OrderedListTime): List<TaskModel>{
        val list = mutableListOf<TaskModel>()
        var head = Lista.head
        while(head != null){
            list.add(head.data as TaskModel)
            head = head.next as NodeGeneric<TaskModel>?
        }
        return  list
    }

    fun addPlc(car: Car){
        val placa = car.placa
        when(placa[6].toString().toInt()){

            0 -> p0.add(placa)
            1 -> p1.add(placa)
            2 -> p2.add(placa)
            3 -> p3.add(placa)
            4 -> p4.add(placa)
            5 -> p5.add(placa)
            6 -> p6.add(placa)
            7 -> p7.add(placa)
            8 -> p8.add(placa)
            9 -> p9.add(placa)

        }
    }

    fun getRestrition() {
        val day = LocalDateTime.now().dayOfMonth
        var plcs = ""
        if (day % 2 == 0) {
            p0.iterator().forEach { plcs += it + "\n" }
            Log.d("Adding Strings", "Adding Strings")
            p2.iterator().forEach { plcs += it + "\n" }
            p4.iterator().forEach { plcs += it + "\n" }
            p6.iterator().forEach { plcs += it + "\n" }
            p8.iterator().forEach { plcs += it + "\n" }
        } else {

            p1.iterator().forEach { plcs += it + "\n" }
            p3.iterator().forEach { plcs += it + "\n" }
            p5.iterator().forEach { plcs += it + "\n" }
            p7.iterator().forEach { plcs += it + "\n" }
            p9.iterator().forEach { plcs += it + "\n" }

        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(context, 16974374)
        builder.setTitle("Vehículos con restricción de circulación hoy")
        builder.setMessage("\n$plcs")
        builder.setPositiveButton("Cerrar") { dialog, which ->
            dialog.dismiss()
        }
        if(plcs != "") builder.show()
    }


}

