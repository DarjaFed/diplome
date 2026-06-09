package data;

public class DataHelper {

    public static String approvedCard() {
        return "4444 4444 4444 4441";
    }

    public static String declinedCard() {
        return "4444 4444 4444 4442";
    }

    public static String invalidCard() {
        return "1234 1234 1234 1234";
    }

    public static String validMonth() {
        return "12";
    }

    public static String invalidMonth13() {
        return "13";
    }

    public static String invalidMonth00() {
        return "00";
    }

    public static String validYear() {
        return "26";
    }

    public static String expiredYear() {
        return "20";
    }

    public static String futureYear() {
        return "35";
    }

    public static String validOwner() {
        return "IVAN IVANOV";
    }

    public static String russianOwner() {
        return "ИВАН ИВАНОВ";
    }

    public static String ownerWithNumbers() {
        return "IVAN123";
    }

    public static String ownerSpecialChars() {
        return "IV@N";
    }

    public static String shortOwner() {
        return "I";
    }

    public static String longOwner() {
        return "IVAN IVANOV IVANOV IVANOV";
    }

    public static String validCVV() {
        return "123";
    }

    public static String shortCVV() {
        return "12";
    }

    public static String longCVV() {
        return "1234";
    }

    public static String zeroCVV() {
        return "000";
    }
}