package danjanny.tutorial.nearme

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

class MyApplication : MultiDexApplication() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        instance = this
    }
}