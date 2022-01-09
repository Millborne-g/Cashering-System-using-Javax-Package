package PIT;

import java.awt.BorderLayout;




import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;



import javax.swing.event.ListSelectionEvent;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import javax.swing.JTextArea;
import java.awt.Button;
import javax.swing.event.PopupMenuListener;


import javax.swing.event.PopupMenuEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Order extends JFrame {
	
	
	private JPanel contentPane;
	static  JTextField txtP;
	ButtonGroup bg = new ButtonGroup();
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField txtSpecify;
	DefaultListModel model = new DefaultListModel();
	DefaultListModel model1 = new DefaultListModel();
	private JList list;
	//OrderParent OP = new OrderParent OP();
 static ArrayList  cntF = new ArrayList(); 
 static ArrayList  cntD = new ArrayList(); 
	public static ArrayList <OrderParent> List = new ArrayList<> (); 
	public static ArrayList <DrinksParent> List1 = new ArrayList<> (); 
	static ArrayList Saleget = new ArrayList(); 
	static ArrayList  SalegetD= new ArrayList(); 
	static ArrayList  QuntgetD= new ArrayList(); 
	static ArrayList  serveF= new ArrayList(); 
	static ArrayList  QuanF= new ArrayList(); 
	static ArrayList  PricegetD= new ArrayList(); 
	static ArrayList  QuanFPrice= new ArrayList();
	static ArrayList  Ricegetquant= new ArrayList();
	static ArrayList  RicegePrice= new ArrayList();
	static ArrayList  Riceget= new ArrayList();
	static ArrayList getFN =  new ArrayList();
	static ArrayList getIdD=  new ArrayList();
	static ArrayList getLeftquantD=  new ArrayList();
	static ArrayList totalquanD=  new ArrayList();
	static ArrayList getSOlD=  new ArrayList();
	String OR = "d://PIT//OR.txt";
	Date date = new Date();
	String fmt = "yy-MM-dd HH:mm:ss";
	SimpleDateFormat sdf = new SimpleDateFormat(fmt);
	
	static int priceR;
	//JCheckBox chckbxTakeOut = new JCheckBox("Take out");
	Connection conn;
	PreparedStatement pst;
	Statement stmt;
	ResultSet rs;
	private JTextField ServetextField_2;
	private JTextField PricetextField_2;
	HashMap hash = new HashMap();
	private JTextField ServeEtextField;
	private JLabel lblServe_1;
	private JTextField TotaltextField = new JTextField("P"+0+".0");
	static int finalPR ;
	static int SlPR=0; ;
	static File f = new File("d://PIT//Drinks.csv");
	FileReader fr;
	BufferedReader br;
	private JTextField PriceDtextField_1;
	private JTextField QtyBtextField_2;
	
	static String[][] Drinks = new String[20][30];
	static int[][] Drinks1 = new int[20][30];
	static int linenum =0,linenum1=0,lineTotal=0,lineTotal1=0;
	static int  j =0;
	JComboBox comboBox ;
	static String fs = "d://PIT//SalesFood.csv";
	static String fsD = "d://PIT//SalesDrinks.csv";
	static FileWriter fw ;
	static String staffName;
	private JLabel lblSize_1;
	private JTextField SizetextField;
	private JTextField EQuanttextField;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	int riceP;
	int checkna=0 ;
	
	
	String [][] List1String = new String[20][30]; 
	int [][] List1Int = new int[20][30]; 
	int lineD =0;;
	
	int[] getSale = new int [30];
	int[] getSold = new int [30];
	int cntGS=0;
	
	chooseStaff CT = new chooseStaff();
	private JLabel lblP;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fw =new FileWriter(f,true);
					Order frame = new Order();
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
	private void read() throws IOException {
		try {
			model.clear();
			model1.clear();
			List.clear();
			//list.isSelectionEmpty();
			String query =  "select * from food1";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			//System.out.println(rs.getString("ID"));
			 String ID;
			 String Food;
			 int serve;
			 int Price;
			 int sold;
			 int sales;
			while(rs.next()) {
				  ID =rs.getString("ID");
				  Food=rs.getString("food");
				 serve=Integer.parseInt(rs.getString("Serve"));
				 Price=Integer.parseInt(rs.getString("Price"));
				 sales =Integer.parseInt(rs.getString("Sales"));
				 sold =Integer.parseInt(rs.getString("Sold"));
				//hash.put(rs.getString("food"),rs.getString("Price"));
				// System.out.println("ID read "+ID);
				 
					model.addElement("          "+Food+"          ");
				OrderParent Op = new OrderParent( ID, Food,serve, Price,sales,sold);
				List.add(Op);
			
			}
			
		}catch(Exception e) {
			//System.out.println("tangina"+e);
		}
			
		try {
			//Drinks.clear();
			int cny=0;
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
					int sales =Integer.parseInt(st.nextToken());
					j++; 
					int sold =Integer.parseInt(st.nextToken());
					j++; 
					
					
					DrinksParent dr = new DrinksParent(ID,name,Price,size,unit,qty,date,sales,sold);
					List1.add(dr);
					model1.addElement("            "+List1.get(cny).name+"           \n");
					List1Int[ lineD][0]=qty;
					totalquanD.add(List1Int[ lineD][0]);
				//	System.out.println("Drinks qty"+List1Int[ lineD][0]);
					//Object[] row = {ID,name,Price,size,unit,qty};
					
					
				}
				lineD++;
				cny++;
				j=0;
				linenum1++;
				
			}
			while(true);
			lineTotal1 =linenum1;
		}catch(Exception eq) {
			//System.out.println(eq);
		}
		
	}
	
	
	public Order(){
		
		try {
			List1.clear();
			List.clear();
		conn = dbConnection();
			read() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize() ;
		//priceR= 8;
		if(priceR==0) {
			txtP.setText(""+8);
			}
			else {
				txtP.setText(""+priceR);
			}
			
		
		
	
	}
public Order(String name){
		
		try {
			staffName=name;
			List1.clear();
			List.clear();
		conn = dbConnection();
			read() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize() ;
		//priceR= 8;
		if(priceR==0) {
			txtP.setText(""+8);
			}
			else {
				txtP.setText(""+priceR);
			}
			
		
		
	
	}
	
	public Order(int  D){
		
		try {
			//txtP.setText(""+D);
			//System.out.println(D);
			//riceP=D
			List1.clear();
			List.clear();
		conn = dbConnection();
			read() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize() ;
		priceR=D;
		if(priceR==0) {
		txtP.setText(""+8);
		}
		else {
			txtP.setText(""+priceR);
		}
		
	
	}
	
	

	public Order(int i, int b)  
	{
		Payment Payment = new Payment("t");
		write1();
		Payment.setVisible(true);
		Payment.textFieldTotalAmt.setText("" + SlPR);		
		dispose();
		
	}
	
	/*public Order(String staff)
	{
		
		try {
			staffName =staff;
		setResizable(false);
		conn = dbConnection();
			read();
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}*/
	
	public Order(int i, boolean b ,int check)  
	{
		checkna = check;
		//System.out.println("back Again");
		if (b == true)
		{
			
			
			try {
				SlPR = i;
				conn = dbConnection();
			//	System.out.println("list1 "+List.size());
			write();
			serveF.clear();
			List.clear();
			List1.clear();
			//conn = dbConnection();
				read();
				initialize();
				
				if(priceR==0) {
					txtP.setText(""+8);
					}
					else {
						txtP.setText(""+priceR);
					}
					//txtP.setText(""+priceR);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else
		{
			conn = dbConnection();
			write();
		//	write1();
			System.exit(0);
		}
	}
	
	public Order(boolean a) throws IOException
	{
		
		List.clear();
		List1.clear();
		conn = dbConnection();
		read();
		initialize();
		if(priceR==0) {
			txtP.setText(""+8);
			}
			else {
				txtP.setText(""+priceR);
			}
			//txtP.setText(""+priceR);
	}
	
	public void write()
	{
		//ff = new FileWriter(file,true);
		
		try
		{
			String SQL;
		
			for(int p =0 ; p<getFN.size();p++) {
		    SQL="UPDATE food1 set Serve=? where Food='"+getFN.get(p)+"'";
			pst = conn.prepareStatement(SQL);					
			pst.setInt(1, (int) serveF.get(p));
			pst.executeUpdate();
			
			   SQL="UPDATE food1 set Sales=? where Food='"+getFN.get(p)+"'";
				pst = conn.prepareStatement(SQL);					
				pst.setInt(1,(int) Saleget.get(p));
				pst.executeUpdate();
			
				  SQL="UPDATE food1 set Sold=? where Food='"+getFN.get(p)+"'";
					pst = conn.prepareStatement(SQL);					
					pst.setInt(1, (int) getSOlD.get(p));
					pst.executeUpdate();
			
			}
			
			
			fw = new FileWriter(f);
			//for(int k =0 ; k <lineD; k ++) {
				//System.out.println("quant d "+List1Int[k][0]);
			//}
			
			for(int y=0; y<List1.size();y++) {
				
		  
				  
				//	System.out.println("wala  ..........");
				//	fw.write(List1.get(y).id+","+List1.get(y).name+","+List1.get(y).price+","+List1.get(y).size+","+List1.get(y).unit+","+  totalquanD.get(y));
					fw.write(List1.get(y).id+","+List1.get(y).name+","+List1.get(y).price+","+List1.get(y).size+","+List1.get(y).unit+","+List1.get(y).qty+","+List1.get(y).date+","+List1.get(y).sales+","+List1.get(y).sold);
					
					fw.append('\n');
					fw.flush();
			//	}
				
			
			}
			totalquanD.clear();
			
		
			getFN.clear();
			serveF.clear();
			//fw.close();
			write1() ;
		}
		
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}
	
	
	
	public void write1() throws NullPointerException {
		Payment Payment = new Payment(  );
	
		//System.out.println("Totalllll  "+ Integer.parseInt(Payment.textFieldTotalAmt.getText()));
		try {

		fw = new FileWriter(fs,true);
		String  k1 = "" ;
		 if(checkna==1) {
			k1= "True";
		 }
		 else {
			
				 k1= "false";
			 
		 }

		//Payment Payment = new Payment();
	for (int z = 0; z < cntF.size(); z ++)
	{
		
				int y =(int) cntF.get(z) ;
				//System.out.println(y);
			 fw.write(List.get((int) cntF.get(z)).ID+ "," +List.get((int) cntF.get(z)).Food+ "," +sdf.format(date)+ "," +Saleget.get(z)+ "," +QuanFPrice.get(z)+ "," +k1+ "," +staffName+"\n");
			 fw.flush(); 
			
	}  
	
	fw = new FileWriter(fsD,true);
	for (int z = 0; z < cntD.size(); z ++)
	{
		
	
			 fw.write(List1.get((int) cntD.get(z)).id+ "," +List1.get((int) cntD.get(z)).name+ "," +sdf.format(date)+ "," +SalegetD.get(z)+ "," +PricegetD.get(z)+ "," +k1+ "," +staffName+"\n");
			 fw.flush(); 
	}  
	
	fw = new FileWriter("D://Games//RiceSales.csv",true);
	for (int z = 0; z < Riceget.size(); z ++)
	{
		
			 fw.write(Riceget.get(z)+","+Ricegetquant.get(z)+","  +RicegePrice.get(z)+ "," +sdf.format(date)+ "," +k1+ "," +staffName+"\n");
			 fw.flush(); 
		
	
			// fw.write(List1.get((int) cntD.get(z)).id+ "," +List1.get((int) cntD.get(z)).name+ "," +sdf.format(date)+ "," +SalegetD.get(z)+PricegetD.get(z)+ "," +k1+ "," +staffName+"\n");
			// fw.flush(); 
	}  
	
	
	
	
	
	
	
	try {
		fw = new FileWriter(OR,true);
		fw = new FileWriter(OR);
	    PrintWriter printWriter = new PrintWriter(fw);
	    //New line
		//Payment Payment = new Payment();
	 fw.write("       \n"	            );
	 printWriter.println();  
	
	// fw.flush();
	 fw.write( "      MILLY's BUILD YOUR MEAL       \n");
	
	 printWriter.println();  
	// fw.flush();
	 fw.write("      "+date.toString()+"           ");
	 printWriter.println();  
	 fw.write( "************************************\n");
	 printWriter.println();  
	// fw.flush();
	 fw.write( "   Food         QTY        Price \n");
	 printWriter.println();  
	// fw.flush();
	 fw.write( "************************************\n");
	 printWriter.println();  
	 fw.flush();
	 String DT =null;
	 if(checkna==1) {
		 DT = "Take Out";
	 }
	 else {
		
			 DT = "Dine in";
		 
	 }
	 fw.write( DT+"\n");
	 printWriter.println();  
	for (int z = 0; z < cntF.size(); z ++)
	{
		
			//	int y =(int) cntF.get(z) ;
			//	System.out.println(y);
			// fw.write(List.get((int) cntF.get(z)).ID+ "," +List.get((int) cntF.get(z)).Food+ "," +sdf.format(date)+ "," +Saleget.get(z)+"\n");
			// fw.flush(); 
			 String na = List.get((int) cntF.get(z)).Food;
			 int q = (int) QuanF.get(z);
				int k=	 (int) QuanFPrice.get(z);
				 fw.write("       \n"
						+na+"        "+q+"      "  +k+"\n");
				 printWriter.println();  
	}  
	
	//fw = new FileWriter(fsD,true);
	for (int z = 0; z < cntD.size(); z ++)
	{
	
			// fw.write(List1.get((int) cntF.get(z)).id+ "," +List1.get((int) cntF.get(z)).name+ "," +sdf.format(date)+ "," +SalegetD.get(z)+"\n");
			// fw.flush(); 
		String na = List1.get((int) cntD.get(z)).name;
		 int q = (int)QuntgetD.get(z);
			int k=	 (int)PricegetD.get(z);
			 fw.write("       \n"
					+na+"        "+q+"      "  +k+"\n");
			 printWriter.println();  
			 
	}  
	
	
	for(int u = 0 ; u<Riceget.size();u++) {
		 fw.write("       \n"
					+Riceget.get(u)+"        "+Ricegetquant.get(u)+"      "  +RicegePrice.get(u)+"\n");
			 printWriter.println();  
	}
	
	
	 fw.write("       \n");
	 printWriter.println();  
	 fw.write("************************************\n");
	 printWriter.println();  
	 fw.write("   Total                   P"+SlPR+".0");
                printWriter.println();  
	fw.flush();
	Ricegetquant.clear();
	RicegePrice.clear();
	Riceget.clear();;
	JOptionPane.showMessageDialog(null, "Official Receipt is located at "+ OR);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}
	
	
	
	
	Receipt();
	
	QuanF.clear();
	QuanFPrice.clear();
	cntF.clear();
	cntD.clear();
	Saleget.clear();
	SalegetD.clear();
	QuntgetD.clear();
	PricegetD.clear();
	getSOlD.clear();
	Riceget.clear();
	
	getIdD.clear();
	PricegetD.clear();
	QuntgetD.clear();
   SalegetD.clear();
   getLeftquantD.clear();
   
	cntF.clear();
	getFN.clear();
	Saleget.clear();
	serveF.clear();
	QuanF.clear();
	QuanFPrice.clear();
	//List.clear();
	//arr.clear();
	//System.out.println("array size "+arr.size());
	//ff.flush();
	SlPR =0 ;
}catch(Exception e) {
	e.printStackTrace();
}
}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 385);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 196));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Image img = new ImageIcon (this.getClass().getResource("/Wood Desktop Wallpapers for HD Widescreen and Mobile.jpg")).getImage();
	//	Image img = new ImageIcon (this.getClass().getResource("download.jpg")).getImage();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, " ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(SystemColor.info);
		panel.setBounds(289, 11, 240, 135);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtP = new JTextField();
		txtP.setText(CT.changeRtextField.getText());
		txtP.setBackground(new Color(253, 245, 230));
		txtP.setBounds(129, 18, 68, 20);
		panel.add(txtP);
		txtP.setColumns(10);
		
		JLabel lblCups = new JLabel("Cups");
		lblCups.setBounds(26, 47, 33, 14);
		panel.add(lblCups);
		
		comboBox = new JComboBox();
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				try {
					//comboBox
					txtSpecify.setText(""+(comboBox.getSelectedIndex()));
					
					
				}catch(Exception e) {}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			}
		});
		comboBox.setBounds(119, 44, 78, 20);
		panel.add(comboBox);
		for(int i=0; i<3 ;i++) {
			comboBox.addItem(i);
		}
		
		txtSpecify = new JTextField();
	
		txtSpecify.setBounds(61, 44, 48, 20);
		panel.add(txtSpecify);
		txtSpecify.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				int Piri = Integer.parseInt(txtSpecify.getText());;
				int RiceQty = 0;
				//if(Integer.parseInt(txtSpecify.getText())!=0) {
					
			//	}
				
				//System.out.println("Combox "+comboBox.getSelectedIndex());
				//if(txtSpecify.getText()!=null) {
				///	Piri = Integer.parseInt(txtSpecify.getText());
				//}
			//	else
				//Piri = comboBox.getSelectedIndex();
				///if(rdbtnRice.isSelected()) {
					
			    Piri *=Integer.parseInt(txtP.getText()); 
			  //  if(Integer.parseInt(txtP.getText())>0||)
					
				//}
			
				//System.out.println(Piri);
			
			
				//ServetextField_2.setText(""+List.get(y).serve);
				if(Integer.parseInt(txtSpecify.getText())>0) {
				SlPR+=Piri;
				TotaltextField.setText("P"+SlPR+".0");
				Ricegetquant.add(Integer.parseInt(txtSpecify.getText()));
			RicegePrice.add(Piri);
			Riceget.add("Rice");
			//System.out.println(Piri);
			//System.out.println("na save na "+Integer.parseInt(txtSpecify.getText()));
			JOptionPane.showMessageDialog(null,"Order successfully added for checkout!","Add for Checkout",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAdd.setBounds(162, 101, 68, 23);
		panel.add(btnAdd);
		
		lblP = new JLabel("P");
		lblP.setBounds(119, 21, 17, 14);
		panel.add(lblP);
		
		JLabel lblRice = new JLabel("Rice");
		lblRice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRice.setBounds(63, 21, 46, 14);
		panel.add(lblRice);
		//receipt() ;
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, " ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(SystemColor.info);
		panel_1.setBounds(10, 157, 519, 147);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 385, 95);
		panel_1.add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				list.setSelectedIndex(-1);
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				int  h = list.getSelectedIndex();
				
			//hghgh
				
			
				try {	
					String query =  "select * from food1";
					pst = conn.prepareStatement(query);
					rs = pst.executeQuery();
					//System.out.println(rs.getString("ID"));
					 String ID;
					 String Food;
					 int serve;
					 int Price;
					 int c=0;
					while(rs.next()) {
						  ID =rs.getString("ID");
						  Food=rs.getString("food");
						 serve=Integer.parseInt(rs.getString("Serve"));
						 Price=Integer.parseInt(rs.getString("Price"));
						//hash.put(rs.getString("food"),rs.getString("Price"));
						// System.out.println("ID read "+ID);
						 
							//model.addElement("          "+ID+"           ");
						//OrderParent Op = new OrderParent( ID, Food,serve, Price);
						//List.add(Op);
						 if(c==h) {
					PricetextField_2.setText(" P"+List.get(h).Price+".0");
					ServetextField_2.setText(""+ List.get(h).serve);
					}
					c++;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		list.setForeground(Color.BLACK);
		list.setBackground(new Color(253, 245, 230));
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setModel(model);
		
		JLabel lblServe = new JLabel("Available Serve: ");
		lblServe.setBounds(10, 120, 97, 14);
		panel_1.add(lblServe);
		
		ServetextField_2 = new JTextField();
		ServetextField_2.setEditable(false);
		ServetextField_2.setBounds(103, 117, 86, 20);
		panel_1.add(ServetextField_2);
		ServetextField_2.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(221, 120, 39, 14);
		panel_1.add(lblPrice);
		
		PricetextField_2 = new JTextField();
		PricetextField_2.setEditable(false);
		PricetextField_2.setColumns(10);
		PricetextField_2.setBounds(270, 117, 86, 20);
		panel_1.add(PricetextField_2);
		
		JButton btnNewButton = new JButton("ADD");
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
			
			
				int y = list.getSelectedIndex();
				// SlPR += SlPR ;
				//TotaltextField.setText(""+0);
				//SlPR+=Integer.parseInt(TotaltextField.getText());
				//System.out.println("ID + "+List.get(y).ID);
				List.get(y).serve-=Integer.parseInt(ServeEtextField.getText());
				List.get(y).Sales += Integer.parseInt(ServeEtextField.getText());
				
			    List.get(y).date = sdf.format(date);
				//System.out.println("date 2 + "+ y);
				finalPR=List.get(y).Price*Integer.parseInt(ServeEtextField.getText());
				//System.out.println("sales + "+ List.get((int) cntF.get(y)).Sales );
				
				List.get(y).sold+=finalPR;
				SlPR+=finalPR;
				List.get(y).quanget = Integer.parseInt(ServeEtextField.getText());
				//System.out.println("Final pr "+finalPR);
				JOptionPane.showMessageDialog(null,"Order successfully added for checkout!","Add for Checkout",JOptionPane.INFORMATION_MESSAGE);
				ServetextField_2.setText(""+List.get(y).serve);
				TotaltextField.setText("P"+SlPR+".0");
				
				cntF.add(y);
				getFN.add(List.get(y).Food);
				Saleget.add(List.get(y).Sales);
				serveF.add(List.get(y).serve);
				QuanF.add(Integer.parseInt(ServeEtextField.getText()));
				QuanFPrice.add(finalPR);
				ServeEtextField.setText("");
				getSOlD.add(List.get(y).sold);
				///getSold[cntGS]=List.get(y).sold;
				
				//System.out.println(" serve diplay "+List.get(y).serve);
				
				//for(int e =0 ; e<List.size();e++) {
					//System.out.println(" serve diplay "+	serveF.get(y));
				//}
				//FoodTot.add(y);
				
				//receipt();
				cntGS++;
			}catch(Exception e) {
				//System.out.println(" yow"+Integer.parseInt(TotaltextField.getText()));
			}
				}
		});
		btnNewButton.setBounds(405, 42, 104, 23);
		panel_1.add(btnNewButton);
		
		ServeEtextField = new JTextField();
		ServeEtextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
			try {	int y = list.getSelectedIndex();
				if(Integer.parseInt(ServeEtextField.getText())<0) {
					TotaltextField.setText("Too Low");
				}
				else if (Integer.parseInt(ServeEtextField.getText())>List.get(y).serve) {
					TotaltextField.setText("Too Much");
				}
				else {
					//SlPR+=finalPR;
					TotaltextField.setText("P"+SlPR+".0");
				}
				}catch(Exception e) {
					
					
				}
			}
		});
		ServeEtextField.setBounds(441, 11, 68, 20);
		panel_1.add(ServeEtextField);
		ServeEtextField.setColumns(10);
		
		lblServe_1 = new JLabel("Serve");
		lblServe_1.setBounds(405, 12, 39, 14);
		panel_1.add(lblServe_1);
		
		JButton btnNewButton_2 = new JButton("Payment");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Payment Payment = new Payment(  );
				if (SlPR <= 0)
				{
					JOptionPane.showMessageDialog(null,"Add a product first",null, JOptionPane.ERROR_MESSAGE);
					return;
				}
			
				
				Payment.setVisible(true);
				Payment.textFieldTotalAmt.setText("" + SlPR);		
				dispose();
				//txtP
			//	PricetextField_2.setText(""+txtP);
			}
		});
		btnNewButton_2.setBounds(405, 110, 104, 23);
		panel_1.add(btnNewButton_2);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(405, 76, 32, 14);
		panel_1.add(lblTotal);
		
		
		//TotaltextField.setText();
		TotaltextField.setEditable(false);
		TotaltextField.setBounds(441, 73, 68, 20);
		panel_1.add(TotaltextField);
		TotaltextField.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Drinks", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBackground(SystemColor.info);
		panel_2.setBounds(10, 11, 269, 135);
		contentPane.add(panel_2);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 163, 74);
		panel_2.add(scrollPane);
		
		JList list_1 = new JList();
		list_1.setBackground(new Color(253, 245, 230));
		scrollPane.setViewportView(list_1);
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				int y = list_1.getSelectedIndex();
				PriceDtextField_1.setText("P"+List1.get(y).price+".0");
				QtyBtextField_2.setText(""+List1.get(y).qty);
				SizetextField.setText(""+List1.get(y).size+" "+List1.get(y).unit);
			}
		});
		list_1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list_1.setLayoutOrientation(JList.VERTICAL_WRAP);
		list_1.setVisibleRowCount(-1);
		list_1.setModel(model1);
		
		JLabel lblPrice_1 = new JLabel("Price:");
		lblPrice_1.setBounds(183, 82, 35, 14);
		panel_2.add(lblPrice_1);
		
		PriceDtextField_1 = new JTextField();
		PriceDtextField_1.setEditable(false);
		PriceDtextField_1.setBounds(219, 76, 40, 20);
		panel_2.add(PriceDtextField_1);
		PriceDtextField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Qty:");
		lblNewLabel_1.setBounds(183, 105, 27, 14);
		panel_2.add(lblNewLabel_1);
		
		QtyBtextField_2 = new JTextField();
		QtyBtextField_2.setEditable(false);
		QtyBtextField_2.setBounds(219, 102, 40, 20);
		panel_2.add(QtyBtextField_2);
		QtyBtextField_2.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("ADD");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int y = list_1.getSelectedIndex();
				//int y = list.getSelectedIndex();
				//int y = List1.
			
				try {
					
					int p= 1;
					
					//int y = list.getSelectedIndex();
					// SlPR += SlPR ;
					//TotaltextField.setText(""+0);
					//SlPR+=Integer.parseInt(TotaltextField.getText());
					//System.out.println(" yow"+Integer.parseInt(TotaltextField.getText())); 
					getLeftquantD.add(List1.get(y).qty);
					int t =List1.get(y).qty - Integer.parseInt(EQuanttextField.getText());
					List1.get(y).qty=t;
					List1.get(y).sales += Integer.parseInt(EQuanttextField.getText());
					List1.get(y).date = sdf.format(date);
					finalPR=List1.get(y).price*Integer.parseInt(EQuanttextField.getText());
				//	System.out.println("date "+List1.get(y).date );
					List1.get(y).sold = finalPR;
					SlPR+=finalPR;
					//List.get(y).quanget = Integer.parseInt(ServeEtextField.getText());
					//System.out.println("Final pr "+finalPR);
					JOptionPane.showMessageDialog(null,"Order successfully added for checkout!","Add for Checkout",JOptionPane.INFORMATION_MESSAGE);
					QtyBtextField_2.setText(""+List1.get(y).qty);
					TotaltextField.setText("P"+SlPR+".0");
					cntD.add(y);
					getIdD.add(List1.get(y).id);
					PricegetD.add(finalPR);
					QuntgetD.add(Integer.parseInt(EQuanttextField.getText()));
				   SalegetD.add(List1.get(y).sales);
				 
				   
				
				   
				   List1Int[y][0]=List1.get(y).qty; 
				  // System.out.print( 	"quant drinks  "+  List1Int[y][0]);
				 // for(int u =0 ; u<p;u++){
					//   System.out.println (""+totalquanD.size());
					 //  = List1.get(y).qty;
				  // }
				   int k= List1.get(y).qty;
				  totalquanD.set(y,List1.get(y).qty);
					//FoodTot.add(y);
					
					//receipt();
					
				}catch(Exception e) {
					//System.out.println(" drinks"+e);
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(10, 101, 60, 23);
		panel_2.add(btnNewButton_1);
		
		lblSize_1 = new JLabel("Size:");
		lblSize_1.setBounds(183, 54, 27, 14);
		panel_2.add(lblSize_1);
		
		SizetextField = new JTextField();
		SizetextField.setEditable(false);
		SizetextField.setColumns(10);
		SizetextField.setBounds(219, 51, 42, 20);
		panel_2.add(SizetextField);
		
		JLabel lblEnterQuant = new JLabel("Quant");
		lblEnterQuant.setBounds(176, 25, 42, 14);
		panel_2.add(lblEnterQuant);
		
		EQuanttextField = new JTextField();
		EQuanttextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {	int y = list_1.getSelectedIndex();
				if(Integer.parseInt(EQuanttextField.getText())<0) {
					TotaltextField.setText("Too Low");
				}
				else if (Integer.parseInt(EQuanttextField.getText())>List1.get(y).qty) {
					TotaltextField.setText("Too Much");
				}
				else {
					//SlPR+=finalPR;
					TotaltextField.setText("P"+SlPR+".0");
				}
				}catch(Exception e) {
					
					
				}
			}
		});
		EQuanttextField.setBounds(213, 22, 46, 20);
		panel_2.add(EQuanttextField);
		EQuanttextField.setColumns(10);

		JButton btnCheckCart = new JButton("Check Orders");
		btnCheckCart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DefaultListModel model2 = new DefaultListModel();
				JList	list2 = new JList();
				list2.setModel(model2);
				for(int h= 0; h<cntF.size();h++){
					model2.addElement(List.get((int) cntF.get(h)).Food+ "           P "+ QuanFPrice.get(h));
				}
				for(int h= 0; h<cntD.size();h++){
					model2.addElement(List1.get((int) cntD.get(h)).name+ "          P "+PricegetD.get(h)  );
				}
				for(int h= 0; h<Riceget.size();h++){
					model2.addElement(Riceget.get(h) + "             P "+  RicegePrice.get(h));
				}
				
				Object[] li = {"                Your Orders ",
						"   You can select to remove",
						list2,
						"Total:                 "+SlPR+"     "
				};
				
				int ch = JOptionPane.showConfirmDialog(null, li,null,JOptionPane.OK_CANCEL_OPTION);
			if(ch==0) {
			String y = (String) list2.getSelectedValue();
			for(int h= 0; h<cntF.size();h++){
				if(y.equals(List.get((int) cntF.get(h)).Food+ "           P "+ QuanFPrice.get(h))) {
				List.get((int) cntF.get(h)).serve+=(int)QuanF.get(h);
				SlPR-=(int )QuanFPrice.get(h);
				getFN.remove(h);
				Saleget.remove(h);
				serveF.remove(h);
			
				QuanFPrice.remove(h);
				
				//ServeEtextField.setText("");
				//System.out.println(" serve diplay "+List.get(y).serve);
				//ServeEtextField.setText("");
				
				ServetextField_2.setText(""+	List.get((int) cntF.get(h)).serve);
				QuanF.remove(h);
				cntF.remove(h);
				getSOlD.remove(h);
				TotaltextField.setText("P"+SlPR+".0");
				//model2.addElement(List.get((int) cntF.get(h)).Food+ "           P "+ QuanFPrice.get(h));
			}
			}
			
			
			
			for(int h= 0; h<cntD.size();h++){
				
				if(y.equals(List1.get((int) cntD.get(h)).name+ "          P "+PricegetD.get(h)  )) {
					List1.get((int) cntD.get(h)).qty+=(int )QuntgetD.get(h);
					//ntD.add(y);
					SlPR-=(int )PricegetD.get(h);
					getIdD.remove(h);
					PricegetD.remove(h);
					
				   SalegetD.remove(h);
				   totalquanD.set(h, getLeftquantD.get(h));
					//int k=100;
					
					getLeftquantD.remove(h);
					
					
					
					//System.out.println("my list quant "+List1.get((int) cntD.get(h)).qty);
					QtyBtextField_2.setText(""+List1.get((int) cntD.get(h)).qty);
					TotaltextField.setText("P"+SlPR+".0");
					
					cntD.remove(h); 
					QuntgetD.remove(h);
					//k-=(int)PricegetD.get(h);
				//	System.out.println("minusan "+k);
					//System.out.println("you select "+y);
					
				}
				//model2.addElement(List1.get((int) cntD.get(h)).name+ "          P "+PricegetD.get(h)  );
			}
			
			
			for(int h= 0; h<Riceget.size();h++){
				if(y.equals(Riceget.get(h) + "             P "+  RicegePrice.get(h))) {
					SlPR-=(int )RicegePrice.get(h);
				Ricegetquant.remove(h);
				RicegePrice.remove(h);
				Riceget.remove(h);
				TotaltextField.setText("P"+SlPR+".0");
			//	model2.addElement(Riceget.get(h) + "             P "+  RicegePrice.get(h));
			}
			}
			//System.out.println("select "+y);
			
		//	for
				JOptionPane.showMessageDialog(null, "Selected Order is Sucessfully Deleted");
				
			}
			else {
				System.out.println("my ch "+ch);
			}
			}
		});
		btnCheckCart.setBounds(313, 315, 113, 23);
		contentPane.add(btnCheckCart);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chooseStaff show = new chooseStaff();
				show.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(10, 315, 89, 23);
		contentPane.add(btnBack);
		
	}
	
	
	private void Receipt() {

		
	}
}

