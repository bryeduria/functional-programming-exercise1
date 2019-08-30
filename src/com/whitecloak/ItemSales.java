package com.whitecloak;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CollectionCertStoreParameters;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemSales {

    public static void main(String[] args) throws IOException {
//        ItemSales.returnAllItemsSorted();
//        ItemSales.totalItemsSoldPerBranch();
//        ItemSales.totalItemsSoldAllBranches();
        ItemSales.totalItemsSoldFor2016();



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

        System.out.println("Items sold in all branches (Alphabetical Order): ");
        itemsSold.forEach(System.out::println);
    }

    public static void totalItemsSoldPerBranch()throws IOException{

        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));

        BigDecimal totalItemsSoldCebu;
        BigDecimal totalItemsSoldDavao;
        BigDecimal totalItemsSoldManila;


        totalItemsSoldCebu = cebuCsv
                .filter(item -> !item.isBlank())
                .map(item -> item.split(","))
                .map(item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .map(item -> item.getProductTotal(item.unitsSold, item.unitPrice))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);

        totalItemsSoldDavao = davaoCsv
                .filter(item -> !item.isBlank())
                .map(item -> item.split(","))
                .map(item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .map(item -> item.getProductTotal(item.unitsSold, item.unitPrice))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);

        totalItemsSoldManila = manilaCsv
                .filter(item -> !item.isBlank())
                .map(item -> item.split(","))
                .map(item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .map(item -> item.getProductTotal(item.unitsSold, item.unitPrice))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);

        System.out.println("\nTotal Items Sold for Cebu: " +totalItemsSoldCebu);

        System.out.println("Total Items Sold for Davao: " +totalItemsSoldDavao);

        System.out.println("Total Items Sold for Manila: " +totalItemsSoldManila);
    }

    public static void totalItemsSoldAllBranches() throws IOException {
        BigDecimal totalItemsSold;
        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));

        Stream<String> allBranches = Stream.concat(cebuCsv, davaoCsv);
        allBranches = Stream.concat(allBranches, manilaCsv);

        totalItemsSold = allBranches
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .map(item -> item.getProductTotal(item.unitsSold, item.unitPrice))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);


        System.out.println("\nTotal Items sold in all branches: "+totalItemsSold);

    }

    public static void totalItemsSoldFor2016() throws  IOException {
        String compareToThisYear = "2016";

        BigDecimal totalSalesFor2016;
        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));

        Stream<String> allBranches = Stream.concat(cebuCsv, davaoCsv);
        allBranches = Stream.concat(allBranches, manilaCsv);

        totalSalesFor2016 = allBranches
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .filter(itemSalesModel -> itemSalesModel.getOrderDate().toString().contains(compareToThisYear))
                .map(item -> item.getProductTotal(item.unitsSold, item.unitPrice))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);


        System.out.println("\nTotal of amount of items sold in "+compareToThisYear+": ");
        System.out.println(totalSalesFor2016);
    }
}
