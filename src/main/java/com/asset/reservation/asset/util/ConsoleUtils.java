package com.asset.reservation.asset.util;

import com.asset.reservation.asset.util.enums.DataTableAssetHeaders;
import com.asset.reservation.asset.util.enums.DataTableReservationHeaders;
import org.springframework.shell.table.*;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class ConsoleUtils {


  public static void generateTable(TableModel tableModel, int totalPages, int currentPage, int tableWidth) {
    TableBuilder tableBuilder = new TableBuilder(tableModel);
    tableBuilder.on(CellMatchers.table()).addAligner(SimpleHorizontalAligner.right);
    tableBuilder.addInnerBorder(BorderStyle.fancy_light);
    tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
    System.out.println(tableBuilder.build().render(tableWidth));

    System.out.println(
        "Current page                               " + currentPage + "/" + totalPages);
  }

  public static void generateTable(TableModel tableModel,  int tableWidth) {
    TableBuilder tableBuilder = new TableBuilder(tableModel);
    tableBuilder.on(CellMatchers.table()).addAligner(SimpleHorizontalAligner.right);
    tableBuilder.addInnerBorder(BorderStyle.fancy_light);
    tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
    System.out.println(tableBuilder.build().render(tableWidth));
  }

  public static LinkedHashMap<String, String> getHeaderAssetMap() {
    LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
    Arrays.stream(DataTableAssetHeaders.values())
        .forEach(
            dataTableReservationHeaders -> {
              headerMap.put(dataTableReservationHeaders.key, dataTableReservationHeaders.value);
            });

    return headerMap;
  }

  public static LinkedHashMap<String, String> getHeaderReservationMap() {
    LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
    Arrays.stream(DataTableReservationHeaders.values())
        .forEach(
            dataTableReservationHeaders -> {
              headerMap.put(dataTableReservationHeaders.key, dataTableReservationHeaders.value);
            });
    return headerMap;
  }

  public static void printMessage(String message) {
    System.out.println(message);
  }
}
