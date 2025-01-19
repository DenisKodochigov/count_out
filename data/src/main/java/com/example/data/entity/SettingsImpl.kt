package com.example.data.entity

import com.example.domain.entity.Settings

data class SettingsImpl(
    override val speechDescription: Boolean,
    override val addressBle: String,
    override val nameBle: String
): Settings