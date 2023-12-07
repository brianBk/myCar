package com.ispace.mycar.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ispace.mycar.R
import com.ispace.mycar.databinding.FragmentCarDetailsBinding
import com.ispace.mycar.viewmodel.MyCarViewModel

class CarDetailsFragment : Fragment() {

    private val args by navArgs<CarDetailsFragmentArgs>()

    private lateinit var detailsBinding: FragmentCarDetailsBinding
    private lateinit var detailsViewModel: MyCarViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_car_details, container, false)

        detailsViewModel = ViewModelProvider(this).get(MyCarViewModel::class.java)

        detailsBinding.apply {
            makeText.text = args.currCar.carMake
            modelText.text = args.currCar.carModel
            platesText.text = args.currCar.carPlates
            ownerText.text = args.currCar.carOwner
            tareText.text = args.currCar.carTare
            weightText.text = args.currCar.carWeight
            dateText.text = args.currCar.datePurchased
        }




        return detailsBinding.root
    }


}