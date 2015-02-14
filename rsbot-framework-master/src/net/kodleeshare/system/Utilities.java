package net.kodleeshare.system;

import java.lang.reflect.Array;

public class Utilities {
	private Utilities(){}
	
	/**
	 * @param c The array class
	 * @param array The array to copy
	 * @return The copy of the array
	 */
	public static final <E> E[] cloneArray(Class<E> c, E[] array) {
		@SuppressWarnings("unchecked")
		E[] a = (E[]) Array.newInstance(c, array.length);
		System.arraycopy(array, 0, a, 0, array.length);
		return a;
	}
	
	public static class MinMaxPair {
		public MinMaxPair(int defaultMin, int defaultMax) {
			this.min = defaultMin;
			this.max = defaultMax;
		}
		public int min;
		public int max;
	}
}
