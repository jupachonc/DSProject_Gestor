package co.dsproject.gestor.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.dsproject.gestor.Car
import co.dsproject.gestor.R
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_cars.*
import kotlinx.android.synthetic.main.fragment_create_car.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var nLetPlaca: EditText
private lateinit var nNumPlaca: EditText
private lateinit var nOwner: EditText
private lateinit var tMarca: EditText
private lateinit var Linea: EditText
private lateinit var Modelo: EditText
private lateinit var UMant: EditText
private lateinit var FcMant: EditText
private lateinit var SOAT: EditText
private lateinit var RTM: EditText
private lateinit var Pol: EditText
private lateinit var Imp: EditText
private lateinit var bSave: Button
private lateinit var uid: String

class CreateCar : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        var prefs = activity?.getSharedPreferences("user", 0)
        uid = prefs!!.getString("user", "").toString()


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_create_car, container, false)
        // Inflate the layout for this fragment
        nLetPlaca = view.findViewById(R.id.Placa_Let)
        nNumPlaca = view.findViewById(R.id.Placa_num)
        nOwner = view.findViewById(R.id.nPropietario)
        tMarca = view.findViewById(R.id.nMarca)
        Linea = view.findViewById(R.id.nLinea)
        Modelo = view.findViewById(R.id.nModelo)
        UMant = view.findViewById(R.id.nUMant)
        FcMant = view.findViewById(R.id.nFcMant)
        SOAT = view.findViewById(R.id.nSOAT)
        RTM = view.findViewById(R.id.nRTM)
        Pol = view.findViewById(R.id.nPol)
        Imp = view.findViewById(R.id.nImp)
        bSave = view.findViewById(R.id.savebtn)


        UMant.setOnClickListener(this)
        SOAT.setOnClickListener(this)
        RTM.setOnClickListener(this)
        Pol.setOnClickListener(this)
        Imp.setOnClickListener(this)
        bSave.setOnClickListener(this)


        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateCar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CreateCar().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        var i = v?.id

        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val ctx = context

        when (i) {

            bSave.id -> {
                saveDb()
                Toast.makeText(context, "Vehiculo creado", Toast.LENGTH_SHORT).show()

            }

            UMant.id -> {
                val datePickerDialog = ctx?.let {
                    DatePickerDialog(it,
                            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                UMant.setText(year.toString() + "-" + String.format("%02d", (monthOfYear + 1)) +
                                        "-" + String.format("%02d", dayOfMonth))
                            }, mYear, mMonth, mDay)
                }
                datePickerDialog!!.show()
            }

            SOAT.id -> {
                val datePickerDialog = ctx?.let {
                    DatePickerDialog(it,
                            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                SOAT.setText(year.toString() + "-" + String.format("%02d", (monthOfYear + 1)) +
                                        "-" + String.format("%02d", dayOfMonth))
                            }, mYear, mMonth, mDay)
                }
                datePickerDialog!!.show()
            }

            RTM.id -> {
                val datePickerDialog = ctx?.let {
                    DatePickerDialog(it,
                            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                RTM.setText(year.toString() + "-" + String.format("%02d", (monthOfYear + 1)) +
                                        "-" + String.format("%02d", dayOfMonth))
                            }, mYear, mMonth, mDay)
                }
                datePickerDialog!!.show()

            }

            Pol.id -> {
                val datePickerDialog = ctx?.let {
                    DatePickerDialog(it,
                            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                Pol.setText(year.toString() + "-" + String.format("%02d", (monthOfYear + 1)) +
                                        "-" + String.format("%02d", dayOfMonth))
                            }, mYear, mMonth, mDay)
                }
                datePickerDialog!!.show()
            }

            Imp.id -> {
                val datePickerDialog = ctx?.let {
                    DatePickerDialog(it,
                            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                                Imp.setText(year.toString() + "-" + (monthOfYear + 1) +
                                        "-" + String.format("%02d", dayOfMonth))
                            }, mYear, mMonth, mDay)
                }
                datePickerDialog!!.show()
            }


        }
    }

    private fun saveDb() {
        val database = FirebaseDatabase.getInstance().reference.child("Users/" + uid + "/Cars").push()
        val car = Car()
        car.placa = nLetPlaca.text.toString() + " " + nNumPlaca.text.toString()
        car.owner = nOwner.text.toString()
        car.marca = tMarca.text.toString()
        car.linea = Linea.text.toString()
        car.modelo = Modelo.text.toString().toInt()
        car.ultimo_mantenimiento = UMant.text.toString()
        car.frecuencia_mantenimiento = FcMant.text.toString().toInt()
        car.soat = SOAT.text.toString()
        car.rtm = RTM.text.toString()
        car.poliza = Pol.text.toString()
        car.impuesto = Imp.text.toString()

        database.setValue(car)

    }
}