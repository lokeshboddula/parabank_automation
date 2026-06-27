package com.incubyte.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

public class TestDataGenerator {

    private static final Map<String, String> registeredUser = new HashMap<>();

    public static Map<String, String> generateUserData() {
        String uniqueSuffix = RandomStringUtils.randomAlphanumeric(6).toLowerCase();

        registeredUser.put("firstName",  "Test");
        registeredUser.put("lastName",   "User" + uniqueSuffix);
        registeredUser.put("address",    "123 QA Street");
        registeredUser.put("city",       "Hyderabad");
        registeredUser.put("state",      "Telangana");
        registeredUser.put("zipCode",    "500081");
        registeredUser.put("phone",      "9876543210");
        registeredUser.put("ssn",        "123-45-6789");
        registeredUser.put("username",   "user_" + uniqueSuffix);
        registeredUser.put("password",   "Password@123");

        System.out.println("[TestDataGenerator] Generated user: " + registeredUser.get("username"));
        return registeredUser;
    }

    public static Map<String, String> getRegisteredUser() {
        return registeredUser;
    }
}