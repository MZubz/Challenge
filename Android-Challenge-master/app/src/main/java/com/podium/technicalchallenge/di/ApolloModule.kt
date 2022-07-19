package com.podium.technicalchallenge.di

import com.apollographql.apollo.ApolloClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.podium.technicalchallenge.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Provides
    @Singleton
    fun providesAuthorizationInterceptor() =
        AuthorizationInterceptor()

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: AuthorizationInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun providesApolloClient(okHttpClient: OkHttpClient): ApolloClient =
        ApolloClient.builder()
            .serverUrl("https://podium-fe-challenge-2021.netlify.app/.netlify/functions/graphql/")
            .okHttpClient(okHttpClient)
            .build()
}