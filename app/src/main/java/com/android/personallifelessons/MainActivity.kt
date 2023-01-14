package com.android.personallifelessons

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.worker.LikeDislikeWorker
import com.android.personallifelessons.presenter.navgraph.Index
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity(), KoinComponent {

    private val workManager : WorkManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Index(
                moveToAuthActivity = {
                    val startAuthActivityIntent = Intent(this, AuthActivity::class.java)
                    startActivity(startAuthActivityIntent)
                    finish()
                }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<LikeDislikeWorker>()
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.SECONDS)
            .build()
        workManager.enqueue(workRequest)
    }
}
