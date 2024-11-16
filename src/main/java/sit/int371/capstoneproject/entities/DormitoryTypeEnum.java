package sit.int371.capstoneproject.entities;

public enum DormitoryTypeEnum {
    all("all"),
    f("f"),
    m("m");

    private String value;

    // Constructor to initialize the value
    DormitoryTypeEnum(String value) {
        this.value = value;
    }

    // Getter to retrieve the value
    public String getValue() {
        return value;
    }
}
