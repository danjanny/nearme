package danjanny.tutorial.nearme.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import danjanny.tutorial.nearme.data.Transaksi

@Dao
interface TransaksiDao {
    @Query("SELECT * FROM transaksi_table")
    suspend fun getAllTransaksi(): List<Transaksi>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaksi(transaksi: List<Transaksi>)
}