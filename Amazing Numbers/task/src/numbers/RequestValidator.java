package numbers;

import java.util.*;

public class RequestValidator {

    private record PropertyFilters(Set<Property> included, Set<Property> excluded){}

    private static final List<Set<Property>> MUTUAL_EXCLUSIVE = List.of(
            Set.of(Property.EVEN, Property.ODD),
            Set.of(Property.DUCK, Property.SPY),
            Set.of(Property.SUNNY, Property.SQUARE),
            Set.of(Property.HAPPY, Property.SAD)
    );

    private static final List<Set<Property>> MUTUAL_EXCLUSIVE_EXCLUDED = List.of(
            Set.of(Property.EVEN, Property.ODD),
            Set.of(Property.HAPPY, Property.SAD)
    );


    public UserRequest validate(String request) {
        String[] tokens = request.trim().split("\\s+");

        switch (tokens.length) {
            case 1 -> { return validateOneParameter(tokens[0]); }
            case 2 -> { return validateTwoParameters(tokens[0], tokens[1]); }
            default -> { return validateTwoParametersWithProperties(tokens); }
        }
    }

    private long parseFirstParameter(String numberStr) {
        if (numberStr.matches("\\d+")) {
            return Long.parseLong(numberStr);
        } else {
            throw new InvalidRequestException("The first parameter should be a natural number or zero.");
        }
    }

    private int parseSecondParameter(String countStr) {
        if (countStr.matches("[1-9]\\d*")) {
            return Integer.parseInt(countStr);
        } else {
            throw new InvalidRequestException("The second parameter should be a natural number.");
        }
    }

    private UserRequest validateOneParameter(String numberStr) {
        return new SingleRequest(parseFirstParameter(numberStr));
    }

    private UserRequest validateTwoParameters(String startStr, String countStr) {
        long start = parseFirstParameter(startStr);
        int count = parseSecondParameter(countStr);

        return new ListRequest(start, count);
    }

    private UserRequest validateTwoParametersWithProperties(String[] tokens) {
        long start = parseFirstParameter(tokens[0]);
        int count = parseSecondParameter(tokens[1]);

        Set<Property> included = new HashSet<>();
        Set<Property> excluded = new HashSet<>();

        Set<String> wrongCommands = new LinkedHashSet<>();
        for (int i = 2; i < tokens.length; i++) {
            boolean isExcluded = tokens[i].startsWith("-");
            String propertyStr = (isExcluded) ? tokens[i].substring(1) : tokens[i];
            Set<Property> targetSet = (isExcluded) ? excluded : included;

            try {
                Property property = Property.valueOf(propertyStr.toUpperCase());
                targetSet.add(property);
            } catch (IllegalArgumentException iae) {
                wrongCommands.add(propertyStr.toUpperCase());
            }
        }

        if (!wrongCommands.isEmpty()) {
            String noun = (wrongCommands.size() == 1) ? "property " : "properties ";
            String verb = (wrongCommands.size() == 1) ? " is" : " are";
            String message = "The " + noun + wrongCommands + verb +  " wrong.\n";
            message += ("Available properties: " + Arrays.toString(Property.values()));
            throw new InvalidRequestException(message);
        }

        for (Set<Property> pair : MUTUAL_EXCLUSIVE) {
            if (included.containsAll(pair)) {
                String message = "The request contains mutually exclusive properties: " + pair + '\n';
                message += "There are no numbers with these properties.";
                throw new InvalidRequestException(message);
            }
        }

        for (Set<Property> pair : MUTUAL_EXCLUSIVE_EXCLUDED) {
            if (excluded.containsAll(pair)) {
                List<String> parts = new ArrayList<>();
                for (Property p : pair) {
                    parts.add("-" + p.name());
                }
                String formatted = "[" + String.join(", ", parts) + "]";
                String message = "The request contains mutually exclusive properties: " + formatted + '\n';
                message += "There are no numbers with these properties.";
                throw new InvalidRequestException(message);
            }
        }

        for (Property p : included) {
            if (excluded.contains(p)) {
                String message = "The request contains mutually exclusive properties: [" + p + ", -" + p + "]\n";
                message += "There are no numbers with these properties.";
                throw new InvalidRequestException(message);
            }
        }

        return new ListWithPropertiesRequest(start, count, included, excluded);
    }
}