package co.dsproject.gestor.ui.cars

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.dsproject.gestor.Car
import co.dsproject.gestor.Lista
import java.time.LocalDate
import java.time.Month
import java.util.*

class CarsViewModel : ViewModel() {
    private var _Cars = Lista<Car>()
    private var head = _Cars.head

    private val _owner = MutableLiveData<String>().apply { value = head.data.owner }
    private val _placa = MutableLiveData<String>().apply { value = head.data.placa }
    private val _marca = MutableLiveData<String>().apply { value = head.data.marca }
    private val _linea = MutableLiveData<String>().apply { value = head.data.linea }
    private val _modelo = MutableLiveData<Int>().apply { value = head.data.modelo }
    private val _ulltimo_mantenimiento = MutableLiveData<LocalDate>().apply { value = head.data.ulltimo_mantenimiento }
    private val _frecuencia_mantenimiento = MutableLiveData<Int>().apply { value = head.data.frecuencia_mantenimiento }
    private val _soat = MutableLiveData<LocalDate>().apply { value = head.data.soat }
    private val _rtm = MutableLiveData<LocalDate>().apply { value = head.data.rtm }
    private val _poliza = MutableLiveData<LocalDate>().apply { value = head.data.poliza }
    private val _impuesto = MutableLiveData<LocalDate>().apply { value = head.data.impuesto }

    private val _text = MutableLiveData<String>().apply {
        value = "Este es el fragmento car"
    }

    val text : LiveData<String> = _text

    val owner : LiveData<String> = _owner
    val placa : LiveData<String> = _placa
    val marca : LiveData<String> = _marca
    val linea : LiveData<String> = _linea
    val modelo : LiveData<Int> = _modelo
    val ultimomantenimiento : LiveData<LocalDate> = _ulltimo_mantenimiento
    val frecuenciamantenimiento : LiveData<Int> = _frecuencia_mantenimiento
    val soat : LiveData<LocalDate> = _soat
    val rtm : LiveData<LocalDate> = _rtm
    val poliza : LiveData<LocalDate> = _poliza
    val impuesto : LiveData<LocalDate> = _impuesto

    @RequiresApi(Build.VERSION_CODES.O)
    fun car(){
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
        _Cars.insert(carro)
    }
}
