package com.ispace.mycar.fragments.entry

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ispace.mycar.R
import com.ispace.mycar.databinding.ActivityMainBinding
import com.ispace.mycar.databinding.FragmentFuelBinding
import com.ispace.mycar.model.CarData
import com.ispace.mycar.model.FuelData
import com.ispace.mycar.viewmodel.MyCarViewModel
import java.math.RoundingMode
import java.util.Calendar

private var gasPrice = 213.35
private var dieselPrice = 202.96
private var total: Double = 0.00

class FuelFragment : Fragment() {

    //val args by navArgs<FuelFragmentArgs>()

    private lateinit var fuelBinding: FragmentFuelBinding
    private lateinit var fuelViewModel: MyCarViewModel

    //private lateinit var mainBinding: ActivityMainBinding

    private var plateList = mutableListOf<String?>()
    private var typeList = mutableListOf<String?>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fuelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fuel, container, false)

       fuelViewModel = ViewModelProvider(this)[MyCarViewModel::class.java]



        //passing the entered car obj to the fuel panel for the fuel details


        //the registered cars


        fuelViewModel.readAllCarData.observe(viewLifecycleOwner, Observer {

            it.forEachIndexed { i, item ->
                plateList.add(i, item.carPlates)
                typeList.add(i, item.carModel + " (${item.carMake})")
            }
        })

        //set up the dropdown for car plates
        val plateAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, plateList)

        fuelBinding.noPlate.setAdapter(plateAdapter)

        /*var plate = arguments?.getBundle("plate")
        var model = arguments?.getBundle("model")
        Log.v("fuel", "Plates: $plate  Model: $model")*/


        //Todo: Research on the setOnItemClickListener, and the rest of the listeners
        fuelBinding.noPlate.setOnItemClickListener { parent, view, position, id ->
            var tempPlate = fuelBinding.noPlate.text.toString()
            var model = fuelViewModel.getModelWithPlate(tempPlate)
            fuelBinding.makeAtv.setText(model)

        }


        fuelBinding.noPlate.setOnDismissListener {
            var tempPlate = fuelBinding.noPlate.text.toString()
            var model = fuelViewModel.getModelWithPlate(tempPlate)
            fuelBinding.makeAtv.setText(model)

        }
        fuelBinding.makeAtv.setOnClickListener {
            var plateNo = fuelBinding.noPlate.text.toString()
            var model = fuelViewModel.getModelWithPlate(plateNo)
            fuelBinding.makeAtv.setText(model)
        }


        return fuelBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



           /* fuelBinding.apply {

                    noPlate.setText(args.currentCar.carPlates)
                    dateAtv.setText(args.currentCar.datePurchased)
                    makeAtv.setText(args.currentCar.carModel)

            }*/


        //set up the dropdown for car type
        val typeArray = resources.getStringArray(R.array.car_model_array).sortedArray()
        val typeAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, typeList)
        val typeTv = fuelBinding.makeAtv
        typeTv.setAdapter(typeAdapter)

        //set up the datepicker
        fuelBinding.apply {
            dateAtv.setOnClickListener {
                val cal = Calendar.getInstance()

                val myYear = cal.get(Calendar.YEAR)
                val myMonth = cal.get(Calendar.MONTH)
                val myDay = cal.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog =
                    DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
                        val fuelDate =
                            (dayOfMonth.toString() + "/" + ((monthOfYear + 1).toString()) + "/" + year.toString())
                        dateAtv.setText(fuelDate)
                    }, myYear, myMonth, myDay)
                datePickerDialog.show()
            }
        }

        //Fuel capacity field
        val capacityArray = resources.getStringArray(R.array.fuel_capacity)
        val capacityAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, capacityArray)
        fuelBinding.capacityAtv.setAdapter(capacityAdapter)

        //calculate the prices based on quantity and fuelType
        var quantity: Double
        //var total = gasPrice

        fuelBinding.apply {

            petrol.setOnClickListener {

                val enteredQuantity = Integer.parseInt(quantityTv.text.toString()).toDouble()
                val capacityValue: Int = Integer.parseInt(capacityAtv.text.toString())

                //petrol.setOnClickListener
                if (petrol.isChecked) {

                    if (enteredQuantity > 0 && enteredQuantity <= capacityValue) {
                        quantity = enteredQuantity

                        priceAtv.setText(gasPrice.toString())
                        total = quantity * gasPrice
                        total = total.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                        totalAtv.setText(total.toString())

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Quantity exceeds the stated capacity",
                            Toast.LENGTH_LONG
                        ).show()
                        quantityTv.setText("1")
                    }
                }

            }
            diesel.setOnClickListener {

                val enteredQuantity = Integer.parseInt(quantityTv.text.toString()).toDouble()
                val capacityValue: Int = Integer.parseInt(capacityAtv.text.toString())

                if (diesel.isChecked) {

                    if (enteredQuantity > 0 && enteredQuantity <= capacityValue) {
                        quantity = enteredQuantity

                        priceAtv.setText(dieselPrice.toString())
                        total = quantity * dieselPrice
                        total = total.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                        totalAtv.setText(total.toString())

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Quantity exceeds the stated capacity",
                            Toast.LENGTH_LONG
                        ).show()
                        quantityTv.setText(capacityValue.toString())
                    }

                }
            }
        }

        //submit the data once the submit button is clicked

        fuelViewModel = ViewModelProvider(this)[MyCarViewModel::class.java]

        fuelBinding.submitBtn.setOnClickListener {
            insertIntoDb()
            clear()
        }


        //creating a badge
        //mainBinding = ActivityMainBinding.inflate(layoutInflater)
        /*  mainBinding = DataBindingUtil.setContentView(requireContext() as Activity, R.layout.activity_main)
          var count = 1
          fuelViewModel.readAllFuelData.observe(viewLifecycleOwner, Observer {
              count = it.size
          })
          mainBinding.bottomNav.getOrCreateBadge(R.id.fuelFragment).apply {
              number = count
              isVisible = count > 0
          }*/


    }

    private fun insertIntoDb() {
        fuelBinding.apply {

            val fPlates = noPlate.text.toString()
            val fDate = dateAtv.text.toString()
            val cType = makeAtv.text.toString()
            val fQuantity = Integer.parseInt(quantityTv.text.toString()).toDouble()
            var fPrice: Double
            var fType: String = ""
            if (petrol.isChecked) {
                fType = "Petrol"
                fPrice = gasPrice
            } else {
                if (diesel.isChecked) fType = "Diesel"
                fPrice = dieselPrice
            }
            val fTotal = total

            //we check the data before inserting into db
            if (inputCheck(fPlates, fDate, cType, fQuantity, fType, fPrice, fTotal)) {
                val fuelInfo = FuelData(0, fPlates, fDate, cType, fQuantity, fType, fPrice, fTotal)
                fuelViewModel.addFuel(fuelInfo)

                Toast.makeText(requireContext(), "Info Successfully Added", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please fill out all the fields and select the Fuel Type",
                    Toast.LENGTH_LONG
                ).show()
            }


        }

    }

    private fun inputCheck(
        plates: String,
        date: String,
        ctype: String,
        quantity: Double,
        fuelType: String,
        price: Double,
        total: Double

    ): Boolean {

        return !(TextUtils.isEmpty(plates) && TextUtils.isEmpty(date) && TextUtils.isEmpty(ctype)
                && quantity.equals(null) && TextUtils.isEmpty(fuelType) && price.equals(null)
                && total.equals(null))
    }

    private fun clear() {
        fuelBinding.apply {
            noPlate.text.clear()
            dateAtv.text.clear()
            makeAtv.text.clear()
            quantityTv.setText("1")
            priceAtv.text.clear()
            totalAtv.text.clear()
        }
    }


}