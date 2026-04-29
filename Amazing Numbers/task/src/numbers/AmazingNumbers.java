package numbers;

import java.util.*;

public class AmazingNumbers {
    public record NumberResult(long number, Set<Property> trueProperties){}

    public NumberResult processRequest(SingleRequest userRequest) {
        long number = userRequest.start();
        Set<Property> trueProperties = new LinkedHashSet<>();
        for (Property p : Property.values()) {
            if (p.test(number)) {
                trueProperties.add(p);
            }
        }
        return new NumberResult(number, trueProperties);
    }

    public List<NumberResult> processRequest(ListRequest userRequest) {
        long start = userRequest.start();
        int count = userRequest.count();
        List<NumberResult> numbersResult = new ArrayList<>();

        for (long i = start; i < start + count; i++) {
            NumberResult nr = processRequest(new SingleRequest(i));
            numbersResult.add(nr);
        }

        return numbersResult;
    }

    public List<NumberResult> processRequest(ListWithPropertiesRequest userRequest) {
        long start = userRequest.start();
        int count = userRequest.count();
        List<NumberResult> numbersResult = new ArrayList<>();

        int index = 0;
        long i = start;
        while (index < count) {
            NumberResult nr = processRequest(new SingleRequest(i));
            if (nr.trueProperties().containsAll(userRequest.included()) &&
                    Collections.disjoint(nr.trueProperties(), userRequest.excluded())) {
                numbersResult.add(nr);
                index++;
            }
            i++;
        }

        return numbersResult;
    }
}
