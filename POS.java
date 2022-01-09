package PIT;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JPasswordField;


public class POS extends JFrame {

	private JPanel contentPane;
	private JTextField NametextField;
	 static File F =  new File ("d://PIT//staff.csv");
	 static FileReader fr ;
	 static BufferedReader Br;
	 static FileWriter fw;
	 static String staff [][] = new String [30][10];
	 static int linenum =0,j=0;
	 private JPasswordField passwordField;
	 //static boolean log = false;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fw = new  FileWriter(F,true);
					fr = new FileReader(F);
					Br = new BufferedReader(fr);
					
					 read();
					POS frame = new POS();
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
	public POS() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 228, 196));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Log In:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(SystemColor.info);
		panel.setBounds(24, 28, 384, 192);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Calibri", Font.BOLD, 17));
		lblUsername.setBounds(30, 47, 121, 14);
		panel.add(lblUsername);
		
		NametextField = new JTextField();
		NametextField.setBounds(140, 44, 205, 20);
		panel.add(NametextField);
		NametextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password\r\n");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 17));
		lblNewLabel.setBounds(30, 91, 90, 14);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Log in");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					
					read();
					String name = NametextField.getText();
					String pass =passwordField.getText();
					int  z =0 ;
					
					
					for(int i=0 ,y=0; i<=linenum;i++) {
						if(staff [i][0].equals(name)&&staff [i][1].equals(pass)) {
							
							chooseStaff  show = new  chooseStaff (name);
						 show.setVisible(true);
						 dispose();
						 fw.close();
						// System.exit(0);
						 break;
						}
						else if (i==linenum-1){
							
						JOptionPane.showMessageDialog(null, "Invalid Password/Username");
						}
						
						//System.out.println(i+" "+(linenum-1));
					}
					
					
					
					
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Password/Username");
				}
			}
		});
		btnNewButton.setBounds(140, 142, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Sign up");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0)  {
				JTextField user = new JTextField();
				JTextField pass = new JTextField();
				try {
					fw = new  FileWriter(F,true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Object[] add = {
						"Username ",user,
						"Password ",pass,
						
						
				};
				int o =JOptionPane.showConfirmDialog(null, add, null, JOptionPane.OK_CANCEL_OPTION);
				//System.out.println(o);
				if(o==0) {
					String user1 = user.getText();
					String pass1 = pass.getText();
					try {
						fw.write(user1+","+pass1);
						fw.append('\n');
						fw.flush();
						read();
						JOptionPane.showMessageDialog(null, "Username and Password saved");
						NametextField.setText("");
						passwordField.setText("");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		lblNewLabel_1.setBounds(328, 11, 46, 14);
		panel.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 88, 205, 20);
		panel.add(passwordField);
	}
	private static void read() {
		
		try {
			//linenum =0;
			//j=0;
			//System.out.println
		do{
			String line = Br.readLine();
			if(line==null) {
				break;
				
			}
				
				StringTokenizer st = new StringTokenizer(line,",");
				while(st.hasMoreTokens()) {
					staff[linenum][j]=st.nextToken();
					//System.out.println(staff[linenum][j]);
					j++;
				
			}
			
			j=0;
			linenum++;
			
		}while(true);
	}catch(Exception e) {}
	}
}
