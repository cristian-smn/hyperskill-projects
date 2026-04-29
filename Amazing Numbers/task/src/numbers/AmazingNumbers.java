package numbers;

import java.util.*;

public class AmazingNumbers {
    public record NumberResult(long number, Set<Property> trueProperties){}

    private NumberResult analyzeNumber(long number) {
        Set<Property> trueProperties = new LinkedHashSet<>();
        for (Property p : Property.values()) {
            if (p.test(number)) {
                trueProperties.add(p);
            }
        }
        return new NumberResult(number, trueProperties);
    }

    public NumberResult processRequest(SingleRequest userRequest) {
        return analyzeNumber(userRequest.start());
    }

    public List<NumberResult> processRequest(ListRequest userRequest) {
        long start = userRequest.start();
        int count = userRequest.count();
        List<NumberResult> numbersResult = new ArrayList<>();

        for (long i = start; i < start + count; i++) {
            numbersResult.add(analyzeNumber(i));
        }

        return numbersResult;
    }

    public List<NumberResult> processRequest(ListWithPropertiesRequest userRequest) {
        long start = userRequest.start();
        int count = userRequest.count();
        List<NumberResult> numbersResult = new ArrayList<>();

        long index = start;
        while (numbersResult.size() < count) {
            if (matches(start, userRequest.included(), userRequest.excluded())) {
                numbersResult.add(analyzeNumber(index));
            }
            index++;
        }

        return numbersResult;
    }

    private boolean matches(long number, Set<Property> included, Set<Property> excluded) {
        for (Property p : included) {
            if (!p.test(number)) {
                return false;
            }
        }

        for (Property p : excluded) {
            if (p.test(number)) {
                return false;
            }
        }

        return true;
    }
}
