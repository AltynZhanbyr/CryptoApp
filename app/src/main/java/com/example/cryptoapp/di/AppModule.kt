package com.example.cryptoapp.di

import com.example.cryptoapp.common.Constants
import com.example.cryptoapp.data.remote.CoinPaprikaApi
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.repository.CoinRepository
import com.example.cryptoapp.domain.use_case.get_coin.GetCoinUseCase
import com.example.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoinRepository(api:CoinPaprikaApi):CoinRepository{
        return CoinRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideCoinPaprikaApi(gson:Converter.Factory):CoinPaprikaApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gson)
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): Converter.Factory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideGetCoinUseCase(repository: CoinRepository):GetCoinUseCase{
        return GetCoinUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetCoinsUseCase(repository: CoinRepository): GetCoinsUseCase {
        return GetCoinsUseCase(repository)
    }
}