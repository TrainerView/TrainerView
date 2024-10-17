package com.trainerview.app.features.update_group_screen.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.base.di.ScreenComponent
import com.trainerview.app.base.di.ViewModelKey
import com.trainerview.app.features.group_domain.GroupRepository
import com.trainerview.app.features.group_domain.GroupRepositoryImpl
import com.trainerview.app.features.participant_domain.ParticipantRepository
import com.trainerview.app.features.participant_domain.ParticipantRepositoryImpl
import com.trainerview.app.features.update_group_screen.UpdateGroupViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Component(
    dependencies = [AppComponent::class],
    modules = [AddGroupModule::class]
)
interface AddGroupComponent : ScreenComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): AddGroupComponent
    }
}

@Module
interface AddGroupModule {

    @IntoMap
    @ViewModelKey(UpdateGroupViewModel::class)
    @Binds
    fun bindVM(impl: UpdateGroupViewModel): ViewModel

    @Binds
    fun bindGroupRepository(impl: GroupRepositoryImpl): GroupRepository

    @Binds
    fun bindParticipantRepository(impl: ParticipantRepositoryImpl): ParticipantRepository
}
