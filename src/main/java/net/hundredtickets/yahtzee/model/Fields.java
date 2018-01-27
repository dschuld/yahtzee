package net.hundredtickets.yahtzee.model;

public enum Fields {

    ONES("1", "ones"),
    TWOS("2", "twos"),
    THREES("3", "threes"),
    FOURS("4", "fours"),
    FIVES("5", "fives"),
    SIXES("6", "sixes"),
    THREE_OF_A_KIND("3OfAKind", "threeOfAKinde"),
    FOUR_OF_A_KIND("3OfAKind", "fourOfAKinde"),
    FULL_HOUSE("fullHouse", "fullHouse"),
    SMALL_STRAIGHT("smallStraight", "smallStraight"),
    LARGE_STRAIGHT("largeStraight", "largeStraight"),
    YAHTZEE("yahtzee", "yahtzee"),
    CHANCE("chance", "chance");

    private final String longName;
    private final String shortName;

    Fields(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getLongName() {
        return this.longName;
    }
}
