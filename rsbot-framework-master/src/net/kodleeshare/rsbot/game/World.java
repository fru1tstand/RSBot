package net.kodleeshare.rsbot.game;

import net.kodleeshare.system.Utilities;

import org.powerbot.script.Random;

public class World {
	private World() {}
	
	//******************************************************** Reaction Time
	private static final int REACTION_TIME_POOL_SIZE = 100;
	private static final int REACTION_TIME_MIN = 25;
	private static final int REACTION_TIME_MAX = 300000; //5 minutes in millis
	
	private static int[] reactionTime;
	private static int reactionTimePointer;
	/**
	 * @return Average reaction time in milliseconds
	 */
	public static int getReactionTimeAverage() {
		int count, value;
		count = value = 0;
		reactionTimeCreateIfNull();
		for(int i : reactionTime) {
			if(i != 0) {
				value += i;
				count++;
			}
		}
		return Math.min(Math.max(value / ((count == 0) ? 1 : count), REACTION_TIME_MIN), REACTION_TIME_MAX);
	}
	public static void addReactionTime(int i) {
		reactionTimeCreateIfNull();
		reactionTime[reactionTimePointer++] = i;
		reactionTimePointer %= REACTION_TIME_POOL_SIZE;
	}
	public static Utilities.MinMaxPair getMinMaxReactionTime() {
		Utilities.MinMaxPair minMax = new Utilities.MinMaxPair(REACTION_TIME_MAX, REACTION_TIME_MIN);
		reactionTimeCreateIfNull();
		for(int i: reactionTime) {
			if(i > 0 && i < minMax.min)
				minMax.min = i;
			if(i > minMax.max)
				minMax.max = i;
		}
		if(minMax.min >= minMax.max)
			minMax.min = minMax.max++;
		return minMax;
	}
	/**
	 * @return A typical time period before another action should occur
	 */
	public static int getActionWaitTime() {
		int sd = 0;
		int elems = 0;
		int av = getReactionTimeAverage();
		Utilities.MinMaxPair mm = getMinMaxReactionTime();
		for (int i : reactionTime) {
			if(i != 0) {
				elems++;
				sd += Math.pow((i - av), 2);
			}
		}
		sd /= ((elems == 0) ? 1 : elems);
		int result = Random.nextGaussian(mm.min, mm.max, av, Math.sqrt(sd)) + Random.getDelay();
		return result;
	}
	private static void reactionTimeCreateIfNull() {
		if(reactionTime == null) {
			reactionTime = new int[REACTION_TIME_POOL_SIZE];
			reactionTimePointer = 0;
		}
	}
}
