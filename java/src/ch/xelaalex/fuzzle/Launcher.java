package ch.xelaalex.fuzzle;

import ch.xelaalex.fuzzle.Searcher.Option;
import ch.xelaalex.fuzzle.Searcher.Searcher;
import ch.xelaalex.fuzzle.Searcher.StackedDataSet;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String filePath = new File("").getAbsolutePath();
        JsonReader reader = new JsonReader(new FileReader(filePath + "\\data\\games\\games.json"));
        Option[] optionsAsArray = new Gson().fromJson(reader, Option[].class);
        StackedDataSet<Option> options = new StackedDataSet<>(optionsAsArray.length);
        options.addAll(optionsAsArray);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Type in search query: ");
            String search = scanner.nextLine();
            long millis = System.currentTimeMillis();
            StackedDataSet<Option> results = new Searcher().find(options, search);
            long time = System.currentTimeMillis() - millis;
            int i = 0;
            for (Option option : results) {
                i++;
                if (i >= 20) break;
                System.out.println(option.toInformalString());
            }
            System.out.println("Scanned through " + options.size() + " options and found " + results.size() + " results in " + time + " milliseconds\n");
        }
    }
}
