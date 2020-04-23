package co.dsproject.gestor

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.Month
import kotlin.concurrent.thread


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [cars.newInstance] factory method to
 * create an instance of this fragment.
 */
class cars : Fragment(), View.OnClickListener{
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var listcars: Lista<Car>
    private lateinit var head: DoubleNode<Car>
    private lateinit var newcar: FloatingActionButton
    private lateinit var backbtn: FloatingActionButton
    private lateinit var nextbtn: FloatingActionButton
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

    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    val carro = Car(
            "ME",
            "ABC123",
            "CHEVROLET",
            "GT",
            2009,
            LocalDate.of(2020, Month.JANUARY, 1),
            15,
            LocalDate.of(2020, Month.JANUARY, 1),
            LocalDate.of(2020, Month.JANUARY, 1),
            LocalDate.of(2020, Month.JANUARY, 1),
            LocalDate.of(2020, Month.JANUARY, 1)

    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        listcars = Lista<Car>()

        //listcars.insert(carro)
        var prefs = activity?.getSharedPreferences("user",0)
        uid = prefs!!.getString("user", "").toString()
        //Toast.makeText(context, uid, Toast.LENGTH_SHORT).show(
        // )



    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cars, container, false)

        newcar = view!!.findViewById(R.id.nuevo_carro)
        backbtn = view.findViewById(R.id.BackButton)
        nextbtn = view.findViewById(R.id.NextButton)
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

        // Inflate the layout for this fragment
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        getCarsfromDB()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment cars.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                cars().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onClick(v: View?) {

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUI() = if(listcars.head != null){

        mPlaca.text = this.head.data.placa
        mOwner.text = this.head.data.owner
        mMarca.text = this.head.data.marca
        mLinea.text = head.data.linea
        mModelo.text = head.data.modelo.toString()
        mMant.text = head.data.ulltimo_mantenimiento.plusDays(head.data.frecuencia_mantenimiento.toLong()).toString()
        mSOAT.text = """${head.data.soat} a ${head.data.soat.plusYears(1).minusDays(1)}"""
        mRTM.text = """${head.data.rtm} a ${head.data.rtm.plusYears(1).minusDays(1)}"""
        mPoliza.text = """${head.data.poliza} a ${head.data.poliza.plusYears(1).minusDays(1)}"""
        mImp.text = """${head.data.impuesto} a ${head.data.impuesto.plusYears(1).minusDays(1)}"""

    }else{
        val n = "null"
        mPlaca.text = "null null"
        mOwner.text = n
        mMarca.text = n
        mLinea.text = n
        mModelo.text = n
        mMant.text = n
        mSOAT.text = n
        mRTM.text = n
        mPoliza.text = n
        mImp.text = n

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCarsfromDB(){
        Thread(
            Runnable {
                val database = FirebaseDatabase.getInstance().getReference("Users/" + uid + "/Cars")
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val car = Car(
                                    snapshot.child("propietario").getValue(String::class.java),
                                    snapshot.child("placa").getValue(String::class.java),
                                    snapshot.child("marca").getValue(String::class.java),
                                    snapshot.child("linea").getValue(String::class.java),
                                    snapshot.child("modelo").getValue(String::class.java)!!.toInt(),
                                    LocalDate.parse(snapshot.child("umant").getValue(String::class.java)),
                                    snapshot.child("fcmant").getValue(String::class.java)!!.toInt(),
                                    LocalDate.parse(snapshot.child("soat").getValue(String::class.java)),
                                    LocalDate.parse(snapshot.child("rtm").getValue(String::class.java)),
                                    LocalDate.parse(snapshot.child("poliza").getValue(String::class.java)),
                                    LocalDate.parse(snapshot.child("imp").getValue(String::class.java))

                            )

                            listcars.insert(car)

                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
            }).start()
        updateUI()
    }
}



