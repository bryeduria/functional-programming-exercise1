package com.whitecloak;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemSales {

    public static void main(String[] args) throws IOException {
        ItemSales.returnAllItemsSorted();
        ItemSales.totalSalesPerBranch();
        ItemSales.totalItemsSoldAllBranches();
        ItemSales.totalItemsSoldFor2016();
        ItemSales.monthTheFruitsAreSoldMost();
        ItemSales.mostSoldItem2012();
        ItemSales.monthOfMostNumberOfSales();
        ItemSales.mostSoldItemManila();
        ItemSales.mostSoldItemCebu();
        ItemSales.mostSoldItemDavao();



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

    public static void totalSalesPerBranch()throws IOException{

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
                .map(item -> item.getProductTotal(item.getUnitsSold(), item.getUnitPrice()))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);

        totalItemsSoldDavao = davaoCsv
                .filter(item -> !item.isBlank())
                .map(item -> item.split(","))
                .map(item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .map(item -> item.getProductTotal(item.getUnitsSold(), item.getUnitPrice()))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);

        totalItemsSoldManila = manilaCsv
                .filter(item -> !item.isBlank())
                .map(item -> item.split(","))
                .map(item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .map(item -> item.getProductTotal(item.getUnitsSold(), item.getUnitPrice()))
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
                .map(item -> item.getProductTotal(item.getUnitsSold(), item.getUnitPrice()))
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
                .map(item -> item.getProductTotal(item.getUnitsSold(), item.getUnitPrice()))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);


        System.out.println("\nTotal of amount of items sold in "+compareToThisYear+": ");
        System.out.println(totalSalesFor2016);
    }

    public static void mostSoldItemCebu()throws IOException{
        String mostSoldItemCebu;
        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));

        mostSoldItemCebu = cebuCsv
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(ItemSalesModel::getItemType,
                        Collectors.summingInt(ItemSalesModel::getUnitsSold)),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));

        System.out.println("\nMost sold item in Cebu: " +mostSoldItemCebu);
    }

    public static void mostSoldItemDavao()throws IOException{
        String mostSoldItemDavao;
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));

        mostSoldItemDavao = davaoCsv
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(ItemSalesModel::getItemType,
                        Collectors.summingInt(ItemSalesModel::getUnitsSold)),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));

        System.out.println("\nMost sold item in Davao: " +mostSoldItemDavao);
    }

    public static void mostSoldItemManila()throws IOException{
        String mostSoldItemManila;
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));

        mostSoldItemManila = manilaCsv
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(ItemSalesModel::getItemType,
                        Collectors.summingInt(ItemSalesModel::getUnitsSold)),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));

        System.out.println("\nMost sold item in Manila: " +mostSoldItemManila);
    }

    public static void monthOfMostNumberOfSales() throws IOException {
        String month;
        Stream<String> allBranches;
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));
        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));

        allBranches = Stream.concat(cebuCsv, davaoCsv);
        allBranches = Stream.concat(allBranches, manilaCsv);

        month = allBranches
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(itemSalesModel -> itemSalesModel.getOrderDate().toString().split("-")[1],
                        Collectors.summingInt(ItemSalesModel::getUnitsSold)),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));

        System.out.println("\nDate: " + Month.of(Integer.parseInt(month)));

    }

    public static void mostSoldItem2012() throws IOException {
        String compareToThisYear = "2012";

        String bestSellingProduct2012;
        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));

        Stream<String> allBranches = Stream.concat(cebuCsv, davaoCsv);
        allBranches = Stream.concat(allBranches, manilaCsv);

        bestSellingProduct2012 = allBranches
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .filter(itemSalesModel -> itemSalesModel.getOrderDate().toString().contains(compareToThisYear))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(ItemSalesModel::getItemType,
                        Collectors.summingInt(ItemSalesModel::getUnitsSold)),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));


        System.out.println("\nMost selling item in the year "+compareToThisYear+": ");
        System.out.println(bestSellingProduct2012);
    }

    public static void monthTheFruitsAreSoldMost() throws IOException {

        String bestSellingMonth;
        Stream<String> cebuCsv = Files.lines(Paths.get("data/cebu.csv"));
        Stream<String> davaoCsv = Files.lines(Paths.get("data/davao.csv"));
        Stream<String> manilaCsv = Files.lines(Paths.get("data/manila.csv"));

        Stream<String> allBranches = Stream.concat(cebuCsv, davaoCsv);
        allBranches = Stream.concat(allBranches, manilaCsv);

        bestSellingMonth = allBranches
                .filter( item -> !item.isBlank())
                .map( item -> item.split(","))
                .map( item -> new ItemSalesModel(
                        String.valueOf(item[0]),
                        LocalDate.parse(item[1], DateTimeFormatter.ofPattern("M/d/yyyy")),
                        Integer.valueOf(item[2]),
                        new BigDecimal(item[3])))
                .filter(itemSalesModel -> itemSalesModel.getItemType().equals("Fruits"))
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(itemSalesModel -> itemSalesModel.getOrderDate().toString().split("-")[1],
                        Collectors.summingInt(ItemSalesModel::getUnitsSold)),
                        map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));


        System.out.println("\nFruits are best sold in the month of: ");
        System.out.println(Month.of(Integer.valueOf(bestSellingMonth)));
    }


}
