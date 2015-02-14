package net.kodleeshare.generic;

public class Urn
{
	public static final Urn		SMELT_CRACKED		= new Urn("Cracked", "Smelting urn", 20271, 20272, 20273, 20274, 4, 5, 0, 7028);
	public static final Urn		SMELT_FRAGILE		= new Urn("Fragile", "Smelting urn", 20277, 20278, 20279, 20280, 17, 5, 4, 7028);
	public static final Urn		SMELT_NORMAL		= new Urn("Normal", "Smelting urn", 20283, 20284, 20285, 20286, 35, 5, 8, 7028);
	public static final Urn		SMELT_STRONG		= new Urn("Strong", "Smelting urn", 20289, 20290, 20291, 20292, 49, 5, 12, 7028);

	public static final Urn		WOOD_CRACKED		= new Urn("Cracked", "Woodcutting urn", 20295, 20296, 20297, 20298, 4, 6, 0, 7031);
	public static final Urn		WOOD_FRAGILE		= new Urn("Fragile", "Woodcutting urn", 20301, 20302, 20303, 20304, 15, 6, 4, 7031);
	public static final Urn		WOOD_NORMAL			= new Urn("Normal", "Woodcutting urn", 20307, 20308, 20309, 20310, 44, 6, 8, 7031);
	public static final Urn		WOOD_STRONG			= new Urn("Strong", "Woodcutting urn", 20313, 20314, 20315, 20316, 61, 6, 12, 7031);

	public static final Urn		FISH_CRACKED		= new Urn("Cracked", "Fishing urn", 20319, 20320, 20321, 20322, 3, 2, 0, 7019);
	public static final Urn		FISH_FRAGILE		= new Urn("Fragile", "Fishing urn", 20325, 20326, 20327, 20328, 15, 2, 4, 7019);
	public static final Urn		FISH_NORMAL			= new Urn("Normal", "Fishing urn", 20331, 20332, 20333, 20334, 41, 2, 8, 7019);
	public static final Urn		FISH_STRONG			= new Urn("Strong", "Fishing urn", 20337, 20338, 20339, 20340, 53, 2, 12, 7019);
	public static final Urn		FISH_DECORATED		= new Urn("Decorated", "Fishing urn", 20343, 20344, 20345, 20346, 76, 2, 16, 7019);

	public static final Urn		COOK_CRACKED		= new Urn("Cracked", "Cooking urn", 20349, 20350, 20351, 20352, 2, 1, 0, 7016);
	public static final Urn		COOK_FRAGILE		= new Urn("Fragile", "Cooking urn", 20355, 20356, 20357, 20358, 12, 1, 4, 7016);
	public static final Urn		COOK_NORMAL			= new Urn("Normal", "Cooking urn", 20361, 20362, 20363, 20364, 36, 1, 8, 7016);
	public static final Urn		COOK_STRONG			= new Urn("Strong", "Cooking urn", 20367, 20368, 20369, 20370, 51, 1, 12, 7016);
	public static final Urn		COOK_DECORATED		= new Urn("Decorated", "Cooking urn", 20373, 20374, 20375, 20376, 81, 1, 16, 7016);

	public static final Urn		MINING_CRACKED		= new Urn("Cracked", "Mining urn", 20379, 20380, 20381, 20382, 1, 3, 0, 7022);
	public static final Urn		MINING_FRAGILE		= new Urn("Fragile", "Mining urn", 20385, 20386, 20387, 20388, 17, 3, 4, 7022);
	public static final Urn		MINING_NORMAL		= new Urn("Normal", "Mining urn", 20391, 20392, 20393, 20394, 32, 3, 8, 7022);
	public static final Urn		MINING_STRONG		= new Urn("Strong", "Mining urn", 20397, 20398, 20399, 20400, 48, 3, 12, 7022);
	public static final Urn		MINING_DECORATED	= new Urn("Decorated", "Mining urn", 20403, 20404, 20405, 20406, 59, 3, 16, 7022);

	public static final Urn		PRAYER_IMPIOUS		= new Urn("Impious", "Prayer urn", 20409, 20410, 20411, 20412, 2, 4, 0, 7025);
	public static final Urn		PRAYER_ACCURSED		= new Urn("Accursed", "Prayer urn", 20415, 20416, 20417, 20418, 26, 4, 4, 7025);
	public static final Urn		PRAYER_INFERNAL		= new Urn("Infernal", "Prayer urn", 20421, 20422, 20423, 20424, 62, 4, 8, 7025);

	public static final Urn[]	URNS_ALL_ALPHA		= { Urn.COOK_CRACKED, Urn.COOK_FRAGILE, Urn.COOK_NORMAL, Urn.COOK_STRONG, Urn.COOK_DECORATED, Urn.FISH_CRACKED, Urn.FISH_FRAGILE, Urn.FISH_NORMAL, Urn.FISH_STRONG, Urn.FISH_DECORATED, Urn.MINING_CRACKED, Urn.MINING_FRAGILE, Urn.MINING_NORMAL, Urn.MINING_STRONG, Urn.MINING_DECORATED, Urn.PRAYER_IMPIOUS, Urn.PRAYER_ACCURSED, Urn.PRAYER_INFERNAL, Urn.SMELT_CRACKED, Urn.SMELT_FRAGILE, Urn.SMELT_NORMAL, Urn.SMELT_STRONG, Urn.WOOD_CRACKED,
			Urn.WOOD_FRAGILE, Urn.WOOD_NORMAL, Urn.WOOD_STRONG };

	public static final Urn[]	URNS_SMELT			= { Urn.SMELT_CRACKED, Urn.SMELT_FRAGILE, Urn.SMELT_NORMAL, Urn.SMELT_STRONG };
	public static final Urn[]	URNS_WOOD			= { Urn.WOOD_CRACKED, Urn.WOOD_FRAGILE, Urn.WOOD_NORMAL, Urn.WOOD_STRONG };
	public static final Urn[]	URNS_FISH			= { Urn.FISH_CRACKED, Urn.FISH_FRAGILE, Urn.FISH_NORMAL, Urn.FISH_STRONG, Urn.FISH_DECORATED };
	public static final Urn[]	URNS_COOK			= { Urn.COOK_CRACKED, Urn.COOK_FRAGILE, Urn.COOK_NORMAL, Urn.COOK_STRONG, Urn.COOK_DECORATED };
	public static final Urn[]	URNS_MINING			= { Urn.MINING_CRACKED, Urn.MINING_FRAGILE, Urn.MINING_NORMAL, Urn.MINING_STRONG, Urn.MINING_DECORATED };
	public static final Urn[]	URNS_PRAYER			= { Urn.PRAYER_IMPIOUS, Urn.PRAYER_ACCURSED, Urn.PRAYER_INFERNAL };

	private static int[]		all_id_unf			= null;
	private static int[]		all_id_nr			= null;

	public static int[] getAllIDunf()
	{
		if (Urn.all_id_unf == null
				|| Urn.all_id_nr == null)
		{
			Urn.setAllIDs();
		}
		return Urn.all_id_unf;
	}

	public static int[] getAllIDnr()
	{
		if (Urn.all_id_unf == null
				|| Urn.all_id_nr == null)
		{
			Urn.setAllIDs();
		}
		return Urn.all_id_nr;
	}

	public static void setAllIDs()
	{
		Urn.all_id_nr = new int[Urn.URNS_ALL_ALPHA.length];
		Urn.all_id_unf = new int[Urn.URNS_ALL_ALPHA.length];
		int i = 0;
		for (Urn theUrn : Urn.URNS_ALL_ALPHA)
		{
			Urn.all_id_nr[i] = theUrn.getIDnr();
			Urn.all_id_unf[i] = theUrn.getIDunf();
			i++;
		}
	}

	private String	s_type;
	private String	s_strength;
	private int		id_unf;			// Value of setting 1170 if selected
	private int		id_nr;
	private int		id_nr_noted;
	private int		id_r;
	private int		level;
	private int		widget_type;		// 1371 -> 62 -> widget_child [Select type of urn]
	private int		widget_strength;	// 1371 -> 44 -> widget_subchild [Selects grade of urn]
	private int		setting_type;		// 1169

	public Urn(String sstr, String stype, int id_unf, int id_nr, int id_nr_noted, int id_r, int level, int widget_urn_type, int widget_urn_strength, int setting_type)
	{
		this.s_type = stype;
		this.s_strength = sstr;
		this.id_unf = id_unf;
		this.id_nr = id_nr;
		this.id_nr_noted = id_nr_noted;
		this.id_r = id_r;
		this.level = level;
		this.widget_type = widget_urn_type;
		this.widget_strength = widget_urn_strength;
		this.setting_type = setting_type;
	}

	public String getName()
	{
		return this.s_strength
				+ " "
				+ this.s_type;
	}

	public int getIDunf()
	{
		return this.id_unf;
	}

	public int getIDnr()
	{
		return this.id_nr;
	}

	public int getIDnr_noted()
	{
		return this.id_nr_noted;
	}

	public int getIDr()
	{
		return this.id_r;
	}

	public int getLevel()
	{
		return this.level;
	}

	public int getWidgetType()
	{
		return this.widget_type;
	}

	public int getWidgetStrength()
	{
		return this.widget_strength;
	}

	public int getSettingType()
	{
		return this.setting_type;
	}

	@Override
	public String toString()
	{
		return this.s_type
				+ ": "
				+ this.s_strength;
	}
}
