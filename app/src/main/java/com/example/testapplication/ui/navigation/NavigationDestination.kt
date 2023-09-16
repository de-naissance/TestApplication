package com.example.testapplication.ui.navigation

/**
 * Интерфейс для описания навигационных пунктов назначения приложения
 */
interface NavigationDestination {
    /**
     * A unique name for defining the path to a composite element
     */
    val route: String

    /**
     * Строковый идентификатор ресурса, содержащий заголовок, который будет отображаться на экране.
     */
    val titleRes: Int
}