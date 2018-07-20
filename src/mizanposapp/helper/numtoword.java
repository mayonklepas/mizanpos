/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mizanposapp.helper;

/**
 *
 * @author MAZIN
 */
public class numtoword {

    private static String[] onesMapping
            = new String[]{
                "Nol", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan",
                "Sepuluh", "Sebelas", "Dua Belas", "Tiga Belas", "Empat Belas", "Lima Belas", "Enam Belas", "Tujuh Belas", "Delapan Belas", "Sembilan Belas"
            };
    private static String[] tensMapping
            = new String[]{
                "Dua Puluh", "Tiga Puluh", "Empat Puluh", "Lima Puluh", "Enam Puluh", "Tujuh Puluh", "Delapan Puluh", "Sembilan Puluh"
            };
    private static String[] groupMapping
            = new String[]{
                "Ratus", "Ribu", "Juta", "Milyar", "Triliun", "Quadrillion", "Quintillion", "Sextillian",
                "Septillion", "Octillion", "Nonillion", "Decillion", "Undecillion", "Duodecillion", "Tredecillion",
                "Quattuordecillion", "Quindecillion", "Sexdecillion", "Septendecillion", "Octodecillion", "Novemdecillion",
                "Vigintillion", "Unvigintillion", "Duovigintillion", "10^72", "10^75", "10^78", "10^81", "10^84", "10^87",
                "Vigintinonillion", "10^93", "10^96", "Duotrigintillion", "Trestrigintillion"
            };

    // NOTE: 10^303 is approaching the limits of double, as ~1.7e308 is where we are going
    // 10^303 is a centillion and a 10^309 is a duocentillion
    public static String TerbilangIndonesia(int number) {
        return KonvertToWord((long) number);
    }

    public static String TerbilangIndonesia(long number) {
        return KonvertToWord((double) number);
    }

    public static String TerbilangIndonesia(double number) {
        String result = "";
        String str = KonvertToWord(number);
        result = str.replaceAll("null", "");
        result = result.substring(0, result.length() - 1);
        return result;// KonvertToWord(number);
    }

    private static String KonvertToWord(double number) {
        String sign = null;
        if (number < 0) {
            sign = "Negatif";
            number = Math.abs(number);
        }

        if (number == 0) {
            return "Nol";
        }

        int decimalDigits = 0;
        //Console.WriteLine(number);
        while (number < 1 || (number - Math.floor(number) > 1e-10)) {
            number *= 10;
            decimalDigits++;
        }
        //Console.WriteLine("Total Decimal Digits: {0}", decimalDigits);

        String decimalString = null;
        while (decimalDigits-- > 0) {
            int digit = (int) (number % 10);
            number /= 10;
            decimalString = onesMapping[digit] + " " + decimalString;
        }

        String retVal = null;
        String Puluhan = null;
        int group = 0;
        if (number < 1) {
            retVal = onesMapping[0];
        } else {
            while (number >= 1) {
                int numberToProcess = (number >= 1e16) ? 0 : (int) (number % 1000);
                number = number / 1000;

                String groupDescription = ProcessGroup(numberToProcess);
                if (groupDescription != null) {
                    if (group > 0) {
                        retVal = groupMapping[group] + " " + retVal;
                    }
                    retVal = groupDescription + " " + retVal;

                    retVal = retVal.replace("Satu Ribu ", "Seribu ");
                    retVal = retVal.replace(" Seribu ", " Satu Ribu ");
                }
                group++;
            }
        }
        return String.format("%s%s%s",
                (String.valueOf(sign) != "null" ? sign + " " : ""),
                retVal,
                (String.valueOf(decimalString) != "null" ? "Koma " + decimalString : ""));
    }

    private static String ProcessGroup(int number) {
        int tens = number % 100;
        int hundreds = number / 100;

        String retVal = null;
        if (hundreds == 1) {
            retVal = "Se" + groupMapping[0].toLowerCase();
        }
        if (hundreds > 1) {
            retVal = onesMapping[hundreds] + " " + groupMapping[0];
        }
        if (tens > 0) {
            if (tens < 20) {
                retVal += ((retVal != null) ? " " : "") + onesMapping[tens];
            } else {
                int ones = tens % 10;
                tens = (tens / 10) - 2; // 20's offset

                retVal += ((retVal != null) ? " " : "") + tensMapping[tens];

                if (ones > 0) {
                    retVal += ((retVal != null) ? " " : "") + onesMapping[ones];
                }
            }
        }

        return retVal;
    }
}
