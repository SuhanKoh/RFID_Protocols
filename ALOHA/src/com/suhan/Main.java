package com.suhan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * This project is mainly made for my undergrad research.
 * Build on top of these follow research(s) paper.
 * <ul>
 * <li><a href="http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=5935155&url=http%3A%2F%2Fieeexplore.ieee.org%2Fxpls%2Fabs_all.jsp%3Farnumber%3D5935155">
 * Efficient information collection protocols for sensor-augmented RFID networks
 * </a>
 * </li>
 * <li>
 * <a href="http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=6576300&url=http%3A%2F%2Fieeexplore.ieee.org%2Fiel7%2F90%2F4359146%2F06576300.pdf%3Farnumber%3D6576300">
 * Unknown-Target Information Collection in Sensor-Enabled RFID Systems
 * </a>
 * </li>
 * </ul
 */
public class Main {

    public static ArrayList<RfidTag> listOfRfidTags;

    public static void main(String[] args) {

        int numberOfTags = 8000;

        //Initiate the tags (We assumed we know all the tags)
        listOfRfidTags = new ArrayList<RfidTag>();
        for (int i = 0; i < numberOfTags; i++) {
            listOfRfidTags.add(new RfidTag());
        }

        //Initiate the time slow as empty.
        List<RfidTag> timeSlot = new LinkedList<RfidTag>();
        timeSlot = clearTimeSlot();

        int round = 1;
        long startTime = System.nanoTime();

        while (!RfidReader.getInstance().broadcastTags(listOfRfidTags, timeSlot)) {
            System.out.println("\n======= Round: " + round++ + " ======== Time Slot Size: " + timeSlot.size() + " Tag size: " + listOfRfidTags.size());
            int size = 0;
            //Tag passed back ID to the tags here.
            for (RfidTag tag : timeSlot) {
                if (tag != null) {
                    System.out.println("Transmitted: " + tag.getRfidTagId() + "  Already transmitted? " + tag.isTransmitted());
                    tag.transmit(); // Assumed no delay on transmit
                    size++;
                }
            }

            timeSlot = clearTimeSlot();
        }

        long endTime = System.nanoTime();

        System.out.println("\n\nTotal time spent transmitting " + numberOfTags + " RFID tags: "
                + (endTime - startTime) / 1000000000.0
                + " second(s)");

    }

    public static LinkedList<RfidTag> clearTimeSlot() {
        return new LinkedList<RfidTag>(Collections.nCopies(listOfRfidTags.size(), null));
    }
}
