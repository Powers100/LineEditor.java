/** 
* Student name:  Donovan Powers
* Completion date: 11/3/25
* Programming Assignment 3
*
* LineEditor.java: Command-line line editor application. 
* If run with no command line arguments, it starts with an empty document.
* If a file name is given as an argument, it loads the file contents into a LineList object.
* Each menu command corresponds to a method in LineList.java.
*/

import java.util.Scanner;

public class LineEditor {

    // Method to print the menu
    public static void printMenu() {
        System.out.println("Menu: m");
        System.out.println("Load File: load fileName appendOption (1-append, 0-new list)");
        System.out.println("Print: p");
        System.out.println("Show document as string: t");
        System.out.println("Display number of lines: lines");
        System.out.println("Display line: line lineNumber");
        System.out.println("Count words: words");
        System.out.println("Delete line: del lineNumberToDelete");
        System.out.println("Append line: a");
        System.out.println("Insert line: i lineNumberToInsert");
        System.out.println("Clear document: cls");
        System.out.println("Replace words: rep originalWord newWord");
        System.out.println("Save to a file: s fileName");
        System.out.println("Quit: quit");
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        LineList lineList = new LineList();
        String command;

        // Load file from command line argument if provided
        if (args.length != 0) {
            lineList.load(args[0], false); // Load without appending
        }

        printMenu(); // Display menu at the start

        while (true) {
            System.out.print("-->");
            command = scnr.nextLine().trim(); // Read and trim user input

            if (command.isEmpty()) {
                continue; // skip empty input
            }

            String[] parts = command.split("\\s+"); // Split input into command and arguments
            String cmd = parts[0].toLowerCase(); // Make command case-insensitive

            switch (cmd) {
                case "quit":
                    scnr.close();
                    return; // exit program

                case "m":
                    printMenu(); // Display menu again
                    break;

                case "load":
                    try {
                        String fileName = parts[1]; // File to load
                        boolean append = (parts.length > 2 && parts[2].equals("1")); // Append option
                        lineList.load(fileName, append);
                    } 
                    catch (Exception e) {
                        System.out.println("Usage: load fileName [appendOption]");
                    }
                    break;

                case "p": // Print document with line numbers
                    lineList.print();
                    break;

                case "t": // Show document as a single string
                    if (lineList.toString().equals("")) {
                        System.out.print(lineList.toString()); // Empty document, avoid extra newline
                        break;
                    }
                    System.out.println(lineList.toString()); // Print full document
                    break;

                case "lines": // Display number of lines
                    System.out.println("There are " + lineList.lines() + " lines.");
                    break;

                case "line": // Display a specific line
                    try {
                        int n = Integer.parseInt(parts[1]);
                        lineList.line(n);
                    } 
                    catch (Exception e) {
                        System.out.println("Usage: line n");
                    }
                    break;

                case "words": // Display total word count
                    System.out.println("There are " + lineList.words() + " words.");
                    break;

                case "del": // Delete a specific line
                    try {
                        int n = Integer.parseInt(parts[1]);
                        lineList.delete(n);
                    } 
                    catch (Exception e) {
                        System.out.println("Usage: del n");
                    }
                    break;

                case "a": // Append a new line to the end of the document
                    System.out.print("Type a line:");
                    String newLine = scnr.nextLine();
                    lineList.addLine(newLine);
                    break;

                case "i": // Insert a new line at a specified position
                    try {
                        int n = Integer.parseInt(parts[1]);
                        System.out.print("Type a line:");
                        String insertLine = scnr.nextLine();
                        lineList.addLine(insertLine, n);
                    } 
                    catch (Exception e) {
                        System.out.println("Usage: i lineNumber");
                    }
                    break;

                case "cls": // Clear the entire document
                    lineList.empty();
                    break;

                case "rep": // Replace words in the document
                    try {
                        String s1 = parts[1];
                        String s2 = parts[2];
                        lineList.replace(s1, s2);
                    } 
                    catch (Exception e) {
                        System.out.println("Usage: rep oldWord newWord");
                    }
                    break;

                case "s": // Save document to file
                    try {
                        String fileName = parts[1];
                        lineList.save(fileName);
                    } 
                    catch (Exception e) {
                        System.out.println("Usage: s fileName");
                    }
                    break;

                default: // Handle unknown commands
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }
}
    