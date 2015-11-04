package htmlParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class AppDetailAnalyzer {

	final String[] s = { "CC", "IN", "WP","WRB","WP$","TO", "PRP", "PRP$", "WDT", "DT" };
	final List<String> exceptPosList;

	public AppDetailAnalyzer() {
		exceptPosList = Arrays.asList(s);
	}
public String getAppName(File htmlFile){
	String name = null;
	Document doc;
	try {
		doc = Jsoup.parse(htmlFile,"UTF-8");
		name = doc.getElementsByTag("h2").text();
	} catch (IOException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}

	return name;
}
	public Element getVersionBlock(File htmlFile) {
		Element versionBlock = null;
		try {
			Document doc = Jsoup.parse(htmlFile, "UTF-8");
			Elements elements = doc.select(".app-version-block");
			versionBlock = elements.first();
			// System.out.println(versionBlock);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return versionBlock;
	}

	// バージョン番号は(バージョン番号 変更日)の形
	public Map<String, String> parseVersionBlock(Element versionBlock) {
		List<String> descriptionList = new ArrayList<String>();
		List<String> versionStringList = new ArrayList<String>();
		Map<String, String> versionInfos = new LinkedHashMap<String, String>();// <version,description>
		if(versionBlock == null){
			versionInfos.put("1.0.0","");
			System.err.println("バージョンブロックが無い");
			return versionInfos;
		}
		Elements versionElements = versionBlock.select("h5");
		Elements descriptions = versionBlock.select(".app-version-note");

		// 説明部分のテキスト抽出
		for (Element description : descriptions) {
			String text = description.text();
			text = text.replace("メモを広げる", "");
			descriptionList.add(text);
		}
		// version番号の取得
		for (Element versionElement : versionElements) {
			String tmp = versionElement.text().split(" ", 2)[1]
					.replace("(", "").replace(")", "").replaceAll("年|月", "/").replace("日", "");			
			versionStringList.add(tmp);
		}

		// version番号と説明をマップに格納
		for (int i = 0; i < versionStringList.size()
				&& i < descriptionList.size(); i++) {
			versionInfos.put(versionStringList.get(i), descriptionList.get(i));

		}
		for (Map.Entry<String, String> m : versionInfos.entrySet()) {
//			System.out.println(m.getKey() + " : " + m.getValue());
		}
		return versionInfos;
	}

	public Map<String, Integer> wordCount(String text) {
		Map<String, Integer> wordCount = new HashMap<String, Integer>();

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String lemma = token.get(LemmaAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				if (lemma.length() > 2
						&& !exceptPosList.contains(pos)) {
//					 if(pos.equals("TO"))
//					System.out.println("word :" + word + ", lemma : " + lemma
//							+ ", pos : " + pos);
					if (wordCount.containsKey(lemma)) {
						wordCount.put(lemma, wordCount.get(lemma) + 1);
					} else {
						wordCount.put(lemma, 1);
					}
				}
			}
		}
		for (Map.Entry<String, Integer> m : wordCount.entrySet()) {
//			System.out.println(m.getKey() + " : " + m.getValue());
		}
		return wordCount;
	}
}
