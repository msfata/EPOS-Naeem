package net.tablet.restaurant;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
//import java.awt.GraphicsDevice;
//import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Restuarant extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean accessGranted;
	private JPanel contentPane;
	public static JTable calculationTable;
	DefaultTableModel PizzaModel;
	DefaultTableModel choiceModel;
	DefaultTableModel toppingModel;
	private JTextField tfDebit;
	private JTextField tfPortion;
	protected int amount;
	private double subTotal;
	protected boolean cash;
	protected boolean isPrintable;
	protected boolean isVatCharged;
	protected double vatCharge;
	protected double total;
	private double tax = 0.20;
	protected static boolean dbReader;
	protected boolean taxable;
	protected double itemTax;
	JCheckBox cbSmall;
	JCheckBox cbMidim;
	JCheckBox cbLarge;
	JCheckBox cbRe;
	JCheckBox cbSm;
	JCheckBox cbMd;
	JCheckBox cbLg;
	private double basePrice;
	private double toppings;
	private JRadioButton rdbtnOn;
	private JRadioButton rdbtnOff;
	private boolean pizzaOrdered;
	public static boolean isValidated;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (Restuarant.accessGranted) {
						Restuarant frame = new Restuarant(accessGranted);
						frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Restuarant(boolean x) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("WINDOWS_CLASSIC".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		accessGranted = x;
		setType(Type.UTILITY);
		try {
			derbyDriver = "jdbc:derby:codejava/msfata;create=true";
			con = DriverManager.getConnection(derbyDriver);
			create();
		} catch (Exception e) {
//			e.getMessage();
		}
//		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//		int width = gd.getDisplayMode().getWidth();
//		int height = gd.getDisplayMode().getHeight();
		int width = 1280;
		int height = 800;
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, width, height);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/* DELETING FROM TABLE */
				try {
					stmt = con.createStatement();
					stmt.executeUpdate("DELETE FROM TEST32");
					stmt.close();
				} catch (SQLException e) {
//					System.out.println(e.getMessage());
				}

			}
		});
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(new Color(255, 255, 255));

		contentPane.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 6),
				new LineBorder(new Color(51, 102, 153), 3)));
		setUndecorated(true);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelExit = new JPanel();
		panelExit.setBackground(new Color(255, 255, 255));
		panelExit.setBounds(1003, 401, 271, 390);
		panelExit.setBorder(null);
		contentPane.add(panelExit);

		JButton btnCancel = new JButton("");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				subTotal = 0;
				try {
					Model.setRowCount(0);
				} catch (Exception e) {
//						e.printStackTrace();
				}
				tfDebit.setText("");
				tfBalance.setText("");
				rdbtnOn.setEnabled(true);
				rdbtnOff.setSelected(true);
				rdbtnOff.setEnabled(false);
				tfDelivery.setText("");
				tfBalance.setBackground(Color.WHITE);

			}
		});
		panelExit.setLayout(new GridLayout(0, 1, 0, 0));

		tfBalance = new JTextField();
		panelExit.add(tfBalance);
		tfBalance.setEditable(false);
		tfBalance.setHorizontalAlignment(SwingConstants.CENTER);
		tfBalance.setForeground(Color.BLACK);
		tfBalance.setFont(new Font("Monaco", Font.BOLD, 40));
		tfBalance.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 4, true), "BALANCE",
				TitledBorder.LEADING, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
		tfBalance.setBackground(Color.WHITE);
		btnCancel.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/button_cancel.png")));
		btnCancel.setBackground(new Color(255, 255, 255));
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCancel.setBorder(null);
		panelExit.add(btnCancel);

		JButton btnCash = new JButton("");
		btnCash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (tfCredit.getText().isEmpty() && (!tfDebit.getText().isEmpty())) {
					new MessageOK("Please Pay First !").setVisible(true);
					MessageOK.btnOK.requestFocus();
				} else if (!tfCredit.getText().isEmpty() && (!tfDebit.getText().isEmpty())) {
					Date date1 = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");

					GregorianCalendar gcalendar = new GregorianCalendar();
					int hou = gcalendar.get(Calendar.HOUR);
					int min = gcalendar.get(Calendar.MINUTE);
					int sec = gcalendar.get(Calendar.SECOND);
					String display = "" + hou + ":" + min + ":" + sec + "";
					try {

						if (Double.parseDouble(tfDebit.getText()) <= Double.parseDouble(tfCredit.getText())) {
							try {
								for (int row = 0; row < Model.getRowCount(); row++) {
									insert("INSERT INTO TEST32 VALUES ('" + Model.getValueAt(row, 0) + "','"
											+ Model.getValueAt(row, 1) + "','" + Model.getValueAt(row, 2) + "','"
											+ Model.getValueAt(row, 3) + "','" + ft.format(date1) + "','" + display
											+ "')");
								}

								if (isPrintable) {
									Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
											new Object[] { "====================" });
									Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
											new Object[] { "total to pay : " + tfDebit.getText() });
									if (tfDelivery.getText().isEmpty() == false) {
										Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
												new Object[] { "delivery charge added to total £5" });
									}
//									if (deliverable == true) {
//										Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
//												new Object[] { "Delivery   : " + formatter.format(deliveryCost) });
//										System.out.println(subTotal + " : " + deliveryCost);
//									}

									if (taxable == true) {
										double x = Double.parseDouble(tfDebit.getText());
										Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
												new Object[] { "tax :" + formatter.format(x * 20 / 100) });
									}

									Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
											new Object[] { "you paid   : " + tfCredit.getText() });
									Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
											new Object[] { "your change : " + tfBalance.getText() });
									Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
											new Object[] { "====================" });
									Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
											new Object[] { "thank you for shopping with us @" });
									Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
											new Object[] { "" + new Date() });

									MessageFormat header = new MessageFormat("");
									int Integer = 1;

									MessageFormat footer = new MessageFormat("PAGE {0,," + Integer + "}");
									++Integer;
									try {
										calculationTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
									} catch (java.awt.print.PrinterException e) {
										// System.err.format("can not print %s% " + e.getMessage());
									}
								}

							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

//							tfDelivery.setText("0");
							rdbtnOn.setEnabled(true);
							rdbtnOff.setSelected(true);
							rdbtnOff.setEnabled(false);
							tfDelivery.setText("");
							cash = true;
							Model.setRowCount(0);
							tfCredit.setText("");
							tfDebit.setText("");
							subTotal = 0;
//							btnDelivery.setEnabled(false);
						} else if (tfCredit.getText().isEmpty() && (!tfDebit.getText().isEmpty())) {
							new MessageOK("Short Payment !").setVisible(true);
						} else {
							new MessageOK("Short Payment !").setVisible(true);
						}
					} catch (NumberFormatException e) {
//						new MessageOK("Please Pay First !").setVisible(true);
						System.out.println(e.getMessage());
					}
				}
//				btnDelivery.setBorder(
//						new TitledBorder(null, "OFF", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));

			}

			private void insert(String string) {
				try {
					stmt = con.createStatement();
					stmt.execute(string);
					stmt.close();
				} catch (SQLException e) {
					e.getMessage();
				}

			}
		});

		JButton button_16 = new JButton("");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_16.setIcon(new ImageIcon(Restuarant.class.getResource("/buttons/button_card.png")));
		button_16.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_16.setBorder(null);
		button_16.setBackground(Color.WHITE);
		panelExit.add(button_16);
		btnCash.setIcon(new ImageIcon(Restuarant.class.getResource("/buttons/button_cash (5).png")));
		btnCash.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCash.setBorder(null);
		btnCash.setBackground(new Color(255, 255, 255));
		panelExit.add(btnCash);

		JButton btnExit = new JButton("");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MessageYesNo("DO YOU REALLY WANT TO EXIT PROGRAM ?").setVisible(true);
				MessageYesNo.btnNo.requestFocus();
			}
		});
		btnExit.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/button_exit (1).png")));
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnExit.setBorder(null);
		btnExit.setBackground(new Color(255, 255, 255));
		panelExit.add(btnExit);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(6, 6, 712, 122);
		scrollPane_1.setViewportBorder(null);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane_1);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		scrollPane_1.setViewportView(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("SEVEN UP", 0.59);
			}
		});
		button_1.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/7 UP.jpg")));
		button_1.setHorizontalAlignment(SwingConstants.CENTER);
		button_1.setBorder(null);
		button_1.setBackground(Color.WHITE);
		panel.add(button_1);

		JButton button_9 = new JButton("");
		button_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selIt("COCA COLA", 0.59);
			}
		});
		button_9.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/cokacola.jpg")));
		button_9.setHorizontalAlignment(SwingConstants.CENTER);
		button_9.setBorder(null);
		button_9.setBackground(Color.WHITE);
		panel.add(button_9);

		JButton button_8 = new JButton("");
		button_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("FANTA", 0.59);
			}
		});
		button_8.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/fanta.jpg")));
		button_8.setHorizontalAlignment(SwingConstants.CENTER);
		button_8.setBorder(null);
		button_8.setBackground(Color.WHITE);
		panel.add(button_8);

		JButton button_7 = new JButton("");
		button_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("PEPSI", 0.59);
			}
		});
		button_7.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/pepsi.png")));
		button_7.setHorizontalAlignment(SwingConstants.CENTER);
		button_7.setBorder(null);
		button_7.setBackground(Color.WHITE);
		panel.add(button_7);

		JButton button_6 = new JButton("");
		button_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("TANGO", 0.59);
			}
		});
		button_6.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/tango.jpg")));
		button_6.setHorizontalAlignment(SwingConstants.CENTER);
		button_6.setBorder(null);
		button_6.setBackground(Color.WHITE);
		panel.add(button_6);

		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("MERANDA", 0.59);
			}
		});
		button_5.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/merinda.jpg")));
		button_5.setHorizontalAlignment(SwingConstants.CENTER);
		button_5.setBorder(null);
		button_5.setBackground(Color.WHITE);
		panel.add(button_5);

		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("BOTTLE", 1.49);
			}
		});
		button_4.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/softBigBottle.jpg")));
		button_4.setHorizontalAlignment(SwingConstants.CENTER);
		button_4.setBorder(null);
		button_4.setBackground(Color.WHITE);
		panel.add(button_4);

		JButton button_13 = new JButton("");
		button_13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("WATER", 0.50);
			}
		});
		button_13.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/Evian.jpg")));
		button_13.setHorizontalAlignment(SwingConstants.CENTER);
		button_13.setBorder(null);
		button_13.setBackground(Color.WHITE);
		panel.add(button_13);

		JButton button_12 = new JButton("");
		button_12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("SAFRAN TEA", 0.50);
			}
		});
		button_12.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/Saffron-Tea.jpg")));
		button_12.setHorizontalAlignment(SwingConstants.CENTER);
		button_12.setBorder(null);
		button_12.setBackground(Color.WHITE);
		panel.add(button_12);

		JButton button_11 = new JButton("");
		button_11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("COFFEE", 0.50);
			}
		});
		button_11.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/coffee.jpg")));
		button_11.setHorizontalAlignment(SwingConstants.CENTER);
		button_11.setBorder(null);
		button_11.setBackground(Color.WHITE);
		panel.add(button_11);

		JButton button_10 = new JButton("");
		button_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("GREEN TEA", 0.50);
			}
		});
		button_10.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/teagreen.jpg")));
		button_10.setHorizontalAlignment(SwingConstants.CENTER);
		button_10.setBorder(null);
		button_10.setBackground(Color.WHITE);
		panel.add(button_10);

		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("BLACK TEA", 0.50);
			}
		});
		button_3.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/teablack.jpg")));
		button_3.setHorizontalAlignment(SwingConstants.CENTER);
		button_3.setBorder(null);
		button_3.setBackground(Color.WHITE);
		panel.add(button_3);

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("SHEER YAKH", 3.50);
			}
		});
		button_2.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/sheerYakh.jpg")));
		button_2.setHorizontalAlignment(SwingConstants.CENTER);
		button_2.setBorder(null);
		button_2.setBackground(Color.WHITE);
		panel.add(button_2);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("YOGART", 1.50);
			}
		});
		button.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/yogurt.jpg")));
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setBorder(null);
		button.setBackground(Color.WHITE);
		panel.add(button);

		JScrollPane calCulationscrollPane = new JScrollPane();
		calCulationscrollPane.setBackground(Color.WHITE);
		calCulationscrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dropTable();
			}
		});
		calCulationscrollPane.setViewportBorder(null);
		calCulationscrollPane.setBounds(720, 76, 554, 322);
		contentPane.add(calCulationscrollPane);
		calCulationscrollPane.setBorder(new LineBorder(Color.WHITE));

		calculationTable = new JTable();
		calculationTable.setShowGrid(false);
		calculationTable.setRowSelectionAllowed(false);
		calculationTable.setFillsViewportHeight(true);
		calculationTable.setGridColor(Color.WHITE);
		calculationTable.setForeground(Color.BLACK);
		calculationTable.setRowHeight(18);
		calculationTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
		calculationTable.setBorder(null);
		calculationTable.setBackground(Color.WHITE);
		calculationTable.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "NAME", "PORTION", "PRICE", "TOTAL" }));

//		table.getColumnModel().getColumn(1).setResizable(false);
		calculationTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		calculationTable.getColumnModel().getColumn(1).setMaxWidth(100);

//		table.getColumnModel().getColumn(2).setResizable(false);
		calculationTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		calculationTable.getColumnModel().getColumn(2).setMaxWidth(100);

//		table.getColumnModel().getColumn(3).setResizable(false);
		calculationTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		calculationTable.getColumnModel().getColumn(3).setMaxWidth(150);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		calculationTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
//		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

		calCulationscrollPane.setViewportView(calculationTable);

		JPanel taxSidePanel = new JPanel();
		taxSidePanel.setForeground(Color.WHITE);
		calCulationscrollPane.setRowHeaderView(taxSidePanel);
		taxSidePanel.setBorder(new LineBorder(Color.WHITE, 2));
		taxSidePanel.setBackground(Color.WHITE);

		JButton btnTax = new JButton("");
		btnTax.setForeground(Color.WHITE);

		btnTax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (taxable == false) {
					btnTax.setBorder(
							new TitledBorder(null, "ON", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
//					tfTax.setText(formatter.format(tax));
					tfDebit.setText(formatter.format(subTotal));
					tfBalance.setText(formatter.format(subTotal));
//					 tfTax.setText("20%");
					taxable = true;
				} else {
					btnTax.setBorder(
							new TitledBorder(null, "OFF", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));

					tfDebit.setText(formatter.format(subTotal));
					tfBalance.setText(formatter.format(subTotal));
//					tfTax.setText("0.0");
//				    tfTax.setText("20%");
					taxable = false;
				}
			}
		});

		taxSidePanel.setLayout(new GridLayout(0, 1, 0, 0));
		btnTax.setIcon(new ImageIcon(Restuarant.class.getResource("/buttons/taxButton.png")));
		btnTax.setBorder(new TitledBorder(null, "OFF", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		btnTax.setBackground(Color.WHITE);
		taxSidePanel.add(btnTax);

		JPanel panelSideTable = new JPanel();
		taxSidePanel.add(panelSideTable);
		panelSideTable.setBackground(Color.WHITE);
		panelSideTable.setBorder(null);
		panelSideTable.setForeground(Color.WHITE);

		JButton btnAdd = new JButton("");
		btnAdd.setBounds(0, 2, 84, 35);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				amount = Integer.parseInt(tfPortion.getText());
				amount = amount + 1;
				tfPortion.setText(String.valueOf(amount));
			}
		});
		panelSideTable.setLayout(null);
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBorder(null);
		btnAdd.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/UP.png")));
		panelSideTable.add(btnAdd);

		tfPortion = new JTextField();
		tfPortion.setBounds(0, 35, 84, 28);
		tfPortion.setBackground(new Color(255, 255, 255));
		tfPortion.setFont(new Font("Tahoma", Font.BOLD, 12));
		tfPortion.setText("1");
		tfPortion.setHorizontalAlignment(SwingConstants.CENTER);
		tfPortion.setBorder(null);
		panelSideTable.add(tfPortion);
		tfPortion.setColumns(10);

		JButton btnMin = new JButton("");
		btnMin.setBounds(0, 61, 84, 35);
		btnMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				amount = Integer.parseInt(tfPortion.getText());
				if (amount >= 2) {
					amount = amount - 1;
					tfPortion.setText(String.valueOf(amount));
				}
			}
		});
		btnMin.setBackground(Color.WHITE);
		btnMin.setBorder(null);
		btnMin.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/down.png")));
		panelSideTable.add(btnMin);

		JButton btnPrintOnOff = new JButton("");
		btnPrintOnOff.setForeground(Color.WHITE);
		taxSidePanel.add(btnPrintOnOff);
		btnPrintOnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

//			captureComponent(calculationTable) ;
				if (isPrintable) {
					btnPrintOnOff.setBorder(
							new TitledBorder(null, "OFF", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
					isPrintable = false;
				} else {
					btnPrintOnOff.setBorder(
							new TitledBorder(null, "ON", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
					isPrintable = true;
				}
			}
		});
		btnPrintOnOff.setIcon(new ImageIcon(Restuarant.class.getResource("/buttons/printButton.png")));
		btnPrintOnOff
				.setBorder(new TitledBorder(null, "OFF", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		btnPrintOnOff.setBackground(Color.WHITE);

		JRadioButton radioButton = new JRadioButton("New radio button");
		calCulationscrollPane.setColumnHeaderView(radioButton);

		JPanel panelNumbers = new JPanel();
		panelNumbers.setBorder(null);
		panelNumbers.setBounds(722, 482, 286, 312);
		contentPane.add(panelNumbers);
		panelNumbers.setBackground(Color.WHITE);
		panelNumbers.setLayout(new GridLayout(0, 3, 0, 0));

		JButton btn7 = new JButton("");
		btn7.setBorder(null);
		btn7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("7");
				}
				paintBalance();
			}
		});
		btn7.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/sevenn.png")));
		btn7.setBackground(new Color(255, 255, 255));
		btn7.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn7);

		JButton btn8 = new JButton("");
		btn8.setBorder(null);
		btn8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("8");
				}
				paintBalance();
			}
		});
		btn8.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/eightt.png")));
		btn8.setBackground(new Color(255, 255, 255));
		btn8.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn8);

		JButton btn9 = new JButton("");
		btn9.setBorder(null);
		btn9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("9");
				}
				paintBalance();
			}
		});
		btn9.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/ninee.png")));
		btn9.setBackground(new Color(255, 255, 255));
		btn9.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn9);

		JButton btn4 = new JButton("");
		btn4.setBorder(null);
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("4");
				}
				paintBalance();
			}
		});
		btn4.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/fouree.png")));
		btn4.setBackground(new Color(255, 255, 255));
		btn4.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn4);

		JButton btn5 = new JButton("");
		btn5.setBorder(null);
		btn5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("5");
				}
				paintBalance();
			}
		});
		btn5.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/fivee.png")));
		btn5.setBackground(new Color(255, 255, 255));
		btn5.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn5);

		JButton btn6 = new JButton("");
		btn6.setBorder(null);
		btn6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("6");
				}
				paintBalance();
			}
		});
		btn6.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/sexx.png")));
		btn6.setBackground(new Color(255, 255, 255));
		btn6.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn6);

		JButton btn1 = new JButton("");
		btn1.setBorder(null);
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("1");
				}
				paintBalance();
			}
		});
		btn1.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/one.png")));
		btn1.setBackground(new Color(255, 255, 255));
		btn1.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn1);

		JButton btn2 = new JButton("");
		btn2.setBorder(null);
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("2");
				}
				paintBalance();
			}
		});
		btn2.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/twoo.png")));
		btn2.setBackground(new Color(255, 255, 255));
		btn2.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn2);

		JButton btn3 = new JButton("");
		btn3.setBorder(null);
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("3");
				}
				paintBalance();
			}
		});
		btn3.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/threee.png")));
		btn3.setBackground(new Color(255, 255, 255));
		btn3.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn3);

		JButton btnDisemal = new JButton("");
		btnDisemal.setBorder(null);
		btnDisemal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append(".");
				}
				paintBalance();
			}
		});
		btnDisemal.setBackground(new Color(255, 255, 255));
		btnDisemal.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/deccc.png")));
		btnDisemal.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btnDisemal);

		JButton btn0 = new JButton("");
		btn0.setBorder(null);
		btn0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pizzaOrdered) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?",
							null, JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						enterPizza();
					} else {
						cancelPizza();
					}
					pizzaOrdered = false;
				} else {
					tfCredit.append("0");
				}
				paintBalance();
			}
		});
		btn0.setBackground(new Color(255, 255, 255));
		btn0.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/zeroo.png")));
		btn0.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btn0);

		JButton btnBack = new JButton("");
		btnBack.setBorder(null);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int last = tfCredit.getText().length();
				if (last >= 1) {
					String str = tfCredit.getText().substring(0, last - 1);
					tfCredit.setText("");
					tfCredit.append(str);
				}
				if (tfCredit.getText().length() < 1) {
					tfBalance.setText(tfDebit.getText());
				}
				if (tfCredit.getText().isEmpty() && tfDebit.getText().isEmpty()) {
					tfBalance.setBackground(Color.WHITE);
				} else if (tfCredit.getText().isEmpty() && (tfDebit.getText().isEmpty()) == false) {
					tfBalance.setBackground(Color.RED);
				} else if ((tfCredit.getText().isEmpty()) == false && tfDebit.getText().isEmpty()) {
					tfBalance.setBackground(Color.GREEN);
				}
//				paintBalance();
			}
		});

		btnBack.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/btnc.png")));
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelNumbers.add(btnBack);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setUI(new CustomTabbedPaneUI());

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(6, 131, 713, 663);

		tabbedPane.setFont(new Font("Monaco", Font.PLAIN, 14));

		contentPane.add(tabbedPane);

		JPanel panelAfghanistani = new JPanel();
		panelAfghanistani.setBackground(Color.WHITE);
		panelAfghanistani.setBorder(null);
		tabbedPane.addTab("main course", null, panelAfghanistani, null);
		panelAfghanistani.setLayout(new GridLayout(0, 5, 1, 1));

		JButton afghan1 = new JButton("");
		afghan1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selIt("QABILY UZBIKI", 7.99);
			}
		});
		afghan1.setBorder(null);
		afghan1.setBackground(new Color(255, 255, 255));
		afghan1.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/qabili.jpg")));
		panelAfghanistani.add(afghan1);

		JButton afghan2 = new JButton("");
		afghan2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("PAKOWRA", 3.99);
			}
		});
		afghan2.setBorder(null);
		afghan2.setBackground(new Color(255, 255, 255));
		afghan2.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/pakawra.jpg")));
		panelAfghanistani.add(afghan2);

		JButton afghan3 = new JButton("");
		afghan3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("ASHAK", 5.99);
			}
		});
		afghan3.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/ashak.jpg")));
		afghan3.setBorder(null);
		afghan3.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan3);

		JButton afghan4 = new JButton("");
		afghan4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("QURMA BAMIA", 4.99);
			}
		});
		afghan4.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/bamia.jpg")));
		afghan4.setBorder(null);
		afghan4.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan4);

		JButton afghan5 = new JButton("");
		afghan5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("BURANI BANJAN", 5.99);
			}
		});
		afghan5.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/banjan.jpg")));
		afghan5.setBorder(null);
		afghan5.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan5);

		JButton afghan6 = new JButton("");
		afghan6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("QURMA GOLPI", 4.99);
			}
		});
		afghan6.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/cauliflower.jpg")));
		afghan6.setBorder(null);
		afghan6.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan6);

		JButton afghan7 = new JButton("");
		afghan7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("SHOLA GURBANDI", 6.99);
			}
		});
		afghan7.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/shola.jpg")));
		afghan7.setBorder(null);
		afghan7.setBackground(Color.WHITE);
		panelAfghanistani.add(afghan7);

		JButton afghan8 = new JButton("");
		afghan8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("LOLA KABAB", 6.99);
			}
		});
		afghan8.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/shesh-kebab.jpg")));
		afghan8.setBorder(null);
		afghan8.setBackground(Color.WHITE);
		panelAfghanistani.add(afghan8);

		JButton afghan9 = new JButton("");
		afghan9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("QAILA KABAB", 6.99);
			}
		});
		afghan9.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/seikhkebab.jpg")));
		afghan9.setBorder(null);
		afghan9.setBackground(Color.WHITE);
		panelAfghanistani.add(afghan9);

		JButton afghan10 = new JButton("");
		afghan10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("CHICKEN QAILA KABAB", 4.99);
			}
		});
		afghan10.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/kabab-che.jpg")));
		afghan10.setBorder(null);
		afghan10.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan10);

		JButton afghan11 = new JButton("");
		afghan11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("SET OF BOLANI", 3.99);
			}
		});
		afghan11.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/bolani.jpg")));
		afghan11.setBorder(null);
		afghan11.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan11);

		JButton afghan12 = new JButton("");
		afghan12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("KABAB DEGI", 8.99);
			}
		});
		afghan12.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/kabab-degi.jpg")));
		afghan12.setBorder(null);
		afghan12.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan12);

		JButton afghan13 = new JButton("");
		afghan13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("CHIPS", 1.99);
			}
		});
		afghan13.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/chips.jpg")));
		afghan13.setBorder(null);
		afghan13.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan13);

		JButton afghan14 = new JButton("");
		afghan14.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("QURMA DHAAL", 3.99);
			}
		});
		afghan14.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/daal.jpg")));
		afghan14.setBorder(null);
		afghan14.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan14);

		JButton afghan15 = new JButton("");
		afghan15.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("CHICKEN ROOL", 5.99);
			}
		});
		afghan15.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/seekh-kabab.jpg")));
		afghan15.setBorder(null);
		afghan15.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan15);

		JButton afghan16 = new JButton("");
		afghan16.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("DO PIAZA", 9.99);
			}
		});
		afghan16.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/dopiaza.jpg")));
		afghan16.setBorder(null);
		afghan16.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan16);

		JButton afghan17 = new JButton("");
		afghan17.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("DAM POKHT", 7.99);
			}
		});
		afghan17.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/gosht qaaq berench.jpg")));
		afghan17.setBorder(null);
		afghan17.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan17);

		JButton afghan18 = new JButton("");
		afghan18.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("SOUP MORGHE", 2.99);
			}
		});
		afghan18.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/soapChicken.jpg")));
		afghan18.setBorder(null);
		afghan18.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan18);

		JButton afghan19 = new JButton("");
		afghan19.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("AASH", 2.99);
			}
		});
		afghan19.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/aash.jpg")));
		afghan19.setBorder(null);
		afghan19.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan19);

		JButton afghan20 = new JButton("");
		afghan20.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("PALAW & GOSHT QAAQ", 9.99);
			}
		});
		afghan20.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/gosht qaaq - Copy.jpg")));
		afghan20.setBorder(null);
		afghan20.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan20);

		JButton afghan21 = new JButton("");
		afghan21.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("KOFTA", 7.99);
			}
		});
		afghan21.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/kofta.jpg")));
		afghan21.setBorder(null);
		afghan21.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan21);

		JButton afghan22 = new JButton("");
		afghan22.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("CHICKEN KARAHI", 6.99);
			}
		});
		afghan22.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/Karahi-chick.JPG")));
		afghan22.setBorder(null);
		afghan22.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan22);

		JButton afghan23 = new JButton("");
		afghan23.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("GOSHT KARAHI", 7.99);
			}
		});
		afghan23.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/Karahi.jpg")));
		afghan23.setBorder(null);
		afghan23.setBackground(new Color(255, 255, 255));
		panelAfghanistani.add(afghan23);

		JButton afghan24 = new JButton("");
		afghan24.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("BAAL MORGH", 4.99);
			}
		});
		afghan24.setIcon(new ImageIcon(Restuarant.class.getResource("/DigitsImages/kebab morgh.jpg")));
		afghan24.setBorder(null);
		afghan24.setBackground(Color.WHITE);
		panelAfghanistani.add(afghan24);

		JButton button_24 = new JButton("");
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selIt("SHORBA", 5.99);
			}
		});
		button_24.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/spanishsoup.jpg")));
		button_24.setBorder(null);
		button_24.setBackground(Color.WHITE);
		panelAfghanistani.add(button_24);

		JPanel panelDessert = new JPanel();
		panelDessert.setBorder(null);
		panelDessert.setBackground(Color.WHITE);
		tabbedPane.addTab("fast food", null, panelDessert, null);
		panelDessert.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel desertPanel = new JPanel();
		desertPanel.setBorder(null);
		desertPanel.setForeground(Color.WHITE);
		desertPanel.setBackground(Color.WHITE);
		panelDessert.add(desertPanel);
		desertPanel.setLayout(new GridLayout(0, 4, 0, 0));
		//
		// JScrollPane scrollPane = new JScrollPane();
		// scrollPane.setViewportBorder(null);
		// scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		// scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// desertPanel.add(scrollPane);

		// JPanel desertPanel = new JPanel();
		// desertPanel.setBackground(Color.WHITE);
		// scrollPane.setViewportView(desertPanel);
		// desertPanel.setLayout(new GridLayout(2, 0, 0, 0));

		JButton starter9 = new JButton("");
		desertPanel.add(starter9);
		starter9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("CAKE SLICE", 1.99);
			}
		});
		starter9.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/cake1.jpg")));
		starter9.setBorder(null);
		starter9.setBackground(Color.WHITE);

		JButton starter10 = new JButton("");
		desertPanel.add(starter10);
		starter10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("FRUIT CAKE", 1.99);
			}
		});
		starter10.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/cake2.jpg")));
		starter10.setBorder(null);
		starter10.setBackground(Color.WHITE);

		JButton starter11 = new JButton("");
		desertPanel.add(starter11);
		starter11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("CREAM CAKE", 1.99);
			}
		});
		starter11.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/cake.jpg")));
		starter11.setBorder(null);
		starter11.setBackground(Color.WHITE);

		JButton starter13 = new JButton("");
		desertPanel.add(starter13);
		starter13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("SAMOSA", 2.99);
			}
		});
		starter13.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/khajore.jpg")));
		starter13.setBorder(null);
		starter13.setBackground(Color.WHITE);

		JButton starter12 = new JButton("");
		desertPanel.add(starter12);
		starter12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("LEMON CAKE", 1.99);
			}
		});
		starter12.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/cakelim.jpg")));
		starter12.setBorder(null);
		starter12.setBackground(Color.WHITE);

		JButton button_14 = new JButton("chapli kabab");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selIt("COOKIE", 8.0);
			}
		});
		button_14.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/kolcha.jpg")));
		button_14.setFont(new Font("SansSerif", Font.PLAIN, 0));
		button_14.setBorder(null);
		button_14.setBackground(Color.WHITE);
		desertPanel.add(button_14);

		JButton starter2 = new JButton("spring rolls");
		desertPanel.add(starter2);
		starter2.setFont(new Font("SansSerif", Font.PLAIN, 0));
		starter2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt(e.getActionCommand(), 4.99);
			}
		});
		starter2.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/appetizer.jpg")));
		starter2.setBorder(null);
		starter2.setBackground(Color.WHITE);

		JButton starter16 = new JButton("");
		desertPanel.add(starter16);
		starter16.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("OMELETTE", 4.99);// omelette
			}
		});
		starter16.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/vegi.jpg")));
		starter16.setBorder(null);
		starter16.setBackground(Color.WHITE);

		JButton starter5 = new JButton("chicken drumstick");
		desertPanel.add(starter5);
		starter5.setFont(new Font("SansSerif", Font.PLAIN, 0));
		starter5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt(e.getActionCommand(), 5.99);// chicken drumsticks
			}
		});
		starter5.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/Chicken Wings.jpg")));
		starter5.setBorder(null);
		starter5.setBackground(Color.WHITE);

		JButton starter8 = new JButton("");
		desertPanel.add(starter8);
		starter8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("BEAN BURGER", 2.99);
			}
		});
		starter8.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/bean-burger.jpg")));
		starter8.setBorder(null);
		starter8.setBackground(Color.WHITE);

		JButton starter4 = new JButton("beefburger");
		desertPanel.add(starter4);
		starter4.setFont(new Font("SansSerif", Font.PLAIN, 0));
		starter4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt(e.getActionCommand(), 2.99);
			}
		});
		starter4.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/beefburger.jpg")));
		starter4.setBorder(null);
		starter4.setBackground(Color.WHITE);

		JButton starter7 = new JButton("chicken burger");
		desertPanel.add(starter7);
		starter7.setFont(new Font("SansSerif", Font.PLAIN, 0));
		starter7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt(e.getActionCommand(), 1.99);
			}
		});
		starter7.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/Chicken-Burger-3.jpg")));
		starter7.setBorder(null);
		starter7.setBackground(Color.WHITE);

		JButton starter6 = new JButton("chicken tika");
		desertPanel.add(starter6);
		starter6.setFont(new Font("SansSerif", Font.PLAIN, 0));
		starter6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt(e.getActionCommand(), 7.99);
			}
		});
		starter6.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/chicken.jpg")));
		starter6.setBorder(null);
		starter6.setBackground(Color.WHITE);

		JButton Starter1 = new JButton("chapli kabab");
		desertPanel.add(Starter1);
		Starter1.setFont(new Font("SansSerif", Font.PLAIN, 0));
		Starter1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt(e.getActionCommand(), 5.99);
			}
		});
		Starter1.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/chapli-kebob.jpg")));
		Starter1.setBorder(null);
		Starter1.setBackground(Color.WHITE);

		JButton starter15 = new JButton("");
		desertPanel.add(starter15);
		starter15.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("KOFTA", 7.99);
			}
		});
		starter15.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/qurmaKachalo.jpg")));
		starter15.setBorder(null);
		starter15.setBackground(Color.WHITE);

		JButton starter14 = new JButton("");
		desertPanel.add(starter14);
		starter14.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selIt("PIZZA", 8.99);
			}
		});
		starter14.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/sabseborani.jpg")));
		starter14.setBorder(null);
		starter14.setBackground(Color.WHITE);

		JButton button_20 = new JButton("");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selIt("CHALI KEEBAB", 6.99);
			}
		});
		button_20.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/chapliKabab3.jpg")));
		button_20.setBorder(null);
		button_20.setBackground(Color.WHITE);
		desertPanel.add(button_20);

		JButton button_21 = new JButton("");
		button_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selIt("SOUP", 3.99);
			}
		});
		button_21.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/PASTa.jpg")));
		button_21.setBorder(null);
		button_21.setBackground(Color.WHITE);
		desertPanel.add(button_21);

		JButton button_22 = new JButton("");
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selIt("SALATA", 2.99);
			}
		});
		button_22.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/salad.jpg")));
		button_22.setBorder(null);
		button_22.setBackground(Color.WHITE);
		desertPanel.add(button_22);

		JButton button_23 = new JButton("");
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selIt("SAMOSA", 3.99);
			}
		});
		button_23.setIcon(new ImageIcon(Restuarant.class.getResource("/DesertStarter/samosas.jpg")));
		button_23.setBorder(null);
		button_23.setBackground(Color.WHITE);
		desertPanel.add(button_23);

		JPanel panelChoice = new JPanel();
		panelChoice.setBackground(Color.WHITE);
		panelChoice.setBorder(new LineBorder(Color.WHITE, 2));
		tabbedPane.addTab("custom pizza", null, panelChoice, null);
		panelChoice.setLayout(null);

		JScrollPane toppingScrollPane = new JScrollPane();
		toppingScrollPane.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		toppingScrollPane.setBackground(Color.WHITE);
		toppingScrollPane.setBounds(0, 6, 278, 622);
		panelChoice.add(toppingScrollPane);

		toppingTable = new JTable();
		toppingTable.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		toppingTable.setShowGrid(false);
		toppingTable.setBackground(new Color(250, 243, 255));
		toppingTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
		toppingTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "", "" }));
		toppingTable.setFillsViewportHeight(true);
		toppingTable.setForeground(Color.BLACK);
		toppingTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		toppingTable.getColumnModel().getColumn(1).setMaxWidth(80);
		toppingScrollPane.setViewportView(toppingTable);

		JPanel panel_1 = new JPanel();
		toppingScrollPane.setRowHeaderView(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnEnt = new JButton("");
		btnEnt.setBorder(null);
		btnEnt.setBackground(new Color(250, 243, 255));
		btnEnt.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/ENT.png")));
		btnEnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				enterPizza();
			}
		});
		panel_1.add(btnEnt);

		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setEditable(false);
		textField.setBackground(Color.BLACK);
		textField.setFont(new Font("SansSerif", Font.BOLD, 15));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBorder(null);
		panel_1.add(textField);
		textField.setColumns(3);

		cbSm = new JCheckBox("SMA");
		cbSm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

//				
//				double x= Double.parseDouble(textField.getText());
//				basePrice = 6.95;
//				textField.setText(formatter.format(basePrice));
			}
		});
		JButton btnCan = new JButton("");
		btnCan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelPizza();
			}
		});
		btnCan.setIcon(new ImageIcon(Restuarant.class.getResource("/controlButton/can (2).png")));
		btnCan.setBorder(null);
		btnCan.setForeground(Color.WHITE);
		// btnCan.setEnabled(false);
		btnCan.setBackground(Color.WHITE);
		panel_1.add(btnCan);
		cbSm.setSelected(true);
		buttonGroup_1.add(cbSm);
		cbSm.setBackground(Color.WHITE);
		panel_1.add(cbSm);

		cbMd = new JCheckBox("MID");
		cbMd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				basePrice = 7.95;
			}
		});
		buttonGroup_1.add(cbMd);
		cbMd.setBackground(Color.WHITE);
		panel_1.add(cbMd);

		cbLg = new JCheckBox("LAR");
		cbLg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				basePrice = 9.95;
			}
		});
		buttonGroup_1.add(cbLg);
		cbLg.setBackground(Color.WHITE);
		panel_1.add(cbLg);

		JButton button_15 = new JButton("");
		button_15.setBorder(null);
		button_15.setBackground(Color.WHITE);
		button_15.setEnabled(false);
		panel_1.add(button_15);

		JButton button_17 = new JButton("");
		button_17.setBorder(null);
		button_17.setEnabled(false);
		button_17.setBackground(Color.WHITE);
		panel_1.add(button_17);

		JButton button_18 = new JButton("");
		button_18.setBorder(null);
		button_18.setEnabled(false);
		button_18.setBackground(new Color(250, 243, 255));
		panel_1.add(button_18);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(null);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setEnabled(false);
		btnNewButton.setBackground(Color.WHITE);
		panel_1.add(btnNewButton);

		JButton button_19 = new JButton("");
		button_19.setForeground(Color.WHITE);
		button_19.setEnabled(false);
		button_19.setBorder(null);
		button_19.setBackground(Color.WHITE);
		panel_1.add(button_19);

		JPanel toppingFrame = new JPanel();
		toppingFrame.setBorder(null);
		toppingFrame.setBackground(Color.WHITE);
		toppingFrame.setBounds(276, 6, 437, 622);
		panelChoice.add(toppingFrame);
		toppingFrame.setLayout(new GridLayout(0, 4, 0, 0));

		JButton ONION = new JButton("Onion");
		ONION.setBorder(null);
		ONION.setFont(new Font("Dialog", Font.PLAIN, 0));
		ONION.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.39);
			}
		});
		ONION.setBackground(Color.WHITE);
		ONION.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/onion.jpg")));
		toppingFrame.add(ONION);

		JButton TOMATO = new JButton("Tomato");
		TOMATO.setBorder(null);
		TOMATO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.35);
			}
		});
		TOMATO.setFont(new Font("Dialog", Font.PLAIN, 0));
		TOMATO.setBackground(Color.WHITE);
		TOMATO.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/tomato.jpg")));
		toppingFrame.add(TOMATO);

		JButton PEPPER = new JButton("Peppers");
		PEPPER.setBorder(null);
		PEPPER.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		PEPPER.setFont(new Font("Dialog", Font.PLAIN, 0));
		PEPPER.setBackground(Color.WHITE);
		PEPPER.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/redPeppers.jpg")));
		toppingFrame.add(PEPPER);

		JButton SWEETCORN = new JButton("Sweetcorn");
		SWEETCORN.setBorder(null);
		SWEETCORN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.39);
			}
		});
		SWEETCORN.setFont(new Font("Dialog", Font.PLAIN, 0));
		SWEETCORN.setBackground(Color.WHITE);
		SWEETCORN.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/sweetcorn.png")));
		toppingFrame.add(SWEETCORN);

		JButton CHILLIES = new JButton("Chilies");
		CHILLIES.setBorder(null);
		CHILLIES.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.39);
			}
		});
		CHILLIES.setFont(new Font("Dialog", Font.PLAIN, 0));
		CHILLIES.setBackground(Color.WHITE);
		CHILLIES.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/greenchilli.jpg")));
		toppingFrame.add(CHILLIES);

		JButton CHEESE = new JButton("Cheese");
		CHEESE.setBorder(null);
		CHEESE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.35);
			}
		});
		CHEESE.setFont(new Font("Dialog", Font.PLAIN, 0));
		CHEESE.setBackground(Color.WHITE);
		CHEESE.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/cheese.jpg")));
		toppingFrame.add(CHEESE);

		JButton BLACKOLIVE = new JButton("Black Olives");
		BLACKOLIVE.setBorder(null);
		BLACKOLIVE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.39);
			}
		});
		BLACKOLIVE.setFont(new Font("Dialog", Font.PLAIN, 0));
		BLACKOLIVE.setBackground(Color.WHITE);
		BLACKOLIVE.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/blackOlives.jpg")));
		toppingFrame.add(BLACKOLIVE);

		JButton GREENOLIVE = new JButton("Green Olives");
		GREENOLIVE.setBorder(null);
		GREENOLIVE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.39);
			}
		});
		GREENOLIVE.setFont(new Font("Dialog", Font.PLAIN, 0));
		GREENOLIVE.setBackground(Color.WHITE);
		GREENOLIVE.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/olives.jpg")));
		toppingFrame.add(GREENOLIVE);

		JButton CHICKEN = new JButton("Chicken");
		CHICKEN.setBorder(null);
		CHICKEN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.39);
			}
		});
		CHICKEN.setFont(new Font("Dialog", Font.PLAIN, 0));
		CHICKEN.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/bacon.jpg")));
		CHICKEN.setBackground(Color.WHITE);
		toppingFrame.add(CHICKEN);

		JButton SAUSAGE = new JButton("Sausage");
		SAUSAGE.setBorder(null);
		SAUSAGE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.39);
			}
		});
		SAUSAGE.setFont(new Font("Dialog", Font.PLAIN, 0));
		SAUSAGE.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/pepperoni.png")));
		SAUSAGE.setBackground(Color.WHITE);
		toppingFrame.add(SAUSAGE);

		JButton MEATBALL = new JButton("Meatballs");
		MEATBALL.setBorder(null);
		MEATBALL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		MEATBALL.setFont(new Font("Dialog", Font.PLAIN, 0));
		MEATBALL.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/meatballs.jpg")));
		MEATBALL.setBackground(Color.WHITE);
		toppingFrame.add(MEATBALL);

		JButton SALAMI = new JButton("Salami");
		SALAMI.setBorder(null);
		SALAMI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		SALAMI.setFont(new Font("Dialog", Font.PLAIN, 0));
		SALAMI.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/salami.jpg")));
		SALAMI.setBackground(Color.WHITE);
		toppingFrame.add(SALAMI);

		JButton BARBECUE = new JButton("BBQ Sauce");
		BARBECUE.setBorder(null);
		BARBECUE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		BARBECUE.setFont(new Font("Dialog", Font.PLAIN, 0));
		BARBECUE.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/bbq.jpg")));
		BARBECUE.setBackground(Color.WHITE);
		toppingFrame.add(BARBECUE);

		JButton PINEAPPLE = new JButton("Pineapple");
		PINEAPPLE.setBorder(null);
		PINEAPPLE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		PINEAPPLE.setFont(new Font("Dialog", Font.PLAIN, 0));
		PINEAPPLE.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/pineapple.jpg")));
		PINEAPPLE.setBackground(Color.WHITE);
		toppingFrame.add(PINEAPPLE);

		JButton TUNA = new JButton("Tuna");
		TUNA.setBorder(null);
		TUNA.setFont(new Font("Dialog", Font.PLAIN, 0));
		TUNA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		TUNA.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/tuna.jpg")));
		TUNA.setBackground(Color.WHITE);
		toppingFrame.add(TUNA);

		JButton Mushroom = new JButton("Mushroom");
		Mushroom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		Mushroom.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/mushroom.jpg")));
		Mushroom.setBorder(null);
		Mushroom.setFont(new Font("Dialog", Font.PLAIN, 0));
		Mushroom.setBackground(Color.WHITE);
		toppingFrame.add(Mushroom);

		JButton JALEPENO = new JButton("Jalepeno");
		JALEPENO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		JALEPENO.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/jalapino.jpg")));
		JALEPENO.setBorder(null);
		JALEPENO.setFont(new Font("Dialog", Font.PLAIN, 0));
		JALEPENO.setBackground(Color.WHITE);
		toppingFrame.add(JALEPENO);

		JButton BananaPeppers = new JButton("Banana Peppers");
		BananaPeppers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		BananaPeppers.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/Banana_Peppers.jpg")));
		BananaPeppers.setBorder(null);
		BananaPeppers.setFont(new Font("Dialog", Font.PLAIN, 0));
		BananaPeppers.setBackground(Color.WHITE);
		toppingFrame.add(BananaPeppers);

		JButton BASIL = new JButton("Basil");
		BASIL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		BASIL.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/basil.jpg")));
		BASIL.setBorder(null);
		BASIL.setFont(new Font("Dialog", Font.PLAIN, 0));
		BASIL.setBackground(Color.WHITE);
		toppingFrame.add(BASIL);

		JButton CALAMILIZEDONION = new JButton("Calamilized Onion");
		CALAMILIZEDONION.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		CALAMILIZEDONION.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/caramelized-onion.jpg")));
		CALAMILIZEDONION.setBorder(null);
		CALAMILIZEDONION.setFont(new Font("Dialog", Font.PLAIN, 0));
		CALAMILIZEDONION.setBackground(Color.WHITE);
		toppingFrame.add(CALAMILIZEDONION);

		JButton CORNEDBEEF = new JButton("Corned Beef");
		CORNEDBEEF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		CORNEDBEEF.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/cornered beaf.jpeg")));
		CORNEDBEEF.setBorder(null);
		CORNEDBEEF.setFont(new Font("Dialog", Font.PLAIN, 0));
		CORNEDBEEF.setBackground(Color.WHITE);
		toppingFrame.add(CORNEDBEEF);

		JButton GARLIC = new JButton("Garlic");
		GARLIC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		GARLIC.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/garlic.jpg")));
		GARLIC.setBorder(null);
		GARLIC.setFont(new Font("Dialog", Font.PLAIN, 0));
		GARLIC.setBackground(Color.WHITE);
		toppingFrame.add(GARLIC);

		JButton PRAWN = new JButton("Prawn");
		PRAWN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		PRAWN.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/Prawn.jpg")));
		PRAWN.setBorder(null);
		PRAWN.setFont(new Font("Dialog", Font.PLAIN, 0));
		PRAWN.setBackground(Color.WHITE);
		toppingFrame.add(PRAWN);

		JButton SPRINGONION = new JButton("Spring Onion");
		SPRINGONION.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTopping(e.getActionCommand(), 0.49);
			}
		});
		SPRINGONION.setIcon(new ImageIcon(Restuarant.class.getResource("/PizzaImages/springOnion.jpg")));
		SPRINGONION.setBorder(null);
		SPRINGONION.setFont(new Font("Dialog", Font.PLAIN, 0));
		SPRINGONION.setBackground(Color.WHITE);
		toppingFrame.add(SPRINGONION);

		JPanel panelPizza = new JPanel();
		panelPizza.setBorder(null);
		panelPizza.setBackground(Color.WHITE);
		tabbedPane.addTab("pizza menue", null, panelPizza, null);
		panelPizza.setLayout(null);

		JScrollPane pizzaScroolPane = new JScrollPane();
		pizzaScroolPane.setBorder(null);
		pizzaScroolPane.setBackground(Color.WHITE);
		pizzaScroolPane.setViewportBorder(null);
		pizzaScroolPane.setBounds(0, -1, 707, 749);
		panelPizza.add(pizzaScroolPane);

		pizzaTable = new JTable();
		pizzaTable.setBackground(Color.WHITE);
		pizzaTable.setForeground(Color.BLACK);
		pizzaTable.setShowGrid(false);
		pizzaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pizzaTable.setFocusable(false);
		pizzaTable.setFocusTraversalPolicyProvider(true);
		pizzaTable.setFocusCycleRoot(true);
		pizzaTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (cbSmall.isSelected() || cbMidim.isSelected() || cbLarge.isSelected()) {
					if (cbSmall.isSelected()) {
						double x = Double.parseDouble("" + PizzaModel.getValueAt(pizzaTable.getSelectedRow(), 2));
						selIt("" + PizzaModel.getValueAt(pizzaTable.getSelectedRow(), 0), x);
						cbRe.setSelected(true);
					} else if (cbMidim.isSelected()) {
						double x = Double.parseDouble("" + PizzaModel.getValueAt(pizzaTable.getSelectedRow(), 3));
						selIt("" + PizzaModel.getValueAt(pizzaTable.getSelectedRow(), 0), x);
						cbRe.setSelected(true);
					} else if (cbLarge.isSelected()) {
						double x = Double.parseDouble("" + PizzaModel.getValueAt(pizzaTable.getSelectedRow(), 4));
						selIt("" + PizzaModel.getValueAt(pizzaTable.getSelectedRow(), 0), x);
						cbRe.setSelected(true);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Choose Pizza Size Please !");
				}
			}
		});
		pizzaTable.setShowHorizontalLines(true);
		pizzaTable.setRowHeight(50);
		pizzaTable.setGridColor(Color.BLACK);
		pizzaTable.setFont(new Font("SansSerif", Font.PLAIN, 10));
		pizzaTable.setBorder(null);
		pizzaTable.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "NAME", "INGREDIENTS", "SM", "MD", "LG" }));

		PizzaModel = (DefaultTableModel) pizzaTable.getModel();

		pizzaTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		pizzaTable.getColumnModel().getColumn(0).setMaxWidth(200);
		pizzaTable.getColumnModel().getColumn(1).setPreferredWidth(550);
		pizzaTable.getColumnModel().getColumn(1).setMaxWidth(700);
		pizzaTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		pizzaTable.getColumnModel().getColumn(2).setMaxWidth(70);
		pizzaTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		pizzaTable.getColumnModel().getColumn(3).setMaxWidth(70);
		pizzaTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		pizzaTable.getColumnModel().getColumn(4).setMaxWidth(70);

		pizzaTable.setDefaultEditor(Object.class, null);// disable editing

		pizzaScroolPane.setViewportView(pizzaTable);

		JPanel pizzaSidePanel = new JPanel();
		pizzaSidePanel.setBackground(Color.BLACK);
		pizzaSidePanel.setBorder(null);
		pizzaScroolPane.setRowHeaderView(pizzaSidePanel);
		pizzaSidePanel.setLayout(new GridLayout(0, 1, 0, 0));

		cbRe = new JCheckBox("");
		cbRe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		cbRe.setBackground(Color.BLACK);
		buttonGroup.add(cbRe);
		cbRe.setVisible(false);
		pizzaSidePanel.add(cbRe);

		cbSmall = new JCheckBox("SM");
		cbSmall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(cbSmall);
		cbSmall.setVerticalAlignment(SwingConstants.BOTTOM);
		cbSmall.setBackground(new Color(51, 204, 0));
		pizzaSidePanel.add(cbSmall);

		cbMidim = new JCheckBox("MD");
		cbMidim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(cbMidim);
		cbMidim.setBackground(new Color(51, 102, 0));
		pizzaSidePanel.add(cbMidim);

		cbLarge = new JCheckBox("LG");
		cbLarge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(cbLarge);
		cbLarge.setVerticalAlignment(SwingConstants.TOP);
		cbLarge.setBackground(new Color(153, 204, 0));
		pizzaSidePanel.add(cbLarge);

		JPanel panelOffer = new JPanel();
		tabbedPane.addTab("offers", null, panelOffer, null);

		PizzaModel.insertRow(0, new Object[] { "Cheese & Tomato", "Cheese & Tomato", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(1, new Object[] { "Vegetarian Hot", "Mushrooms, Onions, Green Peppers, Jalapeno Peppers",
				"4.99", "6.99", "7.99" });
		PizzaModel.insertRow(2,
				new Object[] { "Vegetarian", "Mushrooms, Onions, Green Peppers, Sweetcorn", "4.99", "6.99", "7.99" });
		PizzaModel.insertRow(3, new Object[] { "Hawaiian", "Beef, Pineapple", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(4, new Object[] { "Mushrom & Beef", "Beef Mushroom", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(5,
				new Object[] { "Garlic Sizzler",
						"Tomato sauce, Chicken, Bacon, Pepperoni, Green and Red Peppers, drizzled with Garlic Sauce",
						"6.95", "7.95", "9.95" });
		PizzaModel.insertRow(6,
				new Object[] { "Chinese", "Chinese Chicken, Mushrooms & Sweetcorn", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(7,
				new Object[] { "BBQ Beast",
						"BBQ Sauce, Chicken, Pepperoni, Meatballs, Green peppers, red peppers, Jalapeno Peppers",
						"6.95", "7.95", "9.95" });
		PizzaModel.insertRow(8,
				new Object[] { "Flaming Torch",
						"Pepperoni, Tandoori Chicken, Onion, Jalapeno Peppers, Green Chillies, Garlic Sauce", "6.95",
						"7.95", "9.95" });
		PizzaModel.insertRow(9, new Object[] { "Chicken Hot",
				"Tandoori Chicken, Green Chillies, Fresh Tomato & Mushrooms", "6.95", "7.95", "9.95" });

		PizzaModel.insertRow(10, new Object[] { "Double Pepperoni",
				"Double Cheese and double pepperoni, on a tomato base ", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(11, new Object[] { "Tuna Thunder", "Tuna, Fresh Tomato, Green Chillies & Fresh Garlic",
				"6.95", "7.95", "9.95" });
		PizzaModel.insertRow(12, new Object[] { "Alligator", "Pepperoni, Beef, Bacon, Garlic Sausage & Salami", "6.95",
				"7.95", "9.95" });
		PizzaModel.insertRow(13, new Object[] { "Meatball Feast",
				"Bacon, Garlic Sausage, Meatball, Pepperoni & BBQ Sauce Base", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(14, new Object[] { "New Yorker",
				"American Hotdog, BBQ Sauce, Melted Cheese & Fried Onions", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(15, new Object[] { "BBQ Original",
				"Chicken, Green Peppers, Fried Onion & Special BBQ Sauce", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(16,
				new Object[] { "Meaty One", "Beef, Pepperoni, Chicken, Bacone", "6.95", "7.95", "9.95" });
		PizzaModel.insertRow(17, new Object[] { "Free Choice", "Cheese & Tomato plus 4 Toppings of your choice", "6.95",
				"7.95", "9.95" });
		PizzaModel.insertRow(18, new Object[] { "GoGo Special", "Green Peppers, Mushrooms, Onions, Beef & Pepperoni",
				"6.95", "7.95", "9.95" });
		PizzaModel.insertRow(19,
				new Object[] { "Stuffed Edge", "Pepperoni, Onions, Chillies & Green peppers", "6.95", "7.95", "9.95" });

		JPanel calclationPanel = new JPanel();
		calclationPanel.setBackground(Color.WHITE);
		calclationPanel.setBounds(721, 6, 553, 70);
		contentPane.add(calclationPanel);
		calclationPanel.setLayout(new GridLayout(1, 0, 0, 0));

		tfCredit = new JTextArea();
		tfCredit.setWrapStyleWord(true);
		tfCredit.setLineWrap(true);
		calclationPanel.add(tfCredit);
		tfCredit.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				try {
					Double re = Double.parseDouble(tfCredit.getText()) - Double.parseDouble(tfDebit.getText());
					tfBalance.setText(formatter.format(re));
				} catch (NumberFormatException e) {

				}
//				if (!tfCredit.getText().isEmpty()) {
//					btnDelivery.setEnabled(false);
//				}
			}
		});

		tfCredit.setEditable(false);
		tfCredit.setForeground(new Color(0, 128, 0));
		tfCredit.setFont(new Font("DS-Digital", Font.PLAIN, 20));
		tfCredit.setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 5), "CREDIT", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));

		tfCredit.setBackground(new Color(255, 255, 255));

		tfDebit = new JTextField();
		calclationPanel.add(tfDebit);
		tfDebit.setEditable(false);
		tfDebit.setForeground(new Color(255, 0, 0));
		tfDebit.setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 5), "DEBIT", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		tfDebit.setHorizontalAlignment(SwingConstants.CENTER);
		tfDebit.setFont(new Font("DS-Digital", Font.PLAIN, 20));
		tfDebit.setBackground(new Color(255, 255, 255));

		tfTax = new JTextField();
		tfTax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showInputDialog("enter new tax %");
			}
		});
		tfTax.setText("20%");
		tfTax.setHorizontalAlignment(SwingConstants.CENTER);
		tfTax.setForeground(new Color(0, 0, 0));
		tfTax.setFont(new Font("DS-Digital", Font.PLAIN, 20));
		tfTax.setEditable(false);
		tfTax.setBorder(new TitledBorder(new LineBorder(new Color(230, 230, 250), 5), "VAT", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		tfTax.setBackground(new Color(255, 255, 255));
		calclationPanel.add(tfTax);

		rdbtnOn = new JRadioButton("5.0");
		rdbtnOn.setForeground(Color.WHITE);
		rdbtnOn.setFont(new Font("DS-Digital", Font.PLAIN, 20));

		rdbtnOn.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnOn.setBackground(Color.BLACK);
		calclationPanel.add(rdbtnOn);
		rdbtnOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDeliveryCharge(5.0);
				tfDelivery.setText("" + getDeliveryCharge());
//				subTotal = subTotal + 5.0;
				if (tfDebit.getText().isEmpty() == false) {
					double temp = Double.parseDouble(tfDebit.getText());
					tfDebit.setText(formatter.format(temp + 5.0));
					tfBalance.setText(tfDebit.getText());
					subTotal = Double.parseDouble(tfDebit.getText());
				} else {
					double temp = 0.0;
					tfDebit.setText(formatter.format(temp + 5.0));
					tfBalance.setText(tfDebit.getText());
					subTotal = Double.parseDouble(tfDebit.getText());
				}

				rdbtnOn.setEnabled(false);
				rdbtnOff.setEnabled(true);
			}
		});
		buttonGroup_2.add(rdbtnOn);

		rdbtnOff = new JRadioButton("-5.0");
		rdbtnOff.setForeground(Color.WHITE);
		rdbtnOff.setFont(new Font("DS-Digital", Font.PLAIN, 20));
		rdbtnOff.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDeliveryCharge(0.0);
				tfDelivery.setText("" + getDeliveryCharge());
//				subTotal = subTotal - 5.0;
				if (tfDebit.getText().isEmpty() == false) {
					double temp = Double.parseDouble(tfDebit.getText());
					tfDebit.setText(formatter.format(temp - 5.0));
					tfBalance.setText(tfDebit.getText());
					subTotal = Double.parseDouble(tfDebit.getText());
				} else {
					double temp = 0.0;
					tfDebit.setText(formatter.format(temp - 5.0));
					tfBalance.setText(tfDebit.getText());
					subTotal = Double.parseDouble(tfDebit.getText());
				}
				rdbtnOn.setEnabled(true);
				rdbtnOff.setEnabled(false);
			}
		});

		tfDelivery = new JTextField();
		tfDelivery.setForeground(Color.WHITE);
		tfDelivery.setHorizontalAlignment(SwingConstants.CENTER);
//				tfDelivery.setBackground(new Color(245, 245, 220));
		tfDelivery.setFont(new Font("DS-Digital", Font.PLAIN, 20));
		tfDelivery.setEditable(false);
		tfDelivery.setBorder(new TitledBorder(new LineBorder(new Color(245, 245, 220), 5), "DELIVERY",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
//				.setBackground(new Color(245, 245, 220));
		tfDelivery.setBackground(Color.BLACK);
		calclationPanel.add(tfDelivery);
		rdbtnOff.setBackground(Color.BLACK);
		calclationPanel.add(rdbtnOff);
		buttonGroup_2.add(rdbtnOff);
		rdbtnOff.setSelected(true);

		JPanel panelRecipt = new JPanel();
		panelRecipt.setBorder(null);
		panelRecipt.setBackground(Color.WHITE);
		panelRecipt.setBounds(720, 399, 288, 81);
		contentPane.add(panelRecipt);

		JButton btnRecipt = new JButton("");
		btnRecipt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Restuarant.Model.insertRow(Restuarant.Model.getRowCount(), new Object[] { "====================" });
				;

				Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
						new Object[] { "Total : " + tfDebit.getText() });
//				if (deliverable == true) {
//					Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
//							new Object[] { "Delivery   : " + formatter.format(deliveryCost) });
//				}

				if (taxable == true) {
					double x = Double.parseDouble(tfDebit.getText());
					Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
							new Object[] { "Tax :" + formatter.format(x * 20 / 100) });
				}
				Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
						new Object[] { "Paid   : " + tfCredit.getText() });
				Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
						new Object[] { "Change : " + tfBalance.getText() });
				Restuarant.Model.insertRow(Restuarant.Model.getRowCount(), new Object[] { "====================" });
				Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
						new Object[] { "Thank you for shopping with us @" });
				Restuarant.Model.insertRow(Restuarant.Model.getRowCount(), new Object[] { "" + new Date() });

				MessageFormat header = new MessageFormat("WELCOME RESTUARANT");
				int Integer = 1;

				MessageFormat footer = new MessageFormat("PAGE {0,," + Integer + "}");
				++Integer;
				try {
					calculationTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				} catch (java.awt.print.PrinterException e) {
				}
			}
		});
		panelRecipt.setLayout(new GridLayout(1, 0, 0, 0));
		btnRecipt.setBorder(null);
		btnRecipt.setIcon(new ImageIcon(Restuarant.class.getResource("/buttons/receButton.png")));
		btnRecipt.setBackground(Color.WHITE);
		panelRecipt.add(btnRecipt);

		JButton btnSearch = new JButton("");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Restuarant.Model = (DefaultTableModel) calculationTable.getModel();
				if (Model.getRowCount() < 1) {
					Model.setRowCount(0);
					SearchFrame sf = new SearchFrame();

					sf.setVisible(true);
				}
			}
		});

		JButton btnReport = new JButton("");
		panelRecipt.add(btnReport);
		btnReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Restuarant.Model = (DefaultTableModel) calculationTable.getModel();

				if (Model.getRowCount() < 1) {
					try {
						stmt = con.createStatement();
						rs = stmt.executeQuery("SELECT * FROM TEST32");
						while (rs.next()) {
							String name = rs.getString("name");
							String por = rs.getString("portion");
							String price = rs.getString("price");
							String total = rs.getString("total");
//							String dateOfSale = rs.getString("dateOfSale");
//							String timeOfSale = rs.getString("timeOfSale");
							Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
									new Object[] { name, por, price, total });
						}
						dbReader = true;
						stmt.close();
						rs.close();
					} catch (SQLException e) {
//						create();
					}
				}
			}
		});
		btnReport.setIcon(new ImageIcon(Restuarant.class.getResource("/buttons/repoButton.png")));
		btnReport.setBorder(null);
		btnReport.setBackground(Color.WHITE);
		btnSearch.setIcon(new ImageIcon(Restuarant.class.getResource("/buttons/searButton.png")));
		btnSearch.setBorder(null);
		btnSearch.setBackground(Color.WHITE);
		panelRecipt.add(btnSearch);

	}

	protected void paintBalance() {

		try {
			double credit = Double.parseDouble(tfCredit.getText());
			double debit = Double.parseDouble(tfDebit.getText());

			if (credit > debit) {
				tfBalance.setBackground(Color.GREEN);
			} else if (credit < debit) {
				tfBalance.setBackground(Color.RED);
			} else if (credit == debit) {
				tfBalance.setBackground(Color.WHITE);
			} else {
				tfBalance.setBackground(Color.WHITE);
			}
		} catch (NumberFormatException e) {
			if (tfCredit.getText().isEmpty()) {
				tfBalance.setBackground(Color.RED);
			}
			if (tfDebit.getText().isEmpty()) {
				tfBalance.setBackground(Color.GREEN);
			}
		}
	}

	protected void cancelPizza() {
		toppingModel = (DefaultTableModel) toppingTable.getModel();
		toppingModel.setRowCount(0);
		toppings = 0.0;
		textField.setText(null);
		pizzaOrdered = false;
	}

	protected void enterPizza() {
		Double x = Double.parseDouble(textField.getText());
		int portion = Integer.parseInt(tfPortion.getText());
		MessageFormat header = new MessageFormat(
				"Pay+ " + formatter.format(x) + "x" + formatter.format(portion) + "=" + formatter.format(portion * x));
		int Integer = 1;
		MessageFormat footer = new MessageFormat("PAGE {0,," + Integer + "}");
		++Integer;
		try {
			PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
//		set.add(OrientationRequested.LANDSCAPE);
			set.add(OrientationRequested.PORTRAIT);
			toppingTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
			selIt("FC toppings pizza", Double.parseDouble(textField.getText()));
			toppingModel = (DefaultTableModel) toppingTable.getModel();
			toppingModel.setRowCount(0);
			textField.setText(null);
		} catch (java.awt.print.PrinterException e1) {
		}
		pizzaOrdered = false;

	}

//	private void captureComponent(Component component) {
//		Rectangle rect = component.getBounds();
//		String receipt = "receipt";
//		try {
//			String format = "PNG";
//			String fileName = receipt + "." + format;
//			BufferedImage captureImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
//			component.paint(captureImage.getGraphics());
//
//			ImageIO.write(captureImage, format, new File(fileName));
//
//		} catch (IOException ex) {
//			System.err.println(ex);
//		}
//	}

	protected void setTopping(String actionCommand, double d) {
		toppingModel = (DefaultTableModel) toppingTable.getModel();
		toppingModel.insertRow(toppingModel.getRowCount(), new Object[] { " " + actionCommand, d });
		if (cbSm.isSelected()) {
			basePrice = 6.95;
		} else if (cbMd.isSelected()) {
			basePrice = 7.95;
		} else if (cbLg.isSelected()) {
			basePrice = 9.95;
		}

		toppings += d;
		textField.setText(formatter.format(basePrice + toppings));
		pizzaOrdered = true;
		paintBalance();
	}

	protected void selIt(String string, double d) {
		if (dbReader == false) {
			tfCredit.setText("");
			int num = Integer.parseInt(tfPortion.getText());
			addToTable(string, num, d);
			tfPortion.setText("1");

		} else {
			Restuarant.Model = (DefaultTableModel) calculationTable.getModel();
			Restuarant.Model.setRowCount(0);
			Restuarant.dbReader = false;
			tfCredit.setText("");
			int num = Integer.parseInt(tfPortion.getText());
			addToTable(string, num, d);
			tfPortion.setText("1");
		}
		paintBalance();
	}

	private void create() {
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(
					"CREATE TABLE TEST32 (NAME VARCHAR(30), PORTION VARCHAR(30), PRICE VARCHAR(30), TOTAL VARCHAR(30), dateOfSale VARCHAR(30), timeOfSale VARCHAR(30))");
		} catch (SQLException e) {
			e.getMessage();
		}

	}

	private void dropTable() {
		if (JOptionPane.showInputDialog("").equals("sohrab1363")) {
			try {
				stmt = con.createStatement();
				stmt.executeUpdate("DROP TABLE TEST32");
				new InputMessage().setVisible(true);
				setVisible(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void clearOldValues() {
		tfCredit.setText("");
	}

	protected void addToTable(String name, int portion, double price) {

		if (taxable) {

			double x = price * portion;
			double y = x * tax;
			total = x + y;

		} else {
			total = price * portion;
		}

		Model = (DefaultTableModel) calculationTable.getModel();
		Model.insertRow(Model.getRowCount(),
				new Object[] { "   " + name.toLowerCase(), portion + "  x", price, formatter.format(total) });

		subTotal = subTotal + total;
		tfDebit.setText(formatter.format(subTotal));
		tfBalance.setText(formatter.format(Double.parseDouble(tfDebit.getText())));
	}

	public static void selectiveSearch(String from, String to) {

		Restuarant.Model = (DefaultTableModel) calculationTable.getModel();
		if (Model.getRowCount() < 1) {
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM TEST32 WHERE dateOfSale BETWEEN '" + from + "' AND '" + to + "'");
				while (rs.next()) {
					String name = rs.getString("name");
					String por = rs.getString("portion");
					String price = rs.getString("price");
					String total = rs.getString("total");

					Restuarant.Model.insertRow(Restuarant.Model.getRowCount(),
							new Object[] { name.toLowerCase(), por, price, total });
				}
				dbReader = true;
				stmt.close();
				rs.close();
			} catch (SQLException e) {
//				System.out.println(e.getMessage());
			}
		}

//		try {
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("SELECT * FROM LISTING WHERE NAME = 'IM'");
//			while (rs.next()) {
////				String name = rs.getString("name");
//				String img = rs.getString("img");
////				starter3.setIcon(new ImageIcon(Restuarant.class.getResource(img)));
//
//			}
//			stmt.close();
//			rs.close();
//		} catch (SQLException e) {
////			System.out.println(e.getMessage());
//		}

	}

	public double getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	NumberFormat formatter = new DecimalFormat("#0.00");
	public static DefaultTableModel Model;
	private JTextField tfBalance;
	private JTextArea tfCredit;
	private static String derbyDriver;
	public static java.sql.Connection con;
	public static java.sql.ResultSet rs;
	public static Statement stmt;
	private JTable pizzaTable;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable toppingTable;
	private JTextField textField;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	/* tonight work on delivery charge */
	private double deliveryCharge = 5.0;
	private JTextField tfDelivery;
	private JTextField tfTax;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
}
