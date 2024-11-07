package Helpers;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
   static  String CsvfilePath;
    static FileWriter fileWriter;
    static BufferedWriter bufferedWriter;

    public static void ExportCSvWritter( Map<String, Long> apiResponseTimes) {
        try {

            String CsvrelativePath1 = "ReportPerformance.csv";
            CsvfilePath = System.getProperty("user.dir") + "/src/main/resources" + CsvrelativePath1;
            fileWriter = new FileWriter(CsvfilePath);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("ReportName,Performance in millisec\n");
            for (Map.Entry<String, Long> entry : apiResponseTimes.entrySet()) {
                String key = entry.getKey();
                Long value = entry.getValue();
                bufferedWriter.write(key + "," + value + " Milliseconds" + "\n");
            }
            bufferedWriter.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
            public static void ViewCSvWritter( Map<String, Long> ViewapiResponseTimes) {
                String CsvrelativePath2 = "ViewReportPerformance.csv";
                try {
                    CsvfilePath = System.getProperty("user.dir") + "/src/test/testdata/" + CsvrelativePath2;
                    fileWriter = new FileWriter(CsvfilePath);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write("TotalCount,View Performance in millisec\n");
                    for (Map.Entry<String, Long> entry : ViewapiResponseTimes.entrySet()) {
                        String key = entry.getKey();
                        Long value = entry.getValue();
                        bufferedWriter.write(key + "," + value + " Milliseconds" + "\n");
                    }
                    bufferedWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
      Properties properties;
    String relativePath = "config.properties";
    String filePath = System.getProperty("user.dir") + "/src/main/resources/" + relativePath;//--src\test\resources\testdata
    public ConfigReader() {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }


}