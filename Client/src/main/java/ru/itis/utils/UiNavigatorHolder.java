package ru.itis.utils;

public class UiNavigatorHolder {
    private static UiNavigator navigator;

    public UiNavigator getUiNavigator() {
        if (navigator == null) {
            navigator = new UiNavigator();
        }
        return navigator;
    }
}
