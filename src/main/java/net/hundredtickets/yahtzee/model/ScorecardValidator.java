package net.hundredtickets.yahtzee.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ScorecardValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Scorecard.class.equals(clazz) || Match.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		return;

		// if (target.getClass().equals(Match.class)) {
		// return;
		// }
		// ValidationUtils.rejectIfEmpty(errors, "playerName",
		// "playerName.empty");

	}

}
