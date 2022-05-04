package com.example.githubuserfinder.core.presentation

object Emoji {

    val search: String = String(Character.toChars(0x1F50D))

    val eye: String =
        String(Character.toChars(0x1F441)) + String(Character.toChars(0xFE0F)) + String(
            Character.toChars(0x200D)
        ) + String(Character.toChars(0x200D)) + String(Character.toChars(0xFE0F))

    val blackCat: String =
        String(Character.toChars(0x1F408)) + String(Character.toChars(0x200D)) + String(
            Character.toChars(0x2B1B)
        )
}
