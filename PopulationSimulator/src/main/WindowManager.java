package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import chart.DataType;
import chart.SaveLoadChart;
import entity.Species;

public class WindowManager {

	private JFrame frame;
	private Canvas canvas;
	private JMenuBar menubar;
	private JMenuItem save;
	private JMenuItem load;
	
	private JFrame chartFrame;
	private JMenuBar chMenubar;
	private JMenu dataTypes;
	private JCheckBox num, food, fova, fovr, movespeed, radius, mutrate, eatrate, fleerad, matemin;
	private JList speciesOptions;
	private DefaultListModel<String> l;
	private JButton addSpecies;
	private JTextArea chartTextData;
	private XYChart chart;
	private XChartPanel<XYChart> chartPanel;
	private DataType currentDisplay = DataType.NUM;
	
	private List<JCheckBox> checkBoxes;
	private List<JCheckBox> speciesCheckboxes;
	
	private String title;
	
	private int width;
	private int height;
	
	private SwingWrapper<XYChart> sw;
	
	private Game gameRef;
	public WindowManager(String t, int w, int h, Game game) {
		title = t;
		width = w;
		height = h;
		
		createWindow();
		
		gameRef = game;
		
	}
	
	@SuppressWarnings("deprecation")
	private void createWindow() {
		frame = new JFrame(title);
		System.out.println("JFrame Initiated");
		
		frame.setSize(width, height);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas(); // New canvas element for the JFrame, handles drawing
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		
		canvas.addMouseMotionListener((MouseMotionListener) Game.camera);
		canvas.addMouseWheelListener((MouseWheelListener) Game.camera);
		canvas.addMouseListener(Game.camera);
		
		canvas.addKeyListener(Game.keyboardManager);
		
		frame.add(canvas);
		
		frame.setVisible(true);
		
		
		menubar = new JMenuBar();
		
		save = new JMenuItem("Save");
		menubar.add(save);
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SaveLoadGame.setToSave = true;
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
		});
		
		load = new JMenuItem("Load");
		menubar.add(load);
		
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SaveLoadGame.setToLoad = true;
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
		});
		
		frame.setJMenuBar(menubar);
		
		frame.pack();  //Resize to fit all components
		
		chartFrame = new JFrame("Chart");
		
		chartFrame.setSize(300, height);
		chartFrame.setLocationRelativeTo(frame);
		chartFrame.setResizable(true);
		chartFrame.setLayout(null);
		chartFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		chart = new XYChart(200,200);
		chart.addSeries("herbivore", new double[]{1d}, new double[]{1d});
		chartPanel = new XChartPanel<XYChart>(chart);
		//chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        //chartPanel.setBackground(Color.white);
		
		chart = QuickChart.getChart("Simple XChart Real-time Demo", "Time", "Value", "popNum", new double[] {0}, new double[] {0});
		chartFrame.add(chartPanel);
		
		chMenubar = new JMenuBar();
		
		String[] names = null;
		speciesCheckboxes = new ArrayList<>();
		
		try {
			names = SaveLoadChart.getNames();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(Species spec : Species.speciesList) {
			for(String name : names){
				chart.addSeries(spec.toString()+name, new double[] {0});
				//System.out.println("Series Name: " + spec.toString() + name);
			}
			speciesCheckboxes.add(new JCheckBox(spec.getName().toUpperCase()));
		}
		

	    sw = new SwingWrapper<XYChart>(chart);
	    sw.displayChart();

	    
	    JPanel p = new JPanel();
	    
	    checkBoxes = new ArrayList<>();
	    checkBoxes.add(new JCheckBox("TIME"));				  // Not shown
	    checkBoxes.add(new JCheckBox("SPECIES NAME FILLER")); // Not shown
		checkBoxes.add(new JCheckBox("Pop. Size"));
		checkBoxes.add(new JCheckBox("Satisfaction"));
		checkBoxes.add(new JCheckBox("FOV Angle"));
		checkBoxes.add(new JCheckBox("FOV Radius"));
		checkBoxes.add(new JCheckBox("Move Speed"));
		checkBoxes.add(new JCheckBox("Size"));
		checkBoxes.add(new JCheckBox("Mutation Rate"));
		checkBoxes.add(new JCheckBox("Eating Speed"));
		checkBoxes.add(new JCheckBox("Flee Radius"));
		checkBoxes.add(new JCheckBox("Mate Minimum"));
		checkBoxes.add(new JCheckBox("Mating Radius"));
		
		checkBoxes.get(0).setVisible(false);
		checkBoxes.get(1).setVisible(false);
		for(JCheckBox box : speciesCheckboxes) {
			p.add(box);
		}
		
		for(JCheckBox box : checkBoxes) {
			p.add(box);
		}
		
		l = new DefaultListModel<String>();
		for(int i = 0; i < Species.speciesList.size(); i++)
			l.addElement(Species.speciesList.get(i).getName());
		speciesOptions = new JList<String>(l);
		
		addSpecies = new JButton("Add Species");
		chartFrame.add(speciesOptions);

			
		chartFrame.setLayout(new GridLayout());


		
		addSpecies.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSpecies();
			}
			
		});
		
		chartFrame.add(p);
		chartFrame.setVisible(true);
	}
	
	public void setChartText(String text) {
		chartTextData.setText(text);
	}
	
	public void updateList() {
		l.clear();
		for(int i = 0; i < Species.speciesList.size(); i++)
			l.addElement(Species.speciesList.get(i).getName());
		chartFrame.repaint();
	}
	
	public void updateChart(float[] xAxis, List<String>[] dataSections) {
		
		String[] names = null;

		if(xAxis.length < Species.speciesList.size()) return;
		if(xAxis == null) return;
		
		//System.out.println(xAxis.length);
		
		try {
			names = SaveLoadChart.getNames();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		for(int s = 0; s < Species.speciesList.size(); s++) {// Loop through species
			
			for(int i = 0; i < dataSections.length; i++) { // sections (i == number of section)
				if(i == 1) continue; // Don't look at species name
				
				double[][] data = new double[2][xAxis.length];
				int dataIndex = 0;
				for(int j = s; j < dataSections[i].size(); j++) {// entries (j == number in section)
					
					// If not looking at correct species row, skip
					if(!dataSections[1].get(j).equalsIgnoreCase( Species.speciesList.get(s).getName() )) continue;
				
					
					data[0][dataIndex] = xAxis[j];
					data[1][dataIndex] = Double.parseDouble( dataSections[i].get(j) );
					dataIndex ++;
				}
				
				
				// Add new data to series
				String seriesName = Species.speciesList.get(s).toString()+names[i];

				int k = i;
				if(speciesCheckboxes.size() <= s) continue;
				if(checkBoxes.get(k).isSelected() == true && speciesCheckboxes.get(s).isSelected() == true) {
					chart.updateXYSeries(seriesName, data[0], data[1], null);
				}else 
					chart.updateXYSeries(seriesName, new double[]{}, new double[]{}, null);
				
			}
		}
				
		sw.repaintChart();

		chartPanel.revalidate();
		chartPanel.repaint();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public DataType getCurrentDisplay() {
		return this.currentDisplay;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}
