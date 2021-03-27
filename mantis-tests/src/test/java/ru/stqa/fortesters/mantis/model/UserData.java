package ru.stqa.fortesters.mantis.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mantis_user_table")
public class UserData {

    @Id
    @Column(name = "id")
    private int id = 0;

    @Column(name = "username")
    private String username;

    @Column(name = "realname")
    private String realname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    public int getId() {
        return id;
    }

    public String getUsername() {
        return 	username;
    }

    public String getRealname() {
        return 	realname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        if (id != userData.id) return false;
        if (!Objects.equals(username, userData.username)) return false;
        if (!Objects.equals(realname, userData.realname)) return false;
        return Objects.equals(email, userData.email);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
