package com.podium.technicalchallenge.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.podium.technicalchallenge.DashboardQuery
import com.podium.technicalchallenge.domain.DashboardContentModel
import com.podium.technicalchallenge.domain.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getMovies(): DashboardContentModel? {
        return withContext(Dispatchers.IO) {
            apolloClient
                .query(DashboardQuery())
                .await()
                .data
                ?.toModel()
        }
    }
}