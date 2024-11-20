package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.remote.AccompanyApiService
import com.kakaotech.team25.domain.model.AccompanyInfo
import com.kakaotech.team25.domain.repository.AccompanyRepository
import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import javax.inject.Inject

class DefaultAccompanyRepository @Inject constructor(
    private val accompanyApiService: AccompanyApiService,
) : AccompanyRepository {
    override suspend fun getAccompanyInfo(reservationId: String): Result<List<AccompanyInfo>> {
        val result = accompanyApiService.getAccompanyInfo(reservationId)
        return when (result) {
            is Success -> Result.success(result.body?.data?.asDomain() ?: emptyList())
            is Failure -> Result.failure(Exception("message: ${result.error}"))
            is NetworkError -> Result.failure(Exception("message: ${result.exception.message}"))
            is Unexpected -> Result.failure(Exception("message: ${result.t?.message ?: "Unexpected Error"}"))
        }
    }
}
