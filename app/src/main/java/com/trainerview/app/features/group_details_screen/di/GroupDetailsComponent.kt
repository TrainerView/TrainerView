package com.trainerview.app.features.group_details_screen.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.base.di.ScreenComponent
import com.trainerview.app.base.di.ViewModelKey
import com.trainerview.app.features.group_domain.GroupRepository
import com.trainerview.app.features.group_domain.GroupRepositoryImpl
import com.trainerview.app.features.participant_domain.ParticipantRepository
import com.trainerview.app.features.participant_domain.ParticipantRepositoryImpl
import com.trainerview.app.features.training_domain.TrainingRepository
import com.trainerview.app.features.training_domain.TrainingRepositoryImpl
import com.trainerview.app.features.group_details_screen.GroupDetailsViewModel
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
