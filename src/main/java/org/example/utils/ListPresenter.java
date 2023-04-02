package org.example.utils;

import java.util.List;
import java.util.Map;

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

    public static <K, V> String presentMap(Map<K, V> map) {
        StringBuilder stringBuilder = new StringBuilder();
        map.forEach((k, v) -> stringBuilder.append(k + "\t" +  v + "\n"));
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }

    public static <K, V> String presentMap(Map<K, V> map,
                                           String nameForValues,
                                           String nameForKeys
    ) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!nameForKeys.equals("")) {
            if (!nameForValues.equals("")) {
                map.forEach((k, v) -> stringBuilder.append(nameForKeys + ":" + k + "\t" +
                        nameForValues + ":" + v + "\n"));
            } else {
                map.forEach((k, v) -> stringBuilder.append(nameForKeys + ":" + k + "\t" + v + "\n"));
            }
        } else {
            if (!nameForValues.equals("")) {
                map.forEach((k, v) -> stringBuilder.append(k + "\t" + nameForValues + ":" + v + "\n"));
            } else {
                return presentMap(map);
            }
        }
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }
}
