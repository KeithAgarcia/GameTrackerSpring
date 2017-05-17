package com.theironyard.charlotte;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTrackerSpringApplicationTests {

	@Autowired
	GameRepository games;

	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void addGame() throws Exception {
		Game game = new Game();
		game.setName("Jimbo");
		game.setPlatform("xbox");
		game.setGenre("RPG");
		game.setReleaseYear(1997);

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(game);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/add-game")
						.param("gameName", game.getName())
						.param("gamePlatform", game.getPlatform())
						.param("gameGenre", game.getGenre())
						.param("gameYear", String.valueOf(game.getReleaseYear()))

		);

		Assert.assertEquals(1, games.count());


	}
}
