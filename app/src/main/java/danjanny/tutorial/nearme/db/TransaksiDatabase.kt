package danjanny.tutorial.nearme.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import danjanny.tutorial.nearme.data.Transaksi

@Database(entities = arrayOf(Transaksi::class), version = 1, exportSchema = false)
abstract class TransaksiDatabase : RoomDatabase() {

    abstract fun transaksiDao(): TransaksiDao

    companion object {
        @Volatile
        private var INSTANCE: TransaksiDatabase? = null
        fun getDatabase(context: Context): TransaksiDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransaksiDatabase::class.java,
                    "transaksi_db"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}