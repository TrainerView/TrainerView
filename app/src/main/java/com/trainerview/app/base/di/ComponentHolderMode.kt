package com.trainerview.app.base.di

enum class ComponentHolderMode {
    /**
     * Всегда новый инстанс компонента
     */
    ALWAYS_CREATE_NEW,

    /**
     * Глобальный синглтон, который инициализируется один раз и навсегда
     */
    GLOBAL_SINGLETON
}
