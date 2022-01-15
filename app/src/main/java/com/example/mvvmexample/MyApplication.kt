package com.example.mvvmexample

import android.app.Application
import com.example.mvvmexample.DI.Modules.remoteModule.ApiInterface
import com.example.mvvmexample.Repo.WeatherRepo
import com.example.mvvmexample.helper.BASE_URL
import com.example.mvvmexample.viewModels.MainFragmentViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyApplication : Application() {


    companion object {
        @JvmStatic
        var instance: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) org.koin.core.logger.Level.ERROR else org.koin.core.logger.Level.NONE)
            androidContext(this@MyApplication)
            androidFileProperties()
            modules(
                modules = listOf(
                    configNetworkModule(),
                    configMainFragmentViewModel()
                )
            )
        }

    }


    private fun configNetworkModule(): Module = module {
        single<Converter.Factory>(qualifier = named("gcf")) {
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        }

        single(qualifier = named("interceptor")) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        single(qualifier = named("OkHttpClient")) {
            (OkHttpClient.Builder()
                .addInterceptor((get(qualifier = named("interceptor")) as HttpLoggingInterceptor))
                .build())
        }

        single<Retrofit>(qualifier = named("retrofit")) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(get(qualifier = named("OkHttpClient")))
                .addConverterFactory(get(qualifier = named("gcf")))
                .build()
        }

        single(qualifier = named("apiInterface")) {
            (get(qualifier = named("retrofit")) as Retrofit).create(ApiInterface::class.java)
        }

        single(qualifier = named("weatherRepo")) { WeatherRepo(get(qualifier = named("apiInterface"))) }

    }

    private fun configMainFragmentViewModel(): Module = module {
        viewModel(qualifier = named("mainFragmentViewModel")) {
            MainFragmentViewModel(get(qualifier = named("weatherRepo")))
        }
    }
}