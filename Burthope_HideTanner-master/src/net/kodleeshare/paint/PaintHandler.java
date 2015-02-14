package net.kodleeshare.paint;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Kodlee
 * @version 1.1
 */
public class PaintHandler
{
	public enum Line
	{
		ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);
		private final int	value;

		Line(int value)
		{
			this.value = value;
		}

		public int value()
		{
			return this.value;
		}
	}

	private static String[]	text_display_left	= new String[7];
	private static String[]	text_display_right	= new String[7];

	public PaintHandler()
	{}

	public static void paint(Graphics2D g, boolean displayGraph)
	{
		int paint_height = 0;

		if (PaintVars.displayPaint)
		{
			// Draw graph
			if (displayGraph)
			{
				g.setColor(new Color(0, 0, 50, 200));
				g.fillRect(PaintVars.LOC_PAINT_LEFT - 2, PaintVars.LOC_PAINT_TOP - 11, PaintVars.LOC_PAINT_RIGHT
						- PaintVars.LOC_PAINT_LEFT
						+ 3, 75 + 12);
				Graph.draw(g, PaintVars.LOC_PAINT_LEFT - 2, PaintVars.LOC_PAINT_TOP - 11, PaintVars.LOC_PAINT_RIGHT
						- PaintVars.LOC_PAINT_RIGHT
						+ 3, 75 + 12, 2, "%");
			}

			// Draw background color
			g.setColor(new Color(0, 0, 0, 125));
			paint_height = PaintVars.LOC_PAINT_TOP + 75;

			// draw strings
			g.setColor(Color.CYAN);
			int line = -1;
			for (String dispString : PaintHandler.text_display_left)
			{
				line++;
				if (line == 0)
					continue;
				if (dispString == null)
					continue;
				g.drawString(dispString, PaintVars.LOC_PAINT_LEFT, PaintVars.LOC_PAINT_TOP
						+ (PaintVars.SCALE_FONTSIZE * line));
			}
			line = -1;
			for (String dispString : PaintHandler.text_display_right)
			{
				line++;
				if (dispString == null)
					continue;
				g.drawString(dispString, PaintVars.LOC_PAINT_RIGHT
						- g.getFontMetrics().stringWidth(dispString), PaintVars.LOC_PAINT_TOP
						+ (PaintVars.SCALE_FONTSIZE * line));
			}
		} else
		{
			// draw background color
			g.setColor(new Color(0, 0, 0, 200));
			paint_height = (PaintVars.SCALE_FONTSIZE * 1) + 2;
			g.fillRect(PaintVars.LOC_PAINT_LEFT - 2, PaintVars.LOC_PAINT_TOP - 11, PaintVars.LOC_PAINT_RIGHT
					- PaintVars.LOC_PAINT_LEFT
					+ 3, paint_height);
			g.setColor(Color.CYAN);

		}

		// always draw title
		g.setColor(Color.CYAN);
		g.drawString(PaintHandler.text_display_left[0], PaintVars.LOC_PAINT_LEFT, PaintVars.LOC_PAINT_TOP);
	}

	public static void setDisplayTitle(String text)
	{
		PaintHandler.text_display_left[0] = text;
	}

	public static void setDisplayString(String text, Line line, boolean leftSide)
	{
		if (line.value() > 6
				|| line.value() < 1)
			return;
		if (leftSide)
			PaintHandler.text_display_left[line.value()] = text;
		else
			PaintHandler.text_display_right[line.value()] = text;

	}
}
