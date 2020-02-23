package Laptop;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

public class MessageOK extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JButton btnOK;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessageOK frame = new MessageOK("");
					frame.setVisible(true);
					btnOK.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MessageOK(String string) {

		setType(Type.UTILITY);
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MessageOK.class.getResource("/controlButton/IMG.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(200, 200, 311, 148);
		setBounds(850, 170, 382, 155);// right hand table tablet
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ProgrammingHelp by MSFATA",
				TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(255, 255, 255)));
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextField textField = new JTextField(string);
		textField.setEditable(false);
		textField.setForeground(Color.BLACK);
		textField.setFont(new Font("Monaco", Font.PLAIN, 12));
//		textField.setBorder(null);
		textField.setBackground(Color.WHITE);
		textField.setTransferHandler(null);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(72, 37, 231, 33);
//		setUndecorated(true);
		contentPane.add(textField);

		btnOK = new JButton("");
		btnOK.setForeground(Color.WHITE);
		btnOK.setBounds(106, 95, 159, 33);
		contentPane.add(btnOK);
		btnOK.setBackground(Color.WHITE);
		btnOK.setBorder(null);
		btnOK.setIcon(new ImageIcon(MessageOK.class.getResource("/buttons/buttons/OK.png")));
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MessageOK.class.getResource("/buttons/buttons/LAYOUT.jpg")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(8, 7, 365, 140);
		contentPane.add(label);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnOK.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					btnOK.requestFocus();
					setVisible(false);
				}
			}
		});
	}
}
