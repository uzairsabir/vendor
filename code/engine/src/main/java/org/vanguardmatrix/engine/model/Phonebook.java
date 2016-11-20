package org.vanguardmatrix.engine.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import org.vanguardmatrix.engine.datatypes.Contact;

public class Phonebook {

    public static String getPhoneContactList(Context context) {

        String csvNumbers = "";

        try {

            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                    null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur
                            .getColumnIndex(ContactsContract.Contacts._ID));

                    if (Integer
                            .parseInt(cur.getString(cur
                                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr
                                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                + " = ?", new String[]{id},
                                        null);
                        while (pCur.moveToNext()) {

                            try {

                                String phoneNo = pCur
                                        .getString(pCur
                                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                if (phoneNo.length() > 0) {

                                    Log.e("ALLContactLiseting", " adding "
                                            + phoneNo);

                                    csvNumbers += "\'"
                                            + Contact
                                            .getFormattedNumber(phoneNo)
                                            + "\',";
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pCur.close();
                    }
                }
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvNumbers.substring(0, csvNumbers.length() - 1);

    }

}
