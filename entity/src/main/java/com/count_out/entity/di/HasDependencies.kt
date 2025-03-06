package com.count_out.entity.di

typealias DepsMap = Map<Class<out Dependencies>, Dependencies>

interface HasDependencies {
    val depsMap: DepsMap
}