package com.trainerview.app.features.update_training_screen.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.base.di.ScreenComponent
import com.trainerview.app.base.di.ViewModelKey
import com.trainerview.app.features.training_domain.TrainingRepository
import com.trainerview.app.features.training_domain.TrainingRepositoryImpl
import com.trainerview.app.features.update_training_screen.UpdateTrainingViewModel
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