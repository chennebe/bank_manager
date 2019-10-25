package edu.bu.cs591p1;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListPanel extends JPanel {
	
	private JPanel _wrapper;
	private JPanel _container;

	public ListPanel(ArrayList<String> items) {
		_wrapper = new JPanel();
		_container = new JPanel();
		_container.setLayout(new BoxLayout(_container, BoxLayout.Y_AXIS));
		_wrapper.add(_container);
		
		
	}

}
