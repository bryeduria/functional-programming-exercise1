package com.whitecloak;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemSales {

    public static void main(String[] args) throws IOException {
        ItemSales.returnAllItemsSorted();




    }

    public static void returnAllItemsSorted() throws IOException{
        TreeSet<String> itemsSold = new TreeSet<>();
        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));

        Stream<String> allBranches = Stream.concat(cebuCsv, davaoCsv);
        allBranches = Stream.concat(allBranches, manilaCsv);

        itemsSold = allBranches
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .map(item -> item.getItemType())
                .collect(Collectors.toCollection(TreeSet::new));


        itemsSold.forEach(System.out::println);
    }




}
