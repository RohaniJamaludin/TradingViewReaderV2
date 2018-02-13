package com.jobpoint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;

import org.openqa.selenium.NoSuchWindowException;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.jobpoint.controller.ApplicationController;
import com.jobpoint.controller.ProductController;
import com.jobpoint.controller.TradingController;
import com.jobpoint.object.Product;
import com.jobpoint.object.Trading;
import com.jobpoint.object.TradingDetails;
import com.jobpoint.tools.LimitDocumentFilter;
import com.jobpoint.tools.Parser;

public class ProductView implements ActionListener, ItemListener, WindowListener{
	
	private JFrame frame;
	private JTable table;
	private TradingTableModel model;
	
	private JTextField nameText, symbolText, symbolDescriptionText, urlChartText, totalProfitText, urlPtaText, lotText;
	private JTextField skipTimeSpanText;
	private JCheckBox checkBoxMarket, checkBoxPauseTrading, checkBoxMonday, checkBoxTuesday, checkBoxWednesday;
	private JCheckBox checkBoxThursday, checkBoxFriday, checkBoxSaturday, checkBoxSunday, checkBoxSkip;
	private JComboBox<String> dayCombo, orderTypeCombo;
	private TimePicker openTimePicker, closeTimePicker, pauseTradingTimeFromPicker, pauseTradingTimeToPicker ;
	private JButton saveButton, saveRunButton, stopButton, closeButton;
	private JPanel marketPanel, pauseTradingPanel, buttonPanel, skipPanel;
	private boolean isNew, isChartOn;
	private int rowIndex, id;
	
	public ProductView(boolean isNew, Product product, List<TradingDetails> tradingDetailsList, int rowIndex) {
		initialize();
		this.isNew = isNew;
		this.rowIndex = rowIndex;
		this.stopButton.setEnabled(false);
		if(isNew) {
			setupNew();
			id = 0;
		}else {
			this.id = product.getId();
			frame.setTitle(product.getName());
			populateData(product, tradingDetailsList);
			ApplicationController.MODELMAP.put(id, model);
			ApplicationController.SKIPTRADEMAP.put(id, 0);
		}
	}

	private void initialize() {

		frame = new JFrame("New Product");
		
		
		/*************************Product Panel Start**************************/
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridheight = 1;
		c1.weighty = 0.1;
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.anchor = GridBagConstraints.BASELINE;
		
        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        productPanel.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridwidth = 3;
        c1.weightx = 3;
        productPanel.add(new JLabel("Product Details"), c1);
        
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 7;
        c1.weightx = 7;
        productPanel.add(new JSeparator(), c1);
		
        c1.gridx = 0;
        c1.gridy = 2;
        c1.gridwidth = 2;
        c1.weightx = 2;
        productPanel.add(new JLabel("Name"), c1);
		nameText = new JTextField();
		c1.gridx = 2;
		c1.gridy = 2;
		c1.gridwidth = 5;
		c1.weightx = 5;
        productPanel.add(nameText, c1);
        ((AbstractDocument)nameText.getDocument()).setDocumentFilter(new LimitDocumentFilter(50));

        c1.gridx = 0;
        c1.gridy = 3;
        c1.gridwidth = 2;
        c1.weightx = 2;
        productPanel.add(new JLabel("Symbol"), c1);
		symbolText = new JTextField();
		c1.gridx = 2;
		c1.gridy = 3;
		c1.gridwidth = 5;
		c1.weightx = 5;
        productPanel.add(symbolText, c1);
        ((AbstractDocument)symbolText.getDocument()).setDocumentFilter(new LimitDocumentFilter(10));
		
        c1.gridx = 0;
        c1.gridy = 4;
        c1.gridwidth = 2;
        c1.weightx = 2;
        productPanel.add(new JLabel("Symbol Description"), c1);
        symbolDescriptionText = new JTextField();
        c1.gridx = 2;
        c1.gridy = 4;
        c1.gridwidth = 5;
        c1.weightx = 5;
        productPanel.add(symbolDescriptionText, c1);
        ((AbstractDocument)symbolDescriptionText.getDocument()).setDocumentFilter(new LimitDocumentFilter(50));
		
        c1.gridx = 0;
        c1.gridy = 5;
        c1.gridwidth = 2;
        c1.weightx = 2;
        productPanel.add(new JLabel("Chart URL"), c1);
        urlChartText = new JTextField();
        c1.gridx = 2;
        c1.gridy = 5;
        c1.gridwidth = 5;
        c1.weightx = 5;
        productPanel.add(urlChartText, c1);
        urlChartText.setText("https://www.tradingview.com/chart");
        ((AbstractDocument)urlChartText.getDocument()).setDocumentFilter(new LimitDocumentFilter(100));
        
        c1.gridx = 0;
        c1.gridy = 6;
        c1.gridwidth = 2;
        c1.weightx = 2;
        productPanel.add(new JLabel("PTA URL(use ',' to separate URLs)"), c1);
        urlPtaText = new JTextField();
        c1.gridx = 2;
        c1.gridy = 6;
        c1.gridwidth = 5;
        c1.weightx = 5;
        productPanel.add(urlPtaText, c1);
        urlPtaText.setText("http://127.0.0.1:8872/");
        ((AbstractDocument)urlPtaText.getDocument()).setDocumentFilter(new LimitDocumentFilter(150));
        
        String[] orderTypes = {"Limit", "Market", "Limit - Market", "Market - Limit"};
        
        c1.gridx = 0;
        c1.gridy = 7;
        c1.gridwidth = 2;
        c1.weightx = 2;
        productPanel.add(new JLabel("Order Type"), c1);
        orderTypeCombo = new JComboBox<String>(orderTypes);
        c1.gridx = 2;
        c1.gridy = 7;
        c1.gridwidth = 5;
        c1.weightx = 5;
        productPanel.add(orderTypeCombo, c1);
        orderTypeCombo.setSelectedIndex(0);
        
        c1.gridx = 0;
        c1.gridy = 8;
        c1.gridwidth = 2;
        c1.weightx = 2;
        productPanel.add(new JLabel("No. of Lot"), c1);
		lotText = new JTextField();
		c1.gridx = 2;
		c1.gridy = 8;
		c1.gridwidth = 5;
		c1.weightx = 5;
        productPanel.add(lotText, c1);
        lotText.setText("1");
		
        /******************Skip First Alert Start************************/
        
        c1.gridx = 0;
        c1.gridy = 9;
        c1.gridwidth = 3;
        c1.weightx = 3;
        checkBoxSkip = new JCheckBox("Skip First Alert");
        checkBoxSkip.addItemListener(this);
        productPanel.add(checkBoxSkip, c1);
        
        c1.gridx = 0;
        c1.gridy = 10;
        c1.gridwidth = 7;
        c1.weightx = 7;
        c1.gridheight = 2;
        c1.weighty = 2;
        c1.gridheight = 1;
        c1.weighty = 0.1;
        skipPanel = new JPanel(new GridBagLayout());
        skipPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        productPanel.add(skipPanel, c1);
        
        skipPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#C0C0C0")));
    	
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridheight = 1;
        c2.weighty = 0.1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.BASELINE;
        
        c2.gridx = 0;
        c2.gridy = 0;
        c2.gridwidth = 1;
        c2.weightx = 1;
        skipPanel.add(new JLabel("Time Span(in minutes)"), c2);
        skipTimeSpanText = new JTextField("0");
        c2.gridx = 1;
        c2.gridy = 0;
        c2.gridwidth = 2;
        c2.weightx = 2;
        skipPanel.add(skipTimeSpanText, c2);
        
        /******************Skip First Alert End************************/
        
		
        /******************Market Opening/Closing Start************************/
        c1.gridx = 0;
        c1.gridy = 11;
        c1.gridwidth = 3;
        c1.weightx = 3;
        checkBoxMarket = new JCheckBox("Market Open/Close");
        checkBoxMarket.addItemListener(this);
        productPanel.add(checkBoxMarket, c1);
        
        
        c1.gridx = 0;
        c1.gridy = 12;
        c1.gridwidth = 7;
        c1.weightx = 7;
        c1.gridheight = 2;
        c1.weighty = 2;
        c1.gridheight = 1;
        c1.weighty = 0.1;
        marketPanel = new JPanel(new GridBagLayout());
        marketPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        productPanel.add(marketPanel, c1);
		
        marketPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#C0C0C0")));
	
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridheight = 1;
        c3.weighty = 0.1;
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.anchor = GridBagConstraints.BASELINE;
        
        c3.gridx = 0;
        c3.gridy = 0;
        c3.gridwidth = 2;
        c3.weightx = 2;
        marketPanel.add(new JLabel("Day"), c3);
        
        checkBoxMonday = new JCheckBox("Monday");
        c3.gridx = 0;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        c3.weighty = 0.3;
        marketPanel.add(checkBoxMonday, c3);
        
        checkBoxTuesday = new JCheckBox("Tuesday");
        c3.gridx = 1;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(checkBoxTuesday, c3);
        
        checkBoxWednesday = new JCheckBox("Wednesday");
        c3.gridx = 2;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(checkBoxWednesday, c3);
        
        checkBoxThursday = new JCheckBox("Thursday");
        c3.gridx = 3;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(checkBoxThursday, c3);
        
        checkBoxFriday = new JCheckBox("Friday");
        c3.gridx = 4;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(checkBoxFriday, c3);
        
        checkBoxSaturday = new JCheckBox("Saturday");
        c3.gridx = 5;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(checkBoxSaturday, c3);
        
        checkBoxSunday = new JCheckBox("Sunday");
        c3.gridx = 6;
        c3.gridy = 1;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(checkBoxSunday, c3);
        
        TimePickerSettings tpSetting = new TimePickerSettings();
        tpSetting.setAllowEmptyTimes(false);
        tpSetting.setDisplayToggleTimeMenuButton(false);
        tpSetting.setDisplaySpinnerButtons(true);
        tpSetting.setFormatForDisplayTime("HH:mm");
        
        c3.gridx = 0;
        c3.gridy = 3;
        c3.gridwidth = 1;
        c3.weightx = 2;
        marketPanel.add(new JLabel("Time"), c3);
        
        c3.gridx = 0;
        c3.gridy = 4;
        c3.gridwidth = 1;
        c3.weightx = 2;
        marketPanel.add(new JLabel("From"), c3);
        
        openTimePicker = new TimePicker(tpSetting);
        c3.gridx = 1;
        c3.gridy = 4;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(openTimePicker, c3);
        
        c3.gridx = 3;
        c3.gridy = 4;
        c3.gridwidth = 1;
        c3.weightx = 1;
        marketPanel.add(new JLabel("To"), c3);
        
        closeTimePicker = new TimePicker(tpSetting);
        c3.gridx = 4;
        c3.gridy = 4;
        c3.gridwidth = 2;
        c3.weightx = 2;
        marketPanel.add(closeTimePicker, c3);
        
        /************ Market Opening/Closing Panel End***************/
        
        
        /************ Pause Trading Panel Start***************/
        
        c1.gridx = 0;
        c1.gridy = 13;
        c1.gridwidth = 3;
        c1.weightx = 3;
        checkBoxPauseTrading = new JCheckBox("Pause Trading");
        checkBoxPauseTrading.addItemListener(this);
        productPanel.add(checkBoxPauseTrading, c1);
		
        c1.gridx = 0;
        c1.gridy = 14;
        c1.gridwidth = 7;
        c1.weightx = 7;
        c1.gridheight = 2;
        c1.weighty = 2;
        c1.gridheight = 1;
        c1.weighty = 0.1;
        pauseTradingPanel = new JPanel(new GridBagLayout());
        pauseTradingPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        productPanel.add(pauseTradingPanel, c1);
        
        pauseTradingPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#C0C0C0")));

        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridheight = 1;
        c4.weighty = 0.1;
        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.anchor = GridBagConstraints.BASELINE;
        
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; 

        c4.gridx = 0;
        c4.gridy = 0;
        c4.gridwidth = 1;
        c4.weightx = 1;
        pauseTradingPanel.add(new JLabel("Day"), c4);
        dayCombo = new JComboBox<String>(days);
        c4.gridx = 1;
        c4.gridy = 0;
        c4.gridwidth = 2;
        c4.weightx = 2;
        pauseTradingPanel.add(dayCombo, c4);
        dayCombo.setSelectedIndex(0);
        
        
        c4.gridx = 0;
        c4.gridy = 1;
        c4.gridwidth = 1;
        c4.weightx = 2;
        pauseTradingPanel.add(new JLabel("From"), c4);
        
        pauseTradingTimeFromPicker = new TimePicker(tpSetting);
        c4.gridx = 1;
        c4.gridy = 1;
        c4.gridwidth = 1;
        c4.weightx = 1;
        pauseTradingPanel.add(pauseTradingTimeFromPicker, c4);
        
        c4.gridx = 3;
        c4.gridy = 1;
        c4.gridwidth = 1;
        c4.weightx = 1;
        pauseTradingPanel.add(new JLabel("   To"), c4);
        
        pauseTradingTimeToPicker = new TimePicker(tpSetting);
        c4.gridx = 4;
        c4.gridy = 1;
        c4.gridwidth = 2;
        c4.weightx = 2;
        pauseTradingPanel.add(pauseTradingTimeToPicker, c4);
		
        frame.getContentPane().add(productPanel, BorderLayout.PAGE_START);
        
        
        /**********************Pause Trading Panel End**************************/
        
        c1.gridx = 0;
        c1.gridy = 15;
        c1.gridwidth = 1;
        c1.weightx = 1;
        productPanel.add(new JLabel("Total Profit"), c1);
        c1.gridx = 1;
        c1.gridy = 15;
        c1.gridwidth = 2;
        c1.weightx = 2;
        totalProfitText = new JTextField();
        productPanel.add(totalProfitText, c1);
        totalProfitText.setText("0.00");
        
        /*************************Product Panel End**************************/
        
        
        /*************************Trading Panel Start**************************/
        
        TradingController tradingController = new TradingController();
		List<TradingDetails> tradingDetailsList = tradingController.getAllTradingDetailsByForeignId("product", id);
		 
		model = new TradingTableModel(tradingDetailsList);
		
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(800, 80));
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) return;
				
				int viewRow = table.getSelectedRow();
				int viewColumn = table.getSelectedColumn();
				System.out.println("Selected row :" + viewRow + " , selected column : " + viewColumn);
				
			}
			
		});
		
		TableColumnModel tableColumnModel = table.getColumnModel();
		tableColumnModel.removeColumn(tableColumnModel.getColumn(0));
		tableColumnModel.removeColumn(tableColumnModel.getColumn(9));
		
        JPanel tradingPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tradingPanel.add(scrollPane, BorderLayout.PAGE_START);
		
		frame.getContentPane().add(tradingPanel, BorderLayout.CENTER);
		
		/*************************Trading Panel End**************************/
		
		
		/***********************Button Panel Start**************************/
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);
		
		saveRunButton = new JButton("Run");
		saveRunButton.addActionListener(this);
		buttonPanel.add(saveRunButton);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		buttonPanel.add(stopButton);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		
		/***************************Button Panel End ************************/
		
		frame.addWindowListener(this);
		frame.setSize(680,600);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
	    int x = (dimension.width-w)/2;
	    int y = (dimension.height-h)/2;
	    frame.setLocation(x, y);
	    
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void setupNew() {
		toggleMarketPanel(checkBoxMarket.isSelected());
		togglePauseTradingPanel(checkBoxPauseTrading.isSelected());
		toggleSkipFirstAlertPanel(checkBoxSkip.isSelected());
	}

	private void populateData(final Product product, List<TradingDetails> tradingDetailsList) {
		nameText.setText(product.getName());
		symbolText.setText(product.getSymbol());
		symbolDescriptionText.setText(product.getSymbolDescription());
		urlChartText.setText(product.getChartUrl());
		urlPtaText.setText(product.getPtaUrl());
		orderTypeCombo.setSelectedItem(product.getOrderType());
		lotText.setText(String.valueOf(product.getLot()));

		checkBoxMarket.setSelected(product.getIsMarketOpen());
		toggleMarketPanel(product.getIsMarketOpen());

		checkBoxMonday.setSelected(product.getIsMonday());
		checkBoxTuesday.setSelected(product.getIsTuesday());
		checkBoxWednesday.setSelected(product.getIsWednesday());
		checkBoxThursday.setSelected(product.getIsThursday());
		checkBoxFriday.setSelected(product.getIsFriday());
		checkBoxSaturday.setSelected(product.getIsSaturday());
		checkBoxSunday.setSelected(product.getIsSunday());
		
		openTimePicker.setText(product.getOpenTime());
		closeTimePicker.setText(product.getCloseTime());
		
		checkBoxSkip.setSelected(product.getIsSkip());
		toggleSkipFirstAlertPanel(product.getIsSkip());
		
		skipTimeSpanText.setText(String.valueOf(product.getSkipTimeSpan()));
		
		switch(product.getPauseDay()) {
		case "Monday" : dayCombo.setSelectedIndex(0);
		break;
		case "Tuesday" : dayCombo.setSelectedIndex(1);
		break;
		case "Wednesday" : dayCombo.setSelectedIndex(2);
		break;
		case "Thursday" : dayCombo.setSelectedIndex(3);
		break;
		case "Friday" : dayCombo.setSelectedIndex(4);
		break;
		case "Saturday" : dayCombo.setSelectedIndex(5);
		break;
		case "Sunday" : dayCombo.setSelectedIndex(6);
		break;
		default : dayCombo.setSelectedIndex(0);
		break;
		}
		
		pauseTradingTimeFromPicker.setText(product.getPauseTimeFrom());
		pauseTradingTimeToPicker.setText(product.getPauseTimeTo());
		
		checkBoxPauseTrading.setSelected(product.getIsTradingPause());
		togglePauseTradingPanel(product.getIsTradingPause());
		
		String totalProfit = "0";
		for(TradingDetails tradingDetails : tradingDetailsList ) {
			model.addTrading(tradingDetails);
		}
		
		TradingController tradingController = new TradingController();
		List<Trading> tradingList = tradingController.getAllTradingByForeignId("product", product.getId());
		for(Trading trading : tradingList ) {
			String multipler = "";
			if(trading.getAction().equals("buy")){
				multipler = "-";
			}
			totalProfit = Parser.calculateProfit(totalProfit, multipler+trading.getPrice());
		}
		
		totalProfitText.setText(totalProfit);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getClickCount() == 2) {
					int[] selection = table.getSelectedRows();
					if(selection.length > 0) {
						if(selection.length == 1) {
							int indexRow = selection[0];
							TradingController tradingController = new TradingController();
							int pairId = (Integer) model.getValueAt(indexRow, 10); 
							tradingController.editTrading(pairId, product, indexRow);
							table.getSelectionModel().clearSelection();
						}
					}
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				
				TradingController tradingController = new TradingController();
				List<Trading> tradingList = tradingController.getAllTradingByForeignId("product", product.getId());
				
				String totalProfit = "0";
				for(Trading trading : tradingList ) {
					String multipler = "";
					if(trading.getAction().equals("buy")){
						multipler = "-";
					}
					totalProfit = Parser.calculateProfit(totalProfit, multipler+trading.getPrice());
				}
				totalProfitText.setText(totalProfit);
			}
			
		});
	}
	
	private Product saveProduct() {
		Product product = new Product();
		product.setName(nameText.getText());
		product.setSymbol(symbolText.getText());
		product.setSymbolDescription(symbolDescriptionText.getText());
		product.setChartUrl(urlChartText.getText());
		product.setPtaUrl(urlPtaText.getText());
		product.setOrderType(orderTypeCombo.getSelectedItem().toString());
		product.setLot(Integer.parseInt(lotText.getText()));
		product.setIsMarketOpen(checkBoxMarket.isSelected());
		product.setIsMonday(checkBoxMonday.isSelected());
		product.setIsTuesday(checkBoxTuesday.isSelected());
		product.setIsWednesday(checkBoxWednesday.isSelected());
		product.setIsThursday(checkBoxThursday.isSelected());
		product.setIsFriday(checkBoxFriday.isSelected());
		product.setIsSaturday(checkBoxSaturday.isSelected());
		product.setIsSunday(checkBoxSunday.isSelected());
		product.setIsSkip(checkBoxSkip.isSelected());
		product.setSkipTimeSpan(Integer.parseInt(skipTimeSpanText.getText()));
		product.setOpenTime(openTimePicker.getText());
		product.setCloseTime(closeTimePicker.getText());
		product.setIsTradingPause(checkBoxPauseTrading.isSelected());
		product.setPauseDay(dayCombo.getSelectedItem().toString());
		product.setPauseTimeFrom(pauseTradingTimeFromPicker.getText());
		product.setPauseTimeTo(pauseTradingTimeToPicker.getText());
		
		ProductController productController = new ProductController();
		if(isNew) {
			id = productController.saveProduct(product);
			frame.setTitle(product.getName());
			isNew = false;
			rowIndex = MainWindow.model.getRowCount()-1;
			ApplicationController.MODELMAP.put(id, model);
			ApplicationController.SKIPTRADEMAP.put(id, 0);
		}else {
			product.setId(id);
			product.setTab("");
			productController.updateProduct(product, rowIndex);
		}
		
		return product;
	}
	
	private void toggleMarketPanel(boolean isSelected) {
		for (Component marketComponent : marketPanel.getComponents() ){
			marketComponent.setEnabled(isSelected);
		}
	}
	
	private void togglePauseTradingPanel(boolean isSelected) {
		for (Component pauseTradingComponent : pauseTradingPanel.getComponents() ){
			pauseTradingComponent.setEnabled(isSelected);
		}

	}
	
	private void toggleSkipFirstAlertPanel(boolean isSelected) {
		for (Component skipFirstAlertComponent : skipPanel.getComponents() ){
			skipFirstAlertComponent.setEnabled(isSelected);
		}

	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(saveButton)) {
			saveProduct();
		}
		
		if(event.getSource().equals(saveRunButton)) {
			Product product = saveProduct();
			ProductController productController = new ProductController();
			if(productController.runProduct(product, model)) {
				saveRunButton.setEnabled(false);
				stopButton.setEnabled(true);
				isChartOn = true;
			}
		} 
		
		if(event.getSource().equals(stopButton)) {
			ProductController productController = new ProductController();
			productController.stopProduct(id);
			stopButton.setEnabled(false);
			saveRunButton.setEnabled(true);
		}
		
		if(event.getSource().equals(closeButton)) {
			frame.dispose();
		}
	}

	public void itemStateChanged(ItemEvent e) {

		// TODO Auto-generated method stub
		if(e.getSource().equals(checkBoxMarket)) {
			toggleMarketPanel(checkBoxMarket.isSelected());
		}
		
		if(e.getSource().equals(checkBoxPauseTrading)) {
			togglePauseTradingPanel(checkBoxPauseTrading.isSelected());
		}
		
		if(e.getSource().equals(checkBoxSkip)) {
			toggleSkipFirstAlertPanel(checkBoxSkip.isSelected());
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			ProductController productController = new ProductController();

			if(isChartOn)
				ApplicationController.MODELMAP.remove(id);
			
			if(stopButton.isEnabled()) {
				productController.stopProduct(id);
			}
		}catch(NoSuchWindowException ex) {
			
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
