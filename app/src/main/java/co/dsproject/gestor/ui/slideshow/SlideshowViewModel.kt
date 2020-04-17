package co.dsproject.gestor.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.dsproject.gestor.Car
import co.dsproject.gestor.Lista

class SlideshowViewModel : ViewModel() {
    var lista_carros = Lista<Car>()



    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text
}