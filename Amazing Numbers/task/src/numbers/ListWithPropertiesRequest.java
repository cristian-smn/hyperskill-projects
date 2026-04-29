package numbers;

import java.util.Set;

public record ListWithPropertiesRequest(long start, int count, Set<Property> included, Set<Property> excluded) implements UserRequest {}
