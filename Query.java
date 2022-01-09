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

public class Query extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	FileReader fr ;
	BufferedReader br ;
	static String f1 = "d://PIT//SalesFood.csv";
	static String f2 = "d://PIT//Drinks.csv";
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
					Query frame = new Query();
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
	public Query() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 354);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 196));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.info);
		panel.setBounds(10, 11, 511, 257);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 27, 482, 87);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(SystemColor.info);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Price", "Quantity", "Date", "Sales", "Sold"
			}
		));
		model1 = (DefaultTableModel) table.getModel();
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 141, 482, 76);
		panel.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setBackground(SystemColor.info);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Code", "Name", "Price", "Size", "Unit", "Quantity", "Date", "Sales", "Sold"
			}
		));
		model2 = (DefaultTableModel) table_1.getModel();
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblDish = new JLabel("Dish");
		lblDish.setBounds(10, 11, 46, 14);
		panel.add(lblDish);
		
		JLabel lblDrinks = new JLabel("Drinks");
		lblDrinks.setBounds(10, 127, 46, 14);
		panel.add(lblDrinks);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chooseStaff show = new chooseStaff();
				show.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(10, 279, 89, 23);
		contentPane.add(btnBack);
		
		conn = dbConnection();
		read() ;
	}
	
	
	private void read() {
		try {
		
			
			String query = "select * from food1 ";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			
		
		while(rs.next()) {
			Object[] row = {rs.getString("ID"),rs.getString("Food"),rs.getString("Price"),rs.getString("Serve"),rs.getString("Date"),rs.getString("Sales"),rs.getString("Sold")};
			model1.addRow(row);
		}
			
		}catch(Exception eq) {
			System.out.println(eq);
			eq.printStackTrace();
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
					String r8=st.nextToken();
					String r9=st.nextToken();
				
					
			
					Object[] row = {r1,r2,r3,r4,r5,r6,r7,r8,r9};
					model2.addRow(row);
					
					
					
				}
			
				
			}
			while(true);
			
		}catch(Exception eq) {
			System.out.println(eq);
		}
		
		
	}
}

