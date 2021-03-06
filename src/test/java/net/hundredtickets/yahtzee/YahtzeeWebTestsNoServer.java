package net.hundredtickets.yahtzee;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.hundredtickets.yahtzee.model.Roll;
import net.hundredtickets.yahtzee.service.RoundService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
        when(roundService.rollDice(any(Roll.class))).then((Answer<Roll>) invocation -> {
            Roll returnRoll = invocation.getArgumentAt(0, Roll.class);

            returnRoll.setDice1(1);
            returnRoll.setDice2(1);
            returnRoll.setDice3(1);
            returnRoll.setDice4(1);
            returnRoll.setDice5(123);

            return returnRoll;
        });

    }

    @Test
    public void getContainsHardcodedValues() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        map = new LinkedMultiValueMap<>();
        map.add("Accept-Language", "de-DE,de");
        map.add("RequestId", "asd");

        map.add("player", "Player1");
        map.add("passivePlayer.name", "Player1");
        map.add("activePlayer.name", "Player2");
        map.add("activePlayer.ones", "3");
        map.add("activePlayer.twos", "6");
        map.add("activePlayer.threes", "9");
        map.add("activePlayer.fours", "12");
        map.add("activePlayer.fives", "15");
        map.add("activePlayer.sixes", "18");
        this.mockMvc.perform(get("/match").params(map)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Large Straight")));
    }

    @Test
    public void scorecardValidation() throws Exception {

        // Test the validation by setting a field to an invalid value and
        // testing for a 4xx client error
        this.mockMvc.perform(post("/match").param("activePlayer.fours", "30")).andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void rounds() throws Exception {

        this.mockMvc.perform(post("/roll?player=Player1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("123")));

    }


    @Test
    public void start() throws Exception {

        this.mockMvc.perform(get("/start?player=Player1")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/start?player=Player2")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/start?player=Player2")).andDo(print()).andExpect(status().isOk());

    }


    @Test
    public void startTooManyPlayers() throws Exception {

        this.mockMvc.perform(get("/start?player=Player1")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/start?player=Player2")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/start?player=Player3")).andDo(print()).andExpect(status().is4xxClientError());

    }


    @Test
    public void rollsWithResult() throws Exception {
        //executes a series of saves and checks the scorecard for the magic number 56, which is the grand total
        // of the previous rolls
        this.mockMvc.perform(get("/start?player=Player1")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/match?player=Player1&activePlayer.ones=3")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/match?player=Player1&activePlayer.chance=13")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/match?player=Player1&activePlayer.largeStraight=40")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/match?player=Player1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("56")));
    }

    @Test
    public void rightName() throws Exception {

        this.mockMvc.perform(get("/index.html")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Player")));

    }

    @Test
    @Ignore
    public void additionalYahtzee() throws Exception {
        this.mockMvc.perform(get("/start?player=Player1")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/match?player=Player1&activePlayer.yahtzee=50")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/match?player=Player1&activePlayer.ones=5")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/match?player=Player1&activePlayer.fives=25")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/match?player=Player1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("180")));
    }

}
