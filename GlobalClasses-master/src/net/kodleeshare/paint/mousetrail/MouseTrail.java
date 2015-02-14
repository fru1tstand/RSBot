package net.kodleeshare.paint.mousetrail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class MouseTrail
{
	protected int		length_trail;
	protected Point[]	trail_points;
	protected int		current_pointer	= 0;
	protected int		frequency		= 1;

	public MouseTrail(int length, int frequency)
	{
		this.length_trail = length;
		this.trail_points = new Point[length];
		this.frequency = (frequency == 0 || frequency > length) ? 1 : frequency;
	}

	public void addPos(Point p)
	{
		this.trail_points[(this.current_pointer++ % this.length_trail)] = p;
		this.current_pointer = this.current_pointer
				% this.length_trail;
	}

	public abstract void paint(Graphics2D g, Color c, int transparency, boolean fadeEnd);

	public static Color getColor(boolean fadeEnd, int i, int length, int currPos, int transparency, Color c)
	{
		if (fadeEnd)
			return new Color(c.getRed(), c.getGreen(), c.getBlue(), (transparency * (((i + 1 > currPos) ? i
					- length : i)
					+ length - currPos))
					/ length);
		else
			return c;
	}
}
