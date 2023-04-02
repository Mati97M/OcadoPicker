package org.example.demo;

import org.example.models.Order;
import org.example.models.Picker;
import org.example.models.Store;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.example.utils.JSON_FileParser.parseOrdersFile;
import static org.example.utils.JSON_FileParser.parseStoreFile;

public class PickerDemo {
    public static List<Picker> availablePickers = new LinkedList<>();
    public static List<Picker> readyPickers = new LinkedList<>();
    public static void main(String[] args) {
        int numberOfArguments = args.length;
        if (numberOfArguments > 2) {
            System.out.println("Too many aruments. Program needs only 2 arguments (absolute paths).");
        } else if (numberOfArguments < 2) {
            System.out.println("Program requires exactly 2 arguments (absolute paths). Please input exactly 2 arguments.");
        } else {
            StringBuilder output = new StringBuilder();
            List<Order> orders = parseOrdersFile(args[0]);
            Store store = parseStoreFile(args[1]);
            List<String> pickersNames = store.getPickers();
            for (String name : pickersNames) {
                Picker picker = new Picker(name);
                picker.setCurrentTime(store.getPickingStartTime());
                availablePickers.add(picker);
            }
            int repeatCounter = 0;
            Collections.sort(orders, Comparator
                    .comparing(Order::getCompleteBy)
                    .thenComparing(Order::getPickingTime));
            Picker currPicker = availablePickers.get(0);
            for (int i = 0; i < orders.size(); i++) {
                if (currPicker.getCurrentTime().plusMinutes(orders.get(i).getPickingTime().toMinutes())
                        .compareTo(orders.get(i).getCompleteBy()) > 0) {
                    currPicker = Picker.callNextPicker();
                    i--;
                    repeatCounter++;
                    if (repeatCounter == availablePickers.size()) {
                        orders.remove(i + 1);
                        repeatCounter = 0;
                    }
                } else {
                    output.append( currPicker.getName() + " " + orders.get(i).getOrderId() + " "
                            + currPicker.getCurrentTime() + "\n");
                    currPicker.setCurrentTime(currPicker.getCurrentTime().plusMinutes(orders.get(i).getPickingTime().toMinutes()));
                    currPicker.getOrders().add(orders.get(i));

                    if(currPicker.getCurrentTime().compareTo(store.getPickingEndTime()) == 0) {
                        readyPickers.add(currPicker);
                        availablePickers.remove(currPicker);
                    }
                    repeatCounter = 0;
                }
            }
            readyPickers.addAll(availablePickers);
            System.out.println(output);
        }
    }
}
