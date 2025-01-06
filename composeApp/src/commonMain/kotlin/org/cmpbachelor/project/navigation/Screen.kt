package navigation

sealed class Screen(val route: String) {
    object Nav : Screen("nav_screen")
    object Home : Screen("home_screen")
    object Greeting : Screen("greeting")
    object NFC : Screen("nfc_Screen")
}
