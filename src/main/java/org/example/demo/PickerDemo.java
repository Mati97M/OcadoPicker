package org.example.demo;

import org.example.models.Order;
import org.example.models.Picker;
import org.example.models.Store;
import org.example.utils.ListPresenter;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.utils.JSON_FileParser.parseOrdersFile;
import static org.example.utils.JSON_FileParser.parseStoreFile;

public class PickerDemo {
    public static void main(String[] args) {
        List<Order> orders = parseOrdersFile("C:\\Users\\Admin\\Desktop\\Nowy folder\\self-test-data\\advanced-optimize-order-value\\orders.json");
        Store store = parseStoreFile("C:\\Users\\Admin\\Desktop\\Nowy folder\\self-test-data\\advanced-optimize-order-value\\store.json");
//        OrderScheduler.schedulePicking(orders, store);
        List<Picker> availablePickers = new LinkedList<>();
        List<String> pickersNames = store.getPickers();
        for (String name : pickersNames) {
            Picker picker = new Picker(name);
            availablePickers.add(picker);
        }
        TreeMap <Long, List<Order>> pickingMap =
                (TreeMap<Long, List<Order>>) orders.stream()
                        .collect(Collectors.groupingBy(order -> order.getPickingTime().toMinutes()));
        pickingMap.forEach((key, value) -> Collections.sort(value, Comparator.comparing(Order::getCompleteBy)));
        ListPresenter.presentMap(pickingMap,"List Of Orders", "Duration of picking");

        for (int pickerIndex = 0; pickerIndex < availablePickers.size(); pickerIndex++) {
            if (!pickingMap.isEmpty()) {
                Map.Entry firstEntry = pickingMap.firstEntry();
                long minuteslimit = store.getPickingStartTime().until(store.getPickingEndTime(), ChronoUnit.MINUTES);
                while (availablePickers.get(pickerIndex).getPickingTimeAccrued() <= minuteslimit) {    // tego '=' nie jestem pewien

                        List<Order> ordersOfEntry = (List<Order>) firstEntry.getValue();
                        for (Order order: ordersOfEntry) {
                            if (availablePickers.get(pickerIndex).getPickingTimeAccrued() <= minuteslimit) {
                                availablePickers.get(pickerIndex).setPickingTimeAccrued(availablePickers.get(pickerIndex).getPickingTimeAccrued() + minuteslimit);
                                if (order.getCompleteBy().compareTo(store.getPickingStartTime()
                                        .plusMinutes(availablePickers.get(pickerIndex).getPickingTimeAccrued())) < 0) {
                                    pickerIndex--;
                                }
                                picker.getOrders().add()
                            }

                    }
                }
            } else {
                break;
            }
        }

    }
}
