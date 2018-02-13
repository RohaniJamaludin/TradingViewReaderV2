package com.jobpoint.gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jobpoint.controller.TradingController;
import com.jobpoint.object.Product;
import com.jobpoint.object.Trading;
import com.jobpoint.object.TradingDetails;

public class TradingView implements ActionListener{
	
	private JFrame frame;
	
	private JTextField enterActionText, enterPriceText, enterDateText, enterTimeText, exitActionText, exitPriceText, exitDateText, exitTimeText;
	private JButton saveButton, cancelButton;
	private String title;
	private List<Trading> tradingList;
	private Product product;
	private int indexRow, pairId;
	
	public TradingView(List<Trading> tradingList, Product product, int indexRow) {
/*		this.title = product.getName() + " Trading Id " + trading.getId();*/
		this.tradingList = tradingList;
		this.product = product;
		this.indexRow = indexRow;
		initialize();
		populateData();
	}
	
	private void initialize() {
		frame = new JFrame(title);
		
		GridBagConstraints c = new GridBagConstraints();
        c.gridheight = 1;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.BASELINE;
	
		JPanel tradingPanel = new JPanel(new GridBagLayout());
		tradingPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		tradingPanel.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        c.gridx = 0;
      	c.gridy = 0;
      	c.gridwidth = 3;
        c.weightx = 3;
        tradingPanel.add(new JLabel("Trading Details"), c);
              
        c.gridx = 0;
      	c.gridy = 1;
      	c.gridwidth = 7;
        c.weightx = 7;
        tradingPanel.add(new JSeparator(), c);
      	
        c.gridx = 0;
      	c.gridy = 2;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Enter Action"), c);
        enterActionText = new JTextField();
        c.gridx = 2;
      	c.gridy = 2;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(enterActionText, c);
        
        c.gridx = 0;
      	c.gridy = 3;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Enter Price"), c);
      	enterPriceText = new JTextField();
        c.gridx = 2;
      	c.gridy = 3;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(enterPriceText, c);

        c.gridx = 0;
        c.gridy = 4;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Enter Date"), c);
      	enterDateText = new JTextField();
        c.gridx = 2;
      	c.gridy = 4;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(enterDateText, c);
      		
      	c.gridx = 0;
      	c.gridy = 5;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Enter Time"), c);
        enterTimeText = new JTextField();
        c.gridx = 2;
      	c.gridy = 5;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(enterTimeText, c);
        
        c.gridx = 0;
      	c.gridy = 6;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Exit Action"), c);
        exitActionText = new JTextField();
        c.gridx = 2;
      	c.gridy = 6;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(exitActionText, c);
      		
      	c.gridx = 0;
      	c.gridy = 7;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Exit Price"), c);
        exitPriceText = new JTextField();
        c.gridx = 2;
      	c.gridy = 7;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(exitPriceText, c);
              
        c.gridx = 0;
      	c.gridy = 8;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Exit Date"), c);
        exitDateText = new JTextField();
        c.gridx = 2;
      	c.gridy = 8;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(exitDateText, c);
        
        c.gridx = 0;
      	c.gridy = 9;
      	c.gridwidth = 2;
        c.weightx = 2;
        tradingPanel.add(new JLabel("Exit Time"), c);
        exitTimeText = new JTextField();
        c.gridx = 2;
      	c.gridy = 9;
      	c.gridwidth = 5;
        c.weightx = 5;
        tradingPanel.add(exitTimeText, c);
		
		frame.getContentPane().add(tradingPanel, BorderLayout.PAGE_START);
        
		/***********************Button Panel Start**************************/
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		
		frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		
		/***************************Button Panel End ************************/
		
		frame.setSize(400,260);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
	    int x = (dimension.width-w)/2;
	    int y = (dimension.height-h)/2;
	    frame.setLocation(x, y);
	    
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		enterActionText.setEditable(false);
		enterPriceText.setEditable(false);
		enterDateText.setEditable(false);
		enterTimeText.setEditable(false);
		exitActionText.setEditable(false);

	}
	
	private void populateData() {
		String nextAction = "";
		for(Trading trading : tradingList) {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			
			if(trading.getIsEnter()) {
				enterActionText.setText(trading.getAction());
				enterPriceText.setText(trading.getPrice());
				enterDateText.setText(dateFormat.format(trading.getDate()));
				enterTimeText.setText(trading.getTime());
				if(trading.getAction().equals("sell")) {
					nextAction = "buy";
				}else {
					nextAction = "sell";
				}
			}else {
				exitActionText.setText(nextAction);
				exitPriceText.setText(trading.getPrice());
				exitDateText.setText(dateFormat.format(trading.getDate()));
				exitTimeText.setText(trading.getTime());
			}
			pairId = trading.getPairId();
		}
		
		if(tradingList.size() == 1) {
			exitActionText.setText(nextAction);
		}
		
		if(tradingList.size() == 2) {
			exitPriceText.setEditable(false);
			exitDateText.setEditable(false);
			exitTimeText.setEditable(false);
			saveButton.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(saveButton)) {

			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				java.util.Date exitDate = dateFormat.parse(exitDateText.getText() + " 00:00:00");
				java.util.Date enterDate = dateFormat.parse(enterDateText.getText() + " 00:00:00");
				
				
				new java.util.Date();
				TradingController tradingController = new TradingController();
				
				Trading trading = new Trading();
				
				trading.setAction(exitActionText.getText());
				trading.setPrice(exitPriceText.getText());
				trading.setDate(new Date(exitDate.getTime()));
				trading.setTime(exitTimeText.getText());
				trading.setIsEnter(false);
				trading.setPairId(pairId);
				trading.setProductId(product.getId());
				
				TradingDetails tradingDetails = new TradingDetails();
				
				if(enterActionText.getText().equals("sell")) {
					tradingDetails.setSellAction(enterActionText.getText());
					tradingDetails.setSellPrice(enterPriceText.getText());
					tradingDetails.setSellDate(new Date(enterDate.getTime()));
					tradingDetails.setSellTime(enterTimeText.getText());
					tradingDetails.setSellStatus(true);
					tradingDetails.setBuyAction(exitActionText.getText());
					tradingDetails.setBuyPrice(exitPriceText.getText());
					tradingDetails.setBuyDate(new Date(exitDate.getTime()));
					tradingDetails.setBuyTime(exitTimeText.getText());
					tradingDetails.setBuyStatus(false);
				}else {
					tradingDetails.setBuyAction(enterActionText.getText());
					tradingDetails.setBuyPrice(enterPriceText.getText());
					tradingDetails.setBuyDate(new Date(enterDate.getTime()));
					tradingDetails.setBuyTime(enterTimeText.getText());
					tradingDetails.setBuyStatus(true);
					tradingDetails.setSellAction(exitActionText.getText());
					tradingDetails.setSellPrice(exitPriceText.getText());
					tradingDetails.setSellDate(new Date(exitDate.getTime()));
					tradingDetails.setSellTime(exitTimeText.getText());
					tradingDetails.setSellStatus(false);
				}
				tradingDetails.setPairId(pairId);
				
				tradingController.updateTrading(trading, tradingDetails, indexRow);
				
				frame.dispose();
			}catch(Exception ex) {
				System.out.println(ex);
			}
			
		}
		
		if(e.getSource().equals(cancelButton)) {
			frame.dispose();
		}
	}

}
