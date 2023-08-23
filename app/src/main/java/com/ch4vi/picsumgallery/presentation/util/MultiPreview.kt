package com.ch4vi.picsumgallery.presentation.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "01. Light Theme",
    group = "Theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    device = Devices.NEXUS_5X
)
@Preview(
    name = "02. Dark Theme",
    group = "Theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    device = Devices.NEXUS_5X
)
annotation class MultiPreview
