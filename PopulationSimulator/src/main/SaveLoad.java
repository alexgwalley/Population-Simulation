package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import comparator.CustomAnimalComparator;
import entity.Animal;
import entity.DNA;
import entity.Food;
import math.Vector;

public class SaveLoad {
	
	public static void saveGame() throws IOException {
		saveAnimalData();
		saveFoodData();
	}
	
	private static void saveAnimalData() throws IOException{
		//TODO: Add in text for food data.
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("res/game1.sim"), true));
		for(Animal a : Game.animals) {
			DNA d = a.getDna();
			String line = "a," + a.getName() + "," + a.getPos(0) + "," + a.getPos(1) + "," + a.getFood() + "," + d.getSpecies() + "," 
					+ d.getColor() + "," + d.getFieldOfViewAngle() + "," + d.getFieldOfViewRadius() + "," 
					+ d.getMoveSpeed() + "," + d.getRadius() + "," + d.getMutationRate() + "," + d.getEatingRate() + "," 
					+ d.getFleeRadius() + "," + d.getMatingMinimum();
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	private static void saveFoodData() throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("res/game1.sim"), true));
		bw.newLine();
		for(Food f : Game.food) {
			String line = "f," + f.getPos(0) + "," + f.getPos(1) + "," + f.getFood();
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	public static void loadGame() throws IOException{
		loadAnimals();
		loadFood();
	}
	
	private static void loadAnimals() throws IOException{
		Game.animals = new TreeSet<>(new CustomAnimalComparator());
		BufferedReader br = new BufferedReader(new FileReader("res/game1.sim"));
		String line = "";
		while((line = br.readLine()) != null) {
			String[] data = line.split(",");
			if(!data[0].equals("a")) continue;
			Game.animals.add(new Animal(data[1], new Vector(Float.parseFloat(data[2]),Float.parseFloat(data[3])), d, Integer.parseInt(data[4])));
		}
		br.close();
	}
	
	private static void loadFood() throws IOException{
		Game.food = new ArrayList<Food>();
		BufferedReader br = new BufferedReader(new FileReader("res/game1.sim"));
		String line = "";
		while((line = br.readLine()) != null) {
			String data[] = line.split(",");
			if(!data[0].equals("f")) continue;
			Game.food.add(new Food(new Vector(Float.parseFloat(data[1]), Float.parseFloat(data[2])), Integer.parseInt(data[3])));
		}
	}

}
