package main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import entity.DNA;
import entity.Species;
import generator.NameGenerator;

public class AddSpecies extends JFrame {
	
	private JTextArea textarea1,textarea2,textarea3,textarea4,textarea5,textarea6,textarea7,textarea8,textarea9;
	private JTextField SpeciesName;
	private SpinnerNumberModel colorRed,colorBlue,colorGreen,fova,fovr,movespeed,radius,eatrate,fleeradius,matemin,mutrate;
	private JSpinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9,spinner10;

	private JTextArea speciesList;
	private JTextField[] speciesTolerance;
	
	private JButton ad;
	
	public AddSpecies() {
		setVisible(true);
		setSize(300,500);
		setTitle("Add Species");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(null);
		
		ad = new JButton("Add Species");
		ad.setBounds(0,400,300,100);
		add(ad);
		ad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Species s = new Species(SpeciesName.getText());
				HashMap<String,Integer> food = new HashMap<String,Integer>(){
					{
						for(int i = 0; i < Species.speciesList.size(); i++)
							put(Species.speciesList.get(i).toString(), Integer.parseInt(speciesTolerance[i].getText()));
					}
				};
				DNA d = new DNA(s, new Color((int)colorRed.getNumber(), (int)colorGreen.getNumber(), (int)colorBlue.getNumber()), food, (float)fova.getNumber(), (int)fovr.getNumber(),(float)movespeed.getNumber(),(int)radius.getNumber(),(float)mutrate.getNumber(),(int)eatrate.getNumber(),(int)fleeradius.getNumber(),(int)matemin.getNumber());
				
			}
			
		});
		
		textarea1 = new JTextArea("Red:");
		textarea1.setEditable(false);
		textarea2 = new JTextArea("Blue:");
		textarea2.setEditable(false);
		textarea3 = new JTextArea("Green:");
		textarea3.setEditable(false);
		textarea4 = new JTextArea("FOV Angle:");
		textarea4.setEditable(false);
		textarea5 = new JTextArea("FOV Radius:");
		textarea5.setEditable(false);
		textarea6 = new JTextArea("Size:");
		textarea6.setEditable(false);
		textarea7 = new JTextArea("Eat Speed:");
		textarea7.setEditable(false);
		textarea8 = new JTextArea("Flee Radius:");
		textarea8.setEditable(false);
		textarea9 = new JTextArea("Min to Mate:");
		textarea9.setEditable(false);
		
		Random r = new Random();
		SpeciesName = new JTextField(NameGenerator.newName());
		colorRed = new SpinnerNumberModel((int)r.nextInt(255),0,255,1);
		colorBlue = new SpinnerNumberModel((int)r.nextInt(255),0,255,1);
		colorGreen = new SpinnerNumberModel((int)r.nextInt(255),0,255,1);
		fova = new SpinnerNumberModel((int)r.nextInt(60)+15,15,75,1);
		fovr = new SpinnerNumberModel((int)r.nextInt(600),0,600,10);
		radius = new SpinnerNumberModel((int)r.nextInt(50),1,50,1);
		eatrate = new SpinnerNumberModel((int)r.nextInt(30),0,30,1);
		fleeradius = new SpinnerNumberModel((int)r.nextInt(100),0,100,10);
		matemin = new SpinnerNumberModel((int)r.nextInt(100),0,100,1);
		
		SpeciesName.setBounds(0,0,300,30);
		spinner2 = new JSpinner(colorRed);
		textarea1.setBounds(0,50,50,30);
		spinner2.setBounds(50,50,40,30);
		spinner3 = new JSpinner(colorBlue);
		textarea2.setBounds(100,50,50,30);
		spinner3.setBounds(150,50,50,30);
		spinner4 = new JSpinner(colorGreen);
		textarea3.setBounds(200,50,50,30);
		spinner4.setBounds(250,50,50,30);
		spinner5 = new JSpinner(fova);
		textarea4.setBounds(50,100,50,30);
		spinner5.setBounds(100,100,50,30);
		spinner6 = new JSpinner(fovr);
		textarea5.setBounds(150,100,50,30);
		spinner6.setBounds(200,100,50,30);
		spinner7 = new JSpinner(radius);
		textarea6.setBounds(50,150,50,30);
		spinner7.setBounds(100,150,50,30);
		spinner8 = new JSpinner(eatrate);
		textarea7.setBounds(150,150,50,30);
		spinner8.setBounds(200,150,50,30);
		spinner9 = new JSpinner(fleeradius);
		textarea8.setBounds(50,200,50,30);
		spinner9.setBounds(100,200,50,30);
		spinner10 = new JSpinner(matemin);
		textarea9.setBounds(150,200,50,30);
		spinner10.setBounds(200,200,50,30);
		
		add(SpeciesName);
		add(textarea1);
		add(spinner2);
		add(textarea2);
		add(spinner3);
		add(textarea3);
		add(spinner4);
		add(textarea4);
		add(spinner5);
		add(textarea5);
		add(spinner6);
		add(textarea6);
		add(spinner7);
		add(textarea7);
		add(spinner8);
		add(textarea8);
		add(spinner9);
		add(textarea9);
		add(spinner10);
		
		speciesList = new JTextArea();
		speciesList.setBounds(0,250,150,200);
		speciesList.setEditable(false);
		add(speciesList);
		
		int numOfSpecies = Species.speciesList.size();
		speciesTolerance = new JTextField[numOfSpecies];
		String text = "";
		for(int i = 0; i < numOfSpecies; i++) {
			speciesTolerance[i] = new JTextField("0");
			speciesTolerance[i].setBounds(150,250+20*i,150,20);
			text+=Species.speciesList.get(i)+":\n";
			add(speciesTolerance[i]);
		}
		speciesList.setText(text);
		
		repaint();
	}

}
