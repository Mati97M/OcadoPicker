package org.example.utils;

import java.util.List;

public final class ListPresenter {
    private ListPresenter() {}

    public static <T> String printList(List<T> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T x : list) {
            stringBuilder.append(x + "\n\n");
        }
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }
}
