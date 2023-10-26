package com.eva.timemanagementapp.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.eva.timemanagementapp.R

private val provider = GoogleFont.Provider(
	providerAuthority = "com.google.android.gms.fonts",
	providerPackage = "com.google.android.gms",
	certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Prompt",true)

val PromptFontFamily = FontFamily(
	Font(googleFont = fontName, fontProvider = provider)
)