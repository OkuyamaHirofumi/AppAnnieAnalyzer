package main;

import htmlParser.AppDetailAnalyzer;
import htmlParser.ConnectAppAnnie;
import htmlParser.ConnectAppAnnie.AppKind;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		Map<String, String> versionInfos;
		//URL取得作業
		// ConnectAppAnnie ca = new ConnectAppAnnie();
		// System.out.println("------------FREE-------------------");
		// ca.setUrlList(AppKind.FREE);
		// System.out.println("------------PAY-------------------");
		// ConnectAppAnnie ca2 = new ConnectAppAnnie();
		// ca2.setUrlList(AppKind.PAY);
		// ca.conectAppDetail("https://www.appannie.com/apps/ios/app/facebook/");
		AppDetailAnalyzer analyzer = new AppDetailAnalyzer();
		versionInfos = analyzer.parseVersionBlock(analyzer
				.getVersionBlock(new File("2.html")));
		for (Map.Entry<String, String> m : versionInfos.entrySet()) {
			String text = m.getValue();
			System.out.println("********" + m.getKey() + "***************");
			analyzer.wordCount(text);
		}

	}

}
