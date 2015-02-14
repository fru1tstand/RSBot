package net.kodleeshare.paint;

import java.awt.Color;
import java.awt.Graphics2D;

import org.powerbot.game.api.util.Timer;

/**
 * @author Kodlee
 * @version 1.1
 */
public class Graph
{
	public static Timer		updateTimer			= new Timer(1000);
	public static int		updateTimerInterval	= 1000;
	public static float[]	data				= new float[258];	// stored as percents

	public static void addData(float x)
	{
		if (!Graph.updateTimer.isRunning())
		{
			System.arraycopy(Graph.data, 1, Graph.data, 0, Graph.data.length - 1);
			Graph.data[257] = x;
			Graph.updateTimer = new Timer(Graph.updateTimerInterval);
		}
	}

	public static void draw(Graphics2D g, int x, int y, int width, int height, int precision, String suffix)
	{

		g.setColor(new Color(0, 0, 50, 200));
		g.fillRect(x, y, width, height);

		g.setColor(new Color(124, 66, 29));

		float min = Graph.data[257], max = Graph.data[257];
		for (float currFloat : Graph.data)
		{
			if (currFloat > max)
				max = currFloat;
			if (currFloat < min
					&& currFloat != 0.0f)
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
		for (float currFloat : Graph.data)
		{
			xPos++;

			if (currFloat == 0.0f)
				continue;

			yHeight = (int) Math.round(((currFloat - min)
					/ (max - min) * height));
			g.drawRect(x
					+ xPos, y
					+ height
					- yHeight, 1, 1);
		}
		String s;
		s = Double.toString((double) Math.round(Graph.data[257]
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
