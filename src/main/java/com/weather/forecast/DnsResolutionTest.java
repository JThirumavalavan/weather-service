package com.weather.forecast;

import java.net.InetAddress;

public class DnsResolutionTest {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("api.weather.gov");
            System.out.println("Resolved IP Address: " + address.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}