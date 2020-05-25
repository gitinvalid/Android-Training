package com.example.m09.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.m09.AdviceRepo
import com.example.m09.ApiService
import com.example.m09.MyViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
class MainModule {
    @Provides
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideAdviceRepo(apiService: ApiService) = AdviceRepo(apiService)

    @IntoMap
    @ViewModelKey(MyViewModel::class)
    @Provides
    fun providesViewModel(adviceRepo: AdviceRepo): ViewModel = MyViewModel(adviceRepo)

    @Provides
    @Singleton
    fun providersViewModelFactory(viewModelProviderMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModelProviderMap[modelClass]?.get() as T
                    ?: throw IllegalStateException("Unsupported ViewModel")
            }
        }
}