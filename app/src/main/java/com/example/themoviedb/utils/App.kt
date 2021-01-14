package com.example.themoviedb.utils

import android.app.Application
import android.content.Context
import com.example.themoviedb.api.InjectComponent
import com.example.themoviedb.api.APIModule
import com.example.themoviedb.api.DaggerInjectComponent

class App : Application() {
    companion object {
        var API_KEY = "fd4694b8b546e71f7351fc3d78ec3f61"
        var YT_API_KEY = "AIzaSyBCwzkprEPy02Kc6eBs8aloqoRTYJ5smks"
        var ctx: Context? = null
        lateinit var injectComponent: InjectComponent
    }

    override fun onCreate() {
        super.onCreate()
        /*startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule))
        }*/
        ctx = applicationContext
        injectComponent = initDaggerComponent()

    }

    fun getMyComponent(): InjectComponent {
        return injectComponent
    }

    fun initDaggerComponent(): InjectComponent {
        injectComponent = DaggerInjectComponent
            .builder()
            .aPIModule(APIModule("https://api.themoviedb.org/3/"))
            .build()
        return injectComponent

    }
}