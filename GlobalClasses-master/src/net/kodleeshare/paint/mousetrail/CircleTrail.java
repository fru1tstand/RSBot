package net.kodleeshare.paint.mousetrail;

import java.awt.Color;
import java.awt.Graphics2D;

public class CircleTrail extends MouseTrail
{
	private int	circleSize	= 1;

	public CircleTrail(int length, int frequency, int circleSize)
	{
		super(length, frequency);
		this.circleSize = circleSize;
	}

	@Override
	public void paint(Graphics2D g, Color c, int transparency, boolean fadeEnd)
	{
		for (int i = 0; i < this.length_trail; i++)
		{
			if ((this.trail_points[i] == null)
					|| (i
							% this.frequency != 0))
				continue;
			g.setColor(MouseTrail.getColor(fadeEnd, i, this.length_trail, this.current_pointer, transparency, c));
			g.fillOval(this.trail_points[i].x
					- (this.circleSize / 2), this.trail_points[i].y
					- (this.circleSize / 2), this.circleSize, this.circleSize);
		}
	}

}
