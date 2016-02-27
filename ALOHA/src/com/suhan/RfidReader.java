package com.suhan;

import java.util.List;

/**
 * Created by Suhan on 2/20/16.
 */
public class RfidReader {

//    public static final int MAX_NUMBER_OF_TIME_SLOT = 100;

    private static RfidReader _instance = null;
    private static List<RfidTag> timeSlot;

    /**
     * Singleton method for Rfid Reader, as we assume there will be only one RFID reader.
     *
     * @return instance of the RFID reader.
     */
    public static RfidReader getInstance() {

        if (_instance == null) {
            _instance = new RfidReader();
            return _instance;

        } else {
            return _instance;
        }
    }

    /**
     * It broadcast to all the tags, and let the RFID tag assume onto the Reader's time slot.
     * <p>
     * If there is conflict, it will drop the RFID that is assigned and let them retry next round.
     * See
     * <ul>
     * <li><a href="http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=5935155&url=http%3A%2F%2Fieeexplore.ieee.org%2Fxpls%2Fabs_all.jsp%3Farnumber%3D5935155">
     * Efficient information collection protocols for sensor-augmented RFID networks
     * </a>
     * </li>
     * <p>
     * <li>
     * <a href="http://ieeexplore.ieee.org/xpl/login.jsp?tp=&arnumber=6576300&url=http%3A%2F%2Fieeexplore.ieee.org%2Fiel7%2F90%2F4359146%2F06576300.pdf%3Farnumber%3D6576300">
     * Unknown-Target Information Collection in Sensor-Enabled RFID Systems
     * </a>
     * </li>
     * </ul
     * </p>
     *
     * @param rfidTags Rfidtags in a List object, act like all known nearby rfid tags.
     * @param timeSlot A fixed time slot for ALOHA.
     * @return true if all the tags are finished with transmitting, false if there is a transmission in this round.
     */
    public boolean broadcastTags(List<RfidTag> rfidTags, List<RfidTag> timeSlot) {

        boolean finishedAllTags = true;

        for (int i = 0; i < rfidTags.size(); i++) {
            if (!rfidTags.get(i).isTransmitted()) { //Check if tag is already transmitted
                finishedAllTags = false;

                int timeSlotPosition = rfidTags.get(i).attemptTransmit(rfidTags.size());

                if (timeSlot.get(timeSlotPosition) == null) { //Place rfid on this time slot
                    timeSlot.set(timeSlotPosition, rfidTags.get(i));
                    rfidTags.remove(i--);
                } else { //Conflict happens

                    rfidTags.add(timeSlot.get(timeSlotPosition));
                    timeSlot.set(timeSlotPosition, null); //Setting the existing tag to next round
                }


            }
        }

        return finishedAllTags;

    }
}
