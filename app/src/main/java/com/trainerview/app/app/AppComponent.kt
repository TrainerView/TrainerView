package com.trainerview.app.app

import android.app.Application
import com.trainerview.app.base.db.RoomDB
import com.trainerview.app.base.db.RoomDBModule
import com.trainerview.app.base.di.ComponentHolderMode
import com.trainerview.app.base.di.DIComponent
import com.trainerview.app.base.di.DataBasedComponentHolder
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
