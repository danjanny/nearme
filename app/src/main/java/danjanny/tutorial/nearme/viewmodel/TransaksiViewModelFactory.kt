package danjanny.tutorial.nearme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import danjanny.tutorial.nearme.MyApplication

class TransaksiViewModelFactory(val application: MyApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TransaksiViewModel(application) as T
    }
}