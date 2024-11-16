package sit.int371.capstoneproject.entities;

public enum DormitoryStatusEnum {
    empty("empty"),
    full("full");

    private String value;

    // Constructor to initialize the value
    DormitoryStatusEnum(String value) {
        this.value = value;
    }

    // Getter to retrieve the value
    public String getValue() {
        return value;
    }

}
