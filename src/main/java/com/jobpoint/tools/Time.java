package com.jobpoint.tools;

import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {
	public static long getTimeDifference(String format, String openTime, String closeTime) {
		Date starTime;
		Date endTime;
		
		long milisecondDifference;
		
		try {
			starTime = new SimpleDateFormat(format).parse(openTime);
			endTime = new SimpleDateFormat(format).parse(closeTime);
			
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(starTime);
			
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endTime);
			
			long milisecond1 = startCal.getTimeInMillis();
			long milisecond2 = endCal.getTimeInMillis();
			
			if(milisecond2 < milisecond1) {
				milisecondDifference = (24 * 60) - (milisecond1 - milisecond2)/(1000*60); 
			}else {
				milisecondDifference = (milisecond2 - milisecond1)/(1000*60);
			}
			
		   
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return milisecondDifference;
	}
	
	public static boolean isDifferentDay(String format, String openTime, String closeTime) {
		
		Date starTime;
		Date endTime;
		
		try {
			starTime = new SimpleDateFormat(format).parse(openTime);
			endTime = new SimpleDateFormat(format).parse(closeTime);
			
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(starTime);
			
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endTime);
			
			long milisecond1 = startCal.getTimeInMillis();
			long milisecond2 = endCal.getTimeInMillis();
			if(milisecond2 < milisecond1) {
				return true;
			}
		   
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static String addMinute(String time, int minute) {
		try {
		String newTime = time;
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date;
			date = dateFormat.parse(newTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, minute);
			String newTimeString = dateFormat.format(calendar.getTime());
			return newTimeString;
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return time;
	}
	
	public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//getTimeDifference("HH:mm", "04:30", "00:00");
					String newTime = addMinute("23:40", 30);
					System.out.println(newTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

}
