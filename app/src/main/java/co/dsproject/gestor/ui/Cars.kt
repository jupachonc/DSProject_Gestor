package co.dsproject.gestor.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import co.dsproject.gestor.Car
import co.dsproject.gestor.DoubleNode
import co.dsproject.gestor.Lista
import co.dsproject.gestor.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.Month


class Cars : Fragment(), View.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private var listcars = Lista<Car>()
    private var head: DoubleNode<Car>? = null
    private lateinit var newcar: FloatingActionButton
    private lateinit var backbtn: FloatingActionButton
    private lateinit var nextbtn: FloatingActionButton
    private lateinit var deletebtn: FloatingActionButton
    private lateinit var mPlaca: TextView
    private lateinit var mOwner: TextView
    private lateinit var mMarca: TextView
    private lateinit var mLinea: TextView
    private lateinit var mModelo: TextView
    private lateinit var mMant: TextView
    private lateinit var mSOAT: TextView
    private lateinit var mRTM: TextView
    private lateinit var mPoliza: TextView
    private lateinit var mImp: TextView
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = activity?.getSharedPreferences("user", 0)
        uid = prefs!!.getString("user", "").toString()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cars, container, false)

        newcar = view!!.findViewById(R.id.nuevo_carro)
        backbtn = view.findViewById(R.id.BackButton)
        nextbtn = view.findViewById(R.id.NextButton)
        deletebtn = view.findViewById(R.id.delete)
        mPlaca = view.findViewById(R.id.Placa)
        mOwner = view.findViewById(R.id.propietario_name)
        mMarca = view.findViewById(R.id.Marca_name)
        mLinea = view.findViewById(R.id.Linea_name)
        mModelo = view.findViewById(R.id.Modelo_int)
        mMant = view.findViewById(R.id.Mant_date)
        mSOAT = view.findViewById(R.id.SOAT_date)
        mRTM = view.findViewById(R.id.RTM_date)
        mPoliza = view.findViewById(R.id.Poliza_date)
        mImp = view.findViewById(R.id.Impuesto_date)

        newcar.setOnClickListener(this)
        backbtn.setOnClickListener(this)
        nextbtn.setOnClickListener(this)
        deletebtn.setOnClickListener(this)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        if(listcars.head == null) {
            val dialog = ProgressDialog(activity);
            val database = FirebaseDatabase.getInstance().getReference("Users/" + uid + "/Cars")
            readData(database, object : OnGetDataListener {
                override fun onSuccess(dataSnapshot: DataSnapshot?) {
                    for (snapshot in dataSnapshot!!.children) {
                        var car = snapshot.getValue(Car::class.java)
                        listcars.insert(car)
                    }
                    if(listcars.head != null) head = listcars.head
                    updateUI()
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
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        if(head != null){
        val i = v!!.id
        when(i){
            nextbtn.id -> if(head!!.next != null){
                head = head!!.next
                updateUI()
            }else Toast.makeText(context, getString(R.string.Toastbtn), Toast.LENGTH_SHORT).show()

            backbtn.id -> if(head!!.back != null){
                head = head!!.back
                updateUI()
            }else Toast.makeText(context, getString(R.string.Toastbtn), Toast.LENGTH_SHORT).show()

            newcar.id -> {
                val cc = CreateCar()
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, cc)
                transaction.addToBackStack(null)
                transaction.commit()

            }

            deletebtn.id -> {
                listcars.delete(head!!.data)
                updateCarsDB()
            }




        }
    }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUI() = if (head != null) {

        mPlaca.text = this.head!!.data.placa
        mOwner.text = this.head!!.data.owner
        mMarca.text = this.head!!.data.marca
        mLinea.text = this.head!!.data.linea
        mModelo.text = this.head!!.data.modelo.toString()
        val fMant = LocalDate.parse(head!!.data.ultimo_mantenimiento).plusDays(head!!.data.frecuencia_mantenimiento.toLong())
        mMant.text = FechaLarga(fMant)
        val fSOAT = LocalDate.parse(head!!.data.soat)
        mSOAT.text = FechaLarga(fSOAT)
        val fRTM = LocalDate.parse(head!!.data.rtm)
        mRTM.text = FechaLarga(fRTM)
        val fPol = LocalDate.parse(head!!.data.poliza)
        mPoliza.text = FechaLarga(fPol)
        val fImp = LocalDate.parse(head!!.data.impuesto)
        mImp.text = FechaLarga(fImp)

    } else {
            Toast.makeText(context, "No hay vehículos registrados", Toast.LENGTH_SHORT).show()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun FechaLarga(date : LocalDate): String{
        return date.dayOfMonth.toString() + " de " + NombreMes(date.month) + " del " + date.year
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun NombreMes(m: Month): String{
        when(m){
            Month.JANUARY -> return "Enero"
            Month.FEBRUARY -> return "Febrero"
            Month.MARCH -> return  "Marzo"
            Month.APRIL -> return "Abril"
            Month.MAY -> return "Mayo"
            Month.JUNE -> return "Junio"
            Month.JULY -> return "Julio"
            Month.AUGUST -> return "Agosto"
            Month.SEPTEMBER -> return "Septiembre"
            Month.OCTOBER -> return "Octubre"
            Month.NOVEMBER -> return "Noviembre"
            Month.DECEMBER -> return "Diciembre"
        }

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

    private fun updateCarsDB(){
        val database = FirebaseDatabase.getInstance().getReference("Users/" + uid + "/Cars")
        database.removeValue()
        var ptr = listcars.head
        while(ptr != null){
            database.push().setValue(ptr.data)
            ptr = ptr.next
        }
    }


}

