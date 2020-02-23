package Laptop;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JCalendar;

public class SearchFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected String from;
	private JTextField tfFrom;
	private JTextField tfTo;

	public SearchFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 857, 335);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		contentPane.setBorder(new LineBorder(new Color(0, 139, 139)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JCalendar fromCalendar = new JCalendar();

		fromCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (isVisible()) {
					SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
					tfFrom.setText(ft.format(fromCalendar.getDate()));
					if (!tfTo.getText().isEmpty()) {
						Rest.selectiveSearch(tfFrom.getText(), tfTo.getText());
						setVisible(false);
						tfFrom.setText(null);
						tfTo.setText(null);
					}
				}
			}
		});

		fromCalendar.setBounds(10, 39, 414, 295);
		contentPane.add(fromCalendar);

		final JCalendar toCalendar = new JCalendar();
		toCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (isVisible()) {
					SimpleDateFormat ft1 = new SimpleDateFormat("dd.MM.yyyy");
					tfTo.setText(ft1.format(toCalendar.getDate()));
					if (!tfFrom.getText().isEmpty()) {
						Rest.selectiveSearch(tfFrom.getText(), tfTo.getText());
						setVisible(false);
						tfFrom.setText(null);
						tfTo.setText(null);
					}
				}
			}
		});
		toCalendar.setBounds(434, 39, 414, 295);
		setUndecorated(true);
		contentPane.add(toCalendar);

		JLabel lblDateFrom = new JLabel("FROM : ");
		lblDateFrom.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDateFrom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateFrom.setBounds(10, 11, 205, 25);
		contentPane.add(lblDateFrom);

		JLabel lblDateTo = new JLabel("TO : ");
		lblDateTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateTo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDateTo.setBounds(434, 11, 103, 25);
		contentPane.add(lblDateTo);

		tfFrom = new JTextField();
		tfFrom.setBorder(null);
		tfFrom.setEditable(false);
		tfFrom.setHorizontalAlignment(SwingConstants.LEFT);
		tfFrom.setBounds(219, 11, 205, 24);
		contentPane.add(tfFrom);
		tfFrom.setColumns(10);

		tfTo = new JTextField();
		tfTo.setBorder(null);
		tfTo.setEditable(false);
		tfTo.setHorizontalAlignment(SwingConstants.LEFT);
		tfTo.setColumns(10);
		tfTo.setBounds(547, 11, 176, 24);
		contentPane.add(tfTo);

		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Rest.selectiveSearch(tfFrom.getText(), tfTo.getText());
				setVisible(false);
				tfFrom.setText(null);
				tfTo.setText(null);
			}
		});
		btnSearch.setBounds(733, 11, 115, 25);
		contentPane.add(btnSearch);
	}
}
