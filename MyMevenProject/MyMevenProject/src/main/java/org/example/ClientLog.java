package org.example;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ClientLog {
    private List<String[]> eventLog = new ArrayList<>();

    public void ClientLogs(){
        eventLog.add(new String[]{"productNum", "amount"});
    }

    public void log(int productNum, int amount){
        eventLog.add(new String[]{String.valueOf(productNum), String.valueOf(amount)});
    }

    public void exportAsCSV (File textfile) throws IOException {
            try (
                    CSVWriter csvWriter = new CSVWriter(new FileWriter("log.csv", true))
            ) {
                csvWriter.writeNext((eventLog.toString().split(" ")));
            }

    }

}
