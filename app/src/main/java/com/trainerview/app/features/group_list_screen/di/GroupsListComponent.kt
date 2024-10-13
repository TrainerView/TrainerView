package com.trainerview.app.features.group_list_screen.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.base.di.ScreenComponent
import com.trainerview.app.base.di.ViewModelKey
import com.trainerview.app.features.group_domain.GroupRepository
import com.trainerview.app.features.group_domain.GroupRepositoryImpl
import com.trainerview.app.features.group_list_screen.GroupsListViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(
    dependencies = [AppComponent::class],
    modules = [GroupsListModule::class]
)
interface GroupsListComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): GroupsListComponent
    }
}

@Module
interface GroupsListModule {

    @IntoMap
    @ViewModelKey(GroupsListViewModel::class)
    @Binds
    fun bindVM(impl: GroupsListViewModel): ViewModel

    @Binds
    fun bindRepository(impl: GroupRepositoryImpl): GroupRepository
}