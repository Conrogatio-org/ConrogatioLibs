package org.conrogatio.libs;

import java.util.ArrayList;
import java.util.Random;

public class CObjectAlternator {
	private static Random rand = new Random();
	private static ArrayList<Object> o;
	private int i = -1;
	
	public CObjectAlternator(ArrayList<Object> objects) {
		o = objects;
	}
	
	private static int randInt() {
		return rand.nextInt(o.size());
	}
	
	public CObjectAlternator() {
		o = new ArrayList<Object>();
	}
	
	public Object getNextObject() {
		i++;
		if (i == o.size()) {
			i = 0;
		}
		return o.get(i);
	}
	
	public Object getRandomObject() {
		return o.get(randInt());
	}
	
	public void changeObjects(ArrayList<Object> objects) {
		o = objects;
	}
	
	public void replaceObject(Object object, int position) {
		o.set(position, object);
	}
	
	public void addObject(Object object) {
		o.add(object);
	}
	
	public ArrayList<Object> getObjects() {
		return o;
	}
}
