package com.mlsci.lights.client;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Color {
	private Integer r;
	private Integer g;
	private Integer b;
	private Integer w;
	private Integer color_temp;


	

	public static Color of(int red, int green, int blue) {
		var c = new Color();
		c.setB(blue);
		c.setG(green);
		c.setR(red);
		return c;
	}
	
	public static Color of(int color_temp) {
		var c = new Color();
		c.setColor_temp(color_temp);
		return c;
	}
	
	
	public static final Color RED = Color.of(255, 0, 0);
	public static final Color ORANGE = Color.of(255, 127, 0);
	public static final Color YELLOW = Color.of(255, 255, 0);
	public static final Color GREEN = Color.of(0, 255, 0);
	public static final Color BLUE = Color.of(0, 0, 255);
	public static final Color INDIGO = Color.of(48, 43, 95);
	public static final Color VIOLET = Color.of(139, 0, 255);
	public static final Color PURPLE = Color.of(255, 0, 255);

	public static final Color CYAN = Color.of(0, 255, 255);

	public static final Color[] COLORS = { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET };

	public static final Color WARM_WHITE = Color.of(350);
	public static final Color COLD_WHITE = Color.of(155);
	public static final Color WHITE = Color.of(250);
	public static final Color HIGH_NOON = Color.of(340);
	public static final Color OVERCAST = Color.of(300);
	public static final Color SUNSET_ORANGE = Color.of(253, 94, 83);
	public static final Color SUNRISE_YELLOW = Color.of(247, 205, 93);
	public static final Color BLACK = new Color();// null red and null color temp == OFF


	public static final Color[] CHRISTMAS = { RED, GREEN };
	
	
	
}	
