package com.aionemu.gameserver.utils.rates;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.aionemu.gameserver.configs.main.RateConfig;

/**
 * @author Dr2co
 */

public class HolidayRates {

    private static Calendar calendar = GregorianCalendar.getInstance();

    public static int getHolidayRates(int accessLevel) {
        if (RateConfig.HOLIDAY_RATE_ENAMBLE) {
            Date date = new Date();
            calendar.setTime(date);
            int rate = 0;
            switch (accessLevel) {
                case 0:
                    rate = RateConfig.HOLIDAY_RATE_REGULAR;
                    break;
                case 1:
                    rate = RateConfig.HOLIDAY_RATE_PREMIUM;
                    break;
                case 2:
                    rate = RateConfig.HOLIDAY_RATE_VIP;
                    break;
            }
            for (String level : RateConfig.HOLIDAY_RATE_DAYS.split(",")) {
                if (calendar.get(Calendar.DAY_OF_WEEK) == Integer.parseInt(level)) {
                    return rate;
                }
            }
        }
        return 0;
    }
}

