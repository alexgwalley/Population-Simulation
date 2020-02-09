package entity;

import java.util.HashMap;
import java.util.Map;

import chart.DataType;

public enum State {
	
	FLEE("Flee"), SEEK_FOOD("Seek Food"), SEEK_MATE("Seek Mate"), EAT ("Eat"), DIE("Die"), 
	MATING("Mating"), GOING_TO_MATE("Going to Mate"), HUNTING("Hunting");
	

	private String value;
    private static Map map = new HashMap<>();

    private State(String value) {
        this.value = value;
    }

    static {
        for (DataType state : DataType.values()) {
            map.put(state.getValue(), state);
        }
    }

    public static State valueOf(State s) {
        return (State) map.get(s);
    }

    public String getValue() {
        return value;
    }

}
