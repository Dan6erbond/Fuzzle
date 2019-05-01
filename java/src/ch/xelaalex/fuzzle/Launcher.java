package ch.xelaalex.fuzzle;

import ch.xelaalex.fuzzle.Searcher.Option;
import ch.xelaalex.fuzzle.Searcher.Searcher;
import ch.xelaalex.fuzzle.Searcher.StackedDataSet;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Launcher {
    public static void main(String[] args) throws FileNotFoundException {
        String filePath = new File("").getAbsolutePath();
        JsonReader reader = new JsonReader(new FileReader(filePath + "\\data\\games\\games.json"));
        Option[] options = new Gson().fromJson(reader, Option[].class);

        new Searcher().find(new StackedDataSet<Option>(options.length), "aragami");
    }
}
