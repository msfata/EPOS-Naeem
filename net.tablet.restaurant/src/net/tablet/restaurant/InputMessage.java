package net.tablet.restaurant;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

public class InputMessage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected String str;
	public JTextField textField;
	private static JButton btnEnter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					InputMessage frame = new InputMessage();
					frame.setVisible(true);
					btnEnter.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public InputMessage() {
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 332, 291);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new CompoundBorder(
				new LineBorder(new Color(255, 255, 255), 3, true), new LineBorder(new Color(0, 0, 0), 2, true))));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		panel.setBorder(new LineBorder(Color.WHITE, 3));
		panel.setBounds(14, 219, 301, 61);
		contentPane.add(panel);

		textField = new JTextField();
		textField.setForeground(new Color(102, 153, 0));
		textField.setEditable(false);
		textField.setBounds(3, 0, 294, 29);

		panel.setLayout(null);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textField.setBackground(new Color(102, 153, 85));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textField);
		textField.setColumns(15);

		btnEnter = new JButton("");
		btnEnter.setBounds(151, 31, 146, 26);
		btnEnter.setIcon(new ImageIcon(InputMessage.class.getResource("/controlButton/ENTER123.png")));
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onPaste();
			}

			private void onPaste() {
				Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable t = c.getContents(this);
				if (t == null)
					return;
				try {
					textField.setText((String) t.getTransferData(DataFlavor.stringFlavor));
//					UUID.Password = textField.getText();
//					setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		panel.add(btnEnter);

		JButton btnExit = new JButton("");
		btnExit.setBounds(3, 31, 146, 26);
		btnExit.setIcon(new ImageIcon(InputMessage.class.getResource("/controlButton/redExit.png")));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		panel.add(btnExit);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(InputMessage.class.getResource("/controlButton/proHelp.png")));
		label.setBounds(14, 11, 311, 203);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label);
		setUndecorated(true);
	}
}
