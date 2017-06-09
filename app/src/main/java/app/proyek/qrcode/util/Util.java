package app.proyek.qrcode.util;

import java.text.DecimalFormat;

/**
 * Created by WINDOWS 10 on 09/05/2017.
 */

public class Util {

    public static String convertToCurrency(String value) {
        return convertToCurrency(value, "#,###,###");
    }

    private static String convertToCurrency(String value, String format) {
        DecimalFormat formatter = new DecimalFormat(format);
        return formatter.format(Double.parseDouble(value)).replace(",", ".");
    }

}
