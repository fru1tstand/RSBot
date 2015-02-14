package net.kodleeshare.paint.graph;

import java.awt.Graphics2D;

import org.powerbot.game.api.util.Timer;

/**
 * @author Kodlee
 * @version 1.2
 */
public abstract class Graph
{
	protected Timer		timer_graphUpdate;
	protected int		set_timerInterval;
	protected Float[]	data;
	protected int		arraylength		= 0;
	protected int		current_pointer	= 0;

	public Graph(int graph_interval, int arraylength)
	{
		this.timer_graphUpdate = new Timer(graph_interval);
		this.set_timerInterval = graph_interval;
		this.data = new Float[arraylength];
		this.arraylength = arraylength;
	}

	public void addData(float x)
	{
		if (!this.timer_graphUpdate.isRunning())
		{
			this.data[((this.current_pointer++) % this.arraylength)] = x;
			this.current_pointer = this.current_pointer
					% this.arraylength;
			this.timer_graphUpdate = new Timer(this.set_timerInterval);
		}
	}

	public abstract void draw(Graphics2D g, int x, int y, int width, int height, int precision, String suffix);
}
