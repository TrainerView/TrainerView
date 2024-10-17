package com.trainerview.app.features.update_participant_screen.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.base.di.ScreenComponent
import com.trainerview.app.base.di.ViewModelKey
import com.trainerview.app.features.participant_domain.ParticipantRepository
import com.trainerview.app.features.participant_domain.ParticipantRepositoryImpl
import com.trainerview.app.features.update_participant_screen.UpdateParticipantViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(
    dependencies = [AppComponent::class],
    modules = [AddGroupModule::class]
)
interface UpdateParticipantComponent : ScreenComponent

@Module
interface AddGroupModule {

    @IntoMap
    @ViewModelKey(UpdateParticipantViewModel::class)
    @Binds
    fun bindVM(impl: UpdateParticipantViewModel): ViewModel

    @Binds
    fun bindRepository(impl: ParticipantRepositoryImpl): ParticipantRepository
}
