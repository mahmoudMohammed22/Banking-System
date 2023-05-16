package com.mahmoudtaskapp.bankingsystem.fragment.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mahmoudtaskapp.bankingsystem.R
import com.mahmoudtaskapp.bankingsystem.databinding.FragmentDetailsBinding
import com.mahmoudtaskapp.bankingsystem.module.Customer
import com.mahmoudtaskapp.bankingsystem.roomdatabase.AppDatabase
import com.mahmoudtaskapp.bankingsystem.roomdatabase.loadCustomers


class DetailsFragment : Fragment() {

    private val viewModel : DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    private val navigationArgs :DetailsFragmentArgs by navArgs()

    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var senderName :String? = null
    private var recipientName :String? = null
    private var amount :Int? = null

    lateinit var customers : List<Customer>
    private  var receiver: Customer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.itemId




        viewModel.getCustomer(id).observe(this.viewLifecycleOwner){ displayCustomer->
            bind(displayCustomer)
        }

        viewModel.getCustomers.observe(this.viewLifecycleOwner){
           viewModel.getReceiver()
        }

        viewModel.receiversName.observe(viewLifecycleOwner) {
            val receivers = it
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                receivers
            )

            binding.receiverName.setAdapter(adapter)

            binding.receiverName.setOnItemClickListener { _, _, position, _ ->
                val customers = viewModel.getCustomers.value
                receiver = customers!![position]
                Log.d("log","customer : $receiver")
            }
        }



    }

    private fun bind (customer : Customer){
        binding.apply {
            name.text = getString(R.string.name,customer.customerName)
            accNum.text = getString(R.string.accNum,customer.accountNum)
            balance.text = getString(R.string.balance,customer.balance)

            binding.transform.setOnClickListener {
                if (updateCustomerRecipient(customer)){
                    if (updateCustomerSender(customer)){
                        addTransfromItem()

                    }
                }
            }

        }

    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.receiverName.text.toString(),
            binding.balanceName.text.toString(),
        )
    }


    private fun updateCustomerSender(customer: Customer) : Boolean{
        return if (customer.balance >= binding.balanceName.text.toString().toInt()){
            val newItem = customer.copy(balance = customer.balance - binding.balanceName.text.toString().toInt())
            viewModel.updateBalanceCutomer(newItem)
            senderName = customer.customerName
            amount = binding.balanceName.text.toString().toInt()
            true

        }else{
            Toast.makeText(requireContext(),"Amount is bigger than balance",Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun updateCustomerRecipient(customer: Customer) : Boolean{
        if (isEntryValid()){
                return if (receiver?.customerName == customer.customerName ) {
                    Toast.makeText(requireContext(), "Same Customer", Toast.LENGTH_SHORT).show()

                    false
                }else if (receiver != null){
                    val newItem = receiver!!.copy(
                        balance = receiver!!.balance + binding.balanceName.text.toString().toInt())
                    viewModel.updateBalanceCutomer(newItem)
                    recipientName = receiver!!.customerName
                    true
                }else {
                    Toast.makeText(requireContext(), "Customer Not Found", Toast.LENGTH_SHORT).show()
                    false
                }

        }
        Toast.makeText(requireContext(), "Field Required!" , Toast.LENGTH_SHORT).show()
        return false
    }

    private fun addTransfromItem() {
        viewModel.addItemTransform(
            senderName.toString(),recipientName.toString(), amount.toString()
        )
        Toast.makeText(requireContext(), "Done Transform", Toast.LENGTH_SHORT).show()
        findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToCustomerFragment())

    }






}