package net.hundredtickets.yahtzee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
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
	private ScorecardController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void getScorecard() throws Exception {

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

		map = new LinkedMultiValueMap<String, String>();
		map.add("Accept-Language", "de-DE,de");

		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/match", String.class))
				.contains("Yahtzee");

	}

	@Test
	public void postScorecardContent() throws Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("RequestId", "asd");
		map.add("secondPlayer.name", "David");
		map.add("firstPlayer.ones", "3");
		map.add("firstPlayer.twos", "6");
		map.add("firstPlayer.threes", "9");
		map.add("firstPlayer.fours", "12");
		map.add("firstPlayer.fives", "15");
		map.add("firstPlayer.sixes", "18");
		map.add("Accept-Language", "en-EN");

		// Response must have the value 98 as a result for the upper part (sum
		// of values + bonus)
		// and does not contain the german word "Kniffel" as the default locale
		// is en-US
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/match", map, String.class))
				.contains("David").contains("98").doesNotContain("Kniffel");
	}

	@Test
	public void postScorecardLanguage() throws Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("RequestId", "asd");

		map.add("Accept-Language", "de-DE,de");
		HttpEntity<String> request = new HttpEntity<String>("", map);

		// Response must contain the word "Kniffel" because language was set to
		// German
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/match", request, String.class))
				.contains("Kniffel");
	}

}
