package net.hundredtickets.yahtzee.model;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScorecardRepository extends JpaRepository<Scorecard, Long> {

	Collection<Scorecard> findByPlayerId(String playerId);

	Collection<Scorecard> findByMatchId(Long matchId);

}
