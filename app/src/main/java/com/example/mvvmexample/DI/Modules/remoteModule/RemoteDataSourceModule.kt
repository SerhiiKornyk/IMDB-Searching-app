package com.example.mvvmexample.DI.Modules.remoteModule

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.mvvmexample.helper.BASE_URL
import okhttp3.HttpUrl
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URL


@Module
class RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}

