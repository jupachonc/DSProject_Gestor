package co.dsproject.gestor.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import java.time.LocalDateTime
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
    private lateinit var title_1 : TextView
    private lateinit var title_2 : TextView
    private lateinit var iOwner: TextView
    private lateinit var iMarca: TextView
    private lateinit var iLinea: TextView
    private lateinit var iModelo: TextView
    private lateinit var iMant: TextView
    private lateinit var iSOAT: TextView
    private lateinit var iRTM: TextView
    private lateinit var iPoliza: TextView
    private lateinit var iImp: TextView
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
    private lateinit var mDefault: TextView

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = activity?.getSharedPreferences("user", 0)
        uid = prefs!!.getString("user", "").toString()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cars, container, false)


        //Buttons
        newcar = view.findViewById(R.id.nuevo_carro)
        backbtn = view.findViewById(R.id.BackButton)
        nextbtn = view.findViewById(R.id.NextButton)
        deletebtn = view.findViewById(R.id.delete)

        //Tags
        title_1 = view.findViewById(R.id.Tittle_1)
        title_2 = view.findViewById(R.id.Tittle_2)
        iOwner = view.findViewById(R.id.Propietario_id)
        iMarca = view.findViewById(R.id.Marca)
        iLinea = view.findViewById(R.id.Linea_id)
        iModelo = view.findViewById(R.id.Modelo_id)
        iMant = view.findViewById(R.id.Mant_id)
        iSOAT = view.findViewById(R.id.SOAT_id)
        iRTM = view.findViewById(R.id.RTM_id)
        iPoliza = view.findViewById(R.id.Poliza_id)
        iImp = view.findViewById(R.id.Impuesto_id)


        //Atributtes
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
        mDefault = view.findViewById(R.id.message)


        //Listeners
        newcar.setOnClickListener(this)
        backbtn.setOnClickListener(this)
        nextbtn.setOnClickListener(this)
        deletebtn.setOnClickListener(this)

        return view
    }

    override fun onStart() {
        super.onStart()
        if(listcars.head == null) {
            val dialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
            val database = FirebaseDatabase.getInstance().getReference("Users/" + uid + "/Cars")
            readData(database, object : OnGetDataListener {
                override fun onSuccess(dataSnapshot: DataSnapshot?) {
                    for (snapshot in dataSnapshot!!.children) {
                        var car = snapshot.getValue(Car::class.java)
                        listcars.insert(car)
                        addPlc(car!!)
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
                val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                transaction.replace(R.id.nav_host_fragment, cc)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            deletebtn.id -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context, 16974374)
                builder.setTitle(getString(R.string.AlertDialog_DE))
                builder.setMessage("El vehículo con placa " + head!!.data.placa + " va a ser eliminado.")
                builder.setPositiveButton("Aceptar"){ dialog, which ->
                    listcars.delete(head!!.data)
                    updateCarsDB()
                    head = listcars.head
                    updateUI()
                    Toast.makeText(context, "Vehículo eliminado", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
            }


        }
    }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUI() = if (head != null) {
        getRestrition()
        updateLayout(View.VISIBLE)
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
        updateLayout(View.GONE)
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

    private fun updateLayout(flag: Int){
        if(flag == View.VISIBLE){
            mDefault.visibility = View.GONE

        }else{
            mDefault.visibility = View.VISIBLE
        }

        newcar.visibility = flag; backbtn.visibility = flag; nextbtn.visibility = flag
        deletebtn.visibility = flag; title_1.visibility = flag; title_2.visibility = flag
        iOwner.visibility = flag; iMarca.visibility = flag; iLinea.visibility = flag
        iModelo.visibility = flag; iMant.visibility = flag; iSOAT.visibility = flag
        iRTM.visibility = flag; iPoliza.visibility = flag; iImp.visibility = flag
        mPlaca.visibility = flag; mOwner.visibility = flag; mMarca.visibility = flag
        mLinea.visibility = flag; mModelo.visibility = flag; mMant.visibility = flag
        mSOAT.visibility = flag; mRTM.visibility = flag; mPoliza.visibility = flag
        mImp.visibility = flag


    }

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
    fun getRestrition() {
        val day = LocalDateTime.now().dayOfMonth

        when(day % 2){
            0 -> {
                val plc = head!!.data.placa
                if(p0.contains(plc) || p2.contains(plc) || p4.contains(plc)
                        || p6.contains(plc) || p8.contains(plc)
                ){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context, 16974374)
                    builder.setTitle("¡Ten Cuidado!")
                    builder.setMessage("Este vehículo tiene restricción de circulación hoy")
                    builder.setPositiveButton("Cerrar") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()

                }
            }

            1 -> {
                val plc = head!!.data.placa
                if(p1.contains(plc) || p3.contains(plc) || p5.contains(plc)
                        || p7.contains(plc) || p9.contains(plc)
                ){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context, 16974374)
                    builder.setTitle("Vehículos con restricción de circulación hoy")
                    builder.setMessage("Este vehículo tiene restricción de circulación hoy")
                    builder.setPositiveButton("Cerrar") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()

                }
            }

        }


    }


}

