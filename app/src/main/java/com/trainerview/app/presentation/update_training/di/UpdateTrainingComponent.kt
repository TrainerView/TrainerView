package com.trainerview.app.presentation.update_training.di

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
import com.trainerview.app.presentation.update_group.UpdateGroupViewModel
import com.trainerview.app.presentation.update_training.UpdateTrainingViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(
    dependencies = [AppComponent::class],
    modules = [UpdateTrainingModule::class]
)
interface UpdateTrainingComponent : ScreenComponent

@Module
interface UpdateTrainingModule {

    @IntoMap
    @ViewModelKey(UpdateTrainingViewModel::class)
    @Binds
    fun bindVM(impl: UpdateTrainingViewModel): ViewModel

    @Binds
    fun bindTrainingRepository(repository: TrainingRepositoryImpl): TrainingRepository
}