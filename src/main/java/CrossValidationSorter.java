import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CrossValidationSorter {

    public List<File> retrieveFilesInRange(File directory, int beginning, int end){

        File[] listOfFiles = directory.listFiles();
        List<File> filesToReturn = new ArrayList<File>();

        for(int i = 0; i < listOfFiles.length; i++){
            File file = listOfFiles[i];
            String fileName = file.getName();
            int fileNumber = Integer.parseInt(fileName.substring(2, 5));
            if(fileNumber >= beginning && fileNumber <= end){
               filesToReturn.add(file);
            }
        }

        return filesToReturn;
    }


    public List<File> retrieveFilesOutNotInRange(File directory, int beginning, int end){

        File[] listOfFiles = directory.listFiles();
        List<File> filesToReturn = new ArrayList<File>();

        for(int i = 0; i < listOfFiles.length; i++){
            File file = listOfFiles[i];
            String fileName = file.getName();
            int fileNumber = Integer.parseInt(fileName.substring(2, 5));
            if(!(fileNumber >= beginning && fileNumber <= end)){
                filesToReturn.add(file);
            }
        }

        return filesToReturn;
    }
}
