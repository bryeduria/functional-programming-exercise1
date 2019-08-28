package com.whitecloak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemSales {

    public static void main(String[] args) {
        Set<String> itemsSold = new TreeSet<>();
        try (Stream<String> read = Files.lines(Paths.get("data/cebu.csv"))) {
            itemsSold = read.collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
