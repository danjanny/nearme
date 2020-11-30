package danjanny.tutorial.nearme.data

import com.google.gson.annotations.SerializedName

data class TransaksiResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ArrayList<Transaksi>
)
