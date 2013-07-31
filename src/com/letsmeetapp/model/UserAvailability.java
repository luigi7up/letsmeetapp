package com.letsmeetapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

/**
*   Event JSON comes with an array "invited_users" where each object of that array holds the information about
 *   the invited user email, availability per day etc. This is a helper class that makes that manipulation easier
 */
public class UserAvailability implements Parcelable {
    String email_invitation;
    String user_email;
    ArrayList<String> availability;

    public UserAvailability(){}

    public UserAvailability(String email_invitation, String user_email,ArrayList<String> availability){
        this.email_invitation   = email_invitation;
        this.user_email         = user_email;
        this.availability       = availability;
    }

    public ArrayList<String> getAvailability() {
        return availability;
    }

    public void setAvailability(ArrayList<String> availability) {
        this.availability = availability;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getEmail_invitation() {
        return email_invitation;
    }

    public void setEmail_invitation(String email_invitation) {
        this.email_invitation = email_invitation;
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email_invitation);
        dest.writeString(user_email);
        dest.writeValue(availability);
    }


    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<UserAvailability> CREATOR = new Parcelable.Creator<UserAvailability>() {
        public UserAvailability createFromParcel(Parcel in) {
            //Read serialized values
            String email_invitation         = in.readString();
            String user_email               = in.readString();
            ArrayList<String> availability  = (ArrayList<String>)in.readValue(getClass().getClassLoader());

            //insert deserialized values
            UserAvailability userAvailability = new UserAvailability();
            userAvailability.setEmail_invitation(email_invitation);
            userAvailability.setUser_email(user_email);
            userAvailability.setAvailability(availability);
            return userAvailability;

        }
        public UserAvailability[] newArray(int size) {
            return new UserAvailability[size];
        }
    };





}
