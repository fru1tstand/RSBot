package net.kodleeshare.paint;

import java.awt.Font;

public class PaintVars
{
	// Global
	public static final Font	TXT_STATS		= new Font("Verdana", 0, 11);
	public static final int		LOC_PAINT_TOP	= 300;
	public static final int		LOC_PAINT_LEFT	= 257;
	public static final int		LOC_PAINT_RIGHT	= 515;
	public static final int		LOC_INV_TOP		= 265;
	public static final int		LOC_INV_LEFT	= 550;
	public static final int		SCALE_FONTSIZE	= 12;

	// Non-constant global
	public static int			degreeRotation	= 0;
	public static String		status			= "Nothing";
	public static boolean		displayPaint	= true;

	public PaintVars()
	{}
}
