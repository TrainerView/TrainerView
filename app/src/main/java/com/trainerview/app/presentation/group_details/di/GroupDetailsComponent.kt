package com.trainerview.app.presentation.group_details.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.di.ScreenComponent
import com.trainerview.app.di.ViewModelKey
import com.trainerview.app.domain.GroupRepository
import com.trainerview.app.domain.GroupRepositoryImpl
import com.trainerview.app.domain.ParticipantRepository
import com.trainerview.app.domain.ParticipantRepositoryImpl
import com.trainerview.app.domain.TrainingRepository
import com.trainerview.app.domain.TrainingRepositoryImpl
import com.trainerview.app.presentation.group_details.GroupDetailsViewModel
import com.trainerview.app.presentation.group_list.GroupsListViewModel
import com.trainerview.app.presentation.group_list.di.GroupsListComponent
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(
    dependencies = [AppComponent::class],
    modules = [GroupDetailsModule::class]
)
interface GroupDetailsComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): GroupDetailsComponent
    }
}

@Module
interface GroupDetailsModule {

    @IntoMap
    @ViewModelKey(GroupDetailsViewModel::class)
    @Binds
    fun bindVM(impl: GroupDetailsViewModel): ViewModel

    @Binds
    fun bindGroupRepository(impl: GroupRepositoryImpl): GroupRepository

    @Binds
    fun bindParticipantRepository(impl: ParticipantRepositoryImpl): ParticipantRepository

    @Binds
    fun bindTrainingRepository(impl: TrainingRepositoryImpl): TrainingRepository
}