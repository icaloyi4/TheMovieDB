package com.example.themoviedb.utils

import android.app.Application
import android.content.Context
import com.example.themoviedb.api.APIComponent
import com.example.themoviedb.api.APIModule
import com.example.themoviedb.api.DaggerAPIComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    companion object {
        var API_KEY = "fd4694b8b546e71f7351fc3d78ec3f61"
        var YT_API_KEY = "AIzaSyBCwzkprEPy02Kc6eBs8aloqoRTYJ5smks"
        var ctx: Context? = null
        lateinit var apiComponent: APIComponent
    }

    override fun onCreate() {
        super.onCreate()
        /*startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule))
        }*/
        ctx = applicationContext
        apiComponent = initDaggerComponent()

    }

    fun getMyComponent(): APIComponent {
        return apiComponent
    }

    fun initDaggerComponent(): APIComponent {
        apiComponent = DaggerAPIComponent
            .builder()
            .aPIModule(APIModule("https://api.themoviedb.org/3/"))
            .build()
        return apiComponent

    }
}