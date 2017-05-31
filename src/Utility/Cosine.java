package Utility;

import java.util.*;

/**
 * Created by Alex on 4/20/2017.
 */
public class Cosine {

    public static double findSimilarity(String site1, String site2){
        HashMap<String, Integer> hashA = buildHash(site1);
        HashMap<String, Integer> hashB = buildHash(site2);
        Set<String> allSet = hashA.keySet();
        allSet.retainAll(hashB.keySet());
        double num = calcNum(hashA, hashB, allSet);
        double den = calcDen(hashA, hashB, allSet);
        return num / den;
    }

    private static HashMap<String, Integer> buildHash(String str){
        Scanner s = new Scanner(str);
        HashMap<String, Integer> map = new HashMap<>();
        String n;
        while (s.hasNext()){
            n = s.next();
            if (!map.containsKey(n))
                map.put(n, 1);
            else {
                int i = map.get(n) + 1;
                map.put(n, i);
            }
        }
        return map;
    }

    private static double calcNum(HashMap<String, Integer> hashA, HashMap<String, Integer> hashB, Set<String> allSet){
        double sum = 0.0;
        for (String s : allSet){
            sum += hashA.get(s) * hashB.get(s);
        }
        return sum;
    }

    private static double calcDen(HashMap<String, Integer> hashA, HashMap<String, Integer> hashB, Set<String> allSet){
        double sumA = 0.0;
        double sumB = 0.0;
        for (String s : allSet){
            sumA += Math.pow(hashA.get(s), 2);
            sumB += Math.pow(hashB.get(s), 2);
        }
        double den = Math.sqrt(sumA) * Math.sqrt(sumB);
        return den;
    }
}
