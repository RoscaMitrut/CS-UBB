package org.example.utils;

import org.example.domain.Node;
import org.example.domain.LinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileUtils {

    public static void printMapToFile(Map<String, Integer> countryResult, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("server/src/main/resources/" + filename));

            for (String key : countryResult.keySet()) {
                bw.write("Country: " + key + " ; Score: " + countryResult.get(key));
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printListToFile(LinkedList list, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("server/src/main/resources/" + filename));

            Node node = list.head.next;
            if (node == list.tail) {
                return;
            }
            while (node.isNotLastNode()) {
                bw.write(node.getData().getId() + "," + node.getData().getScore() + "," + node.getData().getCountry());
                bw.newLine();
                node = node.next;
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
