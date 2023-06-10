package com.babynames;

import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Program that searches a name in a proided text file
 * and diplays the results with a chart
 */
public class BabyNames {
    private static final int STARTING_YEAR = 1890;
    private static final String FILE_PATH_NAMES = "/Users/jeff/Downloads/projects/APCSA/babynames/src/main/java/com/babynames/resources/names.txt";
    private static final String FILE_PATH_MEANINGS = "/Users/jeff/Downloads/projects/APCSA/babynames/src/main/java/com/babynames/resources/meanings.txt";

    /**
     * Main method that repeatedly asks for a name until 'quit' is ented
     * searchs the name in the files and displays the results
     * 
     * @param args
     */
    public static void main(String[] args) {
        String name;
        do {
            Optional<String> nameOptional = askForName();
            name = nameOptional.orElse("quit");

            if (!"quit".equalsIgnoreCase(name)) {
                nameOptional.ifPresent(n -> {
                    Optional<String> resultOptional = searchNameInFile(FILE_PATH_NAMES, n);
                    resultOptional.ifPresent(result -> {
                        String[] data = result.split(" ");
                        String gender = data[1];
                        System.out.println(result);
                        searchNameInFile(FILE_PATH_MEANINGS, n.toUpperCase())
                                .ifPresent(System.out::println);

                        // Prepare data for the chart
                        DefaultCategoryDataset dataset = createDataset(data, n);

                        // Create the chart
                        JFreeChart barChart = createChart(dataset, n, gender);

                        // Display the chart
                        ChartFrame frame = new ChartFrame("Bar Chart", barChart);
                        frame.setVisible(true);
                        frame.setSize(800, 600);
                    });
                });
            }
        } while (!"quit".equalsIgnoreCase(name));
    }

    /**
     * Prompts the user for a name input
     * 
     * @return An optional that contains the entered name or is empty if 'quit'
     */
    private static Optional<String> askForName() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter a name (or 'quit' to stop): ");
        return Optional.of(console.next());
    }

    /**
     * Searches for a name in a given file and returns the corresponding line
     * 
     * @param filePath The path of the file where the name should be searched
     * @param name     The name to be searched
     * @return An optional that contains the found line or empty if no name
     */
    private static Optional<String> searchNameInFile(String filePath, String name) {
        try {
            return Files.lines(Paths.get(filePath))
                    .filter(line -> line.toLowerCase().startsWith(name.toLowerCase()))
                    .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Creates a dataset for the JFreeChart based on the give data array
     * 
     * @param data The array that contains the data for the chart
     * @param name The name that should be used as a label for the data
     * @return The created dataset
     */
    private static DefaultCategoryDataset createDataset(String[] data, String name) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        IntStream.range(2, data.length)
                .forEach(i -> {
                    int year = STARTING_YEAR + (i - 2) * 10;
                    dataset.addValue(Integer.parseInt(data[i]), name, Integer.toString(year));
                });
        return dataset;
    }

    /**
     * Creates a bar chart with the given dataset: name and gender
     * 
     * @param dataset The dataset for the chart
     * @param name    The name for the title of the chart
     * @param gender  The gender thats used for the charts color
     * @return The created chart
     */
    private static JFreeChart createChart(DefaultCategoryDataset dataset, String name, String gender) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Name Popularity Over the Years",
                "Year",
                "Ranking",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Setting color based on gender
        if (gender.equals("f")) {
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.pink);
        } else {
            barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.blue);
        }

        return barChart;
    }
}
