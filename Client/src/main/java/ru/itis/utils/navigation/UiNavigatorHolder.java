package ru.itis.utils.navigation;

import ru.itis.utils.navigation.UiNavigator;

public class UiNavigatorHolder {
    private static UiNavigator navigator;

    public UiNavigator getUiNavigator() {
        if (navigator == null) {
            navigator = new UiNavigator();
        }
        return navigator;
    }
}
