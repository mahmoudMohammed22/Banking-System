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


    // list from name customers
    private lateinit var customerName : List<String>


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


        // use that to get name from customer and remove name clicked about it
         viewModel.getReceiverWithoutThisId(id)


        viewModel.getCustomer(id).observe(this.viewLifecycleOwner){ displayCustomer->
            bind(displayCustomer)
        }


        viewModel.receiversName.observe(viewLifecycleOwner) {
            val receivers = it
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                receivers
            )

            binding.receiverName.setAdapter(adapter)
            customerName = it

        }



    }

    // to add item in details and make transform
    private fun bind (customer : Customer){
        binding.apply {
            name.text = getString(R.string.name,customer.customerName)
            accNum.text = getString(R.string.accNum,customer.accountNum)
            balance.text = getString(R.string.balance,customer.balance)

            binding.transform.setOnClickListener {
                if (updateCustomerRecipient(customerName,customer)){
                        addTransfromItem()

                }
            }
        }
    }

    // check if edit text is not empty
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.receiverName.text.toString(),
            binding.balanceName.text.toString(),
        )
    }


    // update sender customer
    private fun updateCustomerSender(customer: Customer) : Boolean{
        return if (customer.balance >= binding.balanceName.text.toString().toLong()){
            val newItem = customer.copy(balance = customer.balance - binding.balanceName.text.toString().toInt())
            viewModel.updateBalanceCutomer(newItem)
            senderName = customer.customerName
            amount = binding.balanceName.text.toString().toInt()
            true

        }else{
            false
        }
    }

    // update receiver customer
    private fun updateCustomerRecipient(customerName : List<String>,customer: Customer) : Boolean{
        if (isEntryValid()){
                if (binding.receiverName.text.toString() in customerName){
                    if(updateCustomerSender(customer)) {
                        viewModel.getReceiverData(binding.receiverName.text.toString())
                        viewModel.customer.observe(this.viewLifecycleOwner) {
                            val newItem = it.balance + binding.balanceName.text.toString().toLong()
                            viewModel.updateBalanceCutomer(
                                Customer(it.id, it.customerName, newItem, it.accountNum
                                )
                            )
                            Log.d("log", "customerNmae: $recipientName")
                            Log.d("log", "customerNS: $it")
                        }
                        recipientName = binding.receiverName.text.toString()
                        return true
                    }else{
                        Toast.makeText(requireContext(),"Amount is bigger than balance",Toast.LENGTH_SHORT).show()
                        return false
                    }

                }else{
                    Toast.makeText(requireContext(), "Not Found" , Toast.LENGTH_SHORT).show()
                    return false

                }
        }
        Toast.makeText(requireContext(), "Field Required!" , Toast.LENGTH_SHORT).show()
        return false
    }

    // insert transform operation
    private fun addTransfromItem() {
        viewModel.addItemTransform(
            senderName.toString(),recipientName.toString(), amount.toString()
        )
        Toast.makeText(requireContext(), "Done Transform", Toast.LENGTH_SHORT).show()
        findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToCustomerFragment())

    }







}