package com.example.uzair.expertdentalcare;

import com.orm.SugarRecord;

/**
 * Created by Uzair on 1/8/2017.
 */

public class AppointmentDatatype extends SugarRecord {

    String firstName;
    String lastName;
    String DateTime;
    String DentistName;
    int PhoneNumber;

    public AppointmentDatatype() {
    }

    public AppointmentDatatype(String firstName, String lastName, String DateTime, String DentistName, String PhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DateTime = DateTime;
        this.DentistName = DentistName;
        this.PhoneNumber = Integer.parseInt(PhoneNumber);
    }
}
