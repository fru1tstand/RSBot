package net.kodleeshare.paint.mouse;

import java.awt.Color;
import java.awt.Graphics2D;

import org.powerbot.game.api.methods.input.Mouse;

public class BasicCross
{
	public BasicCross() {}
	
	// graphics, Width, height, angle, color
	/**
	 * 
	 * @param g
	 * @param i1
	 *            Length of horizontal component of cross
	 * @param i2
	 *            Length of vertical component of cross
	 * @param i3
	 *            Angle of cross in degrees
	 * @param c1
	 *            Color of horizontal component of cross
	 * @param c2
	 *            Color of vertical component of cross
	 */
	public static void Paint(Graphics2D g, int i1, int i2, int i3, Color c1,
			Color c2)
	{
		double angleRad = i3 * Math.PI / 180;
		g.setColor(c1);
		g.drawLine((int) Math.round(Mouse.getX() - (i1 * Math.cos(angleRad))),
				(int) Math.round(Mouse.getY() - (i1 * Math.sin(angleRad))),
				(int) Math.round(Mouse.getX() + (i1 * Math.cos(angleRad))),
				(int) Math.round(Mouse.getY() + (i1 * Math.sin(angleRad))));
		g.setColor(c2);
		g.drawLine((int) Math.round(Mouse.getX() - (i2 * Math.sin(angleRad))),
				(int) Math.round(Mouse.getY() + (i2 * Math.cos(angleRad))),
				(int) Math.round(Mouse.getX() + (i2 * Math.sin(angleRad))),
				(int) Math.round(Mouse.getY() - (i2 * Math.cos(angleRad))));
	}
	
	/**
	 * 
	 * @param g
	 * @param i1
	 *            Length of horizontal component of cross
	 * @param i2
	 *            Length of vertical component of cross
	 * @param i3
	 *            Angle of cross in degrees
	 * @param c1
	 *            Color of cross
	 */
	public static void Paint(Graphics2D g, int i1, int i2, int i3, Color c1)
	{
		BasicCross.Paint(g, i1, i2, i3, c1, c1);
	}
	
	/**
	 * 
	 * @param g
	 * @param i1
	 *            Length of cross legs
	 * @param i2
	 *            Angle of cross in degrees
	 * @param c1
	 *            Color of cross
	 */
	public static void Paint(Graphics2D g, int i1, int i2, Color c1)
	{
		BasicCross.Paint(g, i1, i1, i2, c1, c1);
	}
}
