package com.suhan;

import java.util.Random;

/**
 * Created by Suhan on 2/20/16.
 */
public class RfidTag {

    private static int rfidCounter = 0;
    private int rfidTagId;
    private boolean isTransmitted = false;

    public RfidTag() {
        rfidTagId = ++rfidCounter;
    }

    /**
     * Getter for RFID tag ID.
     *
     * @return ID number of the tag.
     */
    public int getRfidTagId() {
        return rfidTagId;
    }

    /**
     * Check if the tags is already transmitted.
     *
     * @return True if tag is already transmitted, false if tag hasn't transmit yet.
     */
    public boolean isTransmitted() {
        return isTransmitted;
    }

    /**
     * Method for marking the tag is transmitted.
     * Should include delay for mocking real life tag transmission delay.
     * TODO: Include delay for tag's transmission.
     */
    public void transmit() {
        isTransmitted = true;
    }

    /**
     * Method for mocking the tag choosing a time slot, using random instead of hashes.
     *
     * @param maxTimeSlotSize Maximum size of the time slot specified by the reader's broadcast.
     * @return An integer from 0 to (maxTimeSlotSize - 1)
     */
    public int attemptTransmit(int maxTimeSlotSize) {
        return new Random().nextInt(maxTimeSlotSize); //return a number from 0 to (maxTimeSlotSize - 1)
    }
}
