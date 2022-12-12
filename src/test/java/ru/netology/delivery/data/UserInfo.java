package ru.netology.delivery.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@RequiredArgsConstructor
public class UserInfo {
    private final String city;
    private final String name;
    private final String phone;
}
