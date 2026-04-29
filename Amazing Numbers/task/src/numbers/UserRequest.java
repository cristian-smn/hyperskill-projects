package numbers;

public sealed interface UserRequest permits SingleRequest, ListRequest, ListWithPropertiesRequest {}