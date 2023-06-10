# Baby Names Searcher

![Java CI](https://github.com/Computer-Science-Classes/baby-names-searcher/workflows/Java%20CI/badge.svg)

Baby Names Searcher is a Java program that allows you to search for a name in a dataset and view its popularity over the years. The program searches for the names in `names.txt` and `meanings.txt` files and displays a bar chart showing the ranking of the name over the years.

The dataset includes names data from the year 1890 onwards.

## Features

- Search for a name and view its popularity over the years
- Displays a bar chart with the popularity ranking of the name
- Gender-based color coding for the bar chart
- Support for continuous search until the user types 'quit'

## Prerequisites

- Java 8 or higher
- JFreeChart library

## Running the Program

1. Clone the repository: `git clone https://github.com/Computer-Science-Classes/baby-names-searcher.git`
2. Navigate to the project directory: `cd baby-names-searcher`
3. Compile the program: `javac BabyNames.java`
4. Run the program: `java BabyNames`

The program will prompt you to enter a name. You can continue to enter names until you type 'quit'.

## License

This project is licensed under the terms of the MIT license. For more information, see [LICENSE](LICENSE).
