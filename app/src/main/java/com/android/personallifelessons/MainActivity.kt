package com.android.personallifelessons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.work.*
import com.android.personallifelessons.data.dto.request.SignInRequest
import com.android.personallifelessons.data.dto.request.SignUpRequest
import com.android.personallifelessons.domain.repository.UserRepository
import com.android.personallifelessons.domain.worker.LikeDislikeWorker
import com.android.personallifelessons.presenter.navgraph.Index
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity(), KoinComponent {

    val workManager : WorkManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRep : UserRepository by inject()
        setContent {
            Index()
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<LikeDislikeWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.MINUTES)
            .build()
        workManager.enqueue(workRequest)
    }
}
