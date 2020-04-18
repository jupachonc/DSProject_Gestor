package co.dsproject.gestor.ui.cars

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import co.dsproject.gestor.R

class CarsFragment : Fragment() {

    companion object {
        fun newInstance() = CarsFragment()
    }

    private lateinit var viewModel: CarsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cars_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CarsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
