package com.count_out.entity.di

import dagger.Component

class ComponentModule {
}


@Component(dependencies = [ComponentDataModule::class])
interface DependentComponent {
    @Component.Factory
    interface Factory {
        fun create(deps: ComponentDataModule): DependentComponent
    }
}