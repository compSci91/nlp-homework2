import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File directory = new File("/Users/JoshuaHowell/Desktop/Texas A&M/Year 2/Fall 2018/Natural Language Processing/Homework 2/data/imdb1/pos/");


        CrossValidationSorter crossValidationSorter = new CrossValidationSorter();
        List<File> files = crossValidationSorter.retrieveFilesOutNotInRange(directory, 100, 200);

        for(File file : files ){
            System.out.println(file.getName());
        }
    }
}
