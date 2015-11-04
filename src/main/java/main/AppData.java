package main;

import htmlParser.MapDescendingSort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppData {
	String name;

	Map<String, Integer> totalWordCount = new HashMap<String, Integer>();
	List<VersionInfo> versionInfos = new ArrayList<VersionInfo>();//versionごとの情報を管理

	public AppData(List<VersionInfo> versionInfos,String name) {
		//wordCount・versionInfosオブジェクトを作って初期化
		this.versionInfos = versionInfos;
		countTotalWord();
		this.totalWordCount = MapDescendingSort.descendingSort(this.totalWordCount);
		this.name = name;
	};

	// 各ワードの合計出現個数をカウントする
	Map<String, Integer> countTotalWord() {
		Map<String, Integer> WordCount = new HashMap<String, Integer>();
		// 各バージョンごとからwordCountを取り出してtotalWordCountに追加する
		for (VersionInfo vi : versionInfos) {
			WordCount = vi.getWordCount();
			// あるバージョンの中に出現するワードの個数を全体の個数に追加する
			for (String keyWord : WordCount.keySet()) {
				if (totalWordCount.containsKey(keyWord)) {
					Integer count = totalWordCount.get(keyWord);
					count += WordCount.get(keyWord);
					totalWordCount.put(keyWord, count);
				} else {
					totalWordCount.put(keyWord, WordCount.get(keyWord));
				}
			}
		}
		totalWordCount = MapDescendingSort.descendingSort(totalWordCount);
		return totalWordCount;
	}

	public void showData(){
		totalWordCount = MapDescendingSort.descendingSort(totalWordCount);
		System.out.println("----------------- "+name+" ------------------");
		System.out.println("[ToatalWordList]");
		for(String key : totalWordCount.keySet()){
			int count = totalWordCount.get(key);
			System.out.println(key + " : " + count + "コ");
		}
		System.out.println("[VersionList]");
		for(VersionInfo vi : versionInfos){
			System.out.println(vi.versionString);
			for(String key : vi.getWordCount().keySet()){
			System.out.println(key + " : " + vi.getWordCount().get(key));
			}
		}
	}

	public void AppDataToCSV(String fileName,String directory){
		try {
			FileWriter fw = new FileWriter(directory + fileName + ".csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.println(name + ",Number of Word");
			pw.print("word");
			pw.print(",");
			pw.println("number");
			for(Map.Entry<String, Integer> wordCount : totalWordCount.entrySet()){
				pw.println(wordCount.getKey() + "," + wordCount.getValue());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


