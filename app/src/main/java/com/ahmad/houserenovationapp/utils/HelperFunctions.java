package com.ahmad.houserenovationapp.utils;

import android.location.Location;

import com.ahmad.houserenovationapp.enums.Category;

public class HelperFunctions {
    public static String formatCategoryName(Category category) {
        String name = category.name().replace("_", " ").toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static int calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Create Location objects for both points
        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);

        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lon2);

        // Calculate the distance in meters
        double distance = (double)locationA.distanceTo(locationB);

        return (int) Math.ceil(distance);
    }



}
