package sit.int371.capstoneproject.entities;

public enum UserRoleEnum {
    admin("admin"),
    user("user"),
    guest("guest");

    private String value;

    // Constructor to initialize the value
    UserRoleEnum(String value) {
        this.value = value;
    }

    // Getter to retrieve the value
    public String getValue() {
        return value;
    }
}
