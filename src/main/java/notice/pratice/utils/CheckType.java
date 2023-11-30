package notice.pratice.utils;

public class CheckType {
    public static boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isDouble(String strValue) {
        try {
            Double.parseDouble(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isLong(String strValue) {
        try {
            Long.parseLong(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
