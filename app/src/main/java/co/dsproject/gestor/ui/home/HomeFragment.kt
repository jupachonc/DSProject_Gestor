package co.dsproject.gestor.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.dsproject.gestor.R
import co.dsproject.gestor.adapters.ParentAdapter
import co.dsproject.gestor.datafactory.ParentDataFactory
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer { it ->
            //textView.text = it
        })
        initRecycler()
        return root
    }

    private fun initRecycler(){

        recyclerView = root.rv_parent
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL, false)
            adapter = ParentAdapter(ParentDataFactory
                    .getParents(40))
        }

    }
}
