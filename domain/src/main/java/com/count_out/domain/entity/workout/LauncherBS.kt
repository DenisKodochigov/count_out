package com.count_out.domain.entity.workout

import com.count_out.domain.entity.enums.TypeBS

abstract class LauncherBS<SELF: LauncherBS< SELF>>: Domain {
    var type: Domain? = null
    var idOwner: Long = 0
    var list: List<Domain> = emptyList()
    var typeBS: TypeBS? = null
    abstract fun init(typeBS: TypeBS?, type: Domain?, list: List<Domain> = emptyList(), idOwner: Long = 0): SELF
//    abstract fun execute()
}