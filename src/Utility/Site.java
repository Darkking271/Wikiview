package Utility;

import java.util.*;
import java.io.*;

/**
 * Created by Alex on 4/28/2017.
 */
public class Site implements Serializable{

    String url;
    String name;
    List<String> links;
    String contents;

    public Site(String url, String name, List<String> links, String contents){
        this.url = url;
        this.name = name;
        this.links = links;
        this.contents = contents;
    }
}
