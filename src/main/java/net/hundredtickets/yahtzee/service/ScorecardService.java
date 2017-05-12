package net.hundredtickets.yahtzee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.hundredtickets.yahtzee.model.ScorecardRepository;

@Service
public class ScorecardService {

	private final ScorecardRepository repository;

	@Autowired
	public ScorecardService(final ScorecardRepository repository) {
		this.repository = repository;
	}
}
