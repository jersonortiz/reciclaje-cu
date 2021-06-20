/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reciclaje.app.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author estudiante
 */
public class CalendarUtils {
    
        public static Date getZeroTimeDate(Date parse) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        calendar.set(calendar.HOUR_OF_DAY, 0);
        calendar.set(calendar.MINUTE, 0);
        calendar.set(calendar.SECOND, 0);
        calendar.set(calendar.MILLISECOND, 0);

        parse = calendar.getTime();
        return parse;

    }
}
