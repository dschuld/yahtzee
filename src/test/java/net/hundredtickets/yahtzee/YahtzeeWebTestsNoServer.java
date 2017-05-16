package net.hundredtickets.yahtzee;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.hundredtickets.yahtzee.service.RoundService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class YahtzeeWebTestsNoServer {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoundService roundService;

	@Before
	public void setUp() {
		Integer[] dice = { 1, 2, 3, 123, 456 };
		when(roundService.getDice()).thenReturn(dice);

	}

	@Test
	public void testGet() throws Exception {
		this.mockMvc.perform(get("/match")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Large Straight")));
	}

	@Test
	public void scorecardValidation() throws Exception {

		// Test the validation by setting a field to an invalid value and
		// testing for a 4xx client error
		this.mockMvc.perform(post("/match").param("firstPlayer.fours", "30")).andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void rounds() throws Exception {

		this.mockMvc.perform(get("/round")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("123")));

	}

}
