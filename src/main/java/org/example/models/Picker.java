package org.example.models;

import lombok.*;

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

    public Picker(String name) {
        this.name = name;
    }
}
