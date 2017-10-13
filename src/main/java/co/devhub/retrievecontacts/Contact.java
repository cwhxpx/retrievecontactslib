package co.devhub.retrievecontacts;

import android.graphics.Bitmap;

/**
 * Created by nick on 12/10/17.
 */

public class Contact {
    private String contactName;
    private String phoneNumber;
    private String contactId;
    private Bitmap profileIcon;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Bitmap getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(Bitmap profileIcon) {
        this.profileIcon = profileIcon;
    }
}
