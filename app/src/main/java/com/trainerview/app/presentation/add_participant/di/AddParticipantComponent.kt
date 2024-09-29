package com.trainerview.app.presentation.add_participant.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.di.ScreenComponent
import com.trainerview.app.di.ViewModelKey
import com.trainerview.app.domain.ParticipantRepository
import com.trainerview.app.domain.ParticipantRepositoryImpl
import com.trainerview.app.presentation.add_participant.AddParticipantViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(
    dependencies = [AppComponent::class],
    modules = [AddGroupModule::class]
)
interface AddParticipantComponent : ScreenComponent

@Module
interface AddGroupModule {

    @IntoMap
    @ViewModelKey(AddParticipantViewModel::class)
    @Binds
    fun bindVM(impl: AddParticipantViewModel): ViewModel

    @Binds
    fun bindRepository(impl: ParticipantRepositoryImpl): ParticipantRepository
}