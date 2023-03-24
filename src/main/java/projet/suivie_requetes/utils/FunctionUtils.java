package projet.suivie_requetes.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class FunctionUtils {
    public static Long convertStringToLong(String str){
        try {
            return Long.valueOf(str);
        }catch (NumberFormatException e){
            return null;
        }
    }
    public static Date convertStringToDate(String str){
        try {
            if (str.isEmpty()){
                return null;
            }else {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
                return date;
            }
        }catch (Exception e){
            return null;
        }
    }

    /*public static java.sql.Date convertUtilToSql(java.util.Date date){
        return java.sql.Date.valueOf(date.toString());
    }*/
}
