package ru.itis.utils.navigation;

public class UiNavigatorHolder {
    private static UiNavigator navigator;

    public static UiNavigator getUiNavigator() {
        if (navigator == null) {
            navigator = new UiNavigator();
        }
        return navigator;
    }
}
