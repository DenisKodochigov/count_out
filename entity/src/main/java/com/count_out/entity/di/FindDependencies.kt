package com.count_out.entity.di

import androidx.fragment.app.Fragment



val Fragment.parents: Iterable<HasDependencies>
    get() = Iterable<HasDependencies>(
        iterator = TODO()
    )


inline fun <reified D: Dependencies> Fragment.findDependencies(): D {
    val dependenciesClass = D::class.java
    return parents
        .mapNotNull { it.depsMap[dependenciesClass] }
        .firstOrNull() as D? ?: throw IllegalStateException("No $dependenciesClass in $parents")
}