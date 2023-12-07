package com.ispace.mycar.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ispace.mycar.R
import com.ispace.mycar.databinding.FragmentHomeBinding
import com.ispace.mycar.viewmodel.MyCarViewModel


class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeViewModel: MyCarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //our view_model
        homeViewModel = ViewModelProvider(this).get(MyCarViewModel::class.java)

        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeBinding.addCarFab.setOnClickListener {
            goToAd()
        }

        homeBinding.apply {
            adImage.isClickable
            adImage.setOnClickListener{
                goToAd()
            }
        }


    }

    private fun goToAd() {
        val adWeb = Intent(Intent.ACTION_VIEW)
        adWeb.data = Uri.parse("https://www.rolls-roycemotorcars.com")
        startActivity(adWeb)
    }

    //intent.data = Uri.parse("https://www.google.com")



}