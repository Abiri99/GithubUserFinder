package com.example.core_android.presentation

import io.michaelrocks.paranoid.Obfuscate

/**
 * These are emoji codes which are used in the UI.
 * */
@Obfuscate
object Emoji {

    val eye: String =
        String(Character.toChars(0x1F441)) + String(Character.toChars(0xFE0F)) + String(
            Character.toChars(0x200D)
        ) + String(Character.toChars(0x200D)) + String(Character.toChars(0xFE0F))

    val blackCat: String =
        String(Character.toChars(0x1F408)) + String(Character.toChars(0x200D)) + String(
            Character.toChars(0x2B1B)
        )

    val manFacePalming: String =
        String(Character.toChars(0x1F926)) + String(Character.toChars(0x200D)) + String(
            Character.toChars(
                0x2642
            )
        ) + String(Character.toChars(0xFE0F))

    val womanFacePalming: String =
        String(Character.toChars(0x1F926)) + String(Character.toChars(0x200D)) + String(
            Character.toChars(
                0x2640
            )
        ) + String(Character.toChars(0xFE0F))

    val refresh: String = String(Character.toChars(0x1F504))
}
