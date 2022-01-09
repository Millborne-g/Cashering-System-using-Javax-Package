package PIT;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class chooseStaff extends JFrame {
	static String f = "d://PIT//Drinks.csv";
	//File f = new File("d://Games//Drinks.csv");
	static String f1 = "d://PIT//SlaesFood.csv";
	static String f2 = "d://PIT//SlaesDrinks.csv";
	static String f3 = "d://PIT//RiceSales.csv";
	private JPanel contentPane;
	private JTable table;
	Connection conn;
	PreparedStatement pst;
	Statement stm;
	ResultSet rs;
	DefaultTableModel model;
	DefaultTableModel model1;
	String SQL;
	private JTable table_1;
	static FileWriter fw ;
	static FileReader fr ;
	
	Date date = new Date();
	String fmt = "yy-MM-dd HH:mm:ss";
	SimpleDateFormat sdf = new SimpleDateFormat(fmt);
	
	public static ArrayList <DrinksParent> List = new ArrayList<> (); 
	
	String Staffname ;
	
	BufferedReader br;
	static String[][] Drinks = new String[20][30];
	static int [][] Drinks1 = new int [20][30];
	static int linenum=0, lineTotal=0,j=0;
	static  JTextField changeRtextField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fw = new FileWriter(f,true);
					chooseStaff frame = new chooseStaff();
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
			return conn;
		}
		catch (Exception err)
		{
			JOptionPane.showMessageDialog(null, "Connection unsuccessful. Exception -> "+err);
			return null;
		}
	}
	public chooseStaff() {
		conn=dbConnection();
		initialize();
		
	}
	public chooseStaff(String name) {
		Staffname=name;
		conn=dbConnection();
		initialize();
		
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 307);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 196));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Drinks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(SystemColor.info);
		panel.setBounds(357, 11, 321, 215);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 25, 301, 130);
		panel.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setBackground(SystemColor.info);
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Price", "Size", "Unit", "Quantity"
			}
		));
		model1 = (DefaultTableModel) table_1.getModel();
		
		JButton button = new JButton("Delete");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					fw = new FileWriter(f);
					table_1.getSelectedRow();
					
					for(int t=0; t<	lineTotal;t++) {
					if(table_1.getSelectedRow()!=t)	{
					fw.write(List.get(t).id+","+List.get(t).name+","+List.get(t).price+","+List.get(t).size+","+List.get(t).unit+","+List.get(t).qty+","+sdf.format(date)+","+0+","+0);
					fw.append('\n');
					fw.flush();
					}
					}
					JOptionPane.showMessageDialog(null, "Successfully Deleted! Load again to refresh!");
				} catch (IOException e) {
				
					System.out.println(e);
				
				}
				
				
				
				
			}
		});
		button.setBounds(15, 181, 89, 23);
		panel.add(button);
		
		JButton button_2 = new JButton("AddNew");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
	try {
					fw = new FileWriter(f,true);
					JTextField ID = new JTextField();
					JTextField name = new JTextField();
					JTextField Price = new JTextField();
					JTextField Size = new JTextField();
					JTextField Quant= new JTextField();
					JRadioButton oz =new JRadioButton("oz");
					JRadioButton lt =new JRadioButton("lt");
					ButtonGroup bg = new ButtonGroup();
					bg.add(oz);
					bg.add(lt);
					
					Object[] obj = {"Fill-up the Following","ID ",ID,"Drinks ",name,"Price ",Price,"Size ",Size,oz,lt,"Quantity ",Quant};
					int i =JOptionPane.showConfirmDialog(null, obj,null,JOptionPane.OK_CANCEL_OPTION);
					//stm = conn.createStatement();
				//	String name1,data1;
					
					//int Serve1,Price1;
					
					String size1 = null;
					if(oz.isSelected()) {
						size1 = oz.getLabel();
					}
					if(lt.isSelected()) {
						size1 = lt.getLabel();
					}
					
					if(i==0) {
						fw.write(ID.getText()+","+name.getText()+","+Integer.parseInt(Price.getText())+","+Integer.parseInt(Size.getText())+","+size1+","+Integer.parseInt(Quant.getText())+","+sdf.format(date)+","+0+","+0);
						fw.append('\n');
						fw.flush();
					 //stm.executeUpdate("insert into food1 (ID,Food,Price,Serve) "+ "VALUES('"+ID.getText()+"','"+name.getText()+"','"+Integer.parseInt(Price.getText())+"','"+Integer.parseInt(Serve.getText())+"')");
					 JOptionPane.showMessageDialog(null, "Successfully Created! Load again to refresh!");
					}
						
				}  catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_2.setBounds(114, 181, 89, 23);
		panel.add(button_2);
		
		JButton button_3 = new JButton("Update");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					
					table_1.getSelectedRow();
					int change =table_1.getSelectedRow();
					/*	
						String id=null;
						while(rs.next()) {
							id=rs.getString("ID");
							
							o++;
							
						}*/
					System.out.println("row drinks "+table_1.getSelectedRow());
					//	int o=0;
				//	for(int t=0; t<	lineTotal;t++) {
					//if(table_1.getSelectedRow()!=t)	{
					//fw.write(Drinks [t][0]+","+Drinks [linenum][1]+","+Drinks1 [linenum][2]+","+Drinks1 [linenum][3]+","+Drinks [linenum][4]+","+Drinks1 [linenum][5]);
					//fw.flush();
					//	if(t==table.getSelectedRow()) {
					//		change=t;
					//			break;
					//		}
					//}
					//o++;
					
					
					//}
					JTextField ch = new JTextField();
					//System.out.println(Drinks [change][0]+","+Drinks [change][1]+","+Drinks1 [change][2]+","+Drinks1 [change][3]+","+Drinks [change][4]+","+Drinks1 [change][5]);
					//fw.write(List.get(t).id+","+List.get(t).name+","+List.get(t).price+","+List.get(t).size+","+List.get(t).unit+","+List.get(t).qty);
					
					Object[] obj= { "User Found! \nEnter what you want to edit \n1. ID:"+List.get(change).id+"\n2. Name:"+List.get(change).name
							+"\n3. Price:"+List.get(change).price+"\n4. Size:"+List.get(change).size+"\n5. Unit:"+List.get(change).unit+"\n6. Quantity:"+List.get(change).qty,ch};
				JOptionPane.showMessageDialog(null, obj);
				
				try{
					int choice =0;
					choice =Integer.parseInt(ch.getText());
				
				switch (choice) {
				
				
		
					
				case 1:
					String ID=	JOptionPane.showInputDialog("Enter the new ID");
					List.get(change).id= ID;
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh !");
					read();
					break;
				case 2:
					String NAME=	JOptionPane.showInputDialog("Enter the new Name");
					List.get(change).name= NAME;
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					read();
					break;
				case 3:
					int Price1=	Integer.parseInt(JOptionPane.showInputDialog("Enter the new Price"));
					List.get(change).price= Price1;
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					read();
					break;
				case 4:
					int  Size=Integer.parseInt(JOptionPane.showInputDialog("Enter the new size "));
					List.get(change).size=Size;
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					read();
					break;
				case 5:
					String  Unit =JOptionPane.showInputDialog("Enter the new Unit ");
					List.get(change).unit=Unit;
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					read();
					break;
					
				case 6:
					int QTY =Integer.parseInt(JOptionPane.showInputDialog("Enter the new Quantity "));
					List.get(change).qty=QTY;
					JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
					read();
					break;
					
			
					
				
				}
				}catch(Exception e3) {
					System.out.println(e3);
				}
				
				
				fw = new FileWriter(f);
				for(int t=0; t<	lineTotal;t++) {
			//	if(change==t) {
				//fw.write(Drinks [t][0]+","+Drinks [linenum][1]+","+Drinks1 [linenum][2]+","+Drinks [linenum][3]+","+Drinks1 [linenum][4]+","+Drinks1 [linenum][5]);
				fw.write(List.get(t).id+","+List.get(t).name+","+List.get(t).price+","+List.get(t).size+","+List.get(t).unit+","+List.get(t).qty+","+List.get(t).date+","+List.get(t).sales+","+List.get(t).sold);
				
				fw.append('\n');
				fw.flush();
				
			   // }
			}
					
				} catch (IOException e2) {
				
					System.out.println(e2);
				
				}
			}
		});
		
		button_3.setBounds(222, 181, 89, 23);
		panel.add(button_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dishes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBackground(SystemColor.info);
		panel_1.setBounds(10, 11, 337, 215);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 317, 130);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setBackground(SystemColor.info);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Food", "Price", "Serve"
			}
		));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
				
				    
				    System.out.println("ROw "+table.getSelectedRow());
					//model.setRowCount(0);
					String query1 =  "select * from food1";
					pst = conn.prepareStatement(query1);
					rs = pst.executeQuery();
					int o=0;
					String id=null;
					while(rs.next()) {
						id=rs.getString("ID");
						if(o==table.getSelectedRow()) {
							break;
						}
						o++;
						
					}
					
					
					String query = "select * from food1 where ID='"+id+"'";
						pst= conn.prepareStatement(query);					
					rs = pst.executeQuery();
					String data= rs.getString("Id");
					if(id.equals(data)) {
					JOptionPane.showMessageDialog(null, "User Found! \n ID:"+data);
						String SQL="DELETE FROM food1 WHERE ID =?";
						pst = conn.prepareStatement(SQL);					
						pst.setString(1, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Deleted! Load again to refresh!");
						
				}
					else 
					JOptionPane.showMessageDialog(null, "Invalid Choice");
						read();
					
					
					
				}catch(Exception ex) {
					
				}
			}
		});
		btnDelete.setBounds(10, 175, 89, 23);
		panel_1.add(btnDelete);
		
		JButton buttonInsert = new JButton("AddNew");
		buttonInsert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					
					JTextField ID = new JTextField();
					JTextField name = new JTextField();
					JTextField Price = new JTextField();
					JTextField Serve = new JTextField();
					
					
					
					
					Object[] obj = {"Fill-up the Following","ID ",ID,"Dish ",name,"Price ",Price,"Serve ",Serve};
					int i =JOptionPane.showConfirmDialog(null, obj,null,JOptionPane.OK_CANCEL_OPTION);
					stm = conn.createStatement();
				//	String name1,data1;
					//int Serve1,Price1;
					
					if(i==0) {
					 stm.executeUpdate("insert into food1 (ID,Food,Price,Serve,Date,Sales,Sold) "+ "VALUES('"+ID.getText()+"','"+name.getText()+"','"+Integer.parseInt(Price.getText())+"','"+Integer.parseInt(Serve.getText())+"','"+sdf.format(date)+"','"+0+"','"+0+"')");
					 JOptionPane.showMessageDialog(null, "Successfully Created! Load again to refresh!"
							 
							 );
					}
						
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonInsert.setBounds(126, 175, 89, 23);
		panel_1.add(buttonInsert);
		
		JButton button_1 = new JButton("Update");
		button_1.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e)throws java.lang.NumberFormatException  {
				String name,data;
				int Serve,Price;	
				
		
					try {
						/*	String query1 =  "select * from food1";
						pst = conn.prepareStatement(query1);
						rs = pst.executeQuery();
						int o=0;
						String id=null;
						while(rs.next()) {
							id=rs.getString("ID");
							if(o==table.getSelectedRow()) {
								break;
							}
							
						}*/
					    System.out.println("ROw "+table.getSelectedRow());
						//model.setRowCount(0);
						String query1 =  "select * from food1";
						pst = conn.prepareStatement(query1);
						rs = pst.executeQuery();
						int o=0;
						String id=null;
						while(rs.next()) {
							id=rs.getString("ID");
							if(o==table.getSelectedRow()) {
								break;
							}
							o++;
							
						}
						
				 //   String id=	JOptionPane.showInputDialog("Enter the ID Number that you want to Update ");
					String query = "select * from food1 where ID='"+id+"'";
					pst= conn.prepareStatement(query);	
					rs = pst.executeQuery();
					data= rs.getString("ID");
					name=rs.getString("Food");
					Price=rs.getInt("Price");
					Serve=rs.getInt("Serve");
					
					if(id.equals(data)) {
						JTextField ch = new JTextField();
						Object[] obj= { "User Found! \nEnter what you want to edit \n1. ID:"+data+"\n2. Name:"+name
								+"\n3. Price:"+Price+"\n4. Serve:"+Serve,ch};
					JOptionPane.showMessageDialog(null, obj);
					
					try{
						int choice =0;
						choice =Integer.parseInt(ch.getText());
					
					switch (choice) {
					
					
			
						
					case 1:
					String ID=	JOptionPane.showInputDialog("Enter the new ID");
					SQL="UPDATE food1 set ID=? where Food='"+name+"'";
						pst = conn.prepareStatement(SQL);					
						pst.setString(1, ID);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh !");
						read();
						break;
					case 2:
						String NAME=	JOptionPane.showInputDialog("Enter the new Name");
						SQL="UPDATE food1 set Food=? where ID='"+data+"'";
						pst = conn.prepareStatement(SQL);					
						pst.setString(1, NAME);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
						read();
						break;
					case 3:
						int Price1=	Integer.parseInt(JOptionPane.showInputDialog("Enter the new Price"));
						SQL="UPDATE food1 set Price=? where Food='"+name+"'";
						pst = conn.prepareStatement(SQL);					
						pst.setInt(1, Price1);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
						read();
						break;
					case 4:
					int Serve1= Integer.parseInt(JOptionPane.showInputDialog("Enter the new Serve "));
					SQL="UPDATE food1 set Serve=? where Food='"+name+"'";
						pst = conn.prepareStatement(SQL);					
						pst.setInt(1, Serve1);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Updated! Load again to refresh!");
						read();
						break;
				
					
					}
					}catch(Exception e3) {
						System.out.println(e3);
					}
					}
					else 
						JOptionPane.showMessageDialog(null, "Invalid Choice");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						
					}

			}
		});
		button_1.setBounds(238, 175, 89, 23);
		panel_1.add(button_1);
		
		JButton btnOrder = new JButton("ORDER");
		btnOrder.setBounds(125, 237, 89, 23);
		contentPane.add(btnOrder);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) throws NullPointerException {
				try {
				
					 read();
				}catch(Exception eq) {
					System.out.println(eq);
				}
				
			}
		});
		btnLoad.setBounds(10, 237, 89, 23);
		contentPane.add(btnLoad);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manage Default", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBackground(new Color(255, 255, 225));
		panel_2.setBounds(688, 11, 211, 216);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		changeRtextField = new JTextField();
		changeRtextField.setBounds(84, 44, 86, 20);
		panel_2.add(changeRtextField);
		changeRtextField.setColumns(10);
		
		JLabel lblRice = new JLabel("Rice");
		lblRice.setBounds(10, 47, 46, 14);
		panel_2.add(lblRice);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//System.out.println(changeRtextField.getText());
				//Order show = new Order();
				//show.txtP.setText(changeRtextField.getText());
				JOptionPane.showMessageDialog(null,"Rice Price Changed!!");
			}
		});
		btnSave.setBounds(81, 102, 89, 23);
		panel_2.add(btnSave);
		
		JButton btnQuery = new JButton("Show Sales");
		btnQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//OptionPane.showMessageDialog(null, );
				try {
					Sales show = new Sales();
					show.setVisible(true);
					dispose();
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		btnQuery.setBounds(357, 237, 111, 23);
		contentPane.add(btnQuery);
		
		JButton button_4 = new JButton("Query");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Query show = new Query();
				show.setVisible(true);
				dispose();
			}
		});
		button_4.setBounds(499, 237, 89, 23);
		contentPane.add(button_4);
		btnOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(changeRtextField.getText().equals("")) {
				Order show = new Order(Staffname);
				
				show.setVisible(true);
				dispose();}
				else{
					//int u =Integer.parseInt(changeRtextField.getText());
					Order show = new Order(Integer.parseInt(changeRtextField.getText()));
					
					show.setVisible(true);
					dispose();
					
				}
			}
		});
		model = (DefaultTableModel) table.getModel();
		
	
	
	}
	
	
	private void read() {
		try {
			
			model.setRowCount(0);
			String query =  "select * from food1";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Object[] row = {rs.getString("ID"),rs.getString("Food"),rs.getString("Price"),rs.getString("Serve")};
				model.addRow(row);
			}
			
			
		}catch(Exception e2) {
			System.out.println(e2);
		}
		model1.setRowCount(0);
		try {
			//Drinks.clear();
			linenum=0;
			j=0;
			lineTotal=0;
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			do
			{
				String line = br.readLine();

				if (line == null)
					break;

				StringTokenizer st = new StringTokenizer (line,",");
				while(st.hasMoreTokens()) {
					//System.out.println(st.nextToken()+st.nextToken()+st.nextToken()+st.nextToken()+st.nextToken());
					//
					/*Drinks [linenum][j]=st.nextToken();
					j++;
					Drinks [linenum][j]=st.nextToken();
					j++;
					Drinks1 [linenum][j]=Integer.parseInt(st.nextToken());
					j++;
					Drinks1 [linenum][j]=Integer.parseInt(st.nextToken());
					j++;
					Drinks [linenum][j]=st.nextToken();
					j++;
					Drinks1 [linenum][j]=Integer.parseInt(st.nextToken());
					j++;*/

					String ID=st.nextToken();
					j++;
					String name =st.nextToken();
					j++;
					int Price=Integer.parseInt(st.nextToken());
					j++;
					int size =Integer.parseInt(st.nextToken());
					j++;
					String unit = st.nextToken();
					j++;
					int qty =Integer.parseInt(st.nextToken());
					j++;
					
				
					String date = st.nextToken();
					j++;
					int sale =Integer.parseInt(st.nextToken());
					j++;
					int sold =Integer.parseInt(st.nextToken());
					j++;
					
					
					DrinksParent dr = new DrinksParent(ID,name,Price,size,unit,qty,date,sale,sold);
					List.add(dr);
					Object[] row = {ID,name,Price,size,unit,qty};
					model1.addRow(row);
					
				}
				j=0;
				linenum++;
				
			}
			while(true);
			lineTotal =linenum;
		}catch(Exception eq) {
			System.out.println(eq);
		}
	}
}
