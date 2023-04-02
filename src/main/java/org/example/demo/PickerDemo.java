package org.example.demo;

import org.example.models.Order;
import org.example.models.Store;
import org.example.utils.OrderScheduler;

import java.util.List;

import static org.example.utils.JSON_FileParser.parseOrdersFile;
import static org.example.utils.JSON_FileParser.parseStoreFile;

public class PickerDemo {
    public static void main(String[] args) {
        List<Order> orders = parseOrdersFile("C:\\Users\\Admin\\Desktop\\Nowy folder\\self-test-data\\advanced-optimize-order-value\\orders.json");
        Store store = parseStoreFile("C:\\Users\\Admin\\Desktop\\Nowy folder\\self-test-data\\advanced-optimize-order-value\\store.json");
        OrderScheduler.schedulePicking(orders, store);
    }
}
