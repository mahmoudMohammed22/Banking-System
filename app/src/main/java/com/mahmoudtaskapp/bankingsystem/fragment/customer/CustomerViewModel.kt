package com.mahmoudtaskapp.bankingsystem.fragment.customer
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.mahmoudtaskapp.bankingsystem.repositrey.Respiratory
import com.mahmoudtaskapp.bankingsystem.roomdatabase.AppDatabase

class CustomerViewModel(application: Application):AndroidViewModel(application) {


    val database = AppDatabase.getDatabase(application)
    val infoRepository = Respiratory(database)


    val displayCustomer = infoRepository.getCustomers.asLiveData()

}
