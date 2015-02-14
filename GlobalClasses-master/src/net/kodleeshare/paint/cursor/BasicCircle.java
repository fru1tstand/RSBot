package net.kodleeshare.paint.cursor;

import java.awt.Color;
import java.awt.Graphics2D;

import org.powerbot.game.api.methods.input.Mouse;

public class BasicCircle
{
	// radius, thickness, color,
	
	/*
	 * No initialization
	 */
	public BasicCircle() {}
	
	/**
	 * Draws a circle centered around the mouse
	 * 
	 * @param g
	 * @param r1
	 *            Radius of circle
	 * @param t1
	 *            Thickness of circle <strong>*unused</strong>
	 * @param c1
	 *            Color of circle
	 */
	public static void Paint(Graphics2D g, int r1, int t1, Color c1)
	{
		g.setColor(c1);
		g.drawOval(Mouse.getX() - r1, Mouse.getY() - r1, r1 * 2, r1 * 2);
	}
}
