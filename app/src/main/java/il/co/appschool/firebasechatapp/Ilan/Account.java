package il.co.appschool.firebasechatapp.Ilan;

/**
 * Created by talmid on 15/03/2018.
 */

public class Account {
    private String FirstName;
    private String LastName;
    private String DisplayName;
    private String Email;
    private String AccSpecId;
    private long id;

    public Account( long id,String firstName, String lastName, String displayName, String email, String accSpecId) {
        FirstName = firstName;
        LastName = lastName;
        DisplayName = displayName;
        Email = email;
        AccSpecId = accSpecId;
        this.id = id;
    }
    public Account(String firstName , String lastName , String displayName ,String email ,String accSpecId)
    {
        FirstName = firstName;
        LastName = lastName;
        DisplayName = displayName;
        Email = email;
        AccSpecId = accSpecId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAccSpecId() {
        return AccSpecId;
    }

    public void setAccSpecId(String accSpecId) {
        AccSpecId = accSpecId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
