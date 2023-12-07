package com.ispace.mycar.fragments.entry

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ispace.mycar.R
import com.ispace.mycar.databinding.FragmentCarEntryBinding
import com.ispace.mycar.model.CarData
import com.ispace.mycar.viewmodel.MyCarViewModel
import java.util.Calendar

class CarEntryFragment : Fragment() {

    private lateinit var carEntryBinding: FragmentCarEntryBinding
    private lateinit var carEntryViewModel: MyCarViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //val entryView = inflater.inflate(R.layout.fragment_car_entry, container, false)
        carEntryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_car_entry, container, false)


        //car make dropdown menu
        val carMakeArray: Array<String> =
            resources.getStringArray(R.array.car_make_array).sortedArray()
        val carmakeAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, carMakeArray)
        val makeTv = carEntryBinding.carMake
        makeTv.setAdapter(carmakeAdapter)

        //car model dropdown menu
        val carModelArray: Array<String> =
            resources.getStringArray(R.array.car_model_array).sortedArray()
        val modelAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, carModelArray)
        val modelTv = carEntryBinding.carModel
        modelTv.setAdapter(modelAdapter)

        //Adding the date-picker and setting up the date edit text with a clickListener
        carEntryBinding.apply {
            datePurchased.setOnClickListener {
                val cal = Calendar.getInstance()

                val myYear = cal.get(Calendar.YEAR)
                val myMonth = cal.get(Calendar.MONTH)
                val myDay = cal.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(), { _, year, monthOfYear, dayOfMonth ->
                        val ourDate =
                            (dayOfMonth.toString() + "/" + ((monthOfYear + 1).toString()) + "/" + year.toString())
                        datePurchased.setText(ourDate)
                    }, myYear, myMonth, myDay
                )
                datePickerDialog.show()
            }
        }







        carEntryViewModel = ViewModelProvider(this).get(MyCarViewModel::class.java)

        carEntryBinding.submitButton.setOnClickListener {
            insertIntoDatabase()
            clear()
        }




        return carEntryBinding.root
    }


    //inserting data into the database

    private fun insertIntoDatabase() {

        carEntryBinding.apply {
            val cPlates = numberPlate.text.toString()
            val cMake = carMake.text.toString()
            val cModel = carModel.text.toString()
            val cOwner = carOwner.text.toString()
            val cTare = carTare.text.toString()
            val cWeight = carWeight.text.toString()
            val cDate = datePurchased.text.toString()

            if (inputCheck(cPlates, cMake, cModel, cOwner, cTare, cWeight, cDate)) {
                val newCar = CarData(0, cPlates, cMake, cModel, cOwner, cTare, cWeight, cDate)

                //insert the new car into the database
                carEntryViewModel.addCar(newCar)

                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_LONG)
                    .show()

                val action = CarEntryFragmentDirections.actionCarEntryFragmentToFuelFragment(newCar)
                var bundle = sendUsingBundle(newCar)
                findNavController().navigate(action)


            } else {
                Toast.makeText(
                    requireContext(),
                    "Please fill out all the fields in the form",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }


    private fun inputCheck(
        plates: String,
        make: String,
        model: String,
        owner: String,
        tare: String,
        weight: String,
        date: String

    ): Boolean {
        return !(TextUtils.isEmpty(plates) && TextUtils.isEmpty(make) && TextUtils.isEmpty(model)
                && TextUtils.isEmpty(owner) && TextUtils.isEmpty(tare) && TextUtils.isEmpty(weight)
                && TextUtils.isEmpty(date))
    }

    private fun clear() {
        carEntryBinding.apply {
            numberPlate.text?.clear()
            carMake.text.clear()
            carModel.text.clear()
            carOwner.text?.clear()
            carTare.text?.clear()
            carWeight.text?.clear()
            datePurchased.text?.clear()
        }
    }

    private fun sendPlateAndMake(car: CarData) {
        var intent = Intent(requireContext(), FuelFragment::class.java)
        intent.apply {
            this.putExtra("plate", car.carPlates)
            this.putExtra("model", car.carModel)
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_carEntryFragment_to_fuelFragment)
            }


        }
    }

    private fun sendUsingBundle(car: CarData): Bundle {
        var bundle = Bundle()
        bundle.putString("plate", car.carPlates)
        bundle.putString("model", car.carModel)
        return bundle
    }


}