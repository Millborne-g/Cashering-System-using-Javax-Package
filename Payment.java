package PIT;



import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JCheckBox;

public class Payment extends JFrame {

	public JTextField textFieldTotalAmt;
	private JTextField textFieldCash;
	private JTextField textFieldChange;
	static DecimalFormat df = new DecimalFormat ("0.00");
	
	private JButton btnCancel;
	private JButton btnPay;
	
	private double cash, change;
	private boolean s = false;
	static String cosname=null ;
	static String conum =null;
	static String staff=null;
	static String file = "d://PIT//sales.csv";
	public JCheckBox chckbxNewCheckBox;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Payment frame = new Payment();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	int con =0;

	/**
	 * Create the frame.
	 */
	public Payment() {
		initialize();
		
	}
	public Payment(String name , String conum1,String staff1) {
		 cosname=name ;
	    conum =conum1;
		 staff=staff1;
		initialize();
		
	}
	
	public Payment(String name ) {
		
		initialize();
		
	}
	
	private void initialize() {
		getContentPane().setBackground(new Color(255, 228, 196));
		setResizable(false);
		setTitle("Payment");
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.info);
		panel.setBounds(10, 11, 260, 111);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblTotalAmountDue = new JLabel("Total Amount Due");
		lblTotalAmountDue.setBounds(10, 11, 98, 14);
		panel.add(lblTotalAmountDue);
		
		JLabel lblCashTender = new JLabel("Cash");
		lblCashTender.setBounds(10, 36, 98, 14);
		panel.add(lblCashTender);
		
		JLabel lblChange = new JLabel("Change");
		lblChange.setBounds(10, 61, 46, 14);
		panel.add(lblChange);
		
		textFieldTotalAmt = new JTextField();
		textFieldTotalAmt.setEditable(false);
		textFieldTotalAmt.setBounds(118, 8, 132, 20);
		panel.add(textFieldTotalAmt);
		textFieldTotalAmt.setColumns(10);
		
		textFieldCash = new JTextField();
		textFieldCash.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try
				{
					cash = Double.parseDouble(textFieldCash.getText());
					
					if (cash < Double.parseDouble(textFieldTotalAmt.getText()))
						textFieldChange.setText("not enough");
					
					else
					{
						change = cash - Double.parseDouble(textFieldTotalAmt.getText()) ;
						textFieldChange.setText("" + df.format(change));
					}
					
				}
				
				catch (Exception error)
				{
					
				}
			}
				
		});
		textFieldCash.setBounds(118, 33, 132, 20);
		panel.add(textFieldCash);
		textFieldCash.setColumns(10);
		
		textFieldChange = new JTextField();
		textFieldChange.setEditable(false);
		textFieldChange.setBounds(118, 58, 132, 20);
		panel.add(textFieldChange);
		textFieldChange.setColumns(10);
		
		chckbxNewCheckBox = new JCheckBox("Take Out");
		chckbxNewCheckBox.setBackground(SystemColor.info);
		chckbxNewCheckBox.setBounds(118, 81, 132, 23);
		panel.add(chckbxNewCheckBox);
		
		btnPay = new JButton("Pay");
		btnPay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try
				{
					if(chckbxNewCheckBox.isSelected()) {
						con=1;
					}
					else {
						con=0;
					}
					if (s == true)
					{
						
						Order orderExit = new Order (0, false,con);
						orderExit.setVisible(true);
						
					}
					
					cash = Double.parseDouble(textFieldCash.getText());
					
					if (cash < Double.parseDouble(textFieldTotalAmt.getText()))
						JOptionPane.showMessageDialog(null,"Cash isn't enough!",null,JOptionPane.ERROR_MESSAGE);
				
					else
					{
						change = cash - Double.parseDouble(textFieldTotalAmt.getText()) ;
						
						JOptionPane.showMessageDialog(null,"Transaction successful!",null,JOptionPane.INFORMATION_MESSAGE);
						textFieldChange.setText("" + df.format(change));
						
						s = true;
						
					//	Order shown = new Order();
						//shown.write1();
						
						btnCancel.setText("Back");
						btnPay.setText("EXIT");
						
						
					}
				}
				
				catch (Exception error)
				{
					
				}
				
			}

	
		});		
		
		btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				
				if(chckbxNewCheckBox.isSelected()) {
					con=1;
				}
				else {
					con=0;
				}
				
				if (s == false)
				{
					
					try {
						JOptionPane.showMessageDialog(null,"Checkout cancelled. Products for checkout are discarded","Cancel Checkout", JOptionPane.WARNING_MESSAGE);
					Order openOrder;
						openOrder = new Order (true);
						openOrder.setVisible(true);
					dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				else if (s == true)
				{
					s = false;
					Order orderSuccessful = new Order (Integer.parseInt(textFieldTotalAmt.getText()), true,con);
					orderSuccessful.setVisible(true);
					dispose();
					//receipt();
					change();
					dispose();
				}
			}
		});
		btnCancel.setBounds(20, 143, 89, 23);
		getContentPane().add(btnCancel);
		
		
		btnPay.setBounds(127, 143, 129, 23);
		getContentPane().add(btnPay);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 206);
		//receipt();
	}
	
	public void change()
	{
		btnCancel.setText("Cancel");
		btnPay.setText("Pay");
	}
	

}
