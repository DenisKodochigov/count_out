package com.count_out.data.models

import com.count_out.domain.entity.SettingRecord
import com.count_out.domain.entity.Settings

data class SettingsImpl(
    override val speechDescription: SettingRecord,
    override val addressBle: SettingRecord,
    override val nameBle: SettingRecord
): Settings