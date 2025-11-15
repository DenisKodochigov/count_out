package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.TypeBS

abstract class LauncherBS<SELF: LauncherBS< SELF>>: Domain {
    var owner: Domain? = null
    var list: List<Domain> = emptyList()
    var typeBS: TypeBS? = null

    abstract fun list(list: List<Domain>): SELF
    abstract fun type(typeBS: TypeBS?): SELF
    abstract fun init(typeBS: TypeBS?, owner: Domain?, list: List<Domain> = emptyList()): SELF
    abstract fun owner(owner: Domain?): SELF
//    abstract fun execute()
}