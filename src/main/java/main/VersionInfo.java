package main;

import java.util.*;
import java.util.regex.Pattern;


public class VersionInfo {
	private int majarNumber;
	private int minerNumber;
	private int fixNumber;
	String versionString;
	ArrayList<Integer> versionArray = new ArrayList<Integer>();// major~fixの番号を格納
	private Map<String, Integer> WordCount;

	public VersionInfo(String versionString, Map<String, Integer> WordCount) {
		this.versionString = versionString;
		this.WordCount = WordCount;
		for (String number : versionString.split(Pattern.quote("."))) {
			versionArray.add(Integer.parseInt(number));
		}
		if(versionArray.size() > 0){
			majarNumber = versionArray.get(0);
		}
		if (versionArray.size() >= 2) {
			minerNumber = versionArray.get(1);
		}
		if (versionArray.size() >= 3) {
			fixNumber = versionArray.get(2);
		}
	}

	public int getMajar() {
		return majarNumber;
	}

	public int getMiner() {
		return minerNumber;
	}

	public int getFix() {
		return fixNumber;
	}
	public Map<String, Integer> getWordCount(){
		return WordCount;
	}
}
