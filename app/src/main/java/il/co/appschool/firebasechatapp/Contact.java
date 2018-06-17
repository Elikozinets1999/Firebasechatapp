package il.co.appschool.firebasechatapp;

/**
 * Created by elili on 3/20/2018.
 */

public class Contact {
    private String email;
    private String fullName;

    //Builds a new contact
    public Contact(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public Contact() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    /*public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }*/
}
