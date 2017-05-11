package com.theironyard.charlotte;


import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    // specifies ID is the primary key of the database
    // and will be dynamically generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String platform;

    @Column(nullable = false)
    String genre;

    @Column(nullable = false)
    int releaseYear;

    @ManyToOne
    User user;

    public Game() {
    }

    public Game(String name, String platform, String genre, int releaseYear, User user) {
        this.name = name;
        this.platform = platform;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.user = user;
    }
}