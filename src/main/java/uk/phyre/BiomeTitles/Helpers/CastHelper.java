package uk.phyre.biomeTitles.Helpers;

public final class CastHelper {
    public static Boolean parseBoolean(String str) {
        return switch (str.trim().toLowerCase()) {
            case "true", "enabled" -> true;
            case "false", "disabled" -> false;
            default -> null;
        };
    }
}
