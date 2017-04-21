import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmpCheckIn extends JFrame {

	/**
	 * 
	 */

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpCheckIn frame = new EmpCheckIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	Connection conn=null;
	public EmpCheckIn() {
		conn=SQLconnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 558);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Check-In ");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBounds(179, 16, 81, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Guest's First Name:");
		lblNewLabel_1.setBounds(30, 82, 131, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblGuestsLastName = new JLabel("Guest's Last Name:");
		lblGuestsLastName.setBounds(30, 121, 131, 16);
		contentPane.add(lblGuestsLastName);
		
		textField = new JTextField();
		textField.setBounds(159, 77, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(159, 116, 130, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel resPanel = new JPanel();
		resPanel.setBackground(Color.DARK_GRAY);
		resPanel.setVisible(false);
		resPanel.setBounds(48, 231, 375, 211);
		contentPane.add(resPanel);
		resPanel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Room Number:");
		lblNewLabel_2.setBounds(18, 22, 104, 16);
		resPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(128, 22, 61, 16);
		resPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("No. of Guests:");
		lblNewLabel_4.setBounds(18, 42, 104, 16);
		resPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(128, 42, 61, 16);
		resPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Check-out Date:");
		lblNewLabel_6.setBounds(18, 62, 122, 16);
		resPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setBounds(128, 62, 80, 16);
		resPanel.add(lblNewLabel_7);
		
		JButton btnNewButton_1 = new JButton("Check Guest In");
		
		btnNewButton_1.setBounds(97, 130, 176, 29);
		resPanel.add(btnNewButton_1);
		
		JLabel lblNewLabel_success = new JLabel("New label");
		lblNewLabel_success.setBounds(37, 166, 258, 29);
		resPanel.add(lblNewLabel_success);
		lblNewLabel_success.setText("Guest has been checked-in successfully!");
		lblNewLabel_success.setVisible(false);
		
		JButton btnNewButton = new JButton("Lookup Reservation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try{
					String firstname=textField.getText();
					System.out.println(firstname);
					String lastname=textField_1.getText();
					System.out.println(lastname);
					String query="Select * from Guests where firstname=? and lastname=?";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.setString(1, firstname);
					pst.setString(2, lastname);
					ResultSet rs=pst.executeQuery();
				
					int room=rs.getInt(6);
					int guests=rs.getInt(10);
					String coDate=rs.getString(11);
					String gsts=Integer.toString(guests);
					String rm=Integer.toString(room);
					lblNewLabel_3.setText(rm);
					lblNewLabel_5.setText(gsts);
					lblNewLabel_7.setText(coDate);
					resPanel.setVisible(true);
					
					
				}catch(Exception f){
					JOptionPane.showMessageDialog(null, "No booking available for Guest.");
					
				}
				
				
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String firstname=textField.getText();
					String lastname=textField_1.getText();
					String query="Select * from Guests where firstname=? and lastname=?";
					PreparedStatement pst=conn.prepareStatement(query);
					pst.setString(1, firstname);
					pst.setString(2, lastname);
					ResultSet rs=pst.executeQuery();
					int roomnum=rs.getInt(6);
					if(roomnum>0&&roomnum<6){
						String tot="update guests set totaldue = '100.00' where firstname=? and lastname=?";
						PreparedStatement pat=conn.prepareStatement(tot);
						pat.setString(1, textField.getText());
						pat.setString(2, textField_1.getText());
						pat.execute();
						
						}else{
							String tot2="update guests set totaldue = '175.00' where firstname=? and lastname=?";
							PreparedStatement pat2=conn.prepareStatement(tot2);
							pat2.setString(1, textField.getText());
							pat2.setString(2, textField_1.getText());
							pat2.execute();
						}
					int checked=rs.getInt(7);
					System.out.println(checked);
					if(checked==1){
						JOptionPane.showMessageDialog(null, "Guest is already checked-in.");
					}else{
					
					String qy="update guests set checkin = '1' where firstname=? and lastname=?";
					PreparedStatement pt=conn.prepareStatement(qy);
					pt.setString(1, textField.getText());
					pt.setString(2, textField_1.getText());
					pt.execute();
					
					lblNewLabel_success.setVisible(true);
					}
					
				}catch (Exception g){
					JOptionPane.showMessageDialog(null, "Could not check guest in successfully.");
					
				}
			}
		});
		btnNewButton.setBounds(134, 172, 192, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Log off");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Initial i=new Initial();
				i.setVisible(true);
				try{
					conn.close();
				}catch(Exception n){
					
				}
			}
		});
		btnNewButton_2.setBounds(6, 501, 99, 29);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("<<BACK");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					conn.close();
					}catch(Exception f){
						JOptionPane.showMessageDialog(null, "Err");
					}
				setVisible(false);
				EmpSelection es=new EmpSelection();
				es.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(6, 6, 81, 29);
		contentPane.add(btnNewButton_3);
		
	
	
			}
		
		
	}

