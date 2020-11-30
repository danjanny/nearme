package danjanny.tutorial.nearme.repo

import danjanny.tutorial.nearme.MyApplication
import danjanny.tutorial.nearme.api.ApiClient
import danjanny.tutorial.nearme.data.TransaksiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TransaksiRepository(val application: MyApplication) {
    /**
     * Get Transaksi from API
     */
    suspend fun getTransaksiResponse(): TransaksiResponse {
        return withContext(Dispatchers.IO) {
            ApiClient().getService().getTransaksi()
        }
    }
}