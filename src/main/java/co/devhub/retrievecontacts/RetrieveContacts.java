package co.devhub.retrievecontacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.ArrayList;

public class RetrieveContacts {

	Context mContext = null;

	/**selecting fields set**/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME,
			Phone.NUMBER,
			Photo.PHOTO_ID,
			Phone.CONTACT_ID
	};

	/**cursor index for display name**/
	private static final int DISPLAY_NAME = 0;
	/**cursor index for phone number**/
	private static final int PHONES_NUMBER = 1;
	/**cursor index for profile icon**/
	private static final int PROFILE_ICON = 2;
	/**cursor index for contactId**/
	private static final int CONTACT_ID = 3;


	/* Retrieved contacts*/
	private ArrayList<Contact> mContacts = new ArrayList<>();

	public RetrieveContacts(Context context) {
		mContext = context;

		/**retrieving contacts from phone**/
		getPhoneContacts();
	}

	/**Retrieving contacts**/
	private void getPhoneContacts() {
		ContentResolver resolver = mContext.getContentResolver();

		// doing query phone contacts database
		Cursor searchedContacts = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION,
				null,
				null,
				null);
		/*stuffing mContacts with searched set*/
		if (searchedContacts != null) {
			//searchedContacts.moveToFirst();
			while (searchedContacts.moveToNext()) {
				//first checking whether the phone number exists
				String phoneNumber = searchedContacts.getString(PHONES_NUMBER);
				//it shouldn't be retrieved if it hasn't phone number
				if (TextUtils.isEmpty(phoneNumber)) continue;

				//retieving contact name
				String contactName = searchedContacts.getString(DISPLAY_NAME);

				//retrieving contact ID
				Long contactid = searchedContacts.getLong(CONTACT_ID);

				//retrieving profile icon of this contact
				Long profileIcon = searchedContacts.getLong(PROFILE_ICON);

				//converting icon to Bitamp
				Bitmap profileBmp = null;

				//That profileIcon is greater than 0 means
				// existing of profile icon for this contact
				if(profileIcon > 0 ) {
					Uri uri = ContentUris.withAppendedId(
							ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts
							.openContactPhotoInputStream(resolver, uri);
					profileBmp = BitmapFactory.decodeStream(input);
				}else {
					//there is a hidden trouble if This class packed in a separated lib
					profileBmp = BitmapFactory.decodeResource(
							mContext.getResources(), R.drawable.default_icon);
				}

				//Stuffing contact into mContacts
				Contact contact = new Contact();
				contact.setContactName(contactName);
				contact.setPhoneNumber(phoneNumber);
				contact.setProfileIcon(profileBmp);
				mContacts.add(contact);
			}

			//closing search set's cursor
			searchedContacts.close();
		}
	}

	/* Accessing contacts retrieved by this Lib*/
	public ArrayList<Contact> getContacts() {
		return mContacts;
	}
}