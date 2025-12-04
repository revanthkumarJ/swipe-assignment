package com.revanth.swipe.core.models

enum class ThemeConfig(val themeName: String) {
    DARK("dark"),
    LIGHT("light"),
    SYSTEM("system");

    companion object {
        fun fromName(name: String?): ThemeConfig {
            return entries.firstOrNull { it.themeName.equals(name, ignoreCase = true) }
                ?: SYSTEM
        }
    }
}
