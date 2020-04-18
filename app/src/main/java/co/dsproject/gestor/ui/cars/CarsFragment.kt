package co.dsproject.gestor.ui.cars

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import co.dsproject.gestor.Car
import java.util.Date

import co.dsproject.gestor.R
import java.time.LocalDate
import java.time.Month
import java.util.*

class CarsFragment : Fragment() {

    private lateinit var carsviewModel: CarsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        carsviewModel =
                ViewModelProviders.of(this).get(CarsViewModel::class.java)
        val root = inflater.inflate(R.layout.cars_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_cars)
        carsviewModel.car()

        carsviewModel.placa.observe(viewLifecycleOwner, Observer { it ->
            textView.text = it
        })

        carsviewModel.placa.observe(viewLifecycleOwner, Observer { textView.text = it })


        return root
    }



}
