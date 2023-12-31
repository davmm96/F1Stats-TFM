package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.repository.RaceRepository
import com.david.f1stats.domain.model.RaceResult
import javax.inject.Inject

class GetRaceResultUseCase @Inject constructor(
    private val repository: RaceRepository) {
    suspend operator fun invoke(id: Int): Result<List<RaceResult>> = repository.getRaceResult(id)
}
