package htmlParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ConnectAppAnnie {
	public enum AppKind {
		FREE, PAY, ALL
	}
String userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36";
	HashMap<String, String> cookie = new HashMap<String, String>();
	public List<String> urlList = new ArrayList<String>();


	public void setUrlList(AppKind appKind) {
		int kindSurplus = 0;// 抽出するカラムを切り替えるために調べる余り
		// アプリのカテゴリのトップURLをその都度貼り付ける
		String url = "https://www.appannie.com/apps/ios/top/united-states/games/?device=iphone";
		switch (appKind) {
		case FREE:
			kindSurplus = 0;
			break;
		case PAY:
			kindSurplus = 2;
			break;
		case ALL:
			kindSurplus = 4;
			break;
		default:
			break;
		}

		try {
			Connection connection = Jsoup.connect(url);
			connection
					.userAgent(userAgentString);
			loadCookie();
		connection.cookies(cookie);
		
			org.jsoup.nodes.Document document = connection.get();
			Elements hoge = document.select("table a");
			int count = 0;
			for (Element e : hoge) {
				if (count % 6 == kindSurplus) {
					urlList.add("https://www.appannie.com" + e.attr("href"));
				}
				count++;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		urlList.add("EOF");
		int counter = 0;
		for (String u : urlList) {
			System.out.println(u);
		}
	}
	
	public void testConect(){
		try {
			Map<String, String> cookies = Jsoup.connect("https://www.appannie.com/account/login/")
					.userAgent(userAgentString)
					.data("username", "okuyama@se.cs.titech.ac.jp")
					.data("password","humi1121")
					.followRedirects(true)
					.referrer("https://www.appannie.com/account/login/?_ref=header")
					.header("Origin", "https://www.appannie.com")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Upgrade-Insecure-Requests", "1")
					.method(Connection.Method.POST)
					.execute()
					.cookies();
			
			for(Map.Entry<String, String> c : cookie.entrySet()){
			System.out.println(c.getKey() + " : " + c.getValue());
		}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}
public void conectAppDetail(String url){
	Connection connection = Jsoup.connect(url);
	connection.userAgent(userAgentString);
	loadCookie();
	for (Map.Entry<String, String> c : cookie.entrySet()) {
		connection.cookie(c.getKey(), c.getValue());
		System.out.println(c.getKey() + "=" + c.getValue());
	}

	try {
		Document document = connection.get();
		System.out.println(document);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	
}
	public static void htmlParse() {

	}

	// cookieをMapに読み込む
	public void loadCookie() {
		try {
			File cookieFile = new File("Cookie.txt");
			FileReader fileReader = new FileReader(cookieFile);
			BufferedReader br = new BufferedReader(fileReader);
			String line;
			while ((line = br.readLine()) != null) {
				String[] pair = line.split("=");
				if (pair.length == 2) {
					cookie.put(pair[0], pair[1].replace(";", ""));

				}
			}
			cookie.put("km_uq", "");
			// for (Map.Entry<String, String> cookie : cookie.entrySet()) {
			// System.out.println(cookie.getKey() + " : " + cookie.getValue());
			// }
			br.close();
			fileReader.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	
	}
}
