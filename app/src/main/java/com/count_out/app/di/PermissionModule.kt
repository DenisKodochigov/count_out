package com.count_out.app.di

import android.content.Context
import com.count_out.app.permission.PermissionApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Module
@InstallIn(ActivityComponent::class)
class PermissionModule {

    @ExperimentalCoroutinesApi
    @Provides
    @ActivityScoped
    fun providePermissionsHelper(@ActivityContext context: Context, ) = PermissionApp(context)
}
//@HiltViewModel
//@ExperimentalCoroutinesApi
//class FileViewModel @Inject constructor(
//    private val class1: Class1,
//    private val class2: Class2,
//    private val class3: Class3,
//    private val permissionsUtils: PermissionsUtils //Here's the one I'm injecting
//)