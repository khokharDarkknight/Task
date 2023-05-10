package com.example.firebaserecyclerviewcrud;

public class Model {
    private String Username,Useremail,Userid,Userdp;

    
    public Model(String username, String useremail, String userid, String userdp) {
        Username = username;
        Useremail = useremail;
        Userid = userid;
        Userdp = userdp;
    }

    public Model() {
    }

    public Model(String name, String email, String toString) {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUseremail() {
        return Useremail;
    }

    public void setUseremail(String useremail) {
        Useremail = useremail;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getUserdp() {
        return Userdp;
    }

    public void setUserdp(String userdp) {
        Userdp = userdp;
    }
}
