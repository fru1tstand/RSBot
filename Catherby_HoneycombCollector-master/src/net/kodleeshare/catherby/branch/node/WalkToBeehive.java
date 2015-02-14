package net.kodleeshare.catherby.branch.node;

import net.kodleeshare.catherby.branch.GetHoneycomb;
import net.kodleeshare.generic.Global;
import net.kodleeshare.generic.Utils;
import net.kodleeshare.generic.Utils.Cond;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class WalkToBeehive extends Node
{
	private Tile	t	= null;

	@Override
	public boolean activate()
	{
		return !GetHoneycomb.AREA_BEEHIVES.contains(Players.getLocal());
	}

	@Override
	public void execute()
	{
		Global.updateStatus("Walking to beehives");
		if (!GetHoneycomb.AREA_WALKTOFENCE.contains(Players.getLocal()))
			this.t = Utils.walkToArea(GetHoneycomb.AREA_WALKTOFENCE, this.t);
		else if (GetHoneycomb.AREA_WALKTOFENCE.contains(Players.getLocal())
				&& getFence() != null)
			openGate();
		else
			this.t = Utils.walkToArea(GetHoneycomb.AREA_INSIDEWALK, this.t);

	}

	public static boolean openGate()
	{
		final SceneObject s = getFence();
		if (s == null)
			return true;
		Camera.turnTo(s);
		s.interact("Open");
		if (!Utils.waitFor(new Cond()
		{
			public boolean accept()
			{
				return getFence() == null;
			}
		}, Random.nextInt(1200, 1400)))
			return false;
		return true;
	}

	public static SceneObject getFence()
	{
		return SceneEntities.getNearest(new Filter<SceneObject>()
		{
			@Override
			public boolean accept(SceneObject s)
			{
				return s.getId() == GetHoneycomb.ID_FENCE_CLOSED
						&& s.getLocation().equals(GetHoneycomb.TILE_FENCE);
			}
		});

	}
}
