package com.mahmoudtaskapp.bankingsystem.fragment.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mahmoudtaskapp.bankingsystem.module.Customer
import com.mahmoudtaskapp.bankingsystem.module.Transform
import com.mahmoudtaskapp.bankingsystem.repositrey.Respiratory
import com.mahmoudtaskapp.bankingsystem.roomdatabase.AppDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {



    private val _receiversName = MutableLiveData<List<String>>()
    val receiversName: LiveData<List<String>>  = _receiversName

    val database = AppDatabase.getDatabase(application)
    val infoRepository = Respiratory(database)

    fun getCustomer(id:Int):LiveData<Customer>{
        return infoRepository.getCustomer(id).asLiveData()
    }

    fun updateBalanceCutomer(customer: Customer){
        viewModelScope.launch {
            infoRepository.updateCustomer(customer)
        }
    }






    fun isEntryValid(itemName: String, balance: String): Boolean {
        if (itemName.isBlank() || balance.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewItemEntry(itemNameSender: String, itemNameRecipient: String, amount: String): Transform {
        return Transform(
            senderName = itemNameSender,
            recipientName = itemNameRecipient,
            amount = amount.toInt()
        )
    }


    private fun insertTransform(transform: Transform){
        viewModelScope.launch {
            infoRepository.insertTransform(transform)
        }
    }

    fun addItemTransform(itemNameSender: String, itemNameRecipient: String, amount: String){
        val newItem = getNewItemEntry(itemNameSender,itemNameRecipient,amount)
        insertTransform(newItem)

    }

    val getCustomers = infoRepository.getCustomers.asLiveData()

    fun getReceiver(){
            val customerList = getCustomers.value
            _receiversName.value = customerList!!.map { it.customerName }
            Log.d("log",_receiversName.value.toString()
            )

    }


}