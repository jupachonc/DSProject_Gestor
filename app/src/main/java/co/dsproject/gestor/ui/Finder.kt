@file:Suppress("DEPRECATION")

package co.dsproject.gestor.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import co.dsproject.gestor.BstCar
import co.dsproject.gestor.Car
import co.dsproject.gestor.R
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Finder : Fragment(), View.OnClickListener {

    private lateinit var bstCars: BstCar
    private lateinit var uid: String
    private lateinit var mainCons: ConstraintLayout
    private lateinit var delfaultM: TextView
    private lateinit var searchButton: Button
    private lateinit var placaChar: EditText
    private lateinit var placaNum: EditText
    private val p0 = HashSet<String>()
    private val p1 = HashSet<String>()
    private val p2 = HashSet<String>()
    private val p3 = HashSet<String>()
    private val p4 = HashSet<String>()
    private val p5 = HashSet<String>()
    private val p6 = HashSet<String>()
    private val p7 = HashSet<String>()
    private val p8 = HashSet<String>()
    private val p9 = HashSet<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = activity?.getSharedPreferences("user", 0)
        uid = prefs!!.getString("user", "").toString()
        bstCars = BstCar()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_finder, container, false)

        mainCons = view.findViewById(R.id.constraint_main)
        delfaultM = view.findViewById(R.id.message_search)
        searchButton = view.findViewById(R.id.bSearch)
        placaChar = view.findViewById(R.id.fPlacaChar)
        placaNum = view.findViewById(R.id.fPlacaNum)

        searchButton.setOnClickListener(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
        val database = FirebaseDatabase.getInstance().getReference("Users/$uid/Cars")
        readData(database, object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                for (snapshot in dataSnapshot!!.children) {
                    val car = snapshot.getValue(Car::class.java)
                    bstCars.insertBST(car)
                    addPlc(car!!)
                }
                if(null == bstCars.root){delfaultM.visibility = View.VISIBLE; mainCons.visibility = View.GONE}
                else{delfaultM.visibility = View.GONE; mainCons.visibility = View.VISIBLE}
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
            }

            override fun onStart() {
                Log.d("ONSTART", "Started")
                dialog.setMessage("Cargando los vehículos, por favor espere")
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when(v!!.id){
            searchButton.id -> search()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun search() {
        val car = bstCars.findBST(placaChar.text.toString() + " " + placaNum.text.toString())
        if(car != null) {
            getRestrition(car)
            val builder: AlertDialog.Builder = AlertDialog.Builder(context, 16974374)
            builder.setTitle("Información del vehículo\n" +
                    car.marca + " " + car.linea + " " + car.modelo + " " + car.placa)
            val message =
                    "Propietario: " + car.owner + "\n" +
                    "\nPróximos vencimientos:\n" +
                    "\nMantenimiento: " +
                    LocalDate.parse(car.ultimo_mantenimiento).plusDays(car.frecuencia_mantenimiento.toLong())
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+
                    "\nSOAT: " + LocalDate.parse(car.soat).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    "\nRTM: " + LocalDate.parse(car.rtm).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    "\nPóliza: " + LocalDate.parse(car.poliza).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    "\nImpuesto: " + LocalDate.parse(car.impuesto).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            builder.setMessage(message)
            builder.setPositiveButton("Cerrar"){ dialog, _ ->
                    dialog.dismiss()
            }
            /*
            builder.setNegativeButton("Eliminar Vehículo"){ dialog, which ->
                val builder: AlertDialog.Builder = AlertDialog.Builder(context, 16974374)
                builder.setTitle(getString(R.string.AlertDialog_DE))
                builder.setMessage("El vehículo con placa " + car.placa + " va a ser eliminado.")
                builder.setPositiveButton("Aceptar"){ dialog, which ->
                    bstCars.removeBST(car.placa)
                    updateCars()
                    Toast.makeText(context, "Vehículo eliminado", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
            }*/
            builder.show()

        }
        else Toast.makeText(context, "El vehículo no está registrado", Toast.LENGTH_SHORT).show()


    }
    /*
    private fun updateCars(){
        updateCarsDBT(bstCars.root)
        updateCarsDB()
    }
    private fun updateCarsDBT(root: BstCar.Node) {
        if(root != null) {
            listCars.insert(root.data)
            if(root.left != null) updateCarsDBT(root.left)
            if(root.right != null) updateCarsDBT(root.right)
        }

    }

    private fun updateCarsDB(){
        val database = FirebaseDatabase.getInstance().getReference("Users/" + uid + "/Cars")
        database.removeValue()
        var ptr = listCars.head
        while(ptr != null){
            database.push().setValue(ptr.data)
            ptr = ptr.next
        }
    }
    */

    fun addPlc(car: Car){
        val placa = car.placa
        Log.d("adding", placa[6].toString())
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
    private fun getRestrition(car: Car) {
        val day = LocalDateTime.now().dayOfMonth

        when(day % 2){
            0 -> {
                val plc = car.placa
                if(p0.contains(plc) || p2.contains(plc) || p4.contains(plc)
                        || p6.contains(plc) || p8.contains(plc)
                )alertshow()
            }

            1 -> {
                val plc = car.placa
                if(p1.contains(plc) || p3.contains(plc) || p5.contains(plc)
                        || p7.contains(plc) || p9.contains(plc)
                )alertshow()
            }

        }


    }

    private fun alertshow(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context, 16974374)
        builder.setTitle("¡Ten Cuidado!")
        builder.setMessage("Este vehículo tiene restricción de circulación hoy")
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }


}