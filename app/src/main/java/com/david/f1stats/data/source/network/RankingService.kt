package com.david.f1stats.data.source.network

import com.david.f1stats.data.model.rankingDriver.RankingDriverData
import com.david.f1stats.data.model.rankingTeam.RankingTeamData
import com.david.f1stats.utils.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class RankingService @Inject constructor(private val api:APIClient, preferencesHelper: PreferencesHelper){
    private val selectedSeason = preferencesHelper.getSelectedSeason() ?: Calendar.getInstance().get(Calendar.YEAR).toString()

    suspend fun getDriversRanking():List<RankingDriverData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCurrentRankingDrivers(selectedSeason)
            response.body()?.response ?: emptyList()
        }
    }

    suspend fun getTeamsRanking():List<RankingTeamData>{
        return withContext(Dispatchers.IO) {
            val response = api.getCurrentRankingTeams(selectedSeason)
            response.body()?.response ?: emptyList()
        }
    }
}