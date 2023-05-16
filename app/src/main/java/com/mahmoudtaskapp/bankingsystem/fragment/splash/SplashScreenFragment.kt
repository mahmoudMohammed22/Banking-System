package com.mahmoudtaskapp.bankingsystem.fragment.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mahmoudtaskapp.bankingsystem.R


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        Handler().postDelayed({
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToCustomerFragment())
        },2000)
        return view
    }

}