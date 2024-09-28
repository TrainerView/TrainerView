package com.trainerview.app.presentation.add_group.di

import androidx.lifecycle.ViewModel
import com.trainerview.app.app.AppComponent
import com.trainerview.app.di.ScreenComponent
import com.trainerview.app.di.ViewModelKey
import com.trainerview.app.domain.GroupRepository
import com.trainerview.app.domain.GroupRepositoryImpl
import com.trainerview.app.presentation.add_group.AddGroupViewModel
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
    @ViewModelKey(AddGroupViewModel::class)
    @Binds
    fun bindVM(impl: AddGroupViewModel): ViewModel

    @Binds
    fun bindRepository(impl: GroupRepositoryImpl): GroupRepository
}