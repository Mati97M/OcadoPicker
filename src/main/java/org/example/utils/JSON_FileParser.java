package org.example.utils;

import org.example.models.Order;
import org.example.models.Store;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class JSON_FileParser {
    private JSON_FileParser() {}

    public static List<Order> parseOrdersFile(String fileName) {
        String inputData = getStringFromFile(fileName);
        JSONArray array = new JSONArray(inputData);

        List<Order> orders = new LinkedList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            JSONObjectToOrder(orders, jsonObject);
        }
//        ListPresenter.printList(orders);
        return orders;
    }

    public static Store parseStoreFile(String fileName) {
        String inputData = getStringFromFile(fileName);
        JSONObject jsonObject = new JSONObject(inputData);
        Store store = JSONObjectToStore(jsonObject);
//        System.out.println(store);
        return store;
    }

    private static Store JSONObjectToStore(JSONObject jsonObject) {
        Store store = new Store();
        List<String> pickers = jsonObject.getJSONArray("pickers").toList()
                .stream().map(
                        element -> String.valueOf(element))
                .collect(Collectors.toList());
        store.setPickers(pickers);
        store.setPickingStartTime(LocalTime.parse(jsonObject.getString("pickingStartTime")));
        store.setPickingEndTime(LocalTime.parse(jsonObject.getString("pickingEndTime")));
        return store;
    }

    private static String getStringFromFile(String fileName) {
        File file = new File(fileName);
        Scanner scanner = null;
        StringBuilder builder;
        try {
            scanner = new Scanner(file);
            builder = new StringBuilder();

        } catch (FileNotFoundException ex) {
            scanner.close();
            throw new RuntimeException(ex);
        }

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        String inputData = builder.toString();
        return inputData;
    }

    private static void JSONObjectToOrder(List<Order> orders, JSONObject jsonObject) {
        Order order = new Order();
        order.setOrderId(jsonObject.getString("orderId"));
        order.setOrderValue(jsonObject.getBigDecimal("orderValue"));
        order.setPickingTime(Duration.parse(jsonObject.getString("pickingTime")));
        order.setCompleteBy(LocalTime.parse(jsonObject.getString("completeBy")));
        orders.add(order);
    }
}
