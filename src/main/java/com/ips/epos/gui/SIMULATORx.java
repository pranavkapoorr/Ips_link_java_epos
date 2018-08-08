/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ips.epos.gui;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.io.Tcp;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.ips.epos.actors.TcpServerActor;
import com.ips.epos.actors.tcpClient;
import com.ips.epos.protocols.*;
import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.logging.*;
import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Pranav
 */
public class SIMULATORx extends javax.swing.JFrame {
	/**
     * 
     */
    private static final long serialVersionUID = 8748836741109262420L;
    public static volatile boolean isConnected = false;
	private final ObjectMapper mapper;
	private ActorSystem system;
	private ActorRef tcpSender;
	private String printFlag;
	private ActorSystem systemX = null;
	private Properties config;
	private static final String propertiesPath = "config/config.properties";
	boolean isServer = false;

	/**
	 * Creates new form SIMULATORx
	 */
	public SIMULATORx() {
		loadProperties();
		setResizable(false);
		setSize(new Dimension(1291, 1156));
		this.mapper = new ObjectMapper();
		this.mapper.setSerializationInclusion(Include.NON_NULL);
		initComponents();
		startStatusMessageListener();
	}
	private void loadProperties(){
		config = new Properties();
		try {
			InputStream in=new FileInputStream(propertiesPath);
			config.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			details.setText(e.getMessage());
		} catch (IOException e) {
			details.setText(e.getMessage());
		}
			//if(config.getProperty("Connection_Type").equalsIgnoreCase("TCP_IP")){
	}
	
	private void initComponents() {
		if(config.getProperty("Mode").equalsIgnoreCase("Server")){isServer = true;}
		mainPanel = new javax.swing.JPanel();
		mainXpannel = new javax.swing.JPanel();
		statusReceiptPanel = new javax.swing.JPanel();
		statusReceiptPanel.setBounds(739, 127, 468, 887);
		jScrollPane2 = new javax.swing.JScrollPane();
		jScrollPane2.setBounds(17, 186, 436, 685);
		receiptField = new javax.swing.JTextArea();
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setBounds(17, 18, 436, 152);
		statusMessageField = new javax.swing.JTextArea();
		statusMessageField.setLineWrap(true);
		statusMessageField.setFont(new Font("Monospaced", Font.BOLD, 19));
		buttonsPannel = new javax.swing.JPanel();
		buttonsPannel.setBounds(33, 565, 610, 457);
		paymentButton = new javax.swing.JButton();
		paymentButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		paymentButton.setBounds(17, 18, 271, 45);
		reversalButton = new javax.swing.JButton();
		reversalButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		reversalButton.setBounds(331, 18, 264, 42);
		refundButton = new javax.swing.JButton();
		refundButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		refundButton.setBounds(17, 79, 271, 42);
		firstDllButton = new javax.swing.JButton();
		firstDllButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		firstDllButton.setBounds(17, 194, 271, 41);
		xReportButton = new javax.swing.JButton();
		xReportButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		xReportButton.setBounds(17, 140, 271, 42);
		zReportButton = new javax.swing.JButton();
		zReportButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		zReportButton.setBounds(331, 140, 264, 42);
		terminalStatusButton = new javax.swing.JButton();
		terminalStatusButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		terminalStatusButton.setBounds(17, 251, 271, 42);
		reprintReceiptButton = new javax.swing.JButton();
		reprintReceiptButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		reprintReceiptButton.setBounds(331, 79, 264, 42);
		startButton = new javax.swing.JButton();
		startButton.setBounds(43, 423, 107, 21);
		stopButton = new javax.swing.JButton();
		stopButton.setBounds(459, 423, 107, 21);
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane3.setBounds(33, 345, 550, 65);
		details = new javax.swing.JTextArea();
		details.setFont(new Font("Monospaced", Font.PLAIN, 17));
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setBounds(512, 16, 337, 66);
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setBounds(31, 2, 384, 148);
		jPanel1 = new javax.swing.JPanel();
		jPanel1.setBounds(31, 283, 612, 269);
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setBounds(38, 122, 177, 23);
		jLabel4.setFont(new Font("Tahoma", Font.PLAIN, 19));
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setBounds(38, 49, 139, 23);
		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 19));
		terminalIpField = new javax.swing.JTextField();
		terminalIpField.setBounds(340, 45, 149, 27);
		terminalIpField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		terminalPortField = new javax.swing.JTextField();
		terminalPortField.setBounds(isServer?505:340, 49, 71, 22);
		terminalPortField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		amountField = new javax.swing.JTextField();
		amountField.setBounds(340, 120, 122, 27);
		amountField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setBounds(36, 85, 233, 23);
		jLabel5.setFont(new Font("Tahoma", Font.PLAIN, 19));
		statusMessageIpField = new javax.swing.JTextField();
		statusMessageIpField.setBounds(340, 83, 149, 27);
		statusMessageIpField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		statusMessagePortField = new javax.swing.JTextField();
		statusMessagePortField.setBounds(505, 86, 71, 20);
		statusMessagePortField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setBounds(38, 153, 98, 23);
		jLabel6.setFont(new Font("Tahoma", Font.PLAIN, 19));
		GTmessage = new javax.swing.JTextField();
		GTmessage.setBounds(338, 151, 124, 27);
		GTmessage.setFont(new Font("Tahoma", Font.PLAIN, 19));
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setFont(new Font("Tempus Sans ITC", Font.BOLD, 19));
		jLabel7.setBounds(176, 190, 326, 40);
		printButton = new javax.swing.JButton();
		printButton.setBounds(1098, 1027, 109, 40);
		dual_channel = new javax.swing.JCheckBox();
		dual_channel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dual_channel.setBounds(31, 1031, 137, 31);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("AltaPay Link SIMULATOR v 0.0.1");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setLocation(new java.awt.Point(250, 250));
		setLocationByPlatform(true);
		setType(java.awt.Window.Type.POPUP);

		mainPanel.setBackground(new java.awt.Color(102, 102, 102));

		mainXpannel.setBackground(new java.awt.Color(153, 153, 153));
		mainXpannel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED,
				new java.awt.Color(240, 240, 240), new java.awt.Color(240, 240, 240), new java.awt.Color(240, 240, 240),
				new java.awt.Color(240, 240, 240)));

		statusReceiptPanel.setBackground(new java.awt.Color(204, 204, 204));
		statusReceiptPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, new Color(240, 240, 240), Color.BLACK, new Color(102, 102, 102)));

		receiptField.setFont(new Font("Monospaced", Font.PLAIN, 18)); // NOI18N
		receiptField.setLineWrap(true);
		receiptField.setRows(20);
		receiptField.setWrapStyleWord(true);
		receiptField.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, new Color(240, 240, 240), new Color(0, 0, 0), new Color(102, 102, 102)));
		jScrollPane2.setViewportView(receiptField);

		statusMessageField.setColumns(24);
		statusMessageField.setRows(5);
		statusMessageField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				new java.awt.Color(255, 255, 255), new java.awt.Color(240, 240, 240), new java.awt.Color(0, 0, 0),
				new java.awt.Color(102, 102, 102)));
		jScrollPane1.setViewportView(statusMessageField);

		buttonsPannel.setBackground(new java.awt.Color(204, 204, 204));
		buttonsPannel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 255, 255), new Color(240, 240, 240), Color.BLACK, new Color(102, 102, 102)));

		paymentButton.setText("PAYMENT");
		paymentButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.WHITE, Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		paymentButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				paymentButtonMouseClicked(evt);
			}
		});

		reversalButton.setText("REVERSAL");
		reversalButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		reversalButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				reversalButtonMouseClicked(evt);
			}
		});

		refundButton.setText("REFUND");
		refundButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		refundButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				refundButtonMouseClicked(evt);
			}
		});

		firstDllButton.setText("FIRST DLL");
		firstDllButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		firstDllButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				firstDllButtonMouseClicked(evt);
			}
		});

		xReportButton.setText("PED BALANCE");
		xReportButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		xReportButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				xReportButtonMouseClicked(evt);
			}
		});

		zReportButton.setText("END OF DAY");
		zReportButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		zReportButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				zReportButtonMouseClicked(evt);
			}
		});

		terminalStatusButton.setText("PED STATUS");
		terminalStatusButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		terminalStatusButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				terminalStatusButtonMouseClicked(evt);
			}
		});

		reprintReceiptButton.setText("REPRINT RECEIPT");
		reprintReceiptButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(102, 102, 102)));
		reprintReceiptButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				reprintReceiptButtonMouseClicked(evt);
			}
		});

		startButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
		startButton.setForeground(new java.awt.Color(0, 153, 51));
		startButton.setText("START");
		startButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED,
				java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));
		startButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				startButtonMouseClicked(evt);
			}
		});
	

		stopButton.setBackground(new java.awt.Color(255, 255, 255));
		stopButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
		stopButton.setForeground(java.awt.Color.red);
		stopButton.setText("STOP");
		stopButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED,
				java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));
		stopButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				stopButtonMouseClicked(evt);
			}
		});

		details.setColumns(20);
		details.setRows(5);
		details.setText("WELCOME! CHECK THE DETAILS ABOVE THEN ->\nPRESS START TO START SERVICES!");
		details.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));
		jScrollPane3.setViewportView(details);

		jLabel2.setFont(new Font("Gill Sans Ultra Bold Condensed", Font.BOLD, 35)); // NOI18N
		jLabel2.setForeground(java.awt.SystemColor.menu);
		jLabel2.setText("POINT OF SALE...!!");

		jLabel1.setBackground(new java.awt.Color(255, 255, 255));
		jLabel1.setFont(new java.awt.Font("Eras Bold ITC", 1, 48)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/altapay.png"))); // NOI18N
		jLabel1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jLabel1.setOpaque(true);

		jPanel1.setBackground(new java.awt.Color(204, 204, 204));
		jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				java.awt.Color.white, new java.awt.Color(240, 240, 240), new java.awt.Color(0, 0, 0),
				new java.awt.Color(102, 102, 102)));

		jLabel4.setText("AMOUNT (IN PENCE)");
		jLabel4.setOpaque(true);

		jLabel3.setText(isServer?"PED IP+PORT":"PED COM-PORT");
		jLabel3.setOpaque(true);
		
		printFlagField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ECR", "PED" }));
		printFlagField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED,
				java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));

		terminalIpField.setText(config.getProperty("Ped_Ip","000.000.000.000"));
		terminalIpField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				new java.awt.Color(204, 204, 204), null, java.awt.Color.black, new java.awt.Color(102, 102, 102)));
		terminalIpField.setVisible(isServer?true:false);

		terminalPortField.setText(config.getProperty(isServer?"Ped_Port":"Ped_COM-Port", "0000"));
		terminalPortField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.black, java.awt.Color.darkGray));

		amountField.setText("10");
		amountField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));

		jLabel5.setText("STATUSMESSAGE IP+PORT");
		jLabel5.setOpaque(true);

		statusMessageIpField.setText(config.getProperty("Status_Message_Ip","000.000.000.000"));
		statusMessageIpField
				.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
						java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));

		statusMessagePortField.setText(config.getProperty("Status_Message_Port", "0000"));
		statusMessagePortField
				.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
						java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));

		jLabel6.setText("Data for GT");
		jLabel6.setOpaque(true);

		GTmessage.setText("test_gt");
		GTmessage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				java.awt.Color.white, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));

		jLabel7.setText("ALTAPAY LINK SERVER EDITION");
		jLabel7.setOpaque(true);

		

		printButton.setBackground(new java.awt.Color(0, 102, 51));
		printButton.setFont(new Font("Tahoma", Font.BOLD, 16)); // NOI18N
		printButton.setText("PRINT");
		printButton.setMaximumSize(new java.awt.Dimension(67, 40));
		printButton.setMinimumSize(new java.awt.Dimension(67, 40));
		printButton.setPreferredSize(new java.awt.Dimension(67, 40));
		printButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				printButtonActionPerformed(evt);
			}
		});

		dual_channel.setText("Dual-Channel");
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		jPanel1.setLayout(null);
		jPanel1.add(jLabel6);
		printFlagField.setBounds(340, 225, 85, 31);
		printFlagField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		jPanel1.add(printFlagField);
		jPanel1.add(jLabel5);
		jPanel1.add(jLabel4);
		jPanel1.add(jLabel3);
		jPanel1.add(statusMessageIpField);
		jPanel1.add(amountField);
		jPanel1.add(terminalIpField);
		jPanel1.add(GTmessage);
		jPanel1.add(statusMessagePortField);
		jPanel1.add(terminalPortField);
		buttonsPannel.setLayout(null);
		buttonsPannel.add(jScrollPane3);
		buttonsPannel.add(refundButton);
		buttonsPannel.add(paymentButton);
		buttonsPannel.add(xReportButton);
		buttonsPannel.add(terminalStatusButton);
		buttonsPannel.add(zReportButton);
		buttonsPannel.add(reprintReceiptButton);
		buttonsPannel.add(reversalButton);
		buttonsPannel.add(firstDllButton);
		buttonsPannel.add(startButton);
		buttonsPannel.add(stopButton);
		statusReceiptPanel.setLayout(null);
		statusReceiptPanel.add(jScrollPane2);
		statusReceiptPanel.add(jScrollPane1);
		mainXpannel.setLayout(null);
		mainXpannel.add(dual_channel);
		mainXpannel.add(printButton);
		mainXpannel.add(buttonsPannel);
		
		JButton manualDllButton = new JButton("UPDATE DLL");
		manualDllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manualDllButtonClicked(e);
			}
		});
		manualDllButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), new Color(128, 128, 128)));
		manualDllButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		manualDllButton.setBounds(331, 194, 264, 42);
		buttonsPannel.add(manualDllButton);
		
		JButton lastTransStatusButton = new JButton("LAST TRANS STATUS");
		lastTransStatusButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lastTransStatusButtonClicked(e);
			}
		});
		lastTransStatusButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		lastTransStatusButton.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), Color.WHITE, new Color(0, 0, 0), Color.GRAY));
		lastTransStatusButton.setBounds(331, 251, 264, 42);
		buttonsPannel.add(lastTransStatusButton);
		
		JButton btnProbePed = new JButton("PROBE PED");
		btnProbePed.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        probePedButtonClicked(e);
		    }
		});
		btnProbePed.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnProbePed.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, Color.DARK_GRAY, Color.BLACK));
		btnProbePed.setBounds(12, 306, 583, 33);
		buttonsPannel.add(btnProbePed);
		mainXpannel.add(jPanel1);
		
		JLabel lblWaitCard = new JLabel("Wait 4 Card Removed");
		lblWaitCard.setOpaque(true);
		lblWaitCard.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblWaitCard.setBounds(38, 189, 182, 23);
		jPanel1.add(lblWaitCard);
		
		wait4CardRemoved = new JCheckBox("ON/OFF");
		wait4CardRemoved.setBounds(340, 187, 139, 29);
		jPanel1.add(wait4CardRemoved);
		
		JLabel lblServerIpport = new JLabel("SERVER IP+PORT");
		lblServerIpport.setOpaque(true);
		lblServerIpport.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblServerIpport.setBounds(33, 13, 151, 23);
		jPanel1.add(lblServerIpport);
		
		serverIp = new JTextField();
		serverIp.setFont(new Font("Tahoma", Font.PLAIN, 19));
		serverIp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, UIManager.getColor("RadioButtonMenuItem.acceleratorSelectionForeground"), Color.DARK_GRAY));
		serverIp.setBounds(340, 16, 149, 22);
		serverIp.setText(config.getProperty("Service_Ip", "000.000.000.000"));
		jPanel1.add(serverIp);
		serverIp.setColumns(10);
		
		serverPort = new JTextField();
		serverPort.setFont(new Font("Tahoma", Font.PLAIN, 19));
		serverPort.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, Color.BLACK, Color.DARK_GRAY));
		serverPort.setBounds(505, 16, 71, 22);
		serverPort.setText(config.getProperty("Service_Port", "0000"));
		jPanel1.add(serverPort);
		serverPort.setColumns(10);
		
		JLabel lblPrintDevice = new JLabel("PRINT DEVICE");
		lblPrintDevice.setOpaque(true);
		lblPrintDevice.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPrintDevice.setBounds(38, 229, 123, 23);
		jPanel1.add(lblPrintDevice);
		mainXpannel.add(statusReceiptPanel);
		mainXpannel.add(jLabel1);
		mainXpannel.add(jLabel2);
		mainXpannel.add(jLabel7);
		getContentPane().add(mainPanel);
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainXpannel, GroupLayout.PREFERRED_SIZE, 1262, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainXpannel, GroupLayout.PREFERRED_SIZE, 1074, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JLabel lblPranavkvalitorcom = new JLabel("pranavk@valitor.com");
		lblPranavkvalitorcom.setForeground(new Color(240, 255, 255));
		lblPranavkvalitorcom.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblPranavkvalitorcom.setBounds(531, 1037, 172, 20);
		mainXpannel.add(lblPranavkvalitorcom);
		mainPanel.setLayout(gl_mainPanel);

		getAccessibleContext().setAccessibleName("FRAMEX");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	protected void lastTransStatusButtonClicked(MouseEvent e) {
		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting LAST TRANS STATUS REQUEST");
			printFlag = "1";
			IpsJson message = new IpsJson();

			message.setOperationType("LastTransactionStatus");

			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());

			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Reprint","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
		
	}
	protected void manualDllButtonClicked(java.awt.event.MouseEvent evt) {
		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting manual DLL request");
			if (printFlagField.getSelectedIndex() == 0) {
				printFlag = "1";
			} else {
				printFlag = "0";
			}
			IpsJson message = new IpsJson();

			message.setOperationType("UpdateDll");
			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());

			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Dll","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
		
	}
	private void paymentButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_paymentButtonMouseClicked
		if (isConnected) {
			setAllDisplays("", "", "");
			String amount = amountField.getText();
			if (amount.equalsIgnoreCase(null) || amount.equalsIgnoreCase("")) {
				details.setForeground(Color.red);
				details.setText("ENTER SOME AMOUNT IN PENCE");
			} else {
				details.setForeground(Color.BLUE);
				details.setText("starting Payment with " + amount + " pence");
				if (printFlagField.getSelectedIndex() == 0) {
					printFlag = "1";
				} else {
					printFlag = "0";
				}
				IpsJson message = new IpsJson();

				message.setAmount(amount);
				message.setOperationType("Payment");
				message.setTransactionReference(GTmessage.getText());
				message.setPrintFlag(printFlag);
				message.setPedIp(terminalIpField.getText());
				message.setPedPort(terminalPortField.getText());
				String statusMessageIp = null;
				String statusMessagePort = null;
				if (dual_channel.isSelected()) {
					statusMessageIp = statusMessageIpField.getText();
					statusMessagePort = statusMessagePortField.getText();
				}
				if(wait4CardRemoved.isSelected()){
				    message.setWait4CardRemoved("true");
				}
				message.setStatusMessageIp(statusMessageIp);
				message.setStatusMessagePort(statusMessagePort);
				message.setTimeOut(config.getProperty("timeout_Payment","0"));
				String json;
				try {
					json = mapper.writeValueAsString(message);
					tcpSender.tell(json, ActorRef.noSender());
				} catch (JsonProcessingException ex) {
					System.exit(0);
				}
			}
		} else {
			details.setText("NO CONNECTION!");
		} // log.trace("sending json to TCP sender :" + json);

	}// GEN-LAST:event_paymentButtonMouseClicked

	private void refundButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_refundButtonMouseClicked
		if (isConnected) {
			setAllDisplays("", "", "");
			String amount = amountField.getText();
			if (amount.equalsIgnoreCase(null) || amount.equalsIgnoreCase("")) {
				details.setForeground(Color.red);
				details.setText("ENTER SOME AMOUNT IN PENCE");
			} else {
				details.setForeground(Color.BLUE);
				details.setText("starting REFUND with " + amount + " pence");
				if (printFlagField.getSelectedIndex() == 0) {
					printFlag = "1";
				} else {
					printFlag = "0";
				}
				IpsJson message = new IpsJson();

				message.setAmount(amount);
				message.setOperationType("Refund");
				message.setTransactionReference(GTmessage.getText());
				if(wait4CardRemoved.isSelected()){
                    message.setWait4CardRemoved("true");
                }
				message.setPrintFlag(printFlag);
				message.setPedIp(terminalIpField.getText());
				message.setPedPort(terminalPortField.getText());

				String statusMessageIp = null;
				String statusMessagePort = null;
				if (dual_channel.isSelected()) {
					statusMessageIp = statusMessageIpField.getText();
					statusMessagePort = statusMessagePortField.getText();
				}
				message.setStatusMessageIp(statusMessageIp);
				message.setStatusMessagePort(statusMessagePort);
				message.setTimeOut(config.getProperty("timeout_Refund","0"));
				String json;
				try {
					json = mapper.writeValueAsString(message);
					tcpSender.tell(json, ActorRef.noSender());
				} catch (JsonProcessingException ex) {
					System.exit(0);
				}
			}
		} else {
			details.setText("NO CONNECTION!");
		} // log.trace("sending
	}// GEN-LAST:event_refundButtonMouseClicked

	private void reversalButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_reversalButtonMouseClicked
		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting REVERSAL");
			if (printFlagField.getSelectedIndex() == 0) {
				printFlag = "1";
			} else {
				printFlag = "0";
			}
			IpsJson message = new IpsJson();

			message.setOperationType("Reversal");
			message.setTransactionReference(GTmessage.getText());
			if(wait4CardRemoved.isSelected()){
                message.setWait4CardRemoved("true");
            }
			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());

			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Reversal","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
	}

	private void firstDllButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_dllButtonMouseClicked
		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting 1ST DLL request");
			if (printFlagField.getSelectedIndex() == 0) {
				printFlag = "1";
			} else {
				printFlag = "0";
			}
			IpsJson message = new IpsJson();

			message.setOperationType("FirstDll");
			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());

			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Dll","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
	}

	private void xReportButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_xReportButtonMouseClicked
		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting PedBalance REQUEST");
			if (printFlagField.getSelectedIndex() == 0) {
				printFlag = "1";
			} else {
				printFlag = "0";
			}
			IpsJson message = new IpsJson();

			message.setOperationType("PedBalance");

			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());

			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Report","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
	}

	private void zReportButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_zReportButtonMouseClicked
		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting EndOfDay REQUEST");
			if (printFlagField.getSelectedIndex() == 0) {
				printFlag = "1";
			} else {
				printFlag = "0";
			}
			IpsJson message = new IpsJson();

			message.setOperationType("EndOfDay");

			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());

			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Report","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
	}

	private void terminalStatusButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_terminalStatusButtonMouseClicked

		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting PED STATUS REQUEST");
			printFlag = "1";
			IpsJson message = new IpsJson();

			message.setOperationType("PedStatus");

			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());
			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Report","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
	}

	private void reprintReceiptButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_reprintReceiptButtonMouseClicked
		if (isConnected) {
			setAllDisplays("", "", "");
			details.setForeground(Color.BLUE);
			details.setText("starting REPRINT RECEIPT REQUEST");
			printFlag = "1";
			IpsJson message = new IpsJson();

			message.setOperationType("ReprintReceipt");

			message.setPrintFlag(printFlag);
			message.setPedIp(terminalIpField.getText());
			message.setPedPort(terminalPortField.getText());

			String statusMessageIp = null;
			String statusMessagePort = null;
			if (dual_channel.isSelected()) {
				statusMessageIp = statusMessageIpField.getText();
				statusMessagePort = statusMessagePortField.getText();
			}
			message.setStatusMessageIp(statusMessageIp);
			message.setStatusMessagePort(statusMessagePort);
			message.setTimeOut(config.getProperty("timeout_Reprint","0"));
			String json;
			try {
				json = mapper.writeValueAsString(message);
				tcpSender.tell(json, ActorRef.noSender());
			} catch (JsonProcessingException ex) {
				System.exit(0);
			}
		} else {
			details.setText("NO CONNECTION!");
		}
	}
	
	private void probePedButtonClicked(MouseEvent e) {
	    if (isConnected) {
            setAllDisplays("", "", "");
            details.setForeground(Color.BLUE);
            details.setText("starting PROBE PED REQUEST");
            printFlag = "0";
            IpsJson message = new IpsJson();

            message.setOperationType("ProbePed");

            message.setPrintFlag(printFlag);
            message.setPedIp(terminalIpField.getText());
            message.setPedPort(terminalPortField.getText());

            String statusMessageIp = null;
            String statusMessagePort = null;
            if (dual_channel.isSelected()) {
                statusMessageIp = statusMessageIpField.getText();
                statusMessagePort = statusMessagePortField.getText();
            }
            message.setStatusMessageIp(statusMessageIp);
            message.setStatusMessagePort(statusMessagePort);
            message.setTimeOut(config.getProperty("timeout_Probe","0"));
            String json;
            try {
                json = mapper.writeValueAsString(message);
                tcpSender.tell(json, ActorRef.noSender());
            } catch (JsonProcessingException ex) {
                System.exit(0);
            }
        } else {
            details.setText("NO CONNECTION!");
        }
        
    }
	private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (tcpSender != null) {
			if (isConnected) {
				details.setText("TCP CONNECTION ALREADY RUNNING..!!");
			} else {
				tcpSender = null;
				startConnection();
			}
		} else {
			startConnection();
		}
	}
	
	private void startConnection() {
		String[] cloudIpAndPort = {serverIp.getText(),serverPort.getText()};

		if (cloudIpAndPort.length == 2) {
			this.system = ActorSystem.create("IPS_Simulator");
			this.tcpSender = system.actorOf(
					tcpClient.props(new InetSocketAddress(cloudIpAndPort[0], Integer.parseInt(cloudIpAndPort[1])),mapper));
		} else {
			details.setText("FILL IN CLOUD, TERMINAL AND STATUS MESSAGE DETAILS!!");
		}
	}

	private void stopButtonMouseClicked(java.awt.event.MouseEvent evt) {
		if (tcpSender != null) {
			details.setText("STOPPING SERVICE");
			system.stop(tcpSender);
			system.terminate();
			tcpSender = null;
			details.setText("PRESS START TO RESTART SERVICES");
		} else {
			details.setText("NO CONNECTION ACTOR RUNNING ALREADY!");
		}
	}

	

	private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_printButtonActionPerformed

		PrinterJob pjob = PrinterJob.getPrinterJob();
		PageFormat preformat = pjob.defaultPage();
		preformat.setOrientation(PageFormat.PORTRAIT);
		PageFormat postformat = pjob.pageDialog(preformat);
		if (preformat != postformat) {
			pjob.setPrintable(new Printer(receiptField), postformat);
			if (pjob.printDialog()) {
				try {
					pjob.print();
				} catch (PrinterException ex) {
					Logger.getLogger(SIMULATORx.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} 
	}
	
	private void startStatusMessageListener() {
			systemX = ActorSystem.create("system");
			String statusMsgIp = statusMessageIpField.getText();
			String statusMsgPort = statusMessagePortField.getText();
			if (!statusMsgIp.equals("") && !statusMsgPort.equals("")) {
			    ActorRef tcpMnager =Tcp.get(systemX).manager();
			    systemX.actorOf(TcpServerActor.props(tcpMnager, new InetSocketAddress(statusMsgIp, Integer.parseInt(statusMsgPort)), mapper));
			} else {
				details.setText("FILL IN STATUS MESSAGE DETAILS!!");
			}
	}

	private void setAllDisplays(String detailsMessage, String statusMessage, String receiptMessage) {
		details.setText(detailsMessage);
		statusMessageField.setText(statusMessage);
		receiptField.setText(receiptMessage);
	}
	private javax.swing.JComboBox<String> printFlagField = new javax.swing.JComboBox<>();
	private javax.swing.JTextField GTmessage;
	private javax.swing.JTextField amountField;
	private javax.swing.JPanel buttonsPannel;
	public static javax.swing.JTextArea details;
	private javax.swing.JButton firstDllButton;
	private javax.swing.JCheckBox dual_channel;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JPanel mainPanel;
	private javax.swing.JPanel mainXpannel;
	private javax.swing.JButton paymentButton;
	private javax.swing.JButton printButton;
	public static javax.swing.JTextArea receiptField;
	private javax.swing.JButton refundButton;
	private javax.swing.JButton reprintReceiptButton;
	private javax.swing.JButton reversalButton;
	private javax.swing.JButton startButton;
	public static javax.swing.JTextArea statusMessageField;
	private javax.swing.JTextField statusMessageIpField;
	private javax.swing.JTextField statusMessagePortField;
	private javax.swing.JPanel statusReceiptPanel;
	private javax.swing.JButton stopButton;
	private javax.swing.JTextField terminalIpField;
	private javax.swing.JTextField terminalPortField;
	private javax.swing.JButton terminalStatusButton;
	private javax.swing.JButton xReportButton;
	private javax.swing.JButton zReportButton;
	private static JCheckBox wait4CardRemoved;
	private JTextField serverIp;
	private JTextField serverPort;

	public static class Printer implements Printable {
		final Component comp;

		public Printer(Component comp) {
			this.comp = comp;
		}

		@Override
		public int print(Graphics g, PageFormat format, int page_index) throws PrinterException {
			if (page_index > 0) {
				return Printable.NO_SUCH_PAGE;
			}

			Dimension dim = comp.getSize();
			double cHeight = dim.getHeight();
			double cWidth = dim.getWidth();

			double pHeight = format.getImageableHeight();
			double pWidth = format.getImageableWidth();

			double pXStart = format.getImageableX();
			double pYStart = format.getImageableY();

			double xRatio = pWidth / cWidth;
			double yRatio = pHeight / cHeight;

			Graphics2D g2 = (Graphics2D) g;
			g2.translate(pXStart, pYStart);
			g2.scale(xRatio, yRatio);
			comp.paint(g2);

			return Printable.PAGE_EXISTS;
		}
	}
    public JCheckBox getWait4CardRemoved() {
        return wait4CardRemoved;
    }
}
