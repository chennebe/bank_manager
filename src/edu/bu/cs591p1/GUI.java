package edu.bu.cs591p1;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI {
	
	private Bank _b;
	private JFrame _frame;
	private JPanel _titlePanel;
	private JScrollPane _listPanel;
	private JPanel _actionPanel;
	private JPanel _controlPanel;
	private ArrayList<BankAccount> _showingAccounts;
	
	public GUI(Bank b) {
		_b = b;
		_frame = new JFrame();
		_frame.setTitle("The Bank");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLayout(new BoxLayout(_frame.getContentPane(), BoxLayout.Y_AXIS));
		_frame.setSize(720, 640);
		
		_titlePanel = new JPanel();
		_titlePanel.setPreferredSize(new Dimension(720, 64));
		JLabel title = new JLabel("Welcome to The Bank");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		_titlePanel.add(title);
		
		_listPanel = new JScrollPane();
		
		
		_actionPanel = new JPanel();
		
		_controlPanel = new JPanel();
		_controlPanel.setLayout(new GridLayout(1, 3));
		
		_frame.add(_titlePanel);
		_frame.add(_listPanel);
		_frame.add(_actionPanel);
		_frame.add(_controlPanel);
		
		_frame.setVisible( true );
	}

}