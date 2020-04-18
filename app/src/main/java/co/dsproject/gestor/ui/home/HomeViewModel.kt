package co.dsproject.gestor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.dsproject.gestor.Car
import co.dsproject.gestor.Lista

class HomeViewModel : ViewModel() {

    private var cars_list = Lista<Car>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}