package example;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.WindowConstants;
import javax.swing.JTable;


import java.util.Scanner;

public class QuereyDB extends CreateDB{
	public static final String SQL_Statement = "select * from Campers";
	public static void main(String[] args) throws SQLException{
		String result = "";
		
			Connection connection = DriverManager.getConnection(CreateDB.JDBC_URL);
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(SQL_Statement);
			ResultSetMetaData resultmetadata = resultset.getMetaData();
			int colcount = resultmetadata.getColumnCount();
			
			for (int i = 1; i <= colcount; i++) {
				result += String.format("%20s", resultmetadata.getColumnName(i) + "|");
			} while(resultset.next()) {
				result += System.lineSeparator();
				for (int j = 1; j <= colcount; j++) {
					result += String.format("%20s", resultset.getString(j) + "|");
				}
			}

			// Set up JPanel for display of results.
			
			/*
			JFrame frame = new JFrame("My Frame");
			frame.setSize(640, 480);
			JPanel panel = new JPanel();
			panel.setSize(640,  480);
			panel.add(new JLabel("Results \n"));
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.add(panel);
			frame.setLayout(new GridLayout());
			frame.setVisible(true);
			JTextField tf;
			tf = new JTextField(result);
			tf.setMaximumSize( 
				     new Dimension(Integer.MAX_VALUE, tf.getPreferredSize().height) );
			panel.add(tf); //myPanel is the JPanel where I want to put the JTextFields
			
			panel.validate();
			panel.repaint();
			*/
			
			JFrame frameenco = new JFrame ("SQL Result");
	        frameenco.setSize(492, 473);
	        
	       // User interface separation from Logic.
	        
	        JPanel panel = new JPanel (new GridBagLayout ());
	        frameenco.getContentPane().add(panel, BorderLayout.NORTH);
	        GridBagConstraints c = new GridBagConstraints ();
	        c.insets = new Insets(15,15,15,15);
	        JTable table = convertto_table(result);
	        table.setSize(290, 380);
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setSize(394, 289);
	        
	        c.gridx = 3;
	        c.gridy = 3;
	        panel.add(scrollPane, c);
	        
	        frameenco.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	       // System.out.println(result);
	        frameenco.setVisible(true);
	        
	}
	
	public static JTable convertto_table(String t) {
		ArrayList<String> list = new ArrayList<String>();
		Scanner scan = new Scanner(t);
		
		while(scan.hasNext()) {
			list.add(scan.nextLine());
			//System.out.println(scan.nextLine());
		}
		
		// Setting the column names.
		String g = new String("");
		g = list.get(0).replace(" ", "");
		g = g.replace("|", ",");
		String[] col_names = g.split(",");
		
		list.remove(0);
		//obtaining data for the table;
		
		
		String obj_data[][] = new String[list.size()][3];
		
		String l = new String("");
		for(int i = 0; i < list.size(); i++) {
			l = list.get(i);
			l = l.replace(" ", "");
			l = l.replace("|", ",");
			String[] z = l.split(",");
			for (int f = 0; f < 3; f++) {
				obj_data[i][f] = z[f];
				//System.out.println(z[f]);
			}
		}
		
		JTable table = new JTable(obj_data, col_names);
		return table;
	}
}
