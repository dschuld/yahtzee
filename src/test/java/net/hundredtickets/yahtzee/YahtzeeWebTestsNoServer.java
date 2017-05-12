package net.hundredtickets.yahtzee;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class YahtzeeWebTestsNoServer {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGet() throws Exception {
		this.mockMvc.perform(get("/match")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Large Straight")));
	}

	@Test
	public void validation() throws Exception {

		// Test the validation by setting a field to an invalid value and
		// testing the model for errors in this field
		this.mockMvc.perform(post("/match").param("firstPlayer.fours", "30")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Large Straight")))
				.andExpect(model().attributeHasFieldErrors("match", "firstPlayer.fours"));
	}

}
