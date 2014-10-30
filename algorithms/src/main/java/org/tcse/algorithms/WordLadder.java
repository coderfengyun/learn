package org.tcse.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

public class WordLadder {
	private Set<String> dict;

	public List<List<String>> findLadders(String start, String end,
			Set<String> dict) {
		if (start == null || end == null || start.length() != end.length()) {
			return new ArrayList<List<String>>();
		}
		this.dict = dict;
		return broadFirstSearch(start, end);
	}

	private List<List<String>> broadFirstSearch(String start, String end) {
		List<List<String>> result = new ArrayList<List<String>>();
		Queue<WordInPath> currentQueue = new LinkedList<WordInPath>(), nextQueue = new LinkedList<WordInPath>();
		Map<String, Integer> footPrints = new HashMap<String, Integer>();
		currentQueue.offer(new WordInPath(start, null));
		footPrints.put(start, 0);
		boolean found = false;
		while (currentQueue.peek() != null) {
			WordInPath current = currentQueue.poll();
			if (current.reach(end)) {
				result.add(current.buildPath());
				found = true;
				continue;
			}
			if (found) {
				continue;
			}
			for (String neighbor : current.getNeighbors()) {
				if ((!footPrints.containsKey(neighbor))
						|| (current.length() <= footPrints.get(neighbor))) {
					footPrints.put(neighbor, current.length());
					nextQueue.offer(new WordInPath(neighbor, current));
				}
			}
			if (currentQueue.peek() == null) {
				Queue<WordInPath> temp = currentQueue;
				currentQueue = nextQueue;
				nextQueue = temp;
			}
		}
		return result;
	}

	private class WordInPath {
		private final String word;
		private final WordInPath previous;
		private final int size;

		public WordInPath(String word, WordInPath previous) {
			this.word = word;
			this.previous = previous;
			this.size = previous == null ? 1 : previous.length() + 1;
		}

		public List<String> buildPath() {
			LinkedList<String> result = new LinkedList<String>();
			WordInPath current = this;
			while (current != null) {
				result.push(current.word);
				current = current.previous;
			}
			return result;
		}

		public int length() {
			return this.size;
		}

		public boolean reach(String target) {
			return this.word.equals(target);
		}

		private Set<String> getNeighbors() {
			Set<String> result = new HashSet<String>();
			StringBuilder changed = new StringBuilder(this.word);
			for (int i = 0; i < this.word.length(); i++) {
				char ch = this.word.charAt(i);
				for (char c = 'a'; c < 'z'; c++) {
					if (c == ch) {
						continue;
					}
					changed.setCharAt(i, c);
					String temp = changed.toString();
					if (WordLadder.this.dict.contains(temp)) {
						result.add(temp);
					}
					changed.setCharAt(i, ch);
				}
			}
			return result;
		}
	}

	@Test
	public void test() {
		List<List<String>> result = this.findLadders(
				"nape",
				"mild",
				new HashSet<String>(Arrays.asList("dose", "ends", "dine",
						"jars", "prow", "soap", "guns", "hops", "cray", "hove",
						"ella", "hour", "lens", "jive", "wiry", "earl", "mara",
						"part", "flue", "putt", "rory", "bull", "york", "ruts",
						"lily", "vamp", "bask", "peer", "boat", "dens", "lyre",
						"jets", "wide", "rile", "boos", "down", "path", "onyx",
						"mows", "toke", "soto", "dork", "nape", "mans", "loin",
						"jots", "male", "sits", "minn", "sale", "pets", "hugo",
						"woke", "suds", "rugs", "vole", "warp", "mite", "pews",
						"lips", "pals", "nigh", "sulk", "vice", "clod", "iowa",
						"gibe", "shad", "carl", "huns", "coot", "sera", "mils",
						"rose", "orly", "ford", "void", "time", "eloy", "risk",
						"veep", "reps", "dolt", "hens", "tray", "melt", "rung",
						"rich", "saga", "lust", "yews", "rode", "many", "cods",
						"rape", "last", "tile", "nosy", "take", "nope", "toni",
						"bank", "jock", "jody", "diss", "nips", "bake", "lima",
						"wore", "kins", "cult", "hart", "wuss", "tale", "sing",
						"lake", "bogy", "wigs", "kari", "magi", "bass", "pent",
						"tost", "fops", "bags", "duns", "will", "tart", "drug",
						"gale", "mold", "disk", "spay", "hows", "naps", "puss",
						"gina", "kara", "zorn", "boll", "cams", "boas", "rave",
						"sets", "lego", "hays", "judy", "chap", "live", "bahs",
						"ohio", "nibs", "cuts", "pups", "data", "kate", "rump",
						"hews", "mary", "stow", "fang", "bolt", "rues", "mesh",
						"mice", "rise", "rant", "dune", "jell", "laws", "jove",
						"bode", "sung", "nils", "vila", "mode", "hued", "cell",
						"fies", "swat", "wags", "nate", "wist", "honk", "goth",
						"told", "oise", "wail", "tels", "sore", "hunk", "mate",
						"luke", "tore", "bond", "bast", "vows", "ripe", "fond",
						"benz", "firs", "zeds", "wary", "baas", "wins", "pair",
						"tags", "cost", "woes", "buns", "lend", "bops", "code",
						"eddy", "siva", "oops", "toed", "bale", "hutu", "jolt",
						"rife", "darn", "tape", "bold", "cope", "cake", "wisp",
						"vats", "wave", "hems", "bill", "cord", "pert", "type",
						"kroc", "ucla", "albs", "yoko", "silt", "pock", "drub",
						"puny", "fads", "mull", "pray", "mole", "talc", "east",
						"slay", "jamb", "mill", "dung", "jack", "lynx", "nome",
						"leos", "lade", "sana", "tike", "cali", "toge", "pled",
						"mile", "mass", "leon", "sloe", "lube", "kans", "cory",
						"burs", "race", "toss", "mild", "tops", "maze", "city",
						"sadr", "bays", "poet", "volt", "laze", "gold", "zuni",
						"shea", "gags", "fist", "ping", "pope", "cora", "yaks",
						"cosy", "foci", "plan", "colo", "hume", "yowl", "craw",
						"pied", "toga", "lobs", "love", "lode", "duds", "bled",
						"juts", "gabs", "fink", "rock", "pant", "wipe", "pele",
						"suez", "nina", "ring", "okra", "warm", "lyle", "gape",
						"bead", "lead", "jane", "oink", "ware", "zibo", "inns",
						"mope", "hang", "made", "fobs", "gamy", "fort", "peak",
						"gill", "dino", "dina", "tier")));
		for (int i = 0; i < result.size(); i++) {
			for (String word : result.get(i)) {
				System.out.print(word + " ");
			}
			System.out.println();
		}
	}
}
