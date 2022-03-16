import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.border.EtchedBorder;

/**
 * <p>
 * JavaCrud is a basic system that implements the CRUD (Create, Read, Update, Delete) operations on a subset of 
 * personal information data:<br>
 * 1. Family Name, First Name, Middle Name, Suffix<br>
 * 2. Birth date<br>
 * 3. Sex<br>
 * 4. Social Security Number<br>
 * 5. Nationality<br>
 * 6. Current Address<br>
 * Uses MySQL Connector/J, a driver that implements the Java Database Connectivity (JDBC) API, 
 * to access, save and delete data in a database (MySQL).<br>
 * Uses rs2xml.jar, a jar file and a java library that makes JTable manipulation a bit easier, for viewing of SQL Table.
 * </p>
 * @author George Jose P. Montano ITCC 11.1 - B3
 * @version 1
 * @since March 16, 2022
 */
public class JavaCrud implements ActionListener, KeyListener{

	private JFrame frame;
	private JTextField txtFName;
	private JTextField txtMName;
	private JTextField txtLName;
	private JTextField txtSuffix;
	private JTextField txtSocialNum;
	private JTextField txtNationality;
	private JTextField txtAddress;
	private JTextField txtYear;
	private JTextField txtMobileNum;
	private JTextField txt_search;
	
	// Declaration of component variables
	JPanel info_panel, search_panel;
	JLabel PIS, fname, mname, lname, suffix, sex, ssn, nationality, c_address, dob, mo, day, year, mobile_num, num_search;
	JRadioButton maleRadio, femaleRadio;
	JButton add_btn, clr_btn, del_btn, update_btn, how_btn;
	JComboBox monthBox, dayBox;
	String id, first_name, mid_name, last_name, suffix_str, sex_str, month_str, nationality_str, address_str, 
	day_str, year_str, mobile_str, ssn_str;

	/**
	 * Main method to launch the application.
	 * @param args contains the supplied command-line arguments as an array of String objects.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Default constructor to create the application. 
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTable table;
	
	/**
	 * Connect to the database using MySQL Connector/J.
	 */
	public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pis", "root","password"); // database + user + password
        }
        catch (ClassNotFoundException ex){
          ex.printStackTrace();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
	
	/**
	 * Load the table
	 */
	public void table_load() {
		try 
    	{
	    pst = con.prepareStatement("select * from personal_info");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	} 
    	catch (SQLException e) 
    	 {
    		e.printStackTrace();
	  } 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 709, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		// JPanels
		info_panel = new JPanel();
		info_panel.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_panel.setBounds(10, 41, 334, 343);
		info_panel.setLayout(null);

		search_panel = new JPanel();
		search_panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search by ID", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		search_panel.setBounds(354, 319, 315, 65);
		search_panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(354, 47, 315, 263);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
		// JLabels
		PIS = new JLabel("Personal Information System");
		PIS.setFont(new Font("Arial", Font.BOLD, 16));
		PIS.setBounds(82, 11, 238, 14);
		
		fname = new JLabel("First Name");
		fname.setBounds(10, 36, 79, 14);
		
		mname = new JLabel("Middle Name");
		mname.setBounds(10, 61, 79, 14);
		
		lname = new JLabel("Last Name");
		lname.setBounds(10, 86, 79, 14);
		
		suffix = new JLabel("Suffix");
		suffix.setBounds(10, 110, 51, 14);
		
		sex = new JLabel("Sex");
		sex.setBounds(10, 135, 51, 14);
		
		ssn = new JLabel("Social Security #");
		ssn.setBounds(10, 226, 139, 14);
				
		nationality = new JLabel("Nationality");
		nationality.setBounds(10, 251, 109, 14);
		
		c_address = new JLabel("Current Address");
		c_address.setBounds(10, 276, 109, 14);
		
		day = new JLabel("Day");
		day.setBounds(135, 187, 30, 14);
		
		year = new JLabel("Year");
		year.setBounds(212, 187, 30, 14);
		
		mobile_num = new JLabel("Mobile Number");
		mobile_num.setBounds(10, 301, 109, 14);
		
		dob = new JLabel("Date of Birth");
		dob.setBounds(10, 158, 79, 14);
		
		mo = new JLabel("Month");
		mo.setBounds(17, 187, 44, 14);
		
		num_search = new JLabel("Number");
		num_search.setBounds(10, 24, 46, 14);
		
		// TextFields
		txtFName = new JTextField();
		txtFName.setColumns(10);
		txtFName.setBounds(140, 33, 184, 20);
		
		txtMName = new JTextField();
		txtMName.setColumns(10);
		txtMName.setBounds(140, 58, 184, 20);
		
		txtLName = new JTextField();
		txtLName.setColumns(10);
		txtLName.setBounds(140, 83, 184, 20);
		
		txtSuffix = new JTextField();
		txtSuffix.setColumns(10);
		txtSuffix.setBounds(140, 107, 184, 20);
		
		txtSocialNum = new JTextField();
		txtSocialNum.setColumns(10);
		txtSocialNum.setBounds(140, 223, 184, 20);
		
		txtNationality = new JTextField();
		txtNationality.setColumns(10);
		txtNationality.setBounds(140, 248, 184, 20);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(140, 273, 184, 20);
		
		txtYear = new JTextField();
		txtYear.setBounds(241, 184, 83, 20);
		txtYear.setColumns(10);
		
		txtMobileNum = new JTextField();
		txtMobileNum.setColumns(10);
		txtMobileNum.setBounds(140, 298, 184, 20);
		
		txt_search = new JTextField();
		txt_search.setBounds(65, 21, 240, 20);
		txt_search.setColumns(10);
		
		// Radio button
		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");
		maleRadio.setBounds(150, 131, 61, 23);
		femaleRadio.setBounds(231, 131, 79, 23);
		ButtonGroup radio_group = new ButtonGroup();
		radio_group.add(maleRadio);
		radio_group.add(femaleRadio);
		maleRadio.setSelected(true);
		
		// Combo box
		String[] month = {"January", "February", "March", "April", "May", "June", 
				"July", "August", "September", "October", "November", "December"};
		
		String [] date = new String[32];
		for (int i=0;i<=31;++i){
		date[i]=String.valueOf(i+1);
		}
		
		monthBox = new JComboBox(month);
		monthBox.setBounds(59, 183, 71, 22);
		
		dayBox = new JComboBox(date);
		dayBox.setSelectedIndex(0);
		dayBox.setBounds(167, 183, 39, 22);
								
		// JButtons
		add_btn = new JButton("Add");		
		add_btn.setFont(new Font("Tahoma", Font.BOLD, 10));
		add_btn.setBounds(199, 395, 70, 23);
				
		clr_btn = new JButton("Clear");
		clr_btn.setFont(new Font("Tahoma", Font.BOLD, 10));
		clr_btn.setBounds(274, 395, 70, 23);
		
		update_btn = new JButton("Update");
		update_btn.setBounds(525, 395, 70, 23);
		update_btn.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		del_btn = new JButton("Delete");
		del_btn.setBounds(599, 395, 70, 23);
		del_btn.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		how_btn = new JButton("How To Use");
		how_btn.setFont(new Font("Tahoma", Font.BOLD, 10));
		how_btn.setBounds(570, 9, 99, 23);
		
		// Add action and key listeners
		add_btn.addActionListener(this);
		clr_btn.addActionListener(this);
		update_btn.addActionListener(this);
		del_btn.addActionListener(this);
		how_btn.addActionListener(this);
		txt_search.addKeyListener(this);
		
		// Add components
		frame.getContentPane().add(PIS);
		frame.getContentPane().add(search_panel);
		frame.getContentPane().add(info_panel);
		frame.getContentPane().add(add_btn);
		frame.getContentPane().add(clr_btn);
		frame.getContentPane().add(update_btn);
		frame.getContentPane().add(del_btn);
		frame.getContentPane().add(how_btn);
		frame.getContentPane().add(scrollPane);
		info_panel.add(fname);
		info_panel.add(mname);
		info_panel.add(lname);
		info_panel.add(suffix);
		info_panel.add(sex);
		info_panel.add(ssn);
		info_panel.add(nationality);
		info_panel.add(c_address);
		info_panel.add(txtFName);
		info_panel.add(txtMName);
		info_panel.add(txtLName);
		info_panel.add(txtSuffix);
		info_panel.add(maleRadio);
		info_panel.add(femaleRadio);
		info_panel.add(txtSocialNum);
		info_panel.add(txtNationality);
		info_panel.add(txtAddress);
		info_panel.add(txtYear);
		info_panel.add(txtMobileNum);
		info_panel.add(dob);
		info_panel.add(mo);
		info_panel.add(monthBox);
		info_panel.add(dayBox);
		info_panel.add(day);
		info_panel.add(year);
		info_panel.add(mobile_num);
		search_panel.add(num_search);
		search_panel.add(txt_search);
		
	}
	
	/**
	 * Event handling code that handles events generated by components of the application.
	 * This method implements actions based on the source of the event.
	 * @param event is an action that occurs specifically a button is pressed.
	 */
	public void actionPerformed(ActionEvent event) {
			first_name = txtFName.getText();
			mid_name = txtMName.getText();
			last_name = txtLName.getText();
			suffix_str = txtSuffix.getText();
			sex_str = "female";
			if (maleRadio.isSelected()) {
				sex_str = "male";
            }
			month_str = monthBox.getSelectedItem().toString();
			day_str = dayBox.getSelectedItem().toString();
			year_str = txtYear.getText();
			ssn_str = txtSocialNum.getText();
			nationality_str = txtNationality.getText();
			address_str = txtAddress.getText();
			mobile_str = txtMobileNum.getText();
			id = txt_search.getText();
			
			// Create a new data entry in the database from the data in the filled up fields when add button is pressed
			if(event.getSource()==add_btn) {				
				try {
					pst = con.prepareStatement("insert into personal_info(FirstName, MiddleName, LastName, "
							+ "Suffix, Sex, BirthMonth, BirthDate, BirthYear, SSN, Nationality, Address, Mobile)"
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?)");
					pst.setString(1, first_name);
					pst.setString(2, mid_name);
					pst.setString(3, last_name);
					pst.setString(4, suffix_str);
					pst.setString(5, sex_str);
					pst.setString(6, month_str);
					pst.setString(7, day_str);
					pst.setString(8, year_str);
					pst.setString(9, ssn_str);
					pst.setString(10, nationality_str);
					pst.setString(11, address_str);
					pst.setString(12, mobile_str);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "New Entry Creation Successful!");
					table_load();
											           
					clear();
					txt_search.setText("");
					txtFName.requestFocus();
				   }
				catch (SQLException e1) {
				   e1.printStackTrace();
				   JOptionPane.showMessageDialog(null, "Error!");
				   }
			}
			
			// Clear all the fields by calling the clear() method and set request focus back to first field when clear button is pressed 
			else if(event.getSource()==clr_btn) {
				clear();
				txtFName.requestFocus();
			}
			
			// Save the edited data from the database when entry is searched, edited in field and update button is pressed
			else if(event.getSource()==update_btn) {
				
				try {
					pst = con.prepareStatement("update personal_info set FirstName=?, MiddleName=?, LastName=?, "
							+ "Suffix=?, Sex=?, BirthMonth=?, BirthDate=?, BirthYear=?, SSN=?, Nationality=?, Address=?, Mobile=? where id =?");
									
					pst.setString(1, first_name);
					pst.setString(2, mid_name);
					pst.setString(3, last_name);
					pst.setString(4, suffix_str);
					pst.setString(5, sex_str);
					pst.setString(6, month_str);
					pst.setString(7, day_str);
					pst.setString(8, year_str);
					pst.setString(9, ssn_str);
					pst.setString(10, nationality_str);
					pst.setString(11, address_str);
					pst.setString(12, mobile_str);
					pst.setString(13, id);
		            pst.executeUpdate();
		            JOptionPane.showMessageDialog(null, "Update Successful!");
		            table_load();
		          
		            clear();
		            txt_search.setText("");
		            txtFName.requestFocus();
				}
				 
				catch (SQLException e1) {
				e1.printStackTrace();
				}
			}
			
			// Delete an entry from the database when entry is searched and delete button is pressed
			else if(event.getSource()==del_btn) {
				try {
				pst = con.prepareStatement("delete from personal_info where id =?");
				            pst.setString(1, id);
				            pst.executeUpdate();
				            JOptionPane.showMessageDialog(null, "Delete Successful!");
				            table_load();
				          
				            clear();
				            txt_search.setText("");
				            txtFName.requestFocus();
				}
				 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			// Show a simple how-to tutorial message dialog box when how to use button is pressed
			else if(event.getSource()==how_btn) {
				 JOptionPane.showMessageDialog(null, "Add New Entry:\n1) Fill all fields \n2) Press Add"
				 		+ "\n\nEdit Entry:\n1)Enter ID number in search field\n2)Apply desired changes in field\n3)Press Update"
				 		+ "\n\nDelete Entry:\n1)Enter ID number in search field\n2)Press Delete");
			}
	}
	
	/**
	 * Clears all the textfield by setting the text to "", defaults radio selection to male, and combo box selection to index 0
	 */
	public void clear() {
		txtFName.setText("");
		txtMName.setText("");
		txtLName.setText("");
		txtSuffix.setText("");
		monthBox.setSelectedIndex(0);
		maleRadio.setSelected(true);;
		dayBox.setSelectedIndex(0);
		txtYear.setText("");
		txtSocialNum.setText("");
		txtNationality.setText("");
		txtAddress.setText("");
		txtMobileNum.setText("");
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Invoked when a key has been released. Specifically in this instance for the search bar the release key is searched in SQL database.
	 * @param e the event to be processed 
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		try {       
				id = txt_search.getText();
                pst = con.prepareStatement("select FirstName, MiddleName, LastName, Suffix, Sex, "
                		+ "BirthMonth, BirthDate, BirthYear, SSN, Nationality, Address, Mobile from personal_info where id = ?");
                pst.setString(1, id);
                ResultSet rs = pst.executeQuery();
 
            if(rs.next()==true)
            {
                first_name = rs.getString(1);
                mid_name = rs.getString(2);
                last_name = rs.getString(3);
                suffix_str = rs.getString(4);
                sex_str = rs.getString(5);
                month_str = rs.getString(6);
                day_str = rs.getString(7);
                year_str = rs.getString(8);
                ssn_str = rs.getString(9);
                nationality_str = rs.getString(10);
                address_str = rs.getString(11);
                mobile_str = rs.getString(12);
                
                txtFName.setText(first_name);
        		txtMName.setText(mid_name);
        		txtLName.setText(last_name);
        		txtSuffix.setText(suffix_str);
        		monthBox.setSelectedItem(month_str); ;
        		dayBox.setSelectedItem(day_str);
        		if(sex_str == "male") {
        			maleRadio.setSelected(true);
        		}else {
        			femaleRadio.setSelected(true);
        		}        		
        		txtYear.setText(year_str);
        		txtSocialNum.setText(ssn_str);
        		txtNationality.setText(nationality_str);
        		txtAddress.setText(address_str);
        		txtMobileNum.setText(mobile_str);                
            }  
            else
            {
             clear();
            }
 
        }
		catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!");
        }
		
	}
}
