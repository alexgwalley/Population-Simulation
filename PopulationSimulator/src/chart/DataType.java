package chart;

import java.util.HashMap;
import java.util.Map;

public enum DataType {
	
	TIME(0), DEFAULT(1), NUM(2), FOOD(3), FOVA(4), FOVR(5), MOVESPEED(6), 
	RADIUS(7), MUTATIONRATE(8), EATINGRATE(9), FLEERADIUS(10), MATINGMIN(11);

	private int value;
    private static Map map = new HashMap<>();

    private DataType(int value) {
        this.value = value;
    }

    static {
        for (DataType pageType : DataType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static DataType valueOf(int pageType) {
        return (DataType) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
    
}
