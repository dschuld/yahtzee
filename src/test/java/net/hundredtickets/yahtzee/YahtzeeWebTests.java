package net.hundredtickets.yahtzee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class YahtzeeWebTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MatchController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void getScorecard() throws Exception {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		map = new LinkedMultiValueMap<String, String>();
		map.add("Accept-Language", "de-DE,de");
        map.add("RequestId", "asd");
        map.add("player", "David");
        map.add("passivePlayer.name", "Player1");
        map.add("activePlayer.name", "Player2");
        map.add("activePlayer.ones", "3");
        map.add("activePlayer.twos", "6");
        map.add("activePlayer.threes", "9");
        map.add("activePlayer.fours", "12");
        map.add("activePlayer.fives", "15");
        map.add("activePlayer.sixes", "18");
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/match?player=Player1", String.class, map))
				.contains("Yahtzee");

	}

	@Test
	public void postScorecardContent() throws Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("RequestId", "asd");
		map.add("player", "Player1");
		map.add("activePlayer.name", "David");
		map.add("activePlayer.ones", "3");
		map.add("activePlayer.twos", "6");
		map.add("activePlayer.threes", "9");
		map.add("activePlayer.fours", "12");
		map.add("activePlayer.fives", "15");
		map.add("activePlayer.sixes", "18");
		map.add("Accept-Language", "en-EN");

		// Response must have the value 98 as a result for the upper part (sum
		// of values + bonus)
		// and does not contain the german word "Kniffel" as the default locale
		// is en-US
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/match", map, String.class))
				.contains("Player1").contains("98").doesNotContain("Kniffel");
	}

	@Test
	public void postScorecardLanguages() throws Exception {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("RequestId", "asd");
        headers.add("Accept-Language", "de-DE,de");
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("player", "David");
        map.add("passivePlayer.name", "David");
        map.add("activePlayer.ones", "3");
        map.add("activePlayer.twos", "6");
        map.add("activePlayer.threes", "9");
        map.add("activePlayer.fours", "12");
        map.add("activePlayer.fives", "15");
        map.add("activePlayer.sixes", "18");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);


		// Response must contain the word "Kniffel" because language was set to
		// German
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/match", request, String.class))
				.contains("Kniffel");

	}

}
