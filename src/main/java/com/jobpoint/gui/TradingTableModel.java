package com.jobpoint.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jobpoint.object.TradingDetails;
import com.jobpoint.tools.Parser;

public class TradingTableModel extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TradingDetails> tradingDetailsList;
	private final String[] columnNames = new String[] {
            "ID", "Enter", "Price",  "Date", "Time", "Exit", "Price",  "Date", "Time", "Profit", "Pair ID"};


	public TradingTableModel(List<TradingDetails> tradingDetailsList) {
		this.tradingDetailsList = tradingDetailsList;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return tradingDetailsList.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {

		TradingDetails row = tradingDetailsList.get(rowIndex);
		
        if(0 == columnIndex) {
            return rowIndex + 1;
        }
        
        else if(1 == columnIndex) {
            if(row.getSellStatus()) {
            	return row.getSellAction();
            }else {
            	return row.getBuyAction();
            }
        }
        
        else if(2 == columnIndex) {
        	if(row.getSellStatus()) {
            	return row.getSellPrice();
            }else {
            	return row.getBuyPrice();
            }
        }
        
        else if(3 == columnIndex) {
        	if(row.getSellStatus()) {
            	return row.getSellDate();
            }else {
            	return row.getBuyDate();
            }
        }
        
        else if(4 == columnIndex) {
        	if(row.getSellStatus()) {
            	return row.getSellTime();
            }else {
            	return row.getBuyTime();
            }
        }
        
        else if(5 == columnIndex) {
        	if(!row.getSellStatus()) {
            	return row.getSellAction();
            }else {
            	return row.getBuyAction();
            }
        }
        
        else if(6 == columnIndex) {
        	if(!row.getSellStatus()) {
            	return row.getSellPrice();
            }else {
            	return row.getBuyPrice();
            }
        }
        
        else if(7 == columnIndex) {
        	if(!row.getSellStatus()) {
            	return row.getSellDate();
            }else {
            	return row.getBuyDate();
            }
        }
        
        else if(8 == columnIndex) {
        	if(!row.getSellStatus()) {
            	return row.getSellTime();
            }else {
            	return row.getBuyTime();
            }
        }
        
        else if(9 == columnIndex) {
        	String profit = "";
        	if(row.getSellPrice() != null && row.getBuyPrice() != null) {
        		profit = Parser.getProfitLose(row.getSellPrice(), row.getBuyPrice());
        	}
        	return profit;
        }
        
        else if(10 == columnIndex) {
        	return row.getPairId();
        }


        return null;
	}
	
	public String getColumnName(int column){
		return columnNames[column];
	}
	 
	public void addTrading(TradingDetails tradingDetails) {
		tradingDetailsList.add(tradingDetails);
		fireTableRowsInserted(tradingDetailsList.size() -1, tradingDetailsList.size() -1);
	}
		    
	public void removeTrading(int rowIndex) {
		tradingDetailsList.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}
		
	public void updateTrading(int rowIndex, TradingDetails tradingDetails) {
		tradingDetailsList.set(rowIndex, tradingDetails);
		this.fireTableRowsUpdated(rowIndex, rowIndex);
/*		Double totalProfit = Double.parseDouble(Index.profitTotalText.getText());
		totalProfit += Double.parseDouble(strategy.getProfit());
		StrategyIndex.profitTotalText.setText(totalProfit.toString());*/
	}
}
