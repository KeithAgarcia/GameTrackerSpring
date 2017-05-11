package com.theironyard.charlotte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GameTrackerSpringController {
    // adding autowired will cause spring to populate this field
    // when it creates this controller. this is a process
    // called "dependency injection".
    @Autowired
    GameRepository games;

    @Autowired
    UserRepository users;

    @PostConstruct // marks this method to be run after spring has booted up
    public void init() {
        if (users.count() == 0) {
            User user = new User();
            user.name = "Zach";
            user.password = "hunter2";
            users.save(user);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String genre, Integer releaseYear, String search) {
        // ask session for a value at the key "userName"
        String userName = (String) session.getAttribute("userName");

        // use the username to try and find a user in our database
        // with that name
        User user = users.findFirstByName(userName);

        // if we found a user by the username..
        if (user != null) {
            // add the user to the model, with the
            // key "user". can be referenced in the
            // mustache template as "{{user}}"
            model.addAttribute("user", user);
        }

        // cast the results of findAll to a List.
        // findAll by default returns an Iterable
        // List<Game> gameList = (List)games.findAll();

        // the list of games we want to display
        List<Game> gameList;

        // if there is a genre specified..
        if (genre != null) {
            // set game list to our find by genre call
            gameList = games.findByGenre(genre);
        } else if (releaseYear != null) {
            // set game list to our "find by release year" call.
            gameList = games.findByReleaseYear(releaseYear);
        } else if (search != null) {
            // set game list to our "find all by name starts with" call.
            gameList = games.findByNameStartsWith(search);
        } else {
            // if there is no genre, just return
            // all games.
            gameList = games.findAllByUser(user);
        }

        model.addAttribute("games", gameList);
        return "home";
    }

    // these field names match up exactly with the form from the homepage.
    @RequestMapping(path = "/add-game", method = RequestMethod.POST)
    public String addGame(HttpSession session, String gameName, String gamePlatform, String gameGenre, int gameYear) {
        // find the first user in the repository
        // where the user's name is the same as the value
        // of the "userName" attribute in session.
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByName(userName);

        Game game = new Game(gameName, gamePlatform, gameGenre, gameYear, user);
        games.save(game);
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        // lookup the user by the name that we posted
        User user = users.findFirstByName(userName);

        // if we didn't find a user in the database..
        if (user == null) {
            // make a new user object
            user = new User(userName, password);

            // store that object in the database.
            users.save(user);
        } else if (!password.equals(user.getPassword())) {
            // panic if the password is wrong
            throw new Exception("Incorrect password");
        }

        // if we're here, we have a user and we need
        // to store their username in session
        session.setAttribute("userName", userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        // invalidate the session.
        session.invalidate();
        return "redirect:/";
    }
}
