package danjanny.tutorial.nearme.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "transaksi_table")
data class Transaksi(
    @SerializedName("id")
    val id: String,
    @SerializedName("id_tipe_transaksi")
    @ColumnInfo(name = "id_tipe_transaksi")
    val idTipeTransaksi: String,
    @SerializedName("nama_transaksi")
    @ColumnInfo(name = "nama_transaksi")
    val namaTransaksi: String,
    @SerializedName("nominal")
    @ColumnInfo(name = "nominal")
    val nominal: String,
    @SerializedName("tgl_catat")
    @ColumnInfo(name = "tgl_catat")
    val tglCatat: String,
    var isChecked : Boolean = false,
    var adapterPosition: Int? = null
)
{
    @PrimaryKey(autoGenerate = true)
    var roomId: Int? = 0
}