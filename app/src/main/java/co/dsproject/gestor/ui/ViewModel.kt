package co.dsproject.gestor.ui

import androidx.lifecycle.ViewModel
import co.dsproject.gestor.Car
import co.dsproject.gestor.Lista

class ViewModel : ViewModel() {
    var listCars = Lista<Car>()

}