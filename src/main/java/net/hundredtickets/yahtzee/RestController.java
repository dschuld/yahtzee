package net.hundredtickets.yahtzee;


import net.hundredtickets.yahtzee.model.Fields;
import net.hundredtickets.yahtzee.model.Roll;
import net.hundredtickets.yahtzee.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController {

    @Autowired
    private RoundService roundService;

    @GetMapping("/roll")
    @ResponseBody
    public Roll getRoll(@ModelAttribute("roll") Roll roll, BindingResult bindingResult) {

        roundService.fetchCurrentValues(roll);

        return roll;
    }

    @GetMapping("/roll/evaluate/{field}")
    @ResponseBody
    public Integer evaluateRollForField(@PathVariable String field) {

        Roll roll = new Roll();
        roundService.fetchCurrentValues(roll);

        return Fields.getForName(field).evaluate(new Integer[]{roll.getDice1(), roll.getDice2(), roll.getDice3(), roll.getDice4(), roll.getDice5()});
    }


}
