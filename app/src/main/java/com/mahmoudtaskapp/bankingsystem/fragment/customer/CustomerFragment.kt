package com.mahmoudtaskapp.bankingsystem.fragment.customer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoudtaskapp.bankingsystem.databinding.FragmentCustomerBinding


class CustomerFragment : Fragment() {

    private val viewModel : CustomerViewModel by lazy {
        ViewModelProvider(this)[CustomerViewModel::class.java]
    }


    private var _binding : FragmentCustomerBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =  CustomerAdapter{
            val action =  CustomerFragmentDirections.actionCustomerFragmentToDetailsFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.customerRecycler.adapter = adapter


        viewModel.displayCustomer.observe(this.viewLifecycleOwner){ dataCustomer->
            dataCustomer.let {
                adapter.submitList(it)
            }

        }



    }





}