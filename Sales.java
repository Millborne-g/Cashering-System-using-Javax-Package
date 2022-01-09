package PIT;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sales extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	FileReader fr ;
	BufferedReader br ;
	static String f1 = "d://PIT//SalesFood.csv";
	static String f2 = "d://PIT//SalesDrinks.csv";
	static String f3 = "d://PIT//RiceSales.csv";
	DefaultTableModel model1;
	DefaultTableModel model2;
	DefaultTableModel model3;
	
	
	Connection conn;
	PreparedStatement pst;
	Statement stmt;
	ResultSet rs;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sales frame = new Sales();
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
	
	
	private Connection dbConnection() {
		try 
		{   // Step 1.1 - load Java's JDBC SQLite Driver
			Class.forName("org.sqlite.JDBC");			
			// Step 1.2 - get a DB Connection
			Connection conn = DriverManager.getConnection("JDBC:sqlite:d://PIT//food1.sqlite"); // created using SQLite Manager (SQLiteManager_4.6.6_1430708940)
			//System.out.print(conn);
			// prompt user if connection attempt is successful
			JOptionPane.showMessageDialog(null, "Connection Successful.");
			//Receipt();
			return conn;
		}
		catch (Exception err)
		{
			JOptionPane.showMessageDialog(null, "Connection unsuccessful. Exception -> "+err);
			return null;
		}
	}
	public Sales() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 451);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 196));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.info);
		panel.setBounds(10, 11, 511, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 27, 482, 87);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Date", "Sales", "Sold", "TakeOut", "Staffname"
			}
		));
		model1 = (DefaultTableModel) table.getModel();
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 141, 482, 76);
		panel.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Date", "Sales", "Sold", "TakeOut", "Staffname"
			}
		));
		model2 = (DefaultTableModel) table_1.getModel();
		scrollPane_1.setViewportView(table_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 260, 482, 79);
		panel.add(scrollPane_2);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Sales", "Sold", "Date", "TakeOut", "Staffname"
			}
		));
		model3 = (DefaultTableModel) table_2.getModel();
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblDish = new JLabel("Dish");
		lblDish.setBounds(10, 11, 46, 14);
		panel.add(lblDish);
		
		JLabel lblDrinks = new JLabel("Drinks");
		lblDrinks.setBounds(10, 127, 46, 14);
		panel.add(lblDrinks);
		
		JLabel lblRice = new JLabel("Rice");
		lblRice.setBounds(10, 245, 46, 14);
		panel.add(lblRice);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chooseStaff show = new chooseStaff();
				show.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(10, 378, 89, 23);
		contentPane.add(btnBack);
		read() ;
		conn = dbConnection();
	}
	
	
	private void read() {
		try {
			//Drinks.clear();
			//int cny=0;
			//linenum=0;
		//	j=0;
			//lineTotal=0;
			fr = new FileReader(f1);
			br = new BufferedReader(fr);
			do
			{
				String line = br.readLine();

				if (line == null)
					break;
				
				StringTokenizer st = new StringTokenizer (line,",");
				while(st.hasMoreTokens()) {
					String r1=st.nextToken();
					String r2=st.nextToken();
					String r3=st.nextToken();
					String r4=st.nextToken();
					String r5=st.nextToken();
					String r6=st.nextToken();
					String r7=st.nextToken();
					
					
					Object[] row = {r1,r2,r3,r4,r5,r6,r7};
					model1.addRow(row);
					
					
				}
			
				
			}
			while(true);
			
		}catch(Exception eq) {
			JOptionPane.showMessageDialog(null, "No dish has been sell");
		}
		
		try {
			
			fr = new FileReader(f2);
			br = new BufferedReader(fr);
			do
			{
				String line = br.readLine();

				if (line == null)
					break;
				
				StringTokenizer st = new StringTokenizer (line,",");
				while(st.hasMoreTokens()) {
			
					String r1=st.nextToken();
					String r2=st.nextToken();
					String r3=st.nextToken();
					String r4=st.nextToken();
					String r5=st.nextToken();
					String r6=st.nextToken();
					String r7=st.nextToken();
					
			
					Object[] row = {r1,r2,r3,r4,r5,r6,r7};
					model2.addRow(row);
					
					
					
				}
			
				
			}
			while(true);
			
		}catch(Exception eq) {
			JOptionPane.showMessageDialog(null, "No drinks has been sell");
		}
		
		try {
			//Drinks.clear();
			//int cny=0;
			//linenum=0;
		//	j=0;
			//lineTotal=0;
			fr = new FileReader(f3);
			br = new BufferedReader(fr);
			do
			{
				String line = br.readLine();

				if (line == null)
					break;
				
				StringTokenizer st = new StringTokenizer (line,",");
				while(st.hasMoreTokens()) {
			
					String r1=st.nextToken();
					String r2=st.nextToken();
					String r3=st.nextToken();
					String r4=st.nextToken();
					String r5=st.nextToken();
					String r6=st.nextToken();
					
					
			
					Object[] row = {r1,r2,r3,r5,r4,r6};
					model3.addRow(row);
					
					
					
				}
			
				
			}
			while(true);
			
		}catch(Exception eq) {
			JOptionPane.showMessageDialog(null, "No Rice has been sell");
		}
	}
}
