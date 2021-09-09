package br.com.urso.utils;

import org.apache.tomcat.jni.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;


public class DataUtils {

    public static int getAgeFromDate(LocalDate s){
        LocalDate today = LocalDate.now();
        return Period.between(s,today).getYears();
    }

    public static LocalDateTime convertLocalToLocalDateTime(LocalDate dt){
        return dt.atTime(00,00);
    }
}
