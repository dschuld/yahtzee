package net.hundredtickets.yahtzee.model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Fields {

    ONES("1", "ones", countEvaluator(1)),
    TWOS("2", "twos", countEvaluator(2)),
    THREES("3", "threes", countEvaluator(3)),
    FOURS("4", "fours", countEvaluator(4)),
    FIVES("5", "fives", countEvaluator(5)),
    SIXES("6", "sixes", countEvaluator(6)),
    THREE_OF_A_KIND("3OfAKind", "threeOfAKind", xOfAKindEvaluator((counters -> counters.containsValue(3L) || counters.containsValue(4L)))),
    FOUR_OF_A_KIND("4OfAKind", "fourOfAKind", xOfAKindEvaluator((counters -> counters.containsValue(4L)))),
    FULL_HOUSE("fullHouse", "fullHouse", fullHouseEvaluator("fullHouse")),
    SMALL_STRAIGHT("smallStraight", "smallStraight", smallStraightEvaluator()),
    LARGE_STRAIGHT("largeStraight", "largeStraight", largeStraightEvaluator()),
    YAHTZEE("yahtzee", "yahtzee", yahtzeeEvaluator()),
    CHANCE("chance", "chance", countAllEvaluator());

    private static Evaluator smallStraightEvaluator() {
        return dice -> {

            List<Integer> integers = Arrays.asList(dice);
            integers = integers.stream().distinct().collect(Collectors.toList());
            Collections.sort(integers);

            if ((Collections.indexOfSubList(integers, Arrays.asList(1, 2, 3, 4)) > -1)
                    || (Collections.indexOfSubList(integers, Arrays.asList(2, 3, 4, 5)) > -1)
                    || (Collections.indexOfSubList(integers, Arrays.asList(3, 4, 5, 6)) > -1)) {
                return 30;
            }

            return 0;
        };
    }

    private static Evaluator largeStraightEvaluator() {
        return dice -> {

            Set<Integer> set = new HashSet<>(Arrays.asList(dice));
            if (set.size() == 5 && !(set.contains(1) && set.contains(6))) {
                return 40;
            }
            return 0;
        };
    }


    private static Evaluator countAllEvaluator() {
        return dice -> countAll(dice);
    }

    private static Evaluator yahtzeeEvaluator() {
        return dice -> {

            Set<Integer> set = new HashSet<>(Arrays.asList(dice));
            return set.size() == 1 ? 50 : 0;
        };
    }

    private static Evaluator countEvaluator(int value) {
        return dice -> value * Collections.frequency(new ArrayList<>(Arrays.asList(dice)), value);
    }

    private static Evaluator xOfAKindEvaluator(Predicate<Map<Integer, Long>> pred) {

        return dice -> {

            Map<Integer, Long> counters = Arrays.asList(dice).stream()
                    .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
            return pred.test(counters) ? countAll(dice) : 0;
        };
    }

    private static Evaluator fullHouseEvaluator(String field) {
        return (Integer[] dice) -> {

            List<Integer> integers = Arrays.asList(dice);
            Set<Integer> set = new HashSet<>(integers);
            if (set.size() == 2) {

                long count = integers.stream().filter(value -> value == set.iterator().next()).count();

                if ((count == 2 || count == 3) && field.equals("fullHouse")) {
                    return 25;
                } else {
                    return 0;
                }
            }

            return 0;
        };
    }

    private static int countAll(Integer[] dice) {
        return dice[0] + dice[1] + dice[2] + dice[3] + dice[4];

    }

    private final String longName;
    private final String shortName;
    private final Evaluator evaluator;

    Fields(String shortName, String longName, Evaluator evaluator) {
        this.shortName = shortName;
        this.longName = longName;
        this.evaluator = evaluator;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getLongName() {
        return this.longName;
    }

    public int evaluate(Integer[] dice) {
        return this.evaluator.evaluate(dice);
    }

    public static Fields getForName(String fieldName) {
        for (Fields field: Fields.values()) {
            if (field.getLongName().equals(fieldName)) {
                return field;
            }
        }

        throw new IllegalArgumentException("Field of name " + fieldName + " does not exist");
    }
}
