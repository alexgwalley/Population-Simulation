package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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
	private JTextArea chartTextData;
	private XYChart chart;
	private XChartPanel<XYChart> chartPanel;
	private DataType currentDisplay = DataType.NUM;
	
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
		chartFrame.setResizable(false);
		chartFrame.setLayout(null);
		chartFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		chart = new XYChart(200,200);
		chart.addSeries("basic", new double[]{1d}, new double[]{1d});
		chartPanel = new XChartPanel<XYChart>(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
		//chartFrame.add(chartPanel);
		
		chMenubar = new JMenuBar();
		
		// XChart
		// Create Chart
		chart = QuickChart.getChart("Simple XChart Real-time Demo", "Time", "Value", "b", new double[]{0}, new double[]{0});
		//XYSeries popNumSeries = new XYSeries("popNum", new double[] {0}, new double[] {0}, new double[] {0}, org.knowm.xchart.internal.series.Series.DataType.Number);
		//XYSeries foodNumSeries = new XYSeries("foodNum", new double[] {0}, new double[] {0}, new double[] {0}, org.knowm.xchart.internal.series.Series.DataType.Number);
	    chart.addSeries("popNum", new double[] {0});
	    chart.addSeries("foodNum", new double[] {0});
	    chart.addSeries("fovrNum", new double[] {0});
	    chart.addSeries("fovaNum", new double[] {0});
	    chart.addSeries("moveSpeedNum", new double[] {0});
	    chart.addSeries("radiusNum", new double[] {0});
	    chart.addSeries("mutationRateNum", new double[] {0});
	    chart.addSeries("eatRateNum", new double[] {0});
	    chart.addSeries("fleeRateNum", new double[] {0});
	    chart.addSeries("mateMinNum", new double[] {0});
		// Show it
	    sw = new SwingWrapper<XYChart>(chart);
	    sw.displayChart();

	    
		
		dataTypes = new JMenu("Show Data");
		
		num = new JCheckBox("Pop. Size");
		food = new JCheckBox("Hunger");
		fova = new JCheckBox("FOV Angle");
		fovr = new JCheckBox("FOV Radius");
		movespeed = new JCheckBox("Move Speed");
		radius = new JCheckBox("Size");
		mutrate = new JCheckBox("Mutation Rate");
		eatrate = new JCheckBox("Eating Speed");
		fleerad = new JCheckBox("Flee Radius");
		matemin = new JCheckBox("Mate Minimum");
		
		num.setSelected(true);
		
		JPanel p = new JPanel();
		
		chartFrame.setLayout(new GridLayout());
		
		p.add(num);
		p.add(fova);
		p.add(food);
		p.add(fovr);
		p.add(movespeed);
		p.add(radius);
		p.add(mutrate);
		p.add(eatrate);
		p.add(fleerad);
		p.add(matemin);
		
		
		chartFrame.add(p);
		
		
		chartFrame.show();
		chMenubar.add(dataTypes);
		//chartFrame.setJMenuBar(chMenubar);
		
//		ChangeData buttonAction = new ChangeData();
//		num.addActionListener(buttonAction);
//		food.addActionListener(buttonAction);
//		fova.addActionListener(buttonAction);
//		fovr.addActionListener(buttonAction);
//		movespeed.addActionListener(buttonAction);
//		radius.addActionListener(buttonAction);
//		mutrate.addActionListener(buttonAction);
//		eatrate.addActionListener(buttonAction);
//		fleerad.addActionListener(buttonAction);
//		matemin.addActionListener(buttonAction);
		
		chartTextData = new JTextArea();
		chartTextData.setSize(400, height);

		
		//chartFrame.pack();
	}
	
	public void setChartText(String text) {
		chartTextData.setText(text);
	}
	
	public void updateChart(float[] xAxis, float[][] yAxis, DataType... filters) {
		double[] errorBars = new double[xAxis.length];
		for(int i = 0; i < errorBars.length; i++) {
			errorBars[i] = 0;
		}
		
		double[][] popNum = new double[2][xAxis.length];
		double[][] foodNum = new double[2][xAxis.length];
		double[][] fovaNum = new double[2][xAxis.length];
		double[][] fovrNum = new double[2][xAxis.length];
		double[][] moveSpeedNum = new double[2][xAxis.length];
		double[][] radiusNum = new double[2][xAxis.length];
		double[][] mutationRateNum = new double[2][xAxis.length];
		double[][] eatingRateNum = new double[2][xAxis.length];
		double[][] fleeRadiusNum = new double[2][xAxis.length];
		double[][] matingMinNum = new double[2][xAxis.length];
		
		for(int i = 0; i < xAxis.length; i++) {
			popNum[0][i] = (double) xAxis[i];
			popNum[1][i] = (double) yAxis[2][i];
			
			foodNum[0][i] = (double) xAxis[i];
			foodNum[1][i] = (double) yAxis[3][i];
			
			fovaNum[0][i] = (double) xAxis[i];
			fovaNum[1][i] = (double) yAxis[4][i];
			
			fovrNum[0][i] = (double) xAxis[i];
			fovrNum[1][i] = (double) yAxis[5][i];

			moveSpeedNum[0][i] = (double) xAxis[i];
			moveSpeedNum[1][i] = (double) yAxis[6][i];
			
			radiusNum[0][i] = (double) xAxis[i];
			radiusNum[1][i] = (double) yAxis[7][i];
			
			mutationRateNum[0][i] = (double) xAxis[i];
			mutationRateNum[1][i] = (double) yAxis[8][i];
			
			eatingRateNum[0][i] = (double) xAxis[i];
			eatingRateNum[1][i] = (double) yAxis[9][i];
			
			fleeRadiusNum[0][i] = (double) xAxis[i];
			fleeRadiusNum[1][i] = (double) yAxis[10][i];
			
			matingMinNum[0][i] = (double) xAxis[i];
			matingMinNum[1][i] = (double) yAxis[11][i];
			
		}
		
		
		if(num.isSelected())
			chart.updateXYSeries("popNum", popNum[0], popNum[1], null);
		else
			chart.updateXYSeries("popNum", new double[]{0}, new double[]{0}, null);
		
		if(food.isSelected())
			chart.updateXYSeries("foodNum", foodNum[0], foodNum[1], null);
		else
			chart.updateXYSeries("foodNum", new double[]{0}, new double[]{0}, null);
		
		if(fova.isSelected())
			chart.updateXYSeries("fovaNum", fovaNum[0], fovaNum[1], null);
		else
			chart.updateXYSeries("fovaNum", new double[]{0}, new double[]{0}, null);
		
		if(fovr.isSelected())
			chart.updateXYSeries("fovrNum", fovrNum[0], fovrNum[1], null);
		else
			chart.updateXYSeries("fovrNum", new double[]{0}, new double[]{0}, null);
		
		if(movespeed.isSelected())
			chart.updateXYSeries("moveSpeedNum", moveSpeedNum[0], moveSpeedNum[1], null);
		else
			chart.updateXYSeries("moveSpeedNum", new double[]{0}, new double[]{0}, null);
		
		if(radius.isSelected())
			chart.updateXYSeries("radiusNum", radiusNum[0], radiusNum[1], null);
		else
			chart.updateXYSeries("radiusNum", new double[]{0}, new double[]{0}, null);
		
		if(mutrate.isSelected())
			chart.updateXYSeries("mutationRateNum", mutationRateNum[0], mutationRateNum[1], null);
		else
			chart.updateXYSeries("mutationRateNum", new double[]{0}, new double[]{0}, null);
		
		if(eatrate.isSelected())
			chart.updateXYSeries("eatRateNum", eatingRateNum[0], eatingRateNum[1], null);
		else
			chart.updateXYSeries("eatRateNum", new double[]{0}, new double[]{0}, null);
		
		if(fleerad.isSelected())
			chart.updateXYSeries("fleeRateNum", fleeRadiusNum[0], fleeRadiusNum[1], null);
		else
			chart.updateXYSeries("fleeRateNum", new double[]{0}, new double[]{0}, null);
		
		if(matemin.isSelected())
			chart.updateXYSeries("mateMinNum", matingMinNum[0], matingMinNum[1], null);
		else
			chart.updateXYSeries("mateMinNum", new double[]{0}, new double[]{0}, null);
		
		
		sw.repaintChart();


		//chart.addSeries(seriesName, xAxis, yAxis);
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
	
	private class ChangeData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == num) {currentDisplay = DataType.NUM;}
			if(e.getSource() == food) {currentDisplay = DataType.FOOD;}
			if(e.getSource() == fova) {currentDisplay = DataType.FOVA;}
			if(e.getSource() == fovr) {currentDisplay = DataType.FOVR;}
			if(e.getSource() == movespeed) {currentDisplay = DataType.MOVESPEED;}
			if(e.getSource() == radius) {currentDisplay = DataType.RADIUS;}
			if(e.getSource() == mutrate) {currentDisplay = DataType.MUTATIONRATE;}
			if(e.getSource() == eatrate) {currentDisplay = DataType.EATINGRATE;}
			if(e.getSource() == fleerad) {currentDisplay = DataType.FLEERADIUS;}
			if(e.getSource() == matemin) {currentDisplay = DataType.MATINGMIN;}
		}
		
	}
	
}
