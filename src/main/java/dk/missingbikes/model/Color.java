package dk.missingbikes.model;

public enum Color {
        BEIGE("Beige"),
        BLACK("Black"),
        BLUE("Blue"),
        BRONZE("Bronze"),
        BROWN("Brown"),
        DARK_GRAY("Dark Gray"),
        DARK_GREEN("Dark Green"),
        DARK_RED("Dark Red"),
        GOLD("Gold"),
        GRAY("Gray"),
        GREEN("Green"),
        LIGHT_BLUE("Light Blue"),
        LIGHT_GRAY("Light Gray"),
        LIME("Lime"),
        MAGENTA("Magenta"),
        MULTICOLOR("Multicolor"),
        NAVY("Navy"),
        OFF_WHITE("Off White"),
        ORANGE("Orange"),
        PINK("Pink"),
        PURPLE("Purple"),
        RED("Red"),
        SILVER("Silver"),
        TAN("Tan"),
        TEAL("Teal"),
        TURQUOISE("Turquoise"),
        VIOLET("Violet"),
        WHITE("White"),
        YELLOW("Yellow");

    private final String displayName;

    Color(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
