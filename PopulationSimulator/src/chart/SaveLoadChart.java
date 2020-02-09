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
			}
			bw.write(time+","+specie+","+totNum+","+totFood/totNum+","+totFOVa/totNum+","+totFOVr/totNum+","+
					totMoveSpeed/totNum+","+totRadius/totNum+","+totMutRate/totNum+","+totEatRate/totNum+","+
					totFleeRadius/totNum+","+totMateMin/totNum);
			bw.newLine();
		}
		bw.close();
	}
	
	public static List<String>[] loadData(Species specie, DataType... fil) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res/chart.dna"));
		Object[] temp = br.lines().toArray();
		String[] lines = new String[temp.length];
		for(int i = 0; i<lines.length; i++) lines[i] = temp[i].toString();
		ArrayList<String> out = new ArrayList<String>();
		List<DataType> filters = Arrays.asList(fil);

		List<String>[] outData = new ArrayList[fil.length];
		for(int i = 0; i < fil.length; i++) {
			outData[i] = new ArrayList<String>();
		}
		
		for(String line : lines) {
			String[] data = line.split(",");
			
			int index = 0;
			for(int j = 0; j < data.length; j++) {
				if(data.length > 1 && !data[1].equals(specie.toString())) continue;
				if(filters.contains(DataType.valueOf(j))) {
					outData[index].add(data[j]);
					index += 1;
				}
//				out.add(data[index]);
			}
		}

		return outData;
	}

}
