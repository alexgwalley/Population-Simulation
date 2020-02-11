package chart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.Animal;
import entity.DNA;
import entity.Species;
import main.Game;

public class SaveLoadChart {
	
	public static void wipeData() {
		PrintWriter pw;
		try {
			pw = new PrintWriter("res/chart.dna");
			pw.write("");
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void saveData() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("res/chart.dna", true));
		ArrayList<Species> species = new ArrayList<Species>();
		for(Animal a : Game.animals)
			if(!species.contains(a.getDna().getSpecies())) species.add(a.getDna().getSpecies());
		double time = Game.gameTime;
		for(Species specie : species) {
			int totNum = 0;
			int totFood = 0;
			float totFOVa = 0;
			int totFOVr = 0;
			int totMoveSpeed = 0;
			int totRadius = 0;
			float totMutRate = 0;
			int totEatRate = 0;
			int totFleeRadius = 0;
			int totMateMin = 0;
			int totMatingRadiusMin = 0;
			for(Animal a : Game.animals) {
				DNA d = a.getDna();
				if(!a.getDna().getSpecies().equals(specie)) continue;
				totNum++;
				totFood+=a.getFood();
				totFOVa+=d.getFieldOfViewAngle();
				totFOVr+=d.getFieldOfViewRadius();
				totMoveSpeed+=d.getMoveSpeed();
				totRadius+=d.getRadius();
				totMutRate+=d.getMutationRate();
				totEatRate+=d.getEatingRate();
				totFleeRadius+=d.getFleeRadius();
				totMateMin+=d.getMatingMinimum();
				totMatingRadiusMin+=d.getMatingRadius();
			}
			bw.write(time+","+specie+","+totNum+","+totFood/totNum+","+totFOVa/totNum+","+totFOVr/totNum+","+
					totMoveSpeed/totNum+","+totRadius/totNum+","+totMutRate/totNum+","+totEatRate/totNum+","+
					totFleeRadius/totNum+","+totMateMin/totNum +","+totMatingRadiusMin/totNum);
			bw.newLine();
		}
		bw.close();
	}
	
	public static List<String>[] loadData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res/chart.dna"));
		Object[] temp = br.lines().toArray();
		String[] lines = new String[temp.length];
		for(int i = 0; i<lines.length; i++) lines[i] = temp[i].toString();

		List<String>[] outData = new ArrayList[13];
		for(int i = 0; i < 13; i++) {
			outData[i] = new ArrayList<String>();
		}
		
		for(int i = 0; i < lines.length; i++) {
			String[] data = lines[i].split(",");
			
			//int index = 0;
			for(int j = 0; j < data.length; j++) {
				outData[j].add(data[j]);
			}
		}

		return outData;
	}
	
	public static String[] getNames() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res/DNAHelp.dna"));
		String[] items = br.readLine().split(",");
		return items;		
	}

}
