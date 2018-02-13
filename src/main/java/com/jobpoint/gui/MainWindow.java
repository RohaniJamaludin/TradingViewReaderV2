package com.jobpoint.gui;

import java.awt.BorderLayout;
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
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.jobpoint.controller.ApplicationController;
import com.jobpoint.controller.ProductController;
import com.jobpoint.object.Application;
import com.jobpoint.object.Product;
import com.jobpoint.tools.DateLabelFormatter;
import com.jobpoint.tools.LimitDocumentFilter;

public class MainWindow implements ActionListener, WindowListener, ItemListener{
	public static JPanel footerPanel,sendEmailPanel;
	public static JFrame frame;
	private JTable table;
	private JButton addButton, exitButton, removeButton, restartButton, refreshButton;
	public static ProductTableModel model;
	private JPasswordField passwordText;
	private JTextField userNameText, emailText;
	private JCheckBox checkBoxSendEmail;
	private Application application;
	private JDatePickerImpl datePickerFrom, datePickerTo;
	private Date dateFrom, dateTo;
	
	public MainWindow() {
		getApplication();
		initialize();
	}
	
	private void getApplication() {
		application = ApplicationController.getApplication();
	}
	
	private void enableTextField(boolean flag) {
		userNameText.setEnabled(flag);
		passwordText.setEnabled(flag);
		emailText.setEnabled(flag);
		checkBoxSendEmail.setEnabled(flag);
	}
	
	private void initialize() {

		frame = new JFrame("Product");
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridheight = 1;
		c1.weighty = 0.1;
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.anchor = GridBagConstraints.BASELINE;
		
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        loginPanel.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridwidth = 3;
        c1.weightx = 3;
        loginPanel.add(new JLabel("Trading View Login"), c1);
        
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridwidth = 7;
        c1.weightx = 7;
        loginPanel.add(new JSeparator(), c1);
        
        c1.gridx = 0;
        c1.gridy = 2;
        c1.gridwidth = 2;
        c1.weightx = 2;
        loginPanel.add(new JLabel("UserName"), c1);
		userNameText = new JTextField(application.getTvAccount());
		c1.gridx = 2;
		c1.gridy = 2;
		c1.gridwidth = 5;
		c1.weightx = 5;
		loginPanel.add(userNameText, c1);
		((AbstractDocument)userNameText.getDocument()).setDocumentFilter(new LimitDocumentFilter(100));

        c1.gridx = 0;
        c1.gridy = 3;
        c1.gridwidth = 2;
        c1.weightx = 2;
        loginPanel.add(new JLabel("Password"), c1);
		passwordText = new JPasswordField(application.getTvPassword());
		c1.gridx = 2;
		c1.gridy = 3;
		c1.gridwidth = 5;
		c1.weightx = 5;
		loginPanel.add(passwordText, c1);
		((AbstractDocument)passwordText.getDocument()).setDocumentFilter(new LimitDocumentFilter(30));

		
		c1.gridx = 0;
        c1.gridy = 4;
        c1.gridwidth = 3;
        c1.weightx = 3;
        checkBoxSendEmail = new JCheckBox("Send Email Notification");
        checkBoxSendEmail.addItemListener(this);
        loginPanel.add(checkBoxSendEmail, c1);
        
        c1.gridx = 0;
        c1.gridy = 5;
        c1.gridwidth = 7;
        c1.weightx = 7;
        c1.gridheight = 2;
        c1.weighty = 2;
        c1.gridheight = 1;
        c1.weighty = 0.1;
        sendEmailPanel = new JPanel(new GridBagLayout());
        sendEmailPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        loginPanel.add(sendEmailPanel, c1);
        
        sendEmailPanel.setBorder(BorderFactory.createEmptyBorder());
    	
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridheight = 1;
        c2.weighty = 0.1;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.BASELINE;
        
        c2.gridx = 0;
        c2.gridy = 0;
        c2.gridwidth = 1;
        c2.weightx = 1;
        sendEmailPanel.add(new JLabel("Email(use ',' to separate emails)"), c2);
        emailText = new JTextField(application.getEmail());
        c2.gridx = 1;
        c2.gridy = 0;
        c2.gridwidth = 2;
        c2.weightx = 8;
        sendEmailPanel.add(emailText, c2);
		((AbstractDocument)emailText.getDocument()).setDocumentFilter(new LimitDocumentFilter(300));

        
        checkBoxSendEmail.setSelected(application.getIsSendEmail());
        toggleSendEmailPanel(application.getIsSendEmail());
        
		frame.getContentPane().add(loginPanel, BorderLayout.PAGE_START);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		JPanel filterPanel = new JPanel();
		mainPanel.add(filterPanel, BorderLayout.PAGE_START);
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		Calendar now = Calendar.getInstance();
		
		UtilDateModel modelDateFrom = new UtilDateModel();
		modelDateFrom.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
		modelDateFrom.setSelected(true);
		
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelDateFrom, p);
		datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
		
		UtilDateModel modelDateTo = new UtilDateModel();
		modelDateTo.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
		modelDateTo.setSelected(true);
		
		JDatePanelImpl datePanelTo = new JDatePanelImpl(modelDateTo, p);
		datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
		
		GridBagConstraints c3 = new GridBagConstraints();
        c3.gridheight = 1;
        c3.weighty = 0.1;
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.anchor = GridBagConstraints.BASELINE;
        
        c3.gridx = 0;
        c3.gridy = 0;
        c3.gridwidth = 2;
        c3.weightx = 2;
        filterPanel.add(new JLabel("Date From"), c3);
        
        c3.gridx = 3;
        c3.gridy = 0;
        c3.gridwidth = 3;
        c3.weightx = 3;
        filterPanel.add(datePickerFrom, c3);
        
        c3.gridx = 6;
        c3.gridy = 0;
        c3.gridwidth = 1;
        c3.weightx = 1;
        filterPanel.add(new JLabel(), c3);
        
        
        c3.gridx = 7;
        c3.gridy = 0;
        c3.gridwidth = 2;
        c3.weightx = 2;
        filterPanel.add(new JLabel("Date To"), c3);
        
        c3.gridx = 9;
        c3.gridy = 0;
        c3.gridwidth = 3;
        c3.weightx = 3;
        filterPanel.add(datePickerTo, c3);
        
        c3.gridx = 12;
        c3.gridy = 0;
        c3.gridwidth = 1;
        c3.weightx = 1;
        filterPanel.add(new JLabel(), c3);
        
        c3.gridx = 13;
        c3.gridy = 0;
        c3.gridwidth = 2;
        c3.weightx = 2;
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        filterPanel.add(refreshButton, c3);

		java.util.Date dateF = (java.util.Date) datePickerFrom.getModel().getValue();
		java.util.Date dateT = (java.util.Date) datePickerTo.getModel().getValue();
		
		dateFrom = new Date(dateF.getTime());
	    dateTo = new Date(dateT.getTime());
	    
	    ProductController productController = new ProductController();
		List<Product> productList = productController.getAllProduct();

		model = new ProductTableModel(productList, dateFrom, dateTo);
		
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(800, 800));
		table.setFillsViewportHeight(true);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount() == 2) {
					int[] selection = table.getSelectedRows();
					if(selection.length > 0) {
						if(selection.length == 1) {
							int indexRow = selection[0];
							int id = (Integer) model.getValueAt(indexRow, 0); 
							ProductController productController = new ProductController();
							productController.editProduct(id, indexRow);
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
		
		TableColumnModel tableColumnModel = table.getColumnModel();
		tableColumnModel.removeColumn(tableColumnModel.getColumn(0));
		
		JScrollPane scrollPane = new JScrollPane(table);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		
		
		
		footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		frame.getContentPane().add(footerPanel, BorderLayout.PAGE_END);
		
		restartButton = new JButton("Start Trading");
		restartButton.addActionListener(this);
		footerPanel.add(restartButton); 
		
		exitButton = new JButton("Exit Trading");
		exitButton.addActionListener(this);
		footerPanel.add(exitButton);
		
		addButton = new JButton("Add Product");
		addButton.addActionListener(this);
		footerPanel.add(addButton);
		
		removeButton = new JButton("Remove Product");
		removeButton.addActionListener(this);
		footerPanel.add(removeButton);
		
		exitButton.setEnabled(false);
		
		frame.addWindowListener(this);
		frame.setLocationRelativeTo(null);
		
		frame.setSize(720,600);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = frame.getSize().width;
		int h = frame.getSize().height;
	    int x = (dimension.width-w)/2;
	    int y = (dimension.height-h)/2;
	    frame.setLocation(x, y);
	    
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource().equals(refreshButton)) {
			
			java.util.Date dateF = (java.util.Date) datePickerFrom.getModel().getValue();
			java.util.Date dateT = (java.util.Date) datePickerTo.getModel().getValue();
			
			dateFrom = new Date(dateF.getTime());
		    dateTo = new Date(dateT.getTime());
		    
		    ProductController productController = new ProductController();
	        productController.reloadProduct(dateFrom, dateTo);
		}

		if(event.getSource().equals(addButton)) {
			ProductController productController = new ProductController();
			productController.newProduct();
		}
		
		if(event.getSource().equals(removeButton)) {
			ProductController productController = new ProductController();
			int[] selection = table.getSelectedRows();
			
			if(selection.length > 0) {
				for(int i = selection.length - 1 ; i >= 0; i--) {
					
					int id = (int) model.getValueAt(selection[i], 0); 

					if(productController.deleteProduct(id, selection[i])) {
						JOptionPane.showMessageDialog(null, "Row/s successfully deleted!");
					}
				}		
			}
		}
		
		if(event.getSource().equals(exitButton)) {
			ApplicationController.APPLICATIONCONTROLLER.stop();
			restartButton.setEnabled(true);
			exitButton.setEnabled(false);
			enableTextField(true);
		}
		
		if(event.getSource().equals(restartButton)){
			if(ApplicationController.THREAD != null)
			 ApplicationController.APPLICATIONCONTROLLER.stop();
			
			application.setTvAccount(userNameText.getText());
			application.setTvPassword(String.valueOf(passwordText.getPassword()));
			application.setIsSendEmail(checkBoxSendEmail.isSelected());
			application.setEmail(emailText.getText());
			
			ApplicationController.updateApplication(application);
			
			ApplicationController.APPLICATIONCONTROLLER.start(userNameText.getText(), String.valueOf(passwordText.getPassword()));
			
			exitButton.setEnabled(true);
			restartButton.setEnabled(false);
			enableTextField(false);
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			ApplicationController.APPLICATIONCONTROLLER.stop();
			Thread.interrupted();
		}catch(NullPointerException ex) {
			System.out.println(ex);
			Thread.interrupted();
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
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
	
	private void toggleSendEmailPanel(boolean isSelected) {
		for (Component sendEmailComponent : sendEmailPanel.getComponents() ){
			sendEmailComponent.setEnabled(isSelected);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(checkBoxSendEmail)) {
			toggleSendEmailPanel(checkBoxSendEmail.isSelected());
		}
		
	}

}
