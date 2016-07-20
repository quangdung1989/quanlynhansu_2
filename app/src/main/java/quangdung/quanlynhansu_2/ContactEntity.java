package quangdung.quanlynhansu_2;

import java.io.Serializable;

/**
 * Created by QuangDung on 7/15/2016.
 */
public class ContactEntity implements Serializable {

    private int id;
    private String name;
    private String age;
    private String address;
    private String phone;
    private String email;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ContactEntity(int id, String name, String age, String address, String phone, String email, String image) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public ContactEntity() {
    }

    public ContactEntity(int id, String name, String age, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
