package main;

import java.util.ArrayList;
import java.util.Map;


public class VersionInfo {
	private int majarNumber;
	private int minerNumber;
	private int fixNumber;
	String versionString;
	ArrayList<Integer> versionArray = new ArrayList<Integer>();// major~fixの番号を格納
	private Map<String, Integer> WordCount;

	public VersionInfo(String versionString, Map<String, Integer> WordCount) {
		//数字以外のアルファベットを消す
		this.versionString = versionString.replaceAll("[a-z]|[A-Z]", "");
		this.WordCount = WordCount;
//		for (String number : this.versionString.split(Pattern.quote("."))) {
//			versionArray.add(Integer.parseInt(number));
//		}
//		if(versionArray.size() > 0){
//			majarNumber = versionArray.get(0);
//		}
//		if (versionArray.size() >= 2) {
//			minerNumber = versionArray.get(1);
//		}
//		if (versionArray.size() >= 3) {
//			fixNumber = versionArray.get(2);
//		}
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
