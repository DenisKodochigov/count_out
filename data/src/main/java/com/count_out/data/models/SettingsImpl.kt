package com.count_out.data.models

import com.count_out.domain.entity.Settings

data class SettingsImpl(
    override val speechDescription: Boolean,
    override val addressBle: String,
    override val nameBle: String
): Settings