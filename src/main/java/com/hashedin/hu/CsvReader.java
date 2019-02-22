package com.hashedin.hu;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.List;

public class CsvReader {
    public List<String[]>  csvReader(String file)
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
            return allData;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
