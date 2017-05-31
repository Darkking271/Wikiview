package Utility;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

/**
 * Created by Alex on 4/28/2017.
 */
public class Loader {

    //private Queue<File> fileLife;
    //private final int maxFiles = 1000;
    private final String s = File.separator;
    private final String folder = "SiteFiles";
    private final String root = System.getProperty("user.dir");
    private final String fileName = root + s + folder + s;
    //private final String queue = root + s + "RunFiles" + s + "FileLife";
    private int maxLinks;

    public Loader(int maxLinks){
        this.maxLinks = maxLinks;
        File dir = new File(root + s + folder);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public Site load(String url){
        try {
            Site site;
            url = processURL(url);
            File file = new File(fileName + getName(url) + ".site");
            if (file.exists()){
                site = read(file);
                return site;
            }else{
                site = new Site(url, getName(url), getLinks(url), getContents(url));
                write(site, file);
                return site;
            }
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    public void write(Site site, File file)throws IOException{
        System.out.println("Writing " + site.name);
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(site);
        out.close();
        fileOut.close();
    }

    public Site read(File file)throws IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Site site = (Site)in.readObject();
        System.out.println("Reading " + site.name);
        in.close();
        fileIn.close();
        return site;
    }

    public String readContent(String name){
        try {
            File file = new File(fileName + name + ".site");
            Site site = read(file);
            return site.contents;
        }catch(IOException e){
            return null;
        }catch(ClassNotFoundException i){
            return null;
        }
    }

    public String processURL(String url) {
        int endPos;
        if (url.indexOf("?") > 0) {
            endPos = url.indexOf("?");
        } else if (url.indexOf("#") > 0) {
            endPos = url.indexOf("#");
        } else {
            endPos = url.length();
        }

        return url.substring(0, endPos);
    }

    public String getName(String url){
        String[] data = url.split("/");
        String name = data[data.length - 1];
        name = name.replace("_", " ");
        return name;
    }

    private String getContents(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        return doc.select("#mw-content-text").text();
    }

    public List<String> getLinks(String url) throws IOException{
        System.out.println(url);
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("#mw-content-text a");
        return links.stream().map(Loader:: getHref)
                .filter(Loader:: linkValid)
                .limit((long)maxLinks)
                .collect(Collectors.toList());
    }

    public static String getHref(Element link){
        return link.attr("abs:href");
    }

    private static boolean linkValid(String url){
        if (url.contains("wikipedia.org")){
            if (url.contains(".PNG") || url.contains("(disambiguation)") || url.contains(".svg") || url.contains("Citing_sources")
                    || url.contains("Verifiability") || url.contains("edit") || url.contains("Help") || url.contains(".jpg")
                    || url.contains(".JPG") || url.contains("WikiProject_Fact_and_Reference_Check") || url.contains("cite")
                    || url.contains("Users") || url.contains(".png") || url.contains("Talk") || url.contains("Manual_of_Style")
                    || url.contains("Category") || url.contains("File") || url.contains("WikiProject")
                    || url.contains("WikiProject_Philosophy") || url.contains("External_links") || url.contains("Article_wizard")
                    || url.contains("wikipedia:") || url.contains("Wikipedia:") || url.contains(")") || url.contains("(")
                    || url.contains("%") || url.contains("microform") || url.contains("List") || url.contains("list")
                    || url.contains("Template:Expert_needed") || url.contains("MOS:") || url.contains("Template:")) {
                return false;
            }
            else
                return true;
        }
        else
            return false;
    }
}
