package com.theironyard.charlotte;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// a repository is what interacts with your database
// When extending a CrudRepository interface, the first parameter
// in the diamond operator is the thing you want to store.

// the second parameter is the type of the id column.
public interface GameRepository extends CrudRepository<Game, Integer> {
    // Hibernate will analyze the format of this method
    // and generate a query based off its name
    // will generate and run a query similar to:
    // SELECT * FROM games where genre = ?
    List<Game> findByGenre(String genre);

    // roughly equivalent to:
    // SELECT * FROM games where release_year = ?
    List<Game> findByReleaseYear(int year);

    // talk about JPA SQL
    // roughly equivalent to SQL:
    // SELECT * from games where name like ?%
    @Query("SELECT g FROM Game g WHERE g.name LIKE ?1%")
    List<Game> findByNameStartsWith(String name);

    // return a list of all the games that
    // belong to user
    List<Game> findAllByUser(User user);

}