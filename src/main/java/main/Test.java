package main;

import htmlParser.AppDetailAnalyzer;
import htmlParser.MapDescendingSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
public class Test {
	Map<String, Integer> allAppWordCount = new HashMap<>(); // 同一カテゴリ内のアプリ内の全ワードの語数

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
		String category = "productivity/";
		String feeType = "pay/";
		String htmlDirectory = "html/" + category + feeType ;
		String csvDirectory = "csv/" + category + feeType;
		String fileName = "";
		Test t = new Test();

//		for (int i = 1; i <= 100; i++) {
//			Map<String, String> versionInfos;
//			List<VersionInfo> versionInfoList = new ArrayList<>();
//			fileName = htmlDirectory + i + ".html";
//			File htmlFile = new File(fileName);
//			String appName = analyzer.getAppName(htmlFile);
//			System.out.println(appName);
//			versionInfos = analyzer.parseVersionBlock(analyzer
//					.getVersionBlock(htmlFile));
//			for (Map.Entry<String, String> m : versionInfos.entrySet()) {
//				String text = m.getValue();
//				String versionString = m.getKey().split(" ")[0];// バージョン番号の取り出し
//				// System.out.println("********  " + m.getKey() +
//				// "  ***********");
//				Map<String, Integer> wordCountMap = analyzer.wordCount(text);
//				VersionInfo vi = new VersionInfo(versionString, wordCountMap);
//				versionInfoList.add(vi);
//			}
//			AppData appData = new AppData(versionInfoList,
//					analyzer.getAppName(htmlFile));
//			// appData.showData();
//			// for(Map.Entry<String, Integer> allWordCount :
//			// appData.totalWordCount.entrySet()){
//			// System.out.println(allWordCount.getKey() + " : " +
//			// allWordCount.getValue());
//			// }
//
//			t.allAppWordCount = sumWordCount(appData.totalWordCount,
//					t.allAppWordCount);
//
//			 appData.AppDataToCSV(i + "_" + appName,csvDirectory);
//		}
//		// 降順にソート
//		t.allAppWordCount = MapDescendingSort.descendingSort(t.allAppWordCount);
//		// 同カテゴリ内のアプリの総単語数をcsvファイルに書き出し
//		try {
//			FileWriter fw = new FileWriter(csvDirectory + "allAppWordData.csv");
//			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
//			pw.println("AllApp,Number of Word");
//			pw.print("word");
//			pw.print(",");
//			pw.println("number");
//			for (Map.Entry<String, Integer> wordCount : t.allAppWordCount
//					.entrySet()) {
//				pw.println(wordCount.getKey() + "," + wordCount.getValue());
//			}
//			pw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		//全カテゴリ単語数合計
	sumAllCategoryWordCountToCSV();
	}

	public static Map<String, Integer> sumWordCount(
			Map<String, Integer> wordCount, Map<String, Integer> sumWordCount) {
		for (Map.Entry<String, Integer> map : wordCount.entrySet()) {
			String word = map.getKey();
			Integer count = map.getValue();
			if (sumWordCount.containsKey(word)) {
				Integer allCount = sumWordCount.get(word);
				allCount += count;
				sumWordCount.put(word, allCount);
			} else {
				sumWordCount.put(word, count);
			}
		}
		return sumWordCount;
	}

	// 各カテゴリ内のallAppWordData.csvから全アプリの総単語数を計算してcsvに出力
	public static void sumAllCategoryWordCountToCSV() {
		Map<String, Integer> allCategoryWordCount = new HashMap<>();
		String[] categorys = { "news", "SocialNetworking","productivity" };
		String[] feeType = { "free", "pay" };
		String directory = "";
		for (int i = 0; i < categorys.length; i++) {
			for (int j = 0; j < 2; j++) {
				if(categorys[i].equals("productivity") && j == 1)
					continue;
				System.out.println("j : "+ j);
				// １回のループで一つのカテゴリ内の総単語数をallCategoryWordCountに合計する
				Map<String, Integer> categoryWordCount = new HashMap<>();
				directory = "csv/" + categorys[i]+ "/" + feeType[j] + "/"
						+ "allAppWordData.csv";
				try {
					BufferedReader br = new BufferedReader(new FileReader(
							directory));
					String line;
					// 初めの２行いらない
					line = br.readLine();
					line = br.readLine();
					// csvファイルからMapにカテゴリの単語数を格納
					while ((line = br.readLine()) != null) {
						String[] token = line.split(",");
						if(token.length == 2){
						categoryWordCount.put(token[0],
								Integer.parseInt(token[1]));
						}
					}
					allCategoryWordCount = Test.sumWordCount(categoryWordCount,
							allCategoryWordCount);
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 降順にソート
		allCategoryWordCount = MapDescendingSort.descendingSort(allCategoryWordCount);
		// 同カテゴリ内のアプリの総単語数をcsvファイルに書き出し
		try {
			FileWriter fw = new FileWriter("csv/allCategoryAppWordData.csv");
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.println("AllCategory,Number of Word");
			pw.print("word");
			pw.print(",");
			pw.println("number");
			for (Map.Entry<String, Integer> wordCount : allCategoryWordCount
					.entrySet()) {
				pw.println(wordCount.getKey() + "," + wordCount.getValue());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
