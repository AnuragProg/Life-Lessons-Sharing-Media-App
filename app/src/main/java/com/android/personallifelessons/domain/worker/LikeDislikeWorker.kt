package com.android.personallifelessons.domain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.repository.PllRepository
import com.android.personallifelessons.domain.room.LikedDislikedDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LikeDislikeWorker(
    context: Context, workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent{

    val likedDislikedDao : LikedDislikedDao by inject()
    val pllRepo : PllRepository by inject()

    override suspend fun doWork(): Result {

        val def1 = CoroutineScope(Dispatchers.IO).async{
            val likedPlls = likedDislikedDao.getLikedDislikedPlls().first().filter{it.liked}
            pllRepo.likePlls(likedPlls.map{it.pllId})
        }
        val def2 = CoroutineScope(Dispatchers.IO).async{
            val dislikedPlls = likedDislikedDao.getLikedDislikedPlls().first().filter{!it.liked}
            pllRepo.dislikePlls(dislikedPlls.map{it.pllId})
        }

        val results = awaitAll(def1, def2)
        results.forEach{
            when(it){
                is Outcome.Error -> return Result.retry()
                else -> {}
            }
        }
        likedDislikedDao.clearLikedDislikedPlls()
        return Result.success()
    }
}