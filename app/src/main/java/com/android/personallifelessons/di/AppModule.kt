package com.android.personallifelessons.di

import com.android.personallifelessons.data.api.CategoryApi
import com.android.personallifelessons.data.api.CommentApi
import com.android.personallifelessons.data.api.DashboardApi
import com.android.personallifelessons.data.api.UserApi
import com.android.personallifelessons.data.api.impl.CategoryApiImpl
import com.android.personallifelessons.data.api.impl.CommentApiImpl
import com.android.personallifelessons.data.api.impl.DashboardApiImpl
import com.android.personallifelessons.data.api.impl.UserApiImpl
import com.android.personallifelessons.data.repository.*
import com.android.personallifelessons.domain.repository.*
import com.android.personallifelessons.domain.room.SavedItemDatabase
import com.android.personallifelessons.presenter.category.CategoryViewModel
import com.android.personallifelessons.presenter.comments.CommentViewModel
import com.android.personallifelessons.presenter.dashboard.DashboardViewModel
import com.android.personallifelessons.presenter.post.PostViewModel
import com.android.personallifelessons.presenter.profile.ProfileViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Firebase.database.reference }

    single<CategoryApi>{ CategoryApiImpl(get()) }
    single<CommentApi>{ CommentApiImpl(get()) }
    single<DashboardApi>{ DashboardApiImpl(get()) }
    single<UserApi>{ UserApiImpl(get()) }

    single { SavedItemDatabase.getInstance(get()).savedItemDao }
}

val repositoryModule = module{
    single<CategoryRepository>{ CategoryRepositoryImpl(get())}
    single<CommentRepository>{ CommentRepositoryImpl(get(), get()) }
    single<DashBoardRepository>{ DashboardRepositoryImpl(get()) }
    single<ProfileRepository>{ ProfileRepositoryImpl(get()) }
    single<PostRepository>{ PostRepositoryImpl(get()) }
}

val viewModelModule = module{
    viewModel { params ->  DashboardViewModel(get(), get(), params.get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { params ->  CommentViewModel(get(), params.get())}
    viewModel { params -> PostViewModel(get(), params[0], params[1]) }
    viewModel { params -> ProfileViewModel(get(), params.get()) }
}