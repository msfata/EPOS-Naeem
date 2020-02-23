package net.tablet.restaurant;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

@SuppressWarnings("all")
public class MessageYesNoReset extends JFrame {

	private JPanel contentPane;
	public static JButton btnNo;
	static JButton btnYes;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessageYesNoReset frame = new MessageYesNoReset("");
					frame.setVisible(true);
					btnNo.requestFocus();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MessageYesNoReset(String string) {
		setType(Type.UTILITY);
		setUndecorated(true);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MessageYesNoReset.class.getResource("/controlButton/IMG.png")));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(150, 170, 382, 155);
		setBounds(850, 170, 382, 155);//right hand table tablet
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(Color.WHITE);

		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ProgrammingHelp by MSFATA",
				TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(255, 255, 255)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(null);
		panel.setBounds(30, 84, 321, 40);
//		panel.setLayout(new GridLayout(0, 1, 0, 0));
		contentPane.add(panel);

		btnYes = new JButton("");
		btnYes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				UUID.dropTable(UUID.tableName);


			}
		});
		btnYes.setBorder(null);
		btnYes.setBackground(Color.WHITE);
		btnYes.setIcon(new ImageIcon(MessageYesNoReset.class.getResource("/controlButton/YES.PNG")));
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		panel.add(btnYes);

		btnNo = new JButton("");
		btnNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					setVisible(false);
				} else {
					btnYes.requestFocus();
				}
			}
		});
		btnNo.setBorder(null);
		btnNo.setBackground(Color.WHITE);
		btnNo.setIcon(new ImageIcon(MessageYesNoReset.class.getResource("/controlButton/no.png")));
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panel.add(btnNo);

		JTextField textField = new JTextField(string);
		textField.setFocusable(false);
		textField.setFocusTraversalKeysEnabled(false);
		textField.setEditable(false);
		textField.setForeground(Color.WHITE);
		textField.setFont(new Font("Monaco", Font.PLAIN, 12));
		textField.setBorder(null);
		textField.setBackground(Color.BLACK);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(30, 41, 321, 40);
		contentPane.add(textField);
	}
}
