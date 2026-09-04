package dk.missingbikes.model;

public enum Color {
    RED("Red"),
    DARK_RED("Dark Red"),
    ORANGE("Orange"),
    YELLOW("Yellow"),
    LIME("Lime"),
    GREEN("Green"),
    DARK_GREEN("Dark Green"),
    TEAL("Teal"),
    TURQUOISE("Turquoise"),
    BLUE("Blue"),
    LIGHT_BLUE("Light Blue"),
    NAVY("Navy"),
    PURPLE("Purple"),
    VIOLET("Violet"),
    PINK("Pink"),
    MAGENTA("Magenta"),
    BLACK("Black"),
    WHITE("White"),
    OFF_WHITE("Off White"),
    GRAY("Gray"),
    LIGHT_GRAY("Light Gray"),
    DARK_GRAY("Dark Gray"),
    BROWN("Brown"),
    BEIGE("Beige"),
    TAN("Tan"),
    SILVER("Silver"),
    GOLD("Gold"),
    BRONZE("Bronze"),
    MULTICOLOR("Multicolor"),
    OTHER("Other");

    private final String displayName;

    Color(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
