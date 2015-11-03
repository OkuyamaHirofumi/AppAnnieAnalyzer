package main;

import htmlParser.ConnectAppAnnie;
import htmlParser.ConnectAppAnnie.AppKind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {

//		cookie読み込みてすと
		ConnectAppAnnie ca = new ConnectAppAnnie();
		System.out.println("------------FREE-------------------");
	ca.setUrlList(AppKind.FREE);
	System.out.println("------------PAY-------------------");
	ConnectAppAnnie ca2 = new ConnectAppAnnie();
	ca2.setUrlList(AppKind.PAY);
	System.out.println("------------ALL-------------------");
//		ca.conectAppDetail("https://www.appannie.com/apps/ios/app/facebook/");

	}

}
