package net.hundredtickets.yahtzee.model;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScorecardRepository extends JpaRepository<Scorecard, Long> {

	Collection<Scorecard> findByPlayerName(String playerName);

	Collection<Scorecard> findByMatchId(Long matchId);

}
