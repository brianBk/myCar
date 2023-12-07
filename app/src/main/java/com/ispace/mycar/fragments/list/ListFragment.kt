package com.ispace.mycar.fragments.list


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ispace.mycar.R
import com.ispace.mycar.databinding.FragmentListBinding
import com.ispace.mycar.viewmodel.MyCarViewModel




class ListFragment : Fragment() {
    private lateinit var listBinding: FragmentListBinding
    private lateinit var listViewModel: MyCarViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        listBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)


        //RecyclerView
        adapter = ListAdapter()
        recyclerView = listBinding.carList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //carViewModel
        listViewModel = ViewModelProvider(this)[MyCarViewModel::class.java]
        listViewModel.readAllCarData.observe(viewLifecycleOwner) { carData ->
            adapter.setData(carData)
        }

        //navigating to the carEntry fragment to add a new carData object
        listBinding.floatingAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_carEntryFragment)
        }

        //listBinding.root.findViewById<BottomNavigationView>(R.id.bottom_nav


        //add menu


        return listBinding.root
    }

    /*fun setupRecyclerView() {
        val divider = DividerItemDecoration(this.context, recyclerView.layoutManager  )
    }*/

}