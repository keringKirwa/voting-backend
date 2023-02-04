package com.voting.votingsystem.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public  String dateFormatter(String dateToFormat) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM,dd,yyyy HH:mm ");


        Date date = inputFormat.parse(dateToFormat);
        return outputFormat.format(date);


    }

}
