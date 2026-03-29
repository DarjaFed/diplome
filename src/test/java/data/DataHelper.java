package data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
    private static final DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yy");

    public static String approvedCard() {
        return "4444 4444 4444 4441";
    }

    public static String declinedCard() {
        return "4444 4444 4444 4442";
    }

    public static String validMonth() {
        return LocalDate.now().plusMonths(1).format(format);
    }

    public static String validYear() {
        return LocalDate.now().plusYears(2).format(yearFormat);
    }

    public static String expiredYear() {
        return LocalDate.now().minusYears(1).format(yearFormat);
    }

    public static String tooFarYear() {
        return LocalDate.now().plusYears(8).format(yearFormat);
    }

    public static String validOwner() {
        return "IVAN IVANOV";
    }

    public static String validCVV() {
        return "123";
    }
}