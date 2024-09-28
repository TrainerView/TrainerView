package com.trainerview.app.app

import android.app.Application
import com.trainerview.app.data.db.RoomDB
import com.trainerview.app.data.db.RoomDBModule
import com.trainerview.app.di.ComponentHolderMode
import com.trainerview.app.di.DIComponent
import com.trainerview.app.di.DataBasedComponentHolder
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        RoomDBModule::class
    ]
)
interface AppComponent : DIComponent {

    val room: RoomDB

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun app(app: Application): Builder
    }
}

object AppComponentHolder : DataBasedComponentHolder<AppComponent, Application>() {
    override val mode: ComponentHolderMode = ComponentHolderMode.GLOBAL_SINGLETON
    override fun buildComponent(data: Application): AppComponent = DaggerAppComponent.builder().app(data).build()
}