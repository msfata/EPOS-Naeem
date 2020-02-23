package Laptop;

import java.awt.Color;
import java.awt.EventQueue;
//import java.awt.GridLayout;
//import java.awt.SystemColor;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
//import javax.swing.border.CompoundBorder;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
//import javax.swing.border.EtchedBorder;
//import java.awt.Window.Type;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class MessageYesNo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JButton btnNo;
	static JButton btnYes;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessageYesNo frame = new MessageYesNo("");
					frame.setVisible(true);
					btnNo.requestFocus();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MessageYesNo(String string) {
		setType(Type.UTILITY);
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MessageYesNo.class.getResource("/controlButton/IMG.png")));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(850, 170, 382, 155);//right hand table tablet
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(Color.WHITE);
		
		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ProgrammingHelp by MSFATA", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(255, 255, 255)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(null);
		panel.setBounds(30, 84, 320, 38);
//		panel.setLayout(new GridLayout(0, 1, 0, 0));
		contentPane.add(panel);

		btnYes = new JButton("");
		btnYes.setBounds(0, 0, 160, 40);
		btnYes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			System.exit(0);
			}
		});
		btnYes.setBorder(null);
		btnYes.setBackground(Color.WHITE);
		btnYes.setIcon(new ImageIcon(MessageYesNo.class.getResource("/buttons/buttons/YES.png")));
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
			}
		});
		panel.setLayout(null);
		panel.add(btnYes);

	btnNo = new JButton("");
	btnNo.setBounds(160, 0, 160, 40);
	btnNo.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==10) {
			setVisible(false);
			}else {
				btnYes.requestFocus();
			}
		}
	});
		btnNo.setBorder(null);
		btnNo.setBackground(Color.WHITE);
		btnNo.setIcon(new ImageIcon(MessageYesNo.class.getResource("/buttons/buttons/NO.png")));
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
		textField.setForeground(Color.BLACK);
		textField.setFont(new Font("Mongolian Baiti", Font.PLAIN, 12));
		textField.setBackground(Color.WHITE);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(30, 41, 321, 40);
		contentPane.add(textField);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(MessageYesNo.class.getResource("/buttons/buttons/LAYOUT.jpg")));
		label.setBounds(7, 7, 368, 140);
		contentPane.add(label);
	}
}
