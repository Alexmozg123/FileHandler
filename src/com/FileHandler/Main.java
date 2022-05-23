package com.FileHandler;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        File rootFolder = new File(args[0]);
        List<File> fileList = new LinkedList<>();

        String nameResultFile = "Result.txt";
        File resultFile = new File(rootFolder.getAbsoluteFile() + nameResultFile);

        searchFile(rootFolder, fileList, resultFile);

        printSortList(fileList);
    }

    public static void searchFile(File rootFolder, List<File> fileList, File resultFile) {
        if (rootFolder.isDirectory()) {
            File[] files = rootFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        searchFile(file, fileList, resultFile);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".txt")) {
                            fileList.add(file);                             // добавляет в список имя файла
                            String gap = "*gap* \n\n";                      // обозначает разрыв между текстами
                            String buffer = read(file) + gap;               // считавает с файла в строку
                            write(buffer, resultFile.getAbsoluteFile());    // записывает с суммирующий файл
                        }
                    }
                }
            }
        }
    }

    // для чтения из файла
    public static String read(File file) {
        if (file.canRead()) {
            StringBuilder contentReader = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    contentReader.append(str).append('\n');
                }
                return contentReader.toString();
            } catch (Exception ex) {
                System.err.format("IOException: %s", ex);
            }
        }
        return null;
    }

    // для записи в файл
    public static void write(String buffer, File path) {
        try (Writer writer = new FileWriter(path, true)) {
            writer.write(buffer);
            writer.flush();
        } catch (IOException ex) {
            System.err.format("IOException: %s", ex);
        }
    }

    // сортировка и вывод отсортированного списка
    public static void printSortList(List<File> sortList) {
        sortList.sort(Comparator.comparing(File::getName));
        for (File file : sortList) {
            System.out.println(file.getName());
        }
    }
}
