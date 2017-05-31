package Practice;

import org.jsoup.Jsoup;

import java.io.IOException;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import Utility.Cosine;

import javax.swing.*;

/**
 * Created by darki on 4/17/2017.
 */
public class URLParsing {

    public static void main(String[] args)throws IOException{
        String url = "https://en.wikipedia.org/wiki/World_War_II";
        Document doc = Jsoup.connect(url).get();
        String body = doc.title();

        pl(body);

        Elements main = doc.select("#mw-content-text");
        pl(main.text());
        /*for (Element m : main){
            if (linkValid(m.attr("abs:href")))
            pl(m.text() + " : " + m.attr("abs:href"));
        }

        /*Elements links = doc.select("a[href]");
        for (Element link : links){
            pl(link.attr("abs:href") + " : " + link.text() + " : " + ignore(link.text()));
        }

        /*String url2 = "https://en.wikipedia.org/wiki/Lorna_Hodgkinson";
        Document doc2 = Jsoup.connect(url2).get();
        String body2 = doc2.body().text();

        pl(body2);

        double cos = Cosine.findSimilarity(body, body2);
        System.out.println(cos);

        System.out.println(title(body));
        System.out.println(title(body2));*/

    }

    private static boolean ignore(String title){
        if (title.equals("navigation") || title.equals("search") || title.equals("") || title.equals("verification")
                || title.equals("improve this article") || title.equals("adding citations to reliable sources")
                || title.equals("Learn how and when to remove this template message") || title.contains("(disambiguation)")){
            return true;
        }
        else return false;
    }

    private static boolean linkValid(String url){
        if (url.contains(".PNG") || url.contains("(disambiguation)")){
            return false;
        }
        if (url.contains("wikipedia.org")){
            return true;
        }
        else
            return false;
    }

    private static String title(String body){
        String[] t = body.split(" From");
        return t[0];
    }

    public static void p(String out){
        System.out.print(out);
    }

    public static void pl(String out){
        System.out.println(out);
    }
}
