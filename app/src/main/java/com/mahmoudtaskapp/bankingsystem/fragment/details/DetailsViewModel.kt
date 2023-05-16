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



    private var _receiversName = MutableLiveData<List<String>>()
    val receiversName: LiveData<List<String>> get() = _receiversName

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

    private fun getUpdateItemEntry(customerId:Int,customerName: String, accNum: String, balance: String): Customer {
        return Customer(
            id=customerId,
            customerName = customerName,
            accountNum = accNum.toLong(),
            balance = balance.toInt()
        )
    }

    fun updateItem(
        customerId: Int,
        customerName: String,
        accNum: String,
        balance: String
    ) {
        val updatedItem = getUpdateItemEntry(customerId, customerName, accNum, balance)
        updateBalanceCutomer(updatedItem)
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