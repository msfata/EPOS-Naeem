package net.tablet.restaurant;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
//import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class MachineName extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MachineName frame = new MachineName();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MachineName() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 100, 646, 332);
//		setBounds(850, 170, 382, 155);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), new LineBorder(new Color(255, 255, 255), 5)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MachineName.class.getResource("/controlButton/IMG.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(44, 37, 86, 90);
		contentPane.add(label);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(null);
		panel.setBounds(45, 255, 563, 56);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JLabel lblEmail = new JLabel("EMAIL       :");
		panel.add(lblEmail);
		
		JLabel lblMsfatamecom = new JLabel("msfata@me.com");
		panel.add(lblMsfatamecom);
		
		JLabel lblPhone = new JLabel("PHONE      :");
		panel.add(lblPhone);
		
		JLabel label_1 = new JLabel("07421110333");
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(MachineName.class.getResource("/controlButton/proHelp.png")));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(163, 34, 325, 186);
		contentPane.add(label_2);
		
				btnNewButton = new JButton("");
				btnNewButton.setBounds(45, 197, 111, 56);
				contentPane.add(btnNewButton);
				btnNewButton.setBackground(Color.WHITE);
				btnNewButton.setBorder(new LineBorder(Color.WHITE, 5));
				btnNewButton.setIcon(new ImageIcon(MachineName.class.getResource("/controlButton/activate.png")));
				btnNewButton.setForeground(Color.BLACK);
				btnNewButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});
				btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
				
						textField = new JTextField();
						textField.setBounds(156, 219, 319, 34);
						contentPane.add(textField);
						textField.setEditable(false);
						textField.setBorder(null);
						textField.setForeground(Color.BLACK);
						textField.setBackground(new Color(255, 255, 255));
						textField.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent arg0) {
								String computername;
								try {
									computername = InetAddress.getLocalHost().getHostName();
									textField.setText(computername);
								} catch (UnknownHostException e) {
									e.printStackTrace();
								}

								String myString = textField.getText();
								StringSelection stringSelection = new StringSelection(myString);
								Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
								clipboard.setContents(stringSelection, null);
								textField.setForeground(Color.WHITE);
								textField.setBackground(Color.WHITE);
								btnNewButton.requestFocus();
							}
						});
						textField.setHorizontalAlignment(SwingConstants.CENTER);
						textField.setFont(new Font("Tahoma", Font.PLAIN, 7));
						textField.setColumns(10);
	}
}
