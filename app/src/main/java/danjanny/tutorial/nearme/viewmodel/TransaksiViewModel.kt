package danjanny.tutorial.nearme.viewmodel

import androidx.lifecycle.*
import danjanny.tutorial.nearme.MyApplication
import danjanny.tutorial.nearme.api.Resource
import danjanny.tutorial.nearme.repo.TransaksiRepository
import kotlinx.coroutines.Dispatchers

class TransaksiViewModel(val app: MyApplication) : AndroidViewModel(app) {

    /**
     * Get Transaksi Response from Repo API
     */
    fun getTransaksi() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = TransaksiRepository(app).getTransaksiResponse() ))
        } catch(exception: Exception) {
            emit(Resource.error(data= null, message = exception.message ?: "error occured" ))
        }
    }

















//    fun getTransaksi() : LiveData<TransaksiResponse> {
//        var transaksiResponse = MutableLiveData<TransaksiResponse>()
//        viewModelScope.launch(Dispatchers.IO) {
//
//            val transaksiRespFromApi = TransaksiRepository(app).getTransaksiResponse()
//            transaksiResponse.postValue(transaksiRespFromApi)
//        }
//        return transaksiResponse
//    }


}