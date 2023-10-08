package com.david.f1stats.data.repository

import com.david.f1stats.data.mapper.TeamDetailMapper
import com.david.f1stats.data.source.network.TeamService
import com.david.f1stats.domain.model.TeamDetail
import javax.inject.Inject

class TeamRepository @Inject constructor(
    private val api: TeamService,
    private val teamDetailMapper: TeamDetailMapper,
){
    suspend fun getTeamDetail(id: Int): TeamDetail {
        val response = api.getTeamDetail(id)
        return teamDetailMapper.fromMap(response)
    }
}