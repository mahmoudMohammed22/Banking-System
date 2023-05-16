package com.mahmoudtaskapp.bankingsystem.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mahmoudtaskapp.bankingsystem.module.Customer
import com.mahmoudtaskapp.bankingsystem.module.Transform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Customer::class, Transform::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun transformDao(): TransformDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "customer_database"
                ).addCallback(AppDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val context: Context) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.let { database ->
                    val customerDao = database.customerDao()
                    customerDao.insertCustomer(loadCustomers())

                }
            }
        }
    }




}
fun loadCustomers():List<Customer>{
    return listOf<Customer>(
        Customer(customerName = "Mahmoud mohammed", accountNum = 123654221 , balance = 120000),
        Customer(customerName = "Mahmoud sayed", accountNum = 123334457 , balance = 150000),
        Customer(customerName = "Abdo mohammed", accountNum = 442221554 , balance = 80000),
        Customer(customerName = "Ragab mohammed", accountNum = 552331442 , balance = 200000),
        Customer(customerName = "Youssef samir", accountNum = 772661552 , balance = 30000),
        Customer(customerName = "Mohammed youssef", accountNum = 321654312 , balance = 90000),
        Customer(customerName = "Mostafa hamed", accountNum = 675422333 , balance = 70000),
        Customer(customerName = "Sayed azz", accountNum = 987123432 , balance = 17000),
        Customer(customerName = "Smouel samir", accountNum = 333512644 , balance = 1200000),
        Customer(customerName = "Zied mostafa", accountNum = 443112332 , balance = 300000)

    )
}