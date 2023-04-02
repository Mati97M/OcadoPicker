package org.example.utils;

import org.example.models.Order;
import org.example.models.Store;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class OrderScheduler {
    private OrderScheduler() {}

    private static Comparator<Order> pickingTimeComparator = Comparator
            .comparing(Order::getPickingTime)
            .thenComparing(Order::getCompleteBy);

    private static Comparator<Order> pickingTimeComparator2 = Comparator
            .comparing(Order::getPickingTime);

    private static Comparator<Order> pickingTimeComparator3 = Comparator
            .comparing(Order::getCompleteBy);

    private static Comparator<Order> pickingTimeComparator4 = Comparator
            .comparing(Order::getCompleteBy)
            .thenComparing(Order::getPickingTime);

    public static void schedulePicking(List<Order> orders, Store store) {
        Collections.sort(orders, pickingTimeComparator);
        ListPresenter.printList(orders);
        long minuteslimit = store.getPickingStartTime().until(store.getPickingEndTime(), ChronoUnit.MINUTES);
        long pickingTimeAccruedPerPicker = 0;
        int pickerIndex = 0;
        for (int i = 0; i < orders.size(); i++) {
            String picker;
            if (pickerIndex < store.getPickers().size()) {
                picker = store.getPickers().get(pickerIndex);
                long pickingTime = orders.get(i).getPickingTime().toMinutes();
                if (pickingTimeAccruedPerPicker + pickingTime > minuteslimit ||
                        orders.get(i).getCompleteBy().compareTo(store.getPickingStartTime()
                                .plusMinutes(pickingTimeAccruedPerPicker)
                                .plusMinutes(pickingTime)) < 0) {
                    pickingTimeAccruedPerPicker = 0;
                    minuteslimit = store.getPickingStartTime().until(store.getPickingEndTime(), ChronoUnit.MINUTES);
                    pickerIndex++;
                    i--;
                } else {
//                    minuteslimit -= pickingTime;
                    System.out.println(
                            picker + " " + orders.get(i).getOrderId() + " "
                                    + store.getPickingStartTime().plusMinutes(pickingTimeAccruedPerPicker)
                    );
                    pickingTimeAccruedPerPicker += pickingTime;
                }
            } else {
                break;
            }
        }
    }
}
