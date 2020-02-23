package Laptop;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.mail.MessagingException;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Rest extends JFrame {
	private boolean pizzaOrdered;
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
	private double tax = 0.2D;
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
	public static boolean isValidated;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (Rest.accessGranted) {
						System.out.println("nnn");
						Rest frame = new Rest(Rest.accessGranted);

						frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Rest(boolean x) {
		accessGranted = x;

//    setType(Window.Type.UTILITY);
		try {
			derbyDriver = "jdbc:derby:codejava/msfata;create=true";
			con = DriverManager.getConnection(derbyDriver);
			create();
		} catch (Exception localException) {
		}
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setBackground(new Color(0, 0, 0));
		setDefaultCloseOperation(3);
		setBounds(0, 0, width, height);

		this.contentPane = new JPanel();
		this.contentPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				try {
					Rest.stmt = Rest.con.createStatement();
					Rest.stmt.executeUpdate("DELETE FROM TEST32");
					Rest.stmt.close();
				} catch (SQLException localSQLException) {
				}
			}
		});
		this.contentPane.setForeground(Color.WHITE);
		this.contentPane.setBackground(new Color(255, 255, 255));

		this.contentPane.setBorder(null);
		setUndecorated(true);
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setVerticalScrollBarPolicy(21);
		scrollPane_1.setBounds(574, 5, 1334, 192);
		scrollPane_1.setViewportBorder(null);
		scrollPane_1.setHorizontalScrollBarPolicy(32);
		this.contentPane.add(scrollPane_1);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		scrollPane_1.setViewportView(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SEVEN UP", 0.59D);
			}
		});
		button_1.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/7 UP.jpg")));
		button_1.setHorizontalAlignment(0);
		button_1.setBorder(null);
		button_1.setBackground(Color.WHITE);
		panel.add(button_1);

		JButton button_9 = new JButton("");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("COCA COLA", 0.59D);
			}
		});
		button_9.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/cokacola.jpg")));
		button_9.setHorizontalAlignment(0);
		button_9.setBorder(null);
		button_9.setBackground(Color.WHITE);
		panel.add(button_9);

		JButton button_8 = new JButton("");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("FANTA", 0.59D);
			}
		});
		button_8.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/fanta.jpg")));
		button_8.setHorizontalAlignment(0);
		button_8.setBorder(null);
		button_8.setBackground(Color.WHITE);
		panel.add(button_8);

		JButton button_7 = new JButton("");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PEPSI", 0.59D);
			}
		});
		button_7.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/pepsi.png")));
		button_7.setHorizontalAlignment(0);
		button_7.setBorder(null);
		button_7.setBackground(Color.WHITE);
		panel.add(button_7);

		JButton button_6 = new JButton("");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("TANGO", 0.59D);
			}
		});
		button_6.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/tango.jpg")));
		button_6.setHorizontalAlignment(0);
		button_6.setBorder(null);
		button_6.setBackground(Color.WHITE);
		panel.add(button_6);

		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("MERANDA", 0.59D);
			}
		});
		button_5.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/merinda.jpg")));
		button_5.setHorizontalAlignment(0);
		button_5.setBorder(null);
		button_5.setBackground(Color.WHITE);
		panel.add(button_5);

		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BOTTLE", 1.49D);
			}
		});
		button_4.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/softBigBottle.jpg")));
		button_4.setHorizontalAlignment(0);
		button_4.setBorder(null);
		button_4.setBackground(Color.WHITE);
		panel.add(button_4);

		JButton button_13 = new JButton("");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("WATER", 0.5D);
			}
		});
		button_13.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/Evian.jpg")));
		button_13.setHorizontalAlignment(0);
		button_13.setBorder(null);
		button_13.setBackground(Color.WHITE);
		panel.add(button_13);

		JButton button_12 = new JButton("");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SAFRAN TEA", 0.5D);
			}
		});
		button_12.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/Saffron-Tea.jpg")));
		button_12.setHorizontalAlignment(0);
		button_12.setBorder(null);
		button_12.setBackground(Color.WHITE);
		panel.add(button_12);

		JButton button_11 = new JButton("");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("COFFEE", 0.5D);
			}
		});
		button_11.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/coffee.jpg")));
		button_11.setHorizontalAlignment(0);
		button_11.setBorder(null);
		button_11.setBackground(Color.WHITE);
		panel.add(button_11);

		JButton button_10 = new JButton("");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("GREEN TEA", 0.5D);
			}
		});
		button_10.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/teagreen.jpg")));
		button_10.setHorizontalAlignment(0);
		button_10.setBorder(null);
		button_10.setBackground(Color.WHITE);
		panel.add(button_10);

		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BLACK TEA", 0.5D);
			}
		});
		button_3.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/teablack.jpg")));
		button_3.setHorizontalAlignment(0);
		button_3.setBorder(null);
		button_3.setBackground(Color.WHITE);
		panel.add(button_3);

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SHEER YAKH", 3.5D);
			}
		});
		button_2.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/sheerYakh.jpg")));
		button_2.setHorizontalAlignment(0);
		button_2.setBorder(null);
		button_2.setBackground(Color.WHITE);
		panel.add(button_2);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("YOGART", 1.5D);
			}
		});
		button.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/yogurt.jpg")));
		button.setHorizontalAlignment(0);
		button.setBorder(null);
		button.setBackground(Color.WHITE);
		panel.add(button);

		JScrollPane calCulationscrollPane = new JScrollPane();
		calCulationscrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Rest.this.dropTable();
			}
		});
		calCulationscrollPane.setViewportBorder(null);
		calCulationscrollPane.setBounds(9, 274, 557, 468);
		this.contentPane.add(calCulationscrollPane);
		calCulationscrollPane.setBorder(new LineBorder(Color.WHITE));

		calculationTable = new JTable();
		calculationTable.setShowGrid(false);
		calculationTable.setRowSelectionAllowed(false);
		calculationTable.setFillsViewportHeight(true);
		calculationTable.setGridColor(Color.WHITE);
		calculationTable.setForeground(new Color(0, 0, 0));
		calculationTable.setRowHeight(18);
		calculationTable.setFont(new Font("Tahoma", 0, 18));
		calculationTable.setBorder(null);
		calculationTable.setBackground(new Color(255, 255, 255));
		calculationTable
				.setModel(new DefaultTableModel(new Object[0][], new String[] { "NAME", "PORTION", "PRICE", "TOTAL" }));

		calculationTable.setRowHeight(25);

		calculationTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		calculationTable.getColumnModel().getColumn(1).setMaxWidth(100);

		calculationTable.getColumnModel().getColumn(2).setPreferredWidth(70);
		calculationTable.getColumnModel().getColumn(2).setMaxWidth(100);

		calculationTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		calculationTable.getColumnModel().getColumn(3).setMaxWidth(150);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(2);
		calculationTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

		calCulationscrollPane.setViewportView(calculationTable);

		JPanel taxSidePanel = new JPanel();
		taxSidePanel.setForeground(Color.WHITE);
		calCulationscrollPane.setRowHeaderView(taxSidePanel);
		taxSidePanel.setBorder(new LineBorder(Color.WHITE, 2));
		taxSidePanel.setBackground(Color.WHITE);

		final JButton btnTax = new JButton("");
		btnTax.setForeground(Color.WHITE);

		btnTax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!Rest.this.taxable) {
					btnTax.setBorder(new TitledBorder(null, "ON", 4, 2, null, Color.RED));

					Rest.this.tfDebit.setText(Rest.this.formatter.format(Rest.this.subTotal));
					Rest.this.tfBalance.setText(Rest.this.formatter.format(Rest.this.subTotal));

					Rest.this.taxable = true;
				} else {
					btnTax.setBorder(new TitledBorder(null, "OFF", 4, 2, null, Color.BLACK));

					Rest.this.tfDebit.setText(Rest.this.formatter.format(Rest.this.subTotal));
					Rest.this.tfBalance.setText(Rest.this.formatter.format(Rest.this.subTotal));

					Rest.this.taxable = false;
				}
			}
		});
		taxSidePanel.setLayout(new GridLayout(0, 1, 0, 0));
		btnTax.setIcon(new ImageIcon(Rest.class.getResource("/buttons/taxButton.png")));
		btnTax.setBorder(new TitledBorder(null, "OFF", 4, 2, null, Color.BLACK));
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
			public void actionPerformed(ActionEvent e) {
				Rest.this.amount = Integer.parseInt(Rest.this.tfPortion.getText());
				Rest.this.amount += 1;
				Rest.this.tfPortion.setText(String.valueOf(Rest.this.amount));
			}
		});
		panelSideTable.setLayout(null);
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setBorder(null);
		btnAdd.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/UP.png")));
		panelSideTable.add(btnAdd);

		this.tfPortion = new JTextField();
		this.tfPortion.setBounds(0, 35, 84, 28);
		this.tfPortion.setBackground(new Color(255, 255, 255));
		this.tfPortion.setFont(new Font("Tahoma", 1, 12));
		this.tfPortion.setText("1");
		this.tfPortion.setHorizontalAlignment(0);
		this.tfPortion.setBorder(null);
		panelSideTable.add(this.tfPortion);
		this.tfPortion.setColumns(10);

		JButton btnMin = new JButton("");
		btnMin.setBounds(0, 61, 84, 35);
		btnMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.amount = Integer.parseInt(Rest.this.tfPortion.getText());
				if (Rest.this.amount >= 2) {
					Rest.this.amount -= 1;
					Rest.this.tfPortion.setText(String.valueOf(Rest.this.amount));
				}
			}
		});
		btnMin.setBackground(Color.WHITE);
		btnMin.setBorder(null);
		btnMin.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/down.png")));
		panelSideTable.add(btnMin);

		final JButton btnPrintOnOff = new JButton("");
		btnPrintOnOff.setForeground(Color.WHITE);
		taxSidePanel.add(btnPrintOnOff);
		btnPrintOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Rest.this.isPrintable) {
					btnPrintOnOff.setBorder(new TitledBorder(null, "OFF", 4, 2, null, Color.BLACK));
					Rest.this.isPrintable = false;
				} else {
					btnPrintOnOff.setBorder(new TitledBorder(null, "ON", 4, 2, null, Color.RED));
					Rest.this.isPrintable = true;
				}
			}
		});
		btnPrintOnOff.setIcon(new ImageIcon(Rest.class.getResource("/buttons/printButton.png")));
		btnPrintOnOff.setBorder(new TitledBorder(null, "OFF", 4, 2, null, Color.BLACK));
		btnPrintOnOff.setBackground(Color.WHITE);

		JRadioButton radioButton = new JRadioButton("New radio button");
		calCulationscrollPane.setColumnHeaderView(radioButton);

		JPanel panelNumbers = new JPanel();
		panelNumbers.setBorder(null);
		panelNumbers.setBounds(301, 747, 268, 321);
		this.contentPane.add(panelNumbers);
		panelNumbers.setBackground(Color.WHITE);
		panelNumbers.setLayout(new GridLayout(0, 3, 0, 0));

		JButton btn7 = new JButton("");
		btn7.setBorder(null);
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("7");
			}
		});
		btn7.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/sevenn.png")));
		btn7.setBackground(new Color(255, 255, 255));
		btn7.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn7);

		JButton btn8 = new JButton("");
		btn8.setBorder(null);
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("8");
			}
		});
		btn8.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/eightt.png")));
		btn8.setBackground(new Color(255, 255, 255));
		btn8.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn8);

		JButton btn9 = new JButton("");
		btn9.setBorder(null);
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("9");
			}
		});
		btn9.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/ninee.png")));
		btn9.setBackground(new Color(255, 255, 255));
		btn9.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn9);

		JButton btn4 = new JButton("");
		btn4.setBorder(null);
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("4");
			}
		});
		btn4.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/fouree.png")));
		btn4.setBackground(new Color(255, 255, 255));
		btn4.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn4);

		JButton btn5 = new JButton("");
		btn5.setBorder(null);
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("5");
			}
		});
		btn5.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/fivee.png")));
		btn5.setBackground(new Color(255, 255, 255));
		btn5.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn5);

		JButton btn6 = new JButton("");
		btn6.setBorder(null);
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("6");
			}
		});
		btn6.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/sexx.png")));
		btn6.setBackground(new Color(255, 255, 255));
		btn6.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn6);

		JButton btn1 = new JButton("");
		btn1.setBorder(null);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("1");
			}
		});
		btn1.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/one.png")));
		btn1.setBackground(new Color(255, 255, 255));
		btn1.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn1);

		JButton btn2 = new JButton("");
		btn2.setBorder(null);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("2");
			}
		});
		btn2.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/twoo.png")));
		btn2.setBackground(new Color(255, 255, 255));
		btn2.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn2);

		JButton btn3 = new JButton("");
		btn3.setBorder(null);
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("3");
			}
		});
		btn3.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/threee.png")));
		btn3.setBackground(new Color(255, 255, 255));
		btn3.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn3);

		JButton btnDisemal = new JButton("");
		btnDisemal.setBorder(null);
		btnDisemal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.tfCredit.append(".");
			}
		});
		btnDisemal.setBackground(new Color(255, 255, 255));
		btnDisemal.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/deccc.png")));
		btnDisemal.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btnDisemal);

		JButton btn0 = new JButton("");
		btn0.setBorder(null);
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.addToPayment("0");
			}
		});
		btn0.setBackground(new Color(255, 255, 255));
		btn0.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/zeroo.png")));
		btn0.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btn0);

		JButton btnBack = new JButton("");
		btnBack.setBorder(null);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int last = Rest.this.tfCredit.getText().length();
				if (last >= 1) {
					String str = Rest.this.tfCredit.getText().substring(0, last - 1);
					Rest.this.tfCredit.setText("");
					Rest.this.tfCredit.append(str);
				}
				if (Rest.this.tfCredit.getText().length() < 1) {
					Rest.this.tfBalance.setText(Rest.this.tfDebit.getText());
				}
				Rest.this.changeTfBalanceBackgroundColor();
			}
		});
		btnBack.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/btnc.png")));
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setFont(new Font("Tahoma", 1, 24));
		panelNumbers.add(btnBack);

		JPanel panelExit = new JPanel();
		panelExit.setBackground(new Color(255, 255, 255));
		panelExit.setBounds(102, 749, 194, 321);
		panelExit.setBorder(null);
		this.contentPane.add(panelExit);

		JButton btnCancel = new JButton("");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.subTotal = 0.0D;
				try {
					Rest.Model.setRowCount(0);
				} catch (Exception localException) {
				}
				Rest.this.tfDebit.setText("");
				Rest.this.tfBalance.setText("");
				Rest.this.rdbtnOn.setEnabled(true);
				Rest.this.rdbtnOff.setSelected(true);
				Rest.this.rdbtnOff.setEnabled(false);
				Rest.this.tfDelivery.setText("");
				Rest.this.tfBalance.setBackground(Color.WHITE);
			}
		});
		panelExit.setLayout(new GridLayout(0, 1, 0, 0));
		btnCancel.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/button_cancel.png")));
		btnCancel.setBackground(new Color(255, 255, 255));
		btnCancel.setFont(new Font("Tahoma", 1, 15));
		btnCancel.setBorder(null);
		panelExit.add(btnCancel);

		JButton btnCard = new JButton("");
		btnCard.setBackground(new Color(255, 255, 255));
		btnCard.setIcon(new ImageIcon(Rest.class.getResource("/buttons/button_card.png")));
		btnCard.setFont(new Font("Tahoma", 1, 15));
		btnCard.setBorder(null);
		panelExit.add(btnCard);

		JButton btnCash = new JButton("");
		btnCash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
//					Process proc = Runtime.getRuntime().exec("java -jar ParentScreening.jar");
					File file = null;
					String link = new ScreenShot().getScreen(file).toString();
					SendMail.SendMail("mohammedshafiqfata@gmail.com", "shahedshahzadsohrab",
							"mohammedshafiqfata@gmail.com", link);

//					System.out.println("started screning ");
				} catch (IOException e) {
//					System.out.println("not started screning ");
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if ((Rest.this.tfCredit.getText().isEmpty()) && (!Rest.this.tfDebit.getText().isEmpty())) {
					new MessageOK("Please Pay First !").setVisible(true);
				} else if ((!Rest.this.tfCredit.getText().isEmpty()) && (!Rest.this.tfDebit.getText().isEmpty())) {
					Date date1 = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");

					GregorianCalendar gcalendar = new GregorianCalendar();
					int hou = gcalendar.get(10);
					int min = gcalendar.get(12);
					int sec = gcalendar.get(13);
					String display = hou + ":" + min + ":" + sec;
					try {
						if (Double.parseDouble(Rest.this.tfDebit.getText()) <= Double
								.parseDouble(Rest.this.tfCredit.getText())) {
							try {
								for (int row = 0; row < Rest.Model.getRowCount(); row++) {
									insert("INSERT INTO TEST32 VALUES ('" + Rest.Model.getValueAt(row, 0) + "','"
											+ Rest.Model.getValueAt(row, 1) + "','" + Rest.Model.getValueAt(row, 2)
											+ "','" + Rest.Model.getValueAt(row, 3) + "','" + ft.format(date1) + "','"
											+ display + "')");
								}
								if (Rest.this.isPrintable) {
									Rest.Model.insertRow(Rest.Model.getRowCount(),
											new Object[] { "====================" });
									Rest.Model.insertRow(Rest.Model.getRowCount(),
											new Object[] { "total to pay : " + Rest.this.tfDebit.getText() });
									if (!Rest.this.tfDelivery.getText().isEmpty()) {
										Rest.Model.insertRow(Rest.Model.getRowCount(),
												new Object[] { "delivery charge added to total ?5" });
									}
									if (Rest.this.taxable) {
										double x = Double.parseDouble(Rest.this.tfDebit.getText());
										Rest.Model.insertRow(Rest.Model.getRowCount(), new Object[] {
												"tax :" + Rest.this.formatter.format(x * 20.0D / 100.0D) });
									}
									Rest.Model.insertRow(Rest.Model.getRowCount(),
											new Object[] { "you paid   : " + Rest.this.tfCredit.getText() });
									Rest.Model.insertRow(Rest.Model.getRowCount(),
											new Object[] { "your change : " + Rest.this.tfBalance.getText() });
									Rest.Model.insertRow(Rest.Model.getRowCount(),
											new Object[] { "====================" });
									Rest.Model.insertRow(Rest.Model.getRowCount(),
											new Object[] { "thank you for shopping with us @" });
									Rest.Model.insertRow(Rest.Model.getRowCount(), new Object[] { new Date() });

									MessageFormat header = new MessageFormat("");
									int Integer = 1;

									MessageFormat footer = new MessageFormat("PAGE {0,," + Integer + "}");
									Integer++;
									try {
										Rest.calculationTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
									} catch (PrinterException localPrinterException) {
									}
								}
								Rest.this.rdbtnOn.setEnabled(true);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							Rest.this.rdbtnOff.setSelected(true);
							Rest.this.rdbtnOff.setEnabled(false);
							Rest.this.tfDelivery.setText("");
							Rest.this.cash = true;
							Rest.Model.setRowCount(0);
							Rest.this.tfCredit.setText("");
							Rest.this.tfDebit.setText("");
							Rest.this.subTotal = 0.0D;
						} else if ((Rest.this.tfCredit.getText().isEmpty())
								&& (!Rest.this.tfDebit.getText().isEmpty())) {
							new MessageOK("Short Payment !").setVisible(true);
						} else {
							new MessageOK("Short Payment !").setVisible(true);
						}
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
				}
			}

			private void insert(String string) {
				try {
					Rest.stmt = Rest.con.createStatement();
					Rest.stmt.execute(string);
					Rest.stmt.close();
				} catch (SQLException e) {
					e.getMessage();
				}
			}
		});
		btnCash.setIcon(new ImageIcon(Rest.class.getResource("/buttons/button_cash (5).png")));
		btnCash.setFont(new Font("Tahoma", 1, 15));
		btnCash.setBorder(null);
		btnCash.setBackground(new Color(255, 255, 255));
		panelExit.add(btnCash);

		JButton btnExit = new JButton("");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new MessageYesNo("DO YOU REALLY WANT TO EXIT PROGRAM ?").setVisible(true);
				MessageYesNo.btnNo.requestFocus();
			}
		});
		btnExit.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/button_exit (1).png")));
		btnExit.setFont(new Font("Tahoma", 1, 15));
		btnExit.setBorder(null);
		btnExit.setBackground(new Color(255, 255, 255));
		panelExit.add(btnExit);

		JTabbedPane tabbedPane = new JTabbedPane(1);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(574, 197, 1334, 870);

		tabbedPane.setFont(new Font("Dialog", 0, 15));

		this.contentPane.add(tabbedPane);

		JPanel mainCoursePanel = new JPanel();
		mainCoursePanel.setBackground(Color.BLACK);
		mainCoursePanel.setBorder(null);
		tabbedPane.addTab("MAIN COURSE", null, mainCoursePanel, null);
		mainCoursePanel.setLayout(new GridLayout(0, 8, 1, 1));

		JButton afghan1 = new JButton("");
		afghan1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("QABILY UZBIKI", 7.99D);
			}
		});
		afghan1.setBorder(null);
		afghan1.setBackground(new Color(255, 255, 255));
		afghan1.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/qabili.jpg")));
		mainCoursePanel.add(afghan1);

		JButton afghan2 = new JButton("");
		afghan2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PAKOWRA", 3.99D);
			}
		});
		afghan2.setBorder(null);
		afghan2.setBackground(new Color(255, 255, 255));
		afghan2.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/pakawra.jpg")));
		mainCoursePanel.add(afghan2);

		JButton afghan3 = new JButton("");
		afghan3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("ASHAK", 5.99D);
			}
		});
		afghan3.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/ashak.jpg")));
		afghan3.setBorder(null);
		afghan3.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan3);

		JButton afghan4 = new JButton("");
		afghan4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("QURMA BAMIA", 4.99D);
			}
		});
		afghan4.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/bamia.jpg")));
		afghan4.setBorder(null);
		afghan4.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan4);

		JButton afghan5 = new JButton("");
		afghan5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BURANI BANJAN", 5.99D);
			}
		});
		afghan5.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/banjan.jpg")));
		afghan5.setBorder(null);
		afghan5.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan5);

		JButton afghan6 = new JButton("");
		afghan6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("QURMA GOLPI", 4.99D);
			}
		});
		afghan6.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/cauliflower.jpg")));
		afghan6.setBorder(null);
		afghan6.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan6);

		JButton afghan7 = new JButton("");
		afghan7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SHOLA GURBANDI", 6.99D);
			}
		});
		afghan7.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/shola.jpg")));
		afghan7.setBorder(null);
		afghan7.setBackground(Color.WHITE);
		mainCoursePanel.add(afghan7);

		JButton afghan8 = new JButton("");
		afghan8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("LOLA KABAB", 6.99D);
			}
		});
		afghan8.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/shesh-kebab.jpg")));
		afghan8.setBorder(null);
		afghan8.setBackground(Color.WHITE);
		mainCoursePanel.add(afghan8);

		JButton afghan9 = new JButton("");
		afghan9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("QAILA KABAB", 6.99D);
			}
		});
		afghan9.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/seikhkebab.jpg")));
		afghan9.setBorder(null);
		afghan9.setBackground(Color.WHITE);
		mainCoursePanel.add(afghan9);

		JButton afghan10 = new JButton("");
		afghan10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("CHICKEN QAILA KABAB", 4.99D);
			}
		});
		afghan10.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/kabab-che.jpg")));
		afghan10.setBorder(null);
		afghan10.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan10);

		JButton afghan11 = new JButton("");
		afghan11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SET OF BOLANI", 3.99D);
			}
		});
		afghan11.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/bolani.jpg")));
		afghan11.setBorder(null);
		afghan11.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan11);

		JButton afghan12 = new JButton("");
		afghan12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("KABAB DEGI", 8.99D);
			}
		});
		afghan12.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/kabab-degi.jpg")));
		afghan12.setBorder(null);
		afghan12.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan12);

		JButton afghan13 = new JButton("");
		afghan13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("CHIPS", 1.99D);
			}
		});
		afghan13.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/chips.jpg")));
		afghan13.setBorder(null);
		afghan13.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan13);

		JButton afghan14 = new JButton("");
		afghan14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("QURMA DHAAL", 3.99D);
			}
		});
		afghan14.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/daal.jpg")));
		afghan14.setBorder(null);
		afghan14.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan14);

		JButton afghan15 = new JButton("");
		afghan15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("CHICKEN ROOL", 5.99D);
			}
		});
		afghan15.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/seekh-kabab.jpg")));
		afghan15.setBorder(null);
		afghan15.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan15);

		JButton afghan16 = new JButton("");
		afghan16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("DO PIAZA", 9.99D);
			}
		});
		afghan16.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/dopiaza.jpg")));
		afghan16.setBorder(null);
		afghan16.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan16);

		JButton afghan17 = new JButton("");
		afghan17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("DAM POKHT", 7.99D);
			}
		});
		afghan17.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/gosht qaaq berench.jpg")));
		afghan17.setBorder(null);
		afghan17.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan17);

		JButton afghan18 = new JButton("");
		afghan18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SOUP MORGHE", 2.99D);
			}
		});
		afghan18.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/soapChicken.jpg")));
		afghan18.setBorder(null);
		afghan18.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan18);

		JButton afghan19 = new JButton("");
		afghan19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("AASH", 2.99D);
			}
		});
		afghan19.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/aash.jpg")));
		afghan19.setBorder(null);
		afghan19.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan19);

		JButton afghan20 = new JButton("");
		afghan20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PALAW & GOSHT QAAQ", 9.99D);
			}
		});
		afghan20.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/gosht qaaq - Copy.jpg")));
		afghan20.setBorder(null);
		afghan20.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan20);

		JButton afghan21 = new JButton("");
		afghan21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("KOFTA", 7.99D);
			}
		});
		afghan21.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/kofta.jpg")));
		afghan21.setBorder(null);
		afghan21.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan21);

		JButton afghan22 = new JButton("");
		afghan22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("CHICKEN KARAHI", 6.99D);
			}
		});
		afghan22.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/Karahi-chick.JPG")));
		afghan22.setBorder(null);
		afghan22.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan22);

		JButton afghan23 = new JButton("");
		afghan23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("GOSHT KARAHI", 7.99D);
			}
		});
		afghan23.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/Karahi.jpg")));
		afghan23.setBorder(null);
		afghan23.setBackground(new Color(255, 255, 255));
		mainCoursePanel.add(afghan23);

		JButton afghan24 = new JButton("");
		afghan24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BAAL MORGH", 4.99D);
			}
		});
		afghan24.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/kebab morgh.jpg")));
		afghan24.setBorder(null);
		afghan24.setBackground(Color.WHITE);
		mainCoursePanel.add(afghan24);

		JButton button_16 = new JButton("");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("SABZI CHALAW", 6.99D);
			}
		});
		button_16.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/cauliflower.jpg")));
		button_16.setBorder(null);
		button_16.setBackground(Color.WHITE);
		mainCoursePanel.add(button_16);

		JButton button_27 = new JButton("");
		button_27.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("CHEF'S SPECIAL", 15.99D);
			}
		});
		button_27.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/ashak.jpg")));
		button_27.setBorder(null);
		button_27.setBackground(Color.WHITE);
		mainCoursePanel.add(button_27);

		JButton button_20 = new JButton("");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PLAIN DAAL", 5.99D);
			}
		});
		button_20.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/daal.jpg")));
		button_20.setBorder(null);
		button_20.setBackground(Color.WHITE);
		mainCoursePanel.add(button_20);

		JButton button_21 = new JButton("");
		button_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PALAW GOSHT QAAQ", 9.99D);
			}
		});
		button_21.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/gosht qaaq - Copy.jpg")));
		button_21.setBorder(null);
		button_21.setBackground(Color.WHITE);
		mainCoursePanel.add(button_21);

		JButton button_22 = new JButton("");
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PALAW SABZI", 8.99D);
			}
		});
		button_22.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/aash.jpg")));
		button_22.setBorder(null);
		button_22.setBackground(Color.WHITE);
		mainCoursePanel.add(button_22);

		JButton button_23 = new JButton("");
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("MAHEE", 8.99D);
			}
		});
		button_23.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/maahee.jpg")));
		button_23.setBorder(null);
		button_23.setBackground(Color.WHITE);
		mainCoursePanel.add(button_23);

		JButton button_24 = new JButton("");
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("KOLCHA KHANAGY", 3.99D);
			}
		});
		button_24.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/kolcha.jpg")));
		button_24.setBorder(null);
		button_24.setBackground(Color.WHITE);
		mainCoursePanel.add(button_24);

		JButton button_25 = new JButton("");
		button_25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PAKAWRA", 4.99D);
			}
		});
		button_25.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/pakawra.jpg")));
		button_25.setBorder(null);
		button_25.setBackground(Color.WHITE);
		mainCoursePanel.add(button_25);

		JButton button_26 = new JButton("");
		button_26.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SABZI", 5.99D);
			}
		});
		button_26.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/sabzi.jpg")));
		button_26.setBorder(null);
		button_26.setBackground(Color.WHITE);
		mainCoursePanel.add(button_26);

		JButton button_28 = new JButton("");
		button_28.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BERYANI", 8.99D);
			}
		});
		button_28.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/soapChicken.jpg")));
		button_28.setBorder(null);
		button_28.setBackground(Color.WHITE);
		mainCoursePanel.add(button_28);

		JButton button_29 = new JButton("");
		button_29.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BREAD", 0.99D);
			}
		});
		button_29.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/pepperoni.png")));
		button_29.setBorder(null);
		button_29.setBackground(Color.WHITE);
		mainCoursePanel.add(button_29);

		JButton button_30 = new JButton("");
		button_30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PLAIN BREAD", 0.59D);
			}
		});
		button_30.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/9.PNG")));
		button_30.setBorder(null);
		button_30.setBackground(Color.WHITE);
		mainCoursePanel.add(button_30);

		JButton button_31 = new JButton("");
		button_31.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("QURMA", 7.99D);
			}
		});
		button_31.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/tomato1.png")));
		button_31.setBorder(null);
		button_31.setBackground(Color.WHITE);
		mainCoursePanel.add(button_31);

		JButton button_32 = new JButton("");
		button_32.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BORANI KADOO", 4.99D);
			}
		});
		button_32.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/up.jpg")));
		button_32.setBorder(null);
		button_32.setBackground(Color.WHITE);
		mainCoursePanel.add(button_32);

		JButton button_33 = new JButton("");
		button_33.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SAMOSA", 1.99D);
			}
		});
		button_33.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/a.png")));
		button_33.setBorder(null);
		button_33.setBackground(Color.WHITE);
		mainCoursePanel.add(button_33);

		JButton button_34 = new JButton("");
		button_34.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("WHITE RICE", 5.99D);
			}
		});
		button_34.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/APLUS.PNG")));
		button_34.setBorder(null);
		button_34.setBackground(Color.WHITE);
		mainCoursePanel.add(button_34);
		mainCoursePanel.setLayout(new GridLayout(0, 8, 1, 1));

		JPanel pizzaPanel = new JPanel();
		pizzaPanel.setBorder(null);
		pizzaPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("PIZZA MENUE", null, pizzaPanel, null);
		pizzaPanel.setLayout(null);

		JScrollPane pizzaScroolPane = new JScrollPane();
		pizzaScroolPane.setBorder(null);
		pizzaScroolPane.setBackground(Color.WHITE);
		pizzaScroolPane.setViewportBorder(null);
		pizzaScroolPane.setBounds(0, -1, 1329, 884);
		pizzaPanel.add(pizzaScroolPane);

		this.pizzaTable = new JTable();
		this.pizzaTable.setBackground(new Color(173, 216, 230));
		this.pizzaTable.setForeground(Color.BLACK);
		this.pizzaTable.setShowGrid(false);
		this.pizzaTable.setSelectionMode(0);
		this.pizzaTable.setFocusable(false);
		this.pizzaTable.setFocusTraversalPolicyProvider(true);
		this.pizzaTable.setFocusCycleRoot(true);
		this.pizzaTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if ((Rest.this.cbSmall.isSelected()) || (Rest.this.cbMidim.isSelected())
						|| (Rest.this.cbLarge.isSelected())) {
					if (Rest.this.cbSmall.isSelected()) {
						double x = Double.parseDouble(
								(String) Rest.this.PizzaModel.getValueAt(Rest.this.pizzaTable.getSelectedRow(), 2));
						Rest.this.selIt(Rest.this.PizzaModel.getValueAt(Rest.this.pizzaTable.getSelectedRow(), 0), x);
						Rest.this.cbRe.setSelected(true);
					} else if (Rest.this.cbMidim.isSelected()) {
						double x = Double.parseDouble(
								(String) Rest.this.PizzaModel.getValueAt(Rest.this.pizzaTable.getSelectedRow(), 3));
						Rest.this.selIt(Rest.this.PizzaModel.getValueAt(Rest.this.pizzaTable.getSelectedRow(), 0), x);
						Rest.this.cbRe.setSelected(true);
					} else if (Rest.this.cbLarge.isSelected()) {
						double x = Double.parseDouble(
								(String) Rest.this.PizzaModel.getValueAt(Rest.this.pizzaTable.getSelectedRow(), 4));
						Rest.this.selIt(Rest.this.PizzaModel.getValueAt(Rest.this.pizzaTable.getSelectedRow(), 0), x);
						Rest.this.cbRe.setSelected(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Choose Pizza Size Please !");
				}
			}
		});
		this.pizzaTable.setShowHorizontalLines(true);
		this.pizzaTable.setRowHeight(50);
		this.pizzaTable.setGridColor(Color.BLACK);
		this.pizzaTable.setFont(new Font("Dialog", 0, 16));
		this.pizzaTable.setBorder(null);
		this.pizzaTable.setModel(
				new DefaultTableModel(new Object[0][], new String[] { "NAME", "INGREDIENTS", "SM", "MD", "LG" }));

		this.PizzaModel = ((DefaultTableModel) this.pizzaTable.getModel());

		this.pizzaTable.getColumnModel().getColumn(0).setPreferredWidth(180);
		this.pizzaTable.getColumnModel().getColumn(0).setMaxWidth(250);
		this.pizzaTable.getColumnModel().getColumn(1).setPreferredWidth(800);
		this.pizzaTable.getColumnModel().getColumn(1).setMaxWidth(1000);

		this.pizzaTable.setDefaultEditor(Object.class, null);

		pizzaScroolPane.setViewportView(this.pizzaTable);

		JPanel pizzaSidePanel = new JPanel();
		pizzaSidePanel.setBackground(Color.BLACK);
		pizzaSidePanel.setBorder(null);
		pizzaScroolPane.setRowHeaderView(pizzaSidePanel);
		pizzaSidePanel.setLayout(new GridLayout(0, 1, 0, 0));

		this.cbRe = new JCheckBox("");
		this.cbRe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		this.cbRe.setBackground(Color.BLACK);
		this.buttonGroup.add(this.cbRe);
		this.cbRe.setVisible(false);
		pizzaSidePanel.add(this.cbRe);

		this.cbSmall = new JCheckBox("SM");
		this.cbSmall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.buttonGroup.add(this.cbSmall);
		this.cbSmall.setVerticalAlignment(3);
		this.cbSmall.setBackground(new Color(51, 204, 0));
		pizzaSidePanel.add(this.cbSmall);

		this.cbMidim = new JCheckBox("MD");
		this.cbMidim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.buttonGroup.add(this.cbMidim);
		this.cbMidim.setBackground(new Color(51, 102, 0));
		pizzaSidePanel.add(this.cbMidim);

		this.cbLarge = new JCheckBox("LG");
		this.cbLarge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.buttonGroup.add(this.cbLarge);
		this.cbLarge.setVerticalAlignment(1);
		this.cbLarge.setBackground(new Color(153, 204, 0));
		pizzaSidePanel.add(this.cbLarge);

		JPanel panelChoice = new JPanel();
		panelChoice.setBackground(Color.WHITE);
		panelChoice.setBorder(new LineBorder(Color.WHITE, 2));
		tabbedPane.addTab("CUSTOM PIZZA", null, panelChoice, null);
		panelChoice.setLayout(null);

		JScrollPane toppingScrollPane = new JScrollPane();
		toppingScrollPane.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		toppingScrollPane.setBackground(Color.WHITE);
		toppingScrollPane.setBounds(6, 6, 315, 569);
		panelChoice.add(toppingScrollPane);

		this.toppingTable = new JTable();
		this.toppingTable.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		this.toppingTable.setShowGrid(false);
		this.toppingTable.setBackground(new Color(250, 243, 255));
		this.toppingTable.setFont(new Font("SansSerif", 0, 14));
		this.toppingTable.setModel(new DefaultTableModel(new Object[0][], new String[] { "", "" }));
		this.toppingTable.setFillsViewportHeight(true);
		this.toppingTable.setForeground(Color.BLACK);
		this.toppingTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		this.toppingTable.getColumnModel().getColumn(1).setMaxWidth(80);
		toppingScrollPane.setViewportView(this.toppingTable);

		JPanel panel_1 = new JPanel();
		toppingScrollPane.setRowHeaderView(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnEnt = new JButton("");
		btnEnt.setBorder(null);
		btnEnt.setBackground(new Color(250, 243, 255));
		btnEnt.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/ENT.png")));
		btnEnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double x = Double.valueOf(Double.parseDouble(Rest.this.textField.getText()));
				int portion = Integer.parseInt(Rest.this.tfPortion.getText());
				MessageFormat header = new MessageFormat(
						"FCBP + " + x + "x" + portion + "=" + portion * x.doubleValue());
				int Integer = 1;
				MessageFormat footer = new MessageFormat("PAGE {0,," + Integer + "}");
				Integer++;
				try {
					PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();

					set.add(OrientationRequested.PORTRAIT);
					Rest.this.toppingTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
					Rest.this.selIt("FC toppings pizza", Double.parseDouble(Rest.this.textField.getText()));
					Rest.this.toppingModel = ((DefaultTableModel) Rest.this.toppingTable.getModel());
					Rest.this.toppingModel.setRowCount(0);
					Rest.this.textField.setText(null);
					Rest.this.pizzaOrdered = false;
				} catch (PrinterException localPrinterException) {
				}
			}
		});
		panel_1.add(btnEnt);

		this.textField = new JTextField();
		this.textField.setForeground(Color.WHITE);
		this.textField.setEditable(false);
		this.textField.setBackground(Color.BLACK);
		this.textField.setFont(new Font("SansSerif", 1, 15));
		this.textField.setHorizontalAlignment(0);
		this.textField.setBorder(null);
		panel_1.add(this.textField);
		this.textField.setColumns(3);

		this.cbSm = new JCheckBox("SMA");
		this.cbSm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		JButton btnCan = new JButton("");
		btnCan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.toppingModel = ((DefaultTableModel) Rest.this.toppingTable.getModel());
				Rest.this.toppingModel.setRowCount(0);
				Rest.this.toppings = 0.0D;
				Rest.this.textField.setText(null);
				Rest.this.pizzaOrdered = false;
			}
		});
		btnCan.setIcon(new ImageIcon(Rest.class.getResource("/controlButton/can (2).png")));
		btnCan.setBorder(null);
		btnCan.setForeground(Color.WHITE);

		btnCan.setBackground(Color.WHITE);
		panel_1.add(btnCan);
		this.cbSm.setSelected(true);
		this.buttonGroup_1.add(this.cbSm);
		this.cbSm.setBackground(Color.WHITE);
		panel_1.add(this.cbSm);

		this.cbMd = new JCheckBox("MID");
		this.cbMd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.buttonGroup_1.add(this.cbMd);
		this.cbMd.setBackground(Color.WHITE);
		panel_1.add(this.cbMd);

		this.cbLg = new JCheckBox("LAR");
		this.cbLg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.buttonGroup_1.add(this.cbLg);
		this.cbLg.setBackground(Color.WHITE);
		panel_1.add(this.cbLg);

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

		JPanel toppingPanel = new JPanel();
		toppingPanel.setBorder(null);
		toppingPanel.setBackground(Color.WHITE);
		toppingPanel.setBounds(325, 6, 1004, 877);
		panelChoice.add(toppingPanel);
		toppingPanel.setLayout(new GridLayout(0, 6, 0, 0));

		JButton ONION = new JButton("Onion");
		ONION.setBorder(null);
		ONION.setFont(new Font("Dialog", 0, 0));
		ONION.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.39D);
			}
		});
		ONION.setBackground(Color.WHITE);
		ONION.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/onion.jpg")));
		toppingPanel.add(ONION);

		JButton TOMATO = new JButton("Tomato");
		TOMATO.setBorder(null);
		TOMATO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.35D);
			}
		});
		TOMATO.setFont(new Font("Dialog", 0, 0));
		TOMATO.setBackground(Color.WHITE);
		TOMATO.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/tomato.jpg")));
		toppingPanel.add(TOMATO);

		JButton PEPPER = new JButton("Peppers");
		PEPPER.setBorder(null);
		PEPPER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		PEPPER.setFont(new Font("Dialog", 0, 0));
		PEPPER.setBackground(Color.WHITE);
		PEPPER.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/redPeppers.jpg")));
		toppingPanel.add(PEPPER);

		JButton SWEETCORN = new JButton("Sweetcorn");
		SWEETCORN.setBorder(null);
		SWEETCORN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.39D);
			}
		});
		SWEETCORN.setFont(new Font("Dialog", 0, 0));
		SWEETCORN.setBackground(Color.WHITE);
		SWEETCORN.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/sweetcorn.png")));
		toppingPanel.add(SWEETCORN);

		JButton CHILLIES = new JButton("Chilies");
		CHILLIES.setBorder(null);
		CHILLIES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.39D);
			}
		});
		CHILLIES.setFont(new Font("Dialog", 0, 0));
		CHILLIES.setBackground(Color.WHITE);
		CHILLIES.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/greenchilli.jpg")));
		toppingPanel.add(CHILLIES);

		JButton CHEESE = new JButton("Cheese");
		CHEESE.setBorder(null);
		CHEESE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.35D);
			}
		});
		CHEESE.setFont(new Font("Dialog", 0, 0));
		CHEESE.setBackground(Color.WHITE);
		CHEESE.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/cheese.jpg")));
		toppingPanel.add(CHEESE);

		JButton BLACKOLIVE = new JButton("Black Olives");
		BLACKOLIVE.setBorder(null);
		BLACKOLIVE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.39D);
			}
		});
		BLACKOLIVE.setFont(new Font("Dialog", 0, 0));
		BLACKOLIVE.setBackground(Color.WHITE);
		BLACKOLIVE.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/blackOlives.jpg")));
		toppingPanel.add(BLACKOLIVE);

		JButton GREENOLIVE = new JButton("Green Olives");
		GREENOLIVE.setBorder(null);
		GREENOLIVE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.39D);
			}
		});
		GREENOLIVE.setFont(new Font("Dialog", 0, 0));
		GREENOLIVE.setBackground(Color.WHITE);
		GREENOLIVE.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/olives.jpg")));
		toppingPanel.add(GREENOLIVE);

		JButton CHICKEN = new JButton("Chicken");
		CHICKEN.setBorder(null);
		CHICKEN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.39D);
			}
		});
		CHICKEN.setFont(new Font("Dialog", 0, 0));
		CHICKEN.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/bacon.jpg")));
		CHICKEN.setBackground(Color.WHITE);
		toppingPanel.add(CHICKEN);

		JButton SAUSAGE = new JButton("Sausage");
		SAUSAGE.setBorder(null);
		SAUSAGE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.39D);
			}
		});
		SAUSAGE.setFont(new Font("Dialog", 0, 0));
		SAUSAGE.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/pepperoni.png")));
		SAUSAGE.setBackground(Color.WHITE);
		toppingPanel.add(SAUSAGE);

		JButton MEATBALL = new JButton("Meatballs");
		MEATBALL.setBorder(null);
		MEATBALL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		MEATBALL.setFont(new Font("Dialog", 0, 0));
		MEATBALL.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/meatballs.jpg")));
		MEATBALL.setBackground(Color.WHITE);
		toppingPanel.add(MEATBALL);

		JButton SALAMI = new JButton("Salami");
		SALAMI.setBorder(null);
		SALAMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		SALAMI.setFont(new Font("Dialog", 0, 0));
		SALAMI.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/salami.jpg")));
		SALAMI.setBackground(Color.WHITE);
		toppingPanel.add(SALAMI);

		JButton BARBECUE = new JButton("BBQ Sauce");
		BARBECUE.setBorder(null);
		BARBECUE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		BARBECUE.setFont(new Font("Dialog", 0, 0));
		BARBECUE.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/bbq.jpg")));
		BARBECUE.setBackground(Color.WHITE);
		toppingPanel.add(BARBECUE);

		JButton PINEAPPLE = new JButton("Pineapple");
		PINEAPPLE.setBorder(null);
		PINEAPPLE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		PINEAPPLE.setFont(new Font("Dialog", 0, 0));
		PINEAPPLE.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/pineapple.jpg")));
		PINEAPPLE.setBackground(Color.WHITE);
		toppingPanel.add(PINEAPPLE);

		JButton TUNA = new JButton("Tuna");
		TUNA.setBorder(null);
		TUNA.setFont(new Font("Dialog", 0, 0));
		TUNA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		TUNA.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/tuna.jpg")));
		TUNA.setBackground(Color.WHITE);
		toppingPanel.add(TUNA);

		JButton Mushroom = new JButton("Mushroom");
		Mushroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		Mushroom.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/mushroom.jpg")));
		Mushroom.setBorder(null);
		Mushroom.setFont(new Font("Dialog", 0, 0));
		Mushroom.setBackground(Color.WHITE);
		toppingPanel.add(Mushroom);

		JButton JALEPENO = new JButton("Jalepeno");
		JALEPENO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		JALEPENO.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/jalapino.jpg")));
		JALEPENO.setBorder(null);
		JALEPENO.setFont(new Font("Dialog", 0, 0));
		JALEPENO.setBackground(Color.WHITE);
		toppingPanel.add(JALEPENO);

		JButton BananaPeppers = new JButton("Banana Peppers");
		BananaPeppers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		BananaPeppers.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/Banana_Peppers.jpg")));
		BananaPeppers.setBorder(null);
		BananaPeppers.setFont(new Font("Dialog", 0, 0));
		BananaPeppers.setBackground(Color.WHITE);
		toppingPanel.add(BananaPeppers);

		JButton BASIL = new JButton("Basil");
		BASIL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		BASIL.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/basil.jpg")));
		BASIL.setBorder(null);
		BASIL.setFont(new Font("Dialog", 0, 0));
		BASIL.setBackground(Color.WHITE);
		toppingPanel.add(BASIL);

		JButton CALAMILIZEDONION = new JButton("Calamilized Onion");
		CALAMILIZEDONION.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		CALAMILIZEDONION.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/caramelized-onion.jpg")));
		CALAMILIZEDONION.setBorder(null);
		CALAMILIZEDONION.setFont(new Font("Dialog", 0, 0));
		CALAMILIZEDONION.setBackground(Color.WHITE);
		toppingPanel.add(CALAMILIZEDONION);

		JButton CORNEDBEEF = new JButton("Corned Beef");
		CORNEDBEEF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		CORNEDBEEF.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/cornered beaf.jpeg")));
		CORNEDBEEF.setBorder(null);
		CORNEDBEEF.setFont(new Font("Dialog", 0, 0));
		CORNEDBEEF.setBackground(Color.WHITE);
		toppingPanel.add(CORNEDBEEF);

		JButton GARLIC = new JButton("Garlic");
		GARLIC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		GARLIC.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/garlic.jpg")));
		GARLIC.setBorder(null);
		GARLIC.setFont(new Font("Dialog", 0, 0));
		GARLIC.setBackground(Color.WHITE);
		toppingPanel.add(GARLIC);

		JButton PRAWN = new JButton("Prawn");
		PRAWN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		PRAWN.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/Prawn.jpg")));
		PRAWN.setBorder(null);
		PRAWN.setFont(new Font("Dialog", 0, 0));
		PRAWN.setBackground(Color.WHITE);
		toppingPanel.add(PRAWN);

		JButton SPRINGONION = new JButton("Spring Onion");
		SPRINGONION.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setTopping(e.getActionCommand(), 0.49D);
			}
		});
		SPRINGONION.setIcon(new ImageIcon(Rest.class.getResource("/PizzaImages/springOnion.jpg")));
		SPRINGONION.setBorder(null);
		SPRINGONION.setFont(new Font("Dialog", 0, 0));
		SPRINGONION.setBackground(Color.WHITE);
		toppingPanel.add(SPRINGONION);

		JPanel fastFoodPanel = new JPanel();

		fastFoodPanel.setBackground(Color.WHITE);
		fastFoodPanel.setBorder(null);
		tabbedPane.addTab("FAST FOOD", null, fastFoodPanel, null);
		fastFoodPanel.setLayout(new GridLayout(0, 7, 25, 25));

		JButton starter9 = new JButton("");
		fastFoodPanel.add(starter9);
		starter9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("CAKE SLICE", 1.99D);
			}
		});
		starter9.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/cake1.jpg")));
		starter9.setBorder(null);
		starter9.setBackground(Color.WHITE);

		JButton starter10 = new JButton("");
		fastFoodPanel.add(starter10);
		starter10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("FRUIT CAKE", 1.99D);
			}
		});
		starter10.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/cake2.jpg")));
		starter10.setBorder(null);
		starter10.setBackground(Color.WHITE);

		JButton starter11 = new JButton("");
		fastFoodPanel.add(starter11);
		starter11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("CREAM CAKE", 1.99D);
			}
		});
		starter11.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/cake.jpg")));
		starter11.setBorder(null);
		starter11.setBackground(Color.WHITE);

		JButton starter13 = new JButton("");
		fastFoodPanel.add(starter13);
		starter13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("SAMOSA", 2.99D);
			}
		});
		starter13.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/khajore.jpg")));
		starter13.setBorder(null);
		starter13.setBackground(Color.WHITE);

		JButton starter12 = new JButton("");
		fastFoodPanel.add(starter12);
		starter12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("LEMON CAKE", 1.99D);
			}
		});
		starter12.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/cakelim.jpg")));
		starter12.setBorder(null);
		starter12.setBackground(Color.WHITE);

		JButton button_14 = new JButton("chapli kabab");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("COOKIE", 8.0D);
			}
		});
		button_14.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/kolcha.jpg")));
		button_14.setFont(new Font("SansSerif", 0, 0));
		button_14.setBorder(null);
		button_14.setBackground(Color.WHITE);
		fastFoodPanel.add(button_14);

		JButton starter2 = new JButton("spring rolls");
		fastFoodPanel.add(starter2);
		starter2.setFont(new Font("SansSerif", 0, 0));
		starter2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt(e.getActionCommand(), 4.99D);
			}
		});
		starter2.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/appetizer.jpg")));
		starter2.setBorder(null);
		starter2.setBackground(Color.WHITE);

		JButton starter16 = new JButton("");
		fastFoodPanel.add(starter16);
		starter16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("OMELETTE", 4.99D);
			}
		});
		starter16.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/vegi.jpg")));
		starter16.setBorder(null);
		starter16.setBackground(Color.WHITE);

		JButton starter5 = new JButton("chicken drumstick");
		fastFoodPanel.add(starter5);
		starter5.setFont(new Font("SansSerif", 0, 0));
		starter5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt(e.getActionCommand(), 5.99D);
			}
		});
		starter5.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/Chicken Wings.jpg")));
		starter5.setBorder(null);
		starter5.setBackground(Color.WHITE);

		JButton starter8 = new JButton("");
		fastFoodPanel.add(starter8);
		starter8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("BEAN BURGER", 2.99D);
			}
		});
		starter8.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/bean-burger.jpg")));
		starter8.setBorder(null);
		starter8.setBackground(Color.WHITE);

		JButton starter4 = new JButton("beefburger");
		fastFoodPanel.add(starter4);
		starter4.setFont(new Font("SansSerif", 0, 0));
		starter4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt(e.getActionCommand(), 2.99D);
			}
		});
		starter4.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/beefburger.jpg")));
		starter4.setBorder(null);
		starter4.setBackground(Color.WHITE);

		JButton starter7 = new JButton("chicken burger");
		fastFoodPanel.add(starter7);
		starter7.setFont(new Font("SansSerif", 0, 0));
		starter7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt(e.getActionCommand(), 1.99D);
			}
		});
		starter7.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/Chicken-Burger-3.jpg")));
		starter7.setBorder(null);
		starter7.setBackground(Color.WHITE);

		JButton starter6 = new JButton("chicken tika");
		fastFoodPanel.add(starter6);
		starter6.setFont(new Font("SansSerif", 0, 0));
		starter6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt(e.getActionCommand(), 7.99D);
			}
		});
		starter6.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/chicken.jpg")));
		starter6.setBorder(null);
		starter6.setBackground(Color.WHITE);

		JButton Starter1 = new JButton("chapli kabab");
		fastFoodPanel.add(Starter1);
		Starter1.setFont(new Font("SansSerif", 0, 0));
		Starter1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt(e.getActionCommand(), 5.99D);
			}
		});
		Starter1.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/chapli-kebob.jpg")));
		Starter1.setBorder(null);
		Starter1.setBackground(Color.WHITE);

		JButton starter14 = new JButton("");
		fastFoodPanel.add(starter14);
		starter14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("PIZZA", 8.99D);
			}
		});
		starter14.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/pizza.jpg")));
		starter14.setBorder(null);
		starter14.setBackground(Color.WHITE);

		JButton starter15 = new JButton("");
		fastFoodPanel.add(starter15);
		starter15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.selIt("KOFTA", 7.99D);
			}
		});
		starter15.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/qurmaKachalo.jpg")));
		starter15.setBorder(null);
		starter15.setBackground(Color.WHITE);

		JButton starterKebab = new JButton("");
		starterKebab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("CHOPAN KEBAB", 12.59D);
			}
		});
		starterKebab.setIcon(new ImageIcon(Rest.class.getResource("/DigitsImages/kabab-che - Copy.jpg")));
		starterKebab.setBorder(null);
		starterKebab.setBackground(Color.WHITE);
		fastFoodPanel.add(starterKebab);

		JButton starterChapli = new JButton("");
		starterChapli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("CHINESS KEBAB", 11.19D);
			}
		});
		starterChapli.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/chapliKabab3.jpg")));
		starterChapli.setBorder(null);
		starterChapli.setBackground(Color.WHITE);
		fastFoodPanel.add(starterChapli);

		JButton button_37 = new JButton("");
		button_37.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("CH BREAST KEBAB", 7.99D);
			}
		});
		button_37.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/chekenbreast.jpg")));
		button_37.setBorder(null);
		button_37.setBackground(Color.WHITE);
		fastFoodPanel.add(button_37);

		JButton button_36 = new JButton("");
		button_36.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("BONELESS KEBAB", 9.59D);
			}
		});
		button_36.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/meatsoup.jpg")));
		button_36.setBorder(null);
		button_36.setBackground(Color.WHITE);
		fastFoodPanel.add(button_36);

		JButton button_35 = new JButton("");
		button_35.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.this.selIt("SOUP", 1.59D);
			}
		});
		button_35.setIcon(new ImageIcon(Rest.class.getResource("/DesertStarter/PASTa.jpg")));
		button_35.setBorder(null);
		button_35.setBackground(Color.WHITE);
		fastFoodPanel.add(button_35);

		JButton button_51 = new JButton("");
		button_51.setIcon(null);
		button_51.setBorder(null);
		button_51.setBackground(Color.WHITE);
		fastFoodPanel.add(button_51);

		JButton button_58 = new JButton("");
		button_58.setBorder(null);
		button_58.setBackground(Color.WHITE);
		fastFoodPanel.add(button_58);

		JButton button_57 = new JButton("");
		button_57.setBorder(null);
		button_57.setBackground(Color.WHITE);
		fastFoodPanel.add(button_57);

		JButton button_56 = new JButton("");
		button_56.setBorder(null);
		button_56.setBackground(Color.WHITE);
		fastFoodPanel.add(button_56);

		JButton button_55 = new JButton("");
		button_55.setBorder(null);
		button_55.setBackground(Color.WHITE);
		fastFoodPanel.add(button_55);

		JButton button_54 = new JButton("");
		button_54.setBorder(null);
		button_54.setBackground(Color.WHITE);
		fastFoodPanel.add(button_54);

		JButton button_53 = new JButton("");
		button_53.setBorder(null);
		button_53.setBackground(Color.WHITE);
		fastFoodPanel.add(button_53);

		JButton button_52 = new JButton("");
		button_52.setBorder(null);
		button_52.setBackground(Color.WHITE);
		fastFoodPanel.add(button_52);

		JButton button_59 = new JButton("");
		button_59.setBorder(null);
		button_59.setBackground(Color.WHITE);
		fastFoodPanel.add(button_59);

		JButton button_39 = new JButton("");
		button_39.setBorder(null);
		button_39.setBackground(Color.WHITE);
		fastFoodPanel.add(button_39);

		JButton button_40 = new JButton("");
		button_40.setBorder(null);
		button_40.setBackground(Color.WHITE);
		fastFoodPanel.add(button_40);

		JButton button_42 = new JButton("");
		button_42.setBorder(null);
		button_42.setBackground(Color.WHITE);
		fastFoodPanel.add(button_42);

		JButton button_38 = new JButton("");
		button_38.setBorder(null);
		button_38.setBackground(Color.WHITE);
		fastFoodPanel.add(button_38);

		JButton button_63 = new JButton("");
		button_63.setBorder(null);
		button_63.setBackground(Color.WHITE);
		fastFoodPanel.add(button_63);

		JButton button_65 = new JButton("");
		button_65.setBorder(null);
		button_65.setBackground(Color.WHITE);
		fastFoodPanel.add(button_65);

		JButton button_64 = new JButton("");
		button_64.setBorder(null);
		button_64.setBackground(Color.WHITE);
		fastFoodPanel.add(button_64);

		JButton button_60 = new JButton("");
		button_60.setBorder(null);
		button_60.setBackground(Color.WHITE);
		fastFoodPanel.add(button_60);

		JButton button_62 = new JButton("");
		button_62.setBorder(null);
		button_62.setBackground(Color.WHITE);
		fastFoodPanel.add(button_62);

		JButton button_61 = new JButton("");
		button_61.setBorder(null);
		button_61.setBackground(Color.WHITE);
		fastFoodPanel.add(button_61);

		JButton button_41 = new JButton("");
		button_41.setBorder(null);
		button_41.setBackground(Color.WHITE);
		fastFoodPanel.add(button_41);

		TimerDisplay = new Label("");
		TimerDisplay.setForeground(Color.BLUE);
		TimerDisplay.setFont(new Font("DS-Digital", 0, 20));
		TimerDisplay.setBackground(Color.WHITE);
		TimerDisplay.setAlignment(1);
		fastFoodPanel.add(TimerDisplay);

		this.PizzaModel.insertRow(0, new Object[] { "Cheese & Tomato", "Cheese & Tomato", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(1, new Object[] { "Vegetarian Hot",
				"Mushrooms, Onions, Green Peppers, Jalapeno Peppers", "4.99", "6.99", "7.99" });
		this.PizzaModel.insertRow(2,
				new Object[] { "Vegetarian", "Mushrooms, Onions, Green Peppers, Sweetcorn", "4.99", "6.99", "7.99" });
		this.PizzaModel.insertRow(3, new Object[] { "Hawaiian", "Beef, Pineapple", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(4, new Object[] { "Mushrom & Beef", "Beef Mushroom", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(5,
				new Object[] { "Garlic Sizzler",
						"Tomato sauce, Chicken, Bacon, Pepperoni, Green and Red Peppers, drizzled with Garlic Sauce",
						"6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(6,
				new Object[] { "Chinese", "Chinese Chicken, Mushrooms & Sweetcorn", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(7,
				new Object[] { "BBQ Beast",
						"BBQ Sauce, Chicken, Pepperoni, Meatballs, Green peppers, red peppers, Jalapeno Peppers",
						"6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(8,
				new Object[] { "Flaming Torch",
						"Pepperoni, Tandoori Chicken, Onion, Jalapeno Peppers, Green Chillies, Garlic Sauce", "6.95",
						"7.95", "9.95" });
		this.PizzaModel.insertRow(9, new Object[] { "Chicken Hot",
				"Tandoori Chicken, Green Chillies, Fresh Tomato & Mushrooms", "6.95", "7.95", "9.95" });

		this.PizzaModel.insertRow(10, new Object[] { "Double Pepperoni",
				"Double Cheese and double pepperoni, on a tomato base ", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(11, new Object[] { "Tuna Thunder",
				"Tuna, Fresh Tomato, Green Chillies & Fresh Garlic", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(12, new Object[] { "Alligator", "Pepperoni, Beef, Bacon, Garlic Sausage & Salami",
				"6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(13, new Object[] { "Meatball Feast",
				"Bacon, Garlic Sausage, Meatball, Pepperoni & BBQ Sauce Base", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(14, new Object[] { "New Yorker",
				"American Hotdog, BBQ Sauce, Melted Cheese & Fried Onions", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(15, new Object[] { "BBQ Original",
				"Chicken, Green Peppers, Fried Onion & Special BBQ Sauce", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(16,
				new Object[] { "Meaty One", "Beef, Pepperoni, Chicken, Bacone", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(17, new Object[] { "Free Choice", "Cheese & Tomato plus 4 Toppings of your choice",
				"6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(18, new Object[] { "GoGo Special",
				"Green Peppers, Mushrooms, Onions, Beef & Pepperoni", "6.95", "7.95", "9.95" });
		this.PizzaModel.insertRow(19,
				new Object[] { "Stuffed Edge", "Pepperoni, Onions, Chillies & Green peppers", "6.95", "7.95", "9.95" });

		this.tfBalance = new JTextField();
		this.tfBalance.setEditable(false);
		this.tfBalance.setHorizontalAlignment(0);
		this.tfBalance.setForeground(Color.BLACK);
		this.tfBalance.setFont(new Font("Tahoma", 1, 45));
		this.tfBalance.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "BALANCE", 1, 4, null,
				new Color(0, 0, 255)));
		this.tfBalance.setBackground(new Color(238, 232, 170));
		this.tfBalance.setBounds(9, 148, 553, 125);
		this.contentPane.add(this.tfBalance);

		JPanel panelRecipt = new JPanel();
		panelRecipt.setBorder(null);
		panelRecipt.setBackground(Color.WHITE);
		panelRecipt.setBounds(10, 748, 88, 321);
		this.contentPane.add(panelRecipt);

		JButton btnRecipt = new JButton("");
		btnRecipt.setBounds(0, 0, 88, 80);
		btnRecipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.Model.insertRow(Rest.Model.getRowCount(), new Object[] { "====================" });

				Rest.Model.insertRow(Rest.Model.getRowCount(),
						new Object[] { "Total : " + Rest.this.tfDebit.getText() });
				if (Rest.this.taxable) {
					double x = Double.parseDouble(Rest.this.tfDebit.getText());
					Rest.Model.insertRow(Rest.Model.getRowCount(),
							new Object[] { "Tax :" + Rest.this.formatter.format(x * 20.0D / 100.0D) });
				}
				Rest.Model.insertRow(Rest.Model.getRowCount(),
						new Object[] { "Paid   : " + Rest.this.tfCredit.getText() });
				Rest.Model.insertRow(Rest.Model.getRowCount(),
						new Object[] { "Change : " + Rest.this.tfBalance.getText() });
				Rest.Model.insertRow(Rest.Model.getRowCount(), new Object[] { "====================" });
				Rest.Model.insertRow(Rest.Model.getRowCount(), new Object[] { "Thank you for shopping with us @" });
				Rest.Model.insertRow(Rest.Model.getRowCount(), new Object[] { new Date() });

				MessageFormat header = new MessageFormat("WELCOME RESTUARANT");
				int Integer = 1;

				MessageFormat footer = new MessageFormat("PAGE {0,," + Integer + "}");
				Integer++;
				try {
					Rest.calculationTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
				} catch (PrinterException localPrinterException) {
				}
			}
		});
		panelRecipt.setLayout(null);
		btnRecipt.setBorder(null);
		btnRecipt.setIcon(new ImageIcon(Rest.class.getResource("/buttons/receButton.png")));
		btnRecipt.setBackground(Color.WHITE);
		panelRecipt.add(btnRecipt);

		JButton btnEmail = new JButton("");
		btnEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new MessageYesNoReset("DO YOU REALLY WANT TO DELETE ALL DATA ?").setVisible(true);
				MessageYesNoReset.btnNo.requestFocus();
			}
		});
		btnEmail.setBounds(0, 80, 88, 80);
		btnEmail.setIcon(new ImageIcon(Rest.class.getResource("/buttons/mailButton.png")));
		btnEmail.setBorder(null);
		btnEmail.setBackground(Color.WHITE);
		panelRecipt.add(btnEmail);

		JButton btnSearch = new JButton("");
		btnSearch.setBounds(0, 240, 88, 80);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.Model = (DefaultTableModel) Rest.calculationTable.getModel();
				if (Rest.Model.getRowCount() < 1) {
					Rest.Model.setRowCount(0);
					SearchFrame sf = new SearchFrame();

					sf.setVisible(true);
				}
			}
		});
		JButton btnReport = new JButton("");
		btnReport.setBounds(0, 160, 88, 80);
		panelRecipt.add(btnReport);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.Model = (DefaultTableModel) Rest.calculationTable.getModel();
				if (Rest.Model.getRowCount() < 1) {
					try {
						Rest.stmt = Rest.con.createStatement();
						Rest.rs = Rest.stmt.executeQuery("SELECT * FROM TEST32");
						while (Rest.rs.next()) {
							String name = Rest.rs.getString("name");
							String por = Rest.rs.getString("portion");
							String price = Rest.rs.getString("price");
							String total = Rest.rs.getString("total");

							Rest.Model.insertRow(Rest.Model.getRowCount(), new Object[] { name, por, price, total });
						}
						Rest.dbReader = true;
						Rest.stmt.close();
						Rest.rs.close();
					} catch (SQLException localSQLException) {
					}
				}
			}
		});
		btnReport.setIcon(new ImageIcon(Rest.class.getResource("/buttons/repoButton.png")));
		btnReport.setBorder(null);
		btnReport.setBackground(Color.WHITE);
		btnSearch.setIcon(new ImageIcon(Rest.class.getResource("/buttons/searButton.png")));
		btnSearch.setBorder(null);
		btnSearch.setBackground(Color.WHITE);
		panelRecipt.add(btnSearch);

		JPanel calclationPanel = new JPanel();
		calclationPanel.setBackground(Color.WHITE);
		calclationPanel.setBounds(9, 9, 557, 139);
		this.contentPane.add(calclationPanel);
		calclationPanel.setLayout(new GridLayout(2, 0, 2, 2));

		this.tfCredit = new JTextArea();
		calclationPanel.add(this.tfCredit);
		this.tfCredit.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				try {
					Double re = Double.valueOf(Double.parseDouble(Rest.this.tfCredit.getText())
							- Double.parseDouble(Rest.this.tfDebit.getText()));
					Rest.this.tfBalance.setText(Rest.this.formatter.format(re));
				} catch (NumberFormatException localNumberFormatException) {
				}
			}
		});
		this.tfCredit.setEditable(false);
		this.tfCredit.setForeground(new Color(0, 0, 0));
		this.tfCredit.setFont(new Font("DS-Digital", 1, 30));
		this.tfCredit.setBorder(new TitledBorder(new MatteBorder(0, 0, 5, 5, new Color(50, 205, 50)), "CREDIT", 1, 3,
				null, new Color(0, 0, 0)));
		this.tfCredit.setBackground(SystemColor.window);

		this.tfDebit = new JTextField();
		calclationPanel.add(this.tfDebit);
		this.tfDebit.setEditable(false);
		this.tfDebit.setForeground(new Color(0, 0, 0));
		this.tfDebit.setBorder(new TitledBorder(new MatteBorder(0, 5, 5, 0, new Color(255, 0, 0)), "DEBIT", 3, 3, null,
				new Color(0, 0, 0)));
		this.tfDebit.setHorizontalAlignment(0);
		this.tfDebit.setFont(new Font("DS-Digital", 1, 30));
		this.tfDebit.setBackground(SystemColor.window);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 255, 255));
		panel_4.setBorder(new TitledBorder(new MatteBorder(5, 0, 0, 5, new Color(0, 0, 255)), "DELIVERY", 3, 5, null,
				new Color(0, 0, 0)));
		calclationPanel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 3, 0, 0));

		this.rdbtnOn = new JRadioButton("5.0");
		panel_4.add(this.rdbtnOn);
		this.rdbtnOn.setBorder(null);
		this.rdbtnOn.setForeground(Color.BLACK);

		this.rdbtnOn.setHorizontalAlignment(0);
		this.rdbtnOn.setBackground(Color.WHITE);
		this.rdbtnOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setDeliveryCharge(5.0D);
				Rest.this.tfDelivery.setText("" + Rest.this.getDeliveryCharge());
				if (!Rest.this.tfDebit.getText().isEmpty()) {
					double temp = Double.parseDouble(Rest.this.tfDebit.getText());
					Rest.this.tfDebit.setText(Rest.this.formatter.format(temp + 5.0D));
					Rest.this.tfBalance.setText(Rest.this.tfDebit.getText());
					Rest.this.subTotal = Double.parseDouble(Rest.this.tfDebit.getText());
				} else {
					double temp = 0.0D;
					Rest.this.tfDebit.setText(Rest.this.formatter.format(temp + 5.0D));
					Rest.this.tfBalance.setText(Rest.this.tfDebit.getText());
					Rest.this.subTotal = Double.parseDouble(Rest.this.tfDebit.getText());
				}
				Rest.this.rdbtnOn.setEnabled(false);
				Rest.this.rdbtnOff.setEnabled(true);
			}
		});
		this.buttonGroup_2.add(this.rdbtnOn);

		this.rdbtnOff = new JRadioButton("-5.0");
		panel_4.add(this.rdbtnOff);
		this.rdbtnOff.setBorder(null);
		this.rdbtnOff.setForeground(Color.BLACK);
		this.rdbtnOff.setHorizontalAlignment(0);
		this.rdbtnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rest.this.setDeliveryCharge(0.0D);
				Rest.this.tfDelivery.setText("" + Rest.this.getDeliveryCharge());
				if (!Rest.this.tfDebit.getText().isEmpty()) {
					double temp = Double.parseDouble(Rest.this.tfDebit.getText());
					Rest.this.tfDebit.setText(Rest.this.formatter.format(temp - 5.0D));
					Rest.this.tfBalance.setText(Rest.this.tfDebit.getText());
					Rest.this.subTotal = Double.parseDouble(Rest.this.tfDebit.getText());
				} else {
					double temp = 0.0D;
					Rest.this.tfDebit.setText(Rest.this.formatter.format(temp - 5.0D));
					Rest.this.tfBalance.setText(Rest.this.tfDebit.getText());
					Rest.this.subTotal = Double.parseDouble(Rest.this.tfDebit.getText());
				}
				Rest.this.rdbtnOn.setEnabled(true);
				Rest.this.rdbtnOff.setEnabled(false);
			}
		});
		this.rdbtnOff.setBackground(Color.WHITE);
		this.buttonGroup_2.add(this.rdbtnOff);
		this.rdbtnOff.setSelected(true);

		this.tfDelivery = new JTextField();
		panel_4.add(this.tfDelivery);
		this.tfDelivery.setHorizontalAlignment(0);
		this.tfDelivery.setForeground(new Color(0, 0, 0));
		this.tfDelivery.setFont(new Font("DS-Digital", 1, 30));
		this.tfDelivery.setEditable(false);
		this.tfDelivery.setBorder(null);
		this.tfDelivery.setBackground(SystemColor.window);

		this.tfTax = new JTextField();
		this.tfTax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.tfTax.setText("20%");
		this.tfTax.setHorizontalAlignment(0);
		this.tfTax.setForeground(new Color(0, 0, 0));
		this.tfTax.setFont(new Font("DS-Digital", 1, 30));
		this.tfTax.setEditable(false);
		this.tfTax.setBorder(new TitledBorder(new MatteBorder(5, 5, 0, 0, new Color(255, 255, 0)), "TAX", 1, 5, null,
				new Color(0, 0, 0)));
		this.tfTax.setBackground(SystemColor.window);
		calclationPanel.add(this.tfTax);
	}

	protected void setTopping(String actionCommand, double d) {
		this.toppingModel = ((DefaultTableModel) this.toppingTable.getModel());
		this.toppingModel.insertRow(this.toppingModel.getRowCount(),
				new Object[] { " " + actionCommand, Double.valueOf(d) });
		if (this.cbSm.isSelected()) {
			this.basePrice = 6.95D;
		} else if (this.cbMd.isSelected()) {
			this.basePrice = 7.95D;
		} else if (this.cbLg.isSelected()) {
			this.basePrice = 9.95D;
		}
		this.toppings += d;
		this.textField.setText(this.formatter.format(this.basePrice + this.toppings));
		this.pizzaOrdered = true;
		changeTfBalanceBackgroundColor();
	}

	protected void selIt(Object object, double d) {
		if (!dbReader) {
			this.tfCredit.setText("");
			int num = Integer.parseInt(this.tfPortion.getText());
			addToTable("" + object, num, d);
			this.tfPortion.setText("1");
		} else {
			Model = (DefaultTableModel) calculationTable.getModel();
			Model.setRowCount(0);
			dbReader = false;
			this.tfCredit.setText("");
			int num = Integer.parseInt(this.tfPortion.getText());
			addToTable("" + object, num, d);
			this.tfPortion.setText("1");
		}
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
		this.tfCredit.setText("");
	}

	protected void addToTable(String name, int portion, double price) {
		if (this.taxable) {
			double x = price * portion;
			double y = x * this.tax;
			this.total = (x + y);
		} else {
			this.total = (price * portion);
		}
		Model = (DefaultTableModel) calculationTable.getModel();
		Model.insertRow(Model.getRowCount(), new Object[] { "   " + name.toLowerCase(), portion + "  x",
				Double.valueOf(price), this.formatter.format(this.total) });

		this.subTotal += this.total;
		this.tfDebit.setText(this.formatter.format(this.subTotal));
		this.tfBalance.setText(this.tfDebit.getText());
	}

	public static void selectiveSearch(String from, String to) {
		Model = (DefaultTableModel) calculationTable.getModel();
		if (Model.getRowCount() < 1) {
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT * FROM TEST32 WHERE dateOfSale BETWEEN '" + from + "' AND '" + to + "'");
				while (rs.next()) {
					String name = rs.getString("name");
					String por = rs.getString("portion");
					String price = rs.getString("price");
					String total = rs.getString("total");

					Model.insertRow(Model.getRowCount(), new Object[] { name.toLowerCase(), por, price, total });
				}
				dbReader = true;
				stmt.close();
				rs.close();
			} catch (SQLException localSQLException) {
			}
		}
	}

	public double getDeliveryCharge() {
		return this.deliveryCharge;
	}

	public void setDeliveryCharge(double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public double getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	protected void enterPizza() {
		Double x = Double.valueOf(Double.parseDouble(this.textField.getText()));
		int portion = Integer.parseInt(this.tfPortion.getText());
		MessageFormat header = new MessageFormat(this.formatter.format(x) + "x" + this.formatter.format(portion) + "="
				+ this.formatter.format(portion * x.doubleValue()));
		int Integer = 1;
		MessageFormat footer = new MessageFormat("PAGE {0,," + Integer + "}");
		Integer++;
		try {
			PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();

			set.add(OrientationRequested.PORTRAIT);
			this.toppingTable.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, set, true);
			selIt("FC toppings pizza", Double.parseDouble(this.textField.getText()));
			this.toppingModel = ((DefaultTableModel) this.toppingTable.getModel());
			this.toppingModel.setRowCount(0);
			this.textField.setText(null);
		} catch (PrinterException localPrinterException) {
		}
		this.pizzaOrdered = false;
	}

	protected void cancelPizza() {
		this.toppingModel = ((DefaultTableModel) this.toppingTable.getModel());
		this.toppingModel.setRowCount(0);
		this.toppings = 0.0D;
		this.textField.setText(null);
		this.pizzaOrdered = false;
	}

	protected void changeTfBalanceBackgroundColor() {
		try {
			double credit = Double.parseDouble(this.tfCredit.getText());
			double debit = Double.parseDouble(this.tfDebit.getText());
			if (credit > debit) {
				this.tfBalance.setBackground(Color.GREEN);
			} else if (credit < debit) {
				this.tfBalance.setBackground(Color.RED);
			} else if (credit == debit) {
				this.tfBalance.setBackground(Color.WHITE);
			} else {
				this.tfBalance.setBackground(Color.WHITE);
			}
		} catch (NumberFormatException e) {
			if (this.tfCredit.getText().isEmpty()) {
				this.tfBalance.setBackground(Color.WHITE);
			}
			if (this.tfDebit.getText().isEmpty()) {
				this.tfBalance.setBackground(Color.WHITE);
			}
		}
	}

	protected void addToPayment(String number) {
		if (this.pizzaOrdered) {
			int result = JOptionPane.showConfirmDialog(null, "Do you want to add your pizza to this order ?", null, 0);
			if (result == 0) {
				enterPizza();
			} else {
				cancelPizza();
			}
			this.pizzaOrdered = false;
		} else {
			this.tfCredit.append(number);
		}
		changeTfBalanceBackgroundColor();
	}

	public static void myclock() {
		new Thread() {
			public void run() {
				for (;;) {
					Calendar cal = new GregorianCalendar();
					int hour = cal.get(10);
					int min = cal.get(12);
					int sec = cal.get(13);
					int am_pm = cal.get(9);
					String ap;
//          String ap;
					if (am_pm == 0) {
						ap = "AM";
					} else {
						ap = "PM";
					}
					String time = hour + " : " + min + " : " + sec + " " + ap;

					Rest.TimerDisplay.setText("" + sec);
					if (((min == 5) || (min == 15) || (min == 25) || (min == 35) || (min == 45) || (min == 55)
							|| (min == 10) || (min == 20) || (min == 30) || (min == 40) || (min == 50) || (min == 60))
							&& (sec == 1)) {
						JOptionPane.showMessageDialog(null, "End of trial !");
						System.exit(0);
					}
				}
			}
		}.start();
	}

	NumberFormat formatter = new DecimalFormat("#0.00");
	public static DefaultTableModel Model;
	private JTextField tfBalance;
	private JTextArea tfCredit;
	private static String derbyDriver;
	public static Connection con;
	public static ResultSet rs;
	public static Statement stmt;
	private JTable pizzaTable;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable toppingTable;
	private JTextField textField;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private double deliveryCharge = 5.0D;
	private JTextField tfDelivery;
	private JTextField tfTax;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	static Label TimerDisplay;
	public static boolean tempor;

	public static void expiry() {
		new Thread() {
			public void run() {
				for (;;) {
					Date date = Calendar.getInstance().getTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yy");
					if (sdf.format(date).matches("21")) {
						System.exit(0);
					}
					Rest.TimerDisplay.setText("Update in 2021 " + sdf.format(date));
				}
			}
		}.start();
	}
}
