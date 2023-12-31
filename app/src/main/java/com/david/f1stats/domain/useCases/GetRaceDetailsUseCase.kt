package com.david.f1stats.domain.useCases

import com.david.f1stats.data.model.base.Result
import com.david.f1stats.data.repository.RaceRepository
import com.david.f1stats.domain.model.RaceDetail
import javax.inject.Inject

class GetRaceDetailsUseCase @Inject constructor(
    private val repository: RaceRepository) {
    suspend operator fun invoke(id: Int): Result<List<RaceDetail>> = repository.getRaceDetails(id)
}
