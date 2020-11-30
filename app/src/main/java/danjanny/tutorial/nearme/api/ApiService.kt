package danjanny.tutorial.nearme.api

import danjanny.tutorial.nearme.data.TransaksiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("get_transaksi")
    suspend fun getTransaksi(): TransaksiResponse
}