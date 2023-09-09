package com.web.crawler;

import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {
	public static void main(String[] args) {
//		System.out.println("Enter your url to find links :-");
//		String url1 =new java.util.Scanner(System.in).nextLine();
		String url = "https://www.facebook.com/";
		crawl(1, url, new ArrayList<String>());

	}

	private static void crawl(int level, String url, ArrayList<String> arrayList) {
		// level represents the number of stages crawler should search for
		// links.
		if (level == 1) {
			Document requestDoc = request(url, arrayList);
			if (requestDoc != null) {
				for (Element e : requestDoc.select("a[href]")) {
					String absUrl = e.absUrl("href");
					if (arrayList.contains(absUrl) == false) {
						crawl(level++, absUrl, arrayList);
					}
				}
			}

		}

	}

	private static Document request(String url, ArrayList<String> arrayList) {
		try {
			Connection con = Jsoup.connect(url);
			Document document = con.get();
			// The above if condition checks the HTTP status of document
			// retrived is ok i.e, 200
			if (con.response().statusCode() == 200) {
				System.out.println("Link : " + url);
				System.out.println("Title of current url is " + document.title());
				arrayList.add(url);
				return document;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Error occured for link / url=" + url);
		}

		return null;

	}
}
