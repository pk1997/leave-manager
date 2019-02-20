package com.hashedin.hu;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.Executor;

public class main {
    public static void main(String args[])
    {
        readDataFromCsv("/home/panvan_k/IdeaProjects/huleavemanager/src/main/java/com/hashedin/hu/data.csv");
        ExecutorService service = new ExecutorService();
    }
    public static void readDataFromCsv(String file)
    {
        try {
            /*
            Create an object of file reader
            class with CSV file as a parameter.
            */

            FileReader filereader = new FileReader(file);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            System.out.println(allData);
            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    }

