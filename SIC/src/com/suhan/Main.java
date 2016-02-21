package com.suhan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int numberOfTags = 6000;

        int round = 1;
        //Initiate the tags (We assumed we know all the tags), and we don't care the order.

        ArrayList<RfidTag> listOfRfidTags = new ArrayList<RfidTag>();
        for (int i = 0; i < numberOfTags; i++) {
            listOfRfidTags.add(new RfidTag());
        }

        //Initiate the time slot as empty.
        List<RfidTag> timeSlot = new LinkedList<RfidTag>();
        for (int i = 0; i < RfidReader.MAX_NUMBER_OF_TIME_SLOT; i++) {
            timeSlot.add(null);
        }

        //Time start because this is where it because to get the queue
        long startTime = System.nanoTime();

        List<RfidTag> tags = new LinkedList<RfidTag>();
        for (int i = 0; i < RfidReader.MAX_NUMBER_OF_TIME_SLOT && i < listOfRfidTags.size(); i++) {
            tags.add(listOfRfidTags.remove(0));
        }

        while (!RfidReader.getInstance().broadcastTagSIC(tags, timeSlot, listOfRfidTags)) {

            System.out.println("\n======= Round: " + round++ + " ========");

            for (int i = 0; i < timeSlot.size(); i++) {
                if (timeSlot.get(i) != null) {
                    System.out.println("Transmitted: " + timeSlot.get(i).getRfidTagId() + "  Already transmitted? " + timeSlot.get(i).isTransmitted());

                    timeSlot.get(i).transmit();
                    timeSlot.set(i, null);
                }
            }

            for (int i = 0; i < RfidReader.MAX_NUMBER_OF_TIME_SLOT && i < listOfRfidTags.size(); i++) {
                tags.set(i, listOfRfidTags.remove(0));
            }

        }
        long endTime = System.nanoTime();

        System.out.println("\n\nTotal time spent transmitting " + numberOfTags + " RFID tags: "
                + (endTime - startTime) / 1000000000.0
                + " second(s)"
                + " with "
                + RfidReader.MAX_NUMBER_OF_TIME_SLOT
                + " time slots.");

    }
}
