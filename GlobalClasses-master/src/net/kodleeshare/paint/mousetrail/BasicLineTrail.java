package net.kodleeshare.paint.mousetrail;

import java.awt.Color;
import java.awt.Graphics2D;

public class BasicLineTrail extends MouseTrail
{
	public BasicLineTrail(int length, int frequency)
	{
		super(length, frequency);
	}

	@Override
	public void paint(Graphics2D g, Color c, int transparency, boolean fadeEnd)
	{
		for (int i = 0; i < this.length_trail; i++)
		{
			if ((this.trail_points[i] == null)
					|| (this.trail_points[((i + 1) % this.length_trail)] == null)
					|| (((i + 1) % this.length_trail) == this.current_pointer)
					|| (i
							% this.frequency != 0))
				continue;
			g.setColor(MouseTrail.getColor(fadeEnd, i, this.length_trail, this.current_pointer, transparency, c));
			g.drawLine(this.trail_points[i].x, this.trail_points[i].y, this.trail_points[((i + 1) % this.length_trail)].x, this.trail_points[((i + 1) % this.length_trail)].y);
		}
	}
}
