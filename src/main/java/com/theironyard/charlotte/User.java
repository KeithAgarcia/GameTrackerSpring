package com.theironyard.charlotte;

import javax.persistence.*;

@Entity
// specify the table name so that we
// don't interfere with the "user" in
// postgres.
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    // unique constraint means that there
    // cannot be two users with the same name
    // inside the "users" table.
    // in SQL terms, this is called a "constraint"
    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}