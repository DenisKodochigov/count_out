package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.TypeBS

abstract class LauncherBS<SELF: LauncherBS< SELF>>: Domain {
    var owner: Domain? = null
    var elements: List<Domain> = emptyList()
    var typeBS: TypeBS? = null
    abstract fun element(element: List<Domain>): SELF
    abstract fun type(typeBS: TypeBS?): SELF
    abstract fun owner(owner: Domain?): SELF
//    abstract fun execute()
}