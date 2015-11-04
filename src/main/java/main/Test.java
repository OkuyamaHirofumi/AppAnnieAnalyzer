package main;

import htmlParser.AppDetailAnalyzer;
import htmlParser.MapDescendingSort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	Map<String,Integer> allAppWordCount = new HashMap<>(); //同一カテゴリ内のアプリ内の全ワードの語数
	
	public static void main(String[] args) {
	
		
		// URL取得作業
		// ConnectAppAnnie ca = new ConnectAppAnnie();
		// System.out.println("------------FREE-------------------");
		// ca.setUrlList(AppKind.FREE);
		// System.out.println("------------PAY-------------------");
		// ConnectAppAnnie ca2 = new ConnectAppAnnie();
		// ca2.setUrlList(AppKind.PAY);
		// ca.conectAppDetail("https://www.appannie.com/apps/ios/app/facebook/");
		
		
		AppDetailAnalyzer analyzer = new AppDetailAnalyzer();
		/**************** アプリのカテゴリでパスの変更 *****************/
		String htmlDirectory = "html/news/pay/";
		String csvDirectory = "csv/news/pay/";
		String fileName = "";
		Test t = new Test();
		
		
		for (int i = 1; i <= 100; i++) {
			Map<String, String> versionInfos;
			List<VersionInfo> versionInfoList = new ArrayList<>();
			fileName = htmlDirectory + i + ".html";
			File htmlFile =new File(fileName);
			String appName =analyzer.getAppName(htmlFile);
			System.out.println(appName);
			versionInfos = analyzer.parseVersionBlock(analyzer
					.getVersionBlock(htmlFile));
			for (Map.Entry<String, String> m : versionInfos.entrySet()) {
				String text = m.getValue();
				String versionString = m.getKey().split(" ")[0];// バージョン番号の取り出し
				// System.out.println("********  " + m.getKey() +
				// "  ***********");
				Map<String, Integer> wordCountMap = analyzer.wordCount(text);
				VersionInfo vi = new VersionInfo(versionString, wordCountMap);
				versionInfoList.add(vi);
			}
			AppData appData = new AppData(versionInfoList,
					analyzer.getAppName(htmlFile));
			// appData.showData();
			// for(Map.Entry<String, Integer> allWordCount :
			// appData.totalWordCount.entrySet()){
			// System.out.println(allWordCount.getKey() + " : " +
			// allWordCount.getValue());
			// }
			
			t.sumAllAppWordNumber(appData.totalWordCount);
			
			appData.AppDataToCSV(i + "_" + appName,csvDirectory);
		}
		
		t.allAppWordCount = MapDescendingSort.descendingSort(t.allAppWordCount);
		try {
			FileWriter fw = new FileWriter(csvDirectory +  "allAppWordData.csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.println("AllApp,Number of Word");
			pw.print("word");
			pw.print(",");
			pw.println("number");
			for(Map.Entry<String, Integer> wordCount : t.allAppWordCount.entrySet()){
				pw.println(wordCount.getKey() + "," + wordCount.getValue());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sumAllAppWordNumber(Map<String,Integer> wordCount){
		for(Map.Entry<String,Integer> map : wordCount.entrySet()){
			String word = map.getKey();
			Integer count = map.getValue();
			if(allAppWordCount.containsKey(word)){
				Integer allCount = allAppWordCount.get(word);
				allCount += count;
				allAppWordCount.put(word,allCount);
			}else{
				allAppWordCount.put(word,count);
			}
		}
	}
	
}
