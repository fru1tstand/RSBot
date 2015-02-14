package net.kodleeshare.paint.graph;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author Kodlee
 * @version 1.0
 * 
 */
public class PointGraph extends Graph
{
	public PointGraph(int graph_interval, int arraylength)
	{
		super(graph_interval, arraylength);
	}

	@Override
	public void draw(Graphics2D g, int x, int y, int width, int height, int precision, String suffix)
	{

		g.setColor(new Color(0, 0, 50, 200));
		g.fillRect(x, y, width, height);

		g.setColor(new Color(124, 66, 29));

		float max = this.data[0], min = (float) (max - .01);
		for (Float currFloat : this.data)
		{
			if (currFloat == null)
				continue;
			if (currFloat > max)
				max = currFloat;
			if (currFloat < min)
				min = currFloat;
		}
		float diff = max
				- min;
		max += (diff * .1);
		min -= (diff * .1);
		if (min >= max)
			min = max - 1;

		int xPos = -1;
		int yHeight = 0;
		for (int i = this.current_pointer
				+ this.arraylength; i > (this.current_pointer); i--)
		{
			xPos++;
			if (this.data[i
					% this.arraylength] == null)
				continue;
			yHeight = (int) Math.round(((this.data[i
					% this.arraylength] - min)
					/ (max - min) * height));
			g.drawRect(x
					+ xPos, y
					+ height
					- yHeight, 1, 1);
		}

		yHeight = (int) Math.round(((this.data[((this.current_pointer != 0) ? this.current_pointer - 1 : 0)] - min)
				/ (max - min) * height));

		String s;
		s = Double.toString((double) Math.round(this.data[((this.current_pointer != 0) ? this.current_pointer - 1 : 0)]
				* Math.pow(10, precision))
				/ Math.pow(10, precision))
				+ suffix;

		int sw = g.getFontMetrics().stringWidth(s); // m
		int sh = g.getFontMetrics().getHeight(); // t
		int ft = y
				+ height
				- yHeight; // n

		int pl = 3; // padding left
		int pr = 3; // right
		int pt = 1; // top
		int pb = 1; // bot

		int ah = 10; // arrow height
		int aw = 10; // arrow width

		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(x
				- pr
				- sw
				- pl
				- aw, ft
				- ah
				+ 2, sw
				+ pr
				+ pl, sh
				+ pt
				+ pb);
		g.setColor(new Color(0, 0, 0, 150));
		g.fillPolygon(new int[] { x
				- aw, x, x
				- aw }, new int[] { ft
				- (ah / 2), ft, ft
				+ (ah / 2) }, 3);

		g.setColor(Color.WHITE);
		g.drawString(s, x
				- pr
				- sw
				- aw, ft
				+ (ah / 2));
	}
}
