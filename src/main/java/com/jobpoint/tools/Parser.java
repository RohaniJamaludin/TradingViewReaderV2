package com.jobpoint.tools;

import java.text.DecimalFormat;

public class Parser {
	
	public static String parseString(String s) {
		return s;
	}
	
	public static String getProfitLose(String sellPrice, String buyPrice) {
		
		String[] sellPriceArray = parsePrice(sellPrice);
		String[] buyPriceArray = parsePrice(buyPrice);
		
		
		double summationOfProfit = (Integer.valueOf(sellPriceArray[0]) * Double.valueOf(sellPriceArray[1]))
				- (Integer.valueOf(buyPriceArray[0]) * Double.valueOf(buyPriceArray[1]));
		
		DecimalFormat df = new DecimalFormat("######.######");
		
		String profit = String.valueOf(df.format(summationOfProfit));

		if(sellPriceArray[2].equals("'") || buyPriceArray[2].equals("'")) {
			profit = profit.replace(".", "'");
		}
		
		return profit;
	}

	
	private static String[] parsePrice(String string){
		String symbol ="";
		
		if(string.contains(".")) {
			symbol = ".";
		}
		
		if(string.contains(",")) {
			symbol = ",";
		}
		
		if(string.contains("'")) {
			symbol = "'";
		}
		
		double decimalPoint = 1;

		if(!symbol.equals("")) {
			int index = string.indexOf(symbol);
			int length = string.length();
			decimalPoint = 1/Double.valueOf(Math.pow(10, (length - 1)- index)); 
		}
		
		String numberString = string.replace(symbol, "");
		
		String[] number = {numberString, String.valueOf(decimalPoint), symbol};
		
		return number;
	}

	public static String calculateProfit(String firstValue, String secondValue) {

		String[] firstValueArray = parsePrice(firstValue);
		String[] secondValueArray = parsePrice(secondValue);
		
		double summationOfProfit = (Integer.valueOf(firstValueArray[0]) * Double.valueOf(firstValueArray[1]) )
				+ (Integer.valueOf(secondValueArray[0]) * Double.valueOf(secondValueArray[1]));
		
		DecimalFormat df = new DecimalFormat("######.######");
		
		
		String profit = String.valueOf(df.format(summationOfProfit));

		if(firstValueArray[2].equals("'") || secondValueArray[2].equals("'")) {
			profit = profit.replace(".", "'");
		}
		
		return profit;
	}
}
