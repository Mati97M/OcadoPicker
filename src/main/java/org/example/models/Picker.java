package org.example.models;

import lombok.*;
import org.example.demo.PickerDemo;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Picker {

    private String name;
    private List<Order> orders;
    private LocalTime currentTime;
    private static int sCurrentPickerIndex = 0;

    public Picker(String name) {
        this.name = name;
        orders = new LinkedList<>();
    }
    public static Picker callNextPicker() {
        if (sCurrentPickerIndex == PickerDemo.availablePickers.size() - 1) {
            return PickerDemo.availablePickers.get(0);
        }
        return PickerDemo.availablePickers.get(sCurrentPickerIndex + 1);
    }
}
