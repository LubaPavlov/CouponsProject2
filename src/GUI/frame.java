package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class frame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField pwdPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
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
	public frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblCouponSystem = new JLabel("Coupon System");
		panel.add(lblCouponSystem);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		txtUserName = new JTextField();
		txtUserName.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent arg0) {
			}
		});
		txtUserName.setBounds(97, 63, 238, 35);
		txtUserName.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setToolTipText("Password");
		pwdPassword.setBounds(97, 143, 238, 35);
		panel_1.setLayout(null);
		panel_1.add(txtUserName);
		panel_1.add(pwdPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(177, 283, 97, 25);
		panel_1.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(97, 219, 238, 22);
		panel_1.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("User name");
		lblNewLabel.setBounds(96, 34, 84, 16);
		panel_1.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(96, 113, 84, 16);
		panel_1.add(lblPassword);
		
		JLabel lblUserRole = new JLabel("User role");
		lblUserRole.setBounds(96, 190, 84, 16);
		panel_1.add(lblUserRole);
	}
}
