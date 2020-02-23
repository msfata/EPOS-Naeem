package Laptop;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class UUID {
	private static Statement stmt;
	private static java.sql.Connection con;
	private static String derbyDriver;
	private static java.sql.ResultSet rs;
	public static String autoPassword;
	public static String str;
	private static String userPassword;
	static String computername;
	private static String zero = "zero";
	static String tableName = "test786";
	public static String Password;
	private static int counter;
	private static JTextField textField;
//	private static JTextField textField_1;

	public static void main(String[] args) {
//		frameOpen();
		try {
			UUID.computername = InetAddress.getLocalHost().getHostName();
			derbyDriver = "jdbc:derby:codejava/msfata;create=true";
			con = DriverManager.getConnection(derbyDriver);
			stmt = con.createStatement();

//			dropTable(tableName);//MSFATA
			select("SELECT * FROM " + tableName);
		} catch (UnknownHostException | SQLException e) {
			e.printStackTrace();
		}
	}

	static void dropTable(String tableName) {
		try {
//			derbyDriver = "jdbc:derby:codejava/msfata;create=true";
//			con = DriverManager.getConnection(derbyDriver);

			stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE " + tableName);

			JOptionPane.showMessageDialog(null, "droped");
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "not droped");
			e.printStackTrace();
		} // MSFATA
	}

	/* DELETING FROM TABLE */
	public static void delete(String delete) {
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(delete);
		} catch (SQLException e) {
			e.getSuppressed();
		}
	}

	public static void insert(String insert) throws UnknownHostException {
		try {
			stmt = con.createStatement();
			stmt.execute(insert);
		} catch (SQLException e) {
			e.getSuppressed();
		}
	}

	/* CREATING TABLE */
	public static void create(String create) throws UnknownHostException {

		try {
			UUID.computername = InetAddress.getLocalHost().getHostName();
			stmt = con.createStatement();
			stmt.executeUpdate(create);
			insert("INSERT INTO " + tableName + " VALUES('" + UUID.computername + "','" + zero + "')");
			select("SELECT * FROM " + tableName);
		} catch (SQLException e) {
			e.getSuppressed();
		}
	}

	/* EDITING VALUES IN TABLE */
	public static void update(String update) {
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* SELECTING FROM TABLE */
	public static void select(String select) {

		try {
			rs = stmt.executeQuery(select);
			while (rs.next()) {
				autoPassword = rs.getString("autoPassword");
				userPassword = rs.getString("userPassword");
//				JOptionPane.showMessageDialog(null, autoPassword + " ap");
//				JOptionPane.showMessageDialog(null, userPassword + " up");
				if (autoPassword.equals(userPassword)) {
//					stmt.close();
//					rs.close();
//					con.close();
					// grant access
					Rest.accessGranted = true;
					new Rest(true).setVisible(true);

//					System.out.println("GRANTD");
				} else {
					// Password = JOptionPane.showInputDialog("ENTER ADMIN PASSWORD !");
					frameOpen();
//					if (Password.equals(computername)) {
//						update("UPDATE " + tableName + " SET userPassword = '" + Password + "' WHERE autoPassword = '"
//								+ UUID.computername + "'");
//
//						select("SELECT * FROM " + tableName);
//					} else {
//						while (counter < 2) {
//							frameOpen();
//							// Password = JOptionPane.showInputDialog("ENTER ADMIN PASSWORD 1!");
//							counter++;
//						}
//						System.exit(0);
//					}
				} // MSFATA
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			/* no table exist */
			if (e.getErrorCode() == 30000) {
				/* create table */
				try {
					create("CREATE TABLE " + tableName + "(autoPassword VARCHAR(100), userPassword VARCHAR(100))");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private static void frameOpen() {
		JFrame jf = new JFrame();
		jf.setType(Type.UTILITY);
		jf.setUndecorated(true);
		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(MessageYesNo.class.getResource("/controlButton/IMG.png")));
		jf.setAlwaysOnTop(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.setBounds(150, 170, 430, 141);
		jf.setBounds(500, 200, 427, 214);
		JPanel contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(Color.WHITE);

		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ProgrammingHelp by MSFATA",
				TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(255, 255, 255)));
		jf.setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setForeground(new Color(255, 255, 180));
		textField.setBorder(null);
		textField.setBackground(Color.WHITE);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(316, 178, 98, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		JButton btnActvateSoftware = new JButton("");
		btnActvateSoftware.setBorder(null);
		btnActvateSoftware.setBackground(Color.WHITE);
		btnActvateSoftware.setIcon(new ImageIcon(UUID.class.getResource("/controlButton/activate.png")));
		btnActvateSoftware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onPaste();
				Password = textField.getText();
				if (Password.equals(computername)) {
					update("UPDATE " + tableName + " SET userPassword = '" + Password + "' WHERE autoPassword = '"
							+ UUID.computername + "'");

					select("SELECT * FROM " + tableName);
					jf.setVisible(false);
				} else {
					while (counter < 2) {
						frameOpen();
						// Password = JOptionPane.showInputDialog("ENTER ADMIN PASSWORD 1!");
						counter++;
					}
//					System.exit(0);MSFATA

				}
			}

			private void onPaste() {
				Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable t = c.getContents(this);
				if (t == null)
					return;
				try {
					textField.setText((String) t.getTransferData(DataFlavor.stringFlavor));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnActvateSoftware.setBounds(317, 131, 97, 43);
		contentPane.add(btnActvateSoftware);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(null);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setIcon(new ImageIcon(UUID.class.getResource("/controlButton/IMG.png")));
		btnNewButton.setBounds(316, 12, 97, 118);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UUID.class.getResource("/controlButton/proHelp.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 9, 312, 199);
		contentPane.add(lblNewLabel);
		jf.setVisible(true);
	}
}
