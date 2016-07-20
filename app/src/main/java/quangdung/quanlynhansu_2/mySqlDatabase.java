package quangdung.quanlynhansu_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;

/**
 * Created by QuangDung on 7/15/2016.
 */
public class mySqlDatabase extends SQLiteDataController {

    public mySqlDatabase(Context con) {
        super(con);
    }

    public ArrayList<ContactEntity> getAllContact() {
        ArrayList listContacts = new ArrayList();
        int id;
        String name;
        String phone;
        String age;
        String address;
        String email;
        String image;
        ContactEntity contact;

        try {
            openDataBase();
            Cursor cs;
            String selectQuery = "SELECT id, name, age, address, phone, email, image FROM newNhanVien";
            cs = database.rawQuery(selectQuery, null);

            while (cs.moveToNext()) {
                id = cs.getInt(0);
                name = cs.getString(1);
                age = cs.getString(2);
                address = cs.getString(3);
                phone = cs.getString(4);
                email = cs.getString(5);
                image = cs.getString(6);
                contact = new ContactEntity(id, name, age, address, phone, email, image);
                listContacts.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return listContacts;
    }

    public boolean insertContact(ContactEntity contact) {
        boolean result = false;
        try {
            openDataBase();
            ContentValues cv = new ContentValues();
            cv.put("name", contact.getName());
            cv.put("age", contact.getAge());
            cv.put("address", contact.getAddress());
            cv.put("phone", contact.getPhone());
            cv.put("email", contact.getEmail());
            cv.put("image", contact.getImage());

            if (database.insert("newNhanVien", null, cv) > -1)
                result = true;
            else
                result = false;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public boolean editContact(ContactEntity contactEntity) {
        boolean result = false;
        try {
            openDataBase();
            ContentValues cv = new ContentValues();

            cv.put("name", contactEntity.getName());
            cv.put("address", contactEntity.getAddress());
            cv.put("phone", contactEntity.getPhone());
            cv.put("age", contactEntity.getAge());
            cv.put("email", contactEntity.getEmail());
            cv.put("image", contactEntity.getImage());

            if (database.update("newNhanVien", cv
                    , "id =" + contactEntity.getId()
                    , null) > -1)
                result = true;
            else
                result = false;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }

    public boolean deleteContactByID(int id) {
        boolean result = false;
        try {
            openDataBase();
            if (database.delete("newNhanVien", "id =" + id, null) > -1)
                result = true;
            else
                result = false;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }


}
