/** 
* 
* Student name:  Donovan Powers
* Completion date: 11/3/25
* Programming Assignment 3
*
* LineList.txt: the template file of LineList.java
* Student tasks: implement tasks as specified in this file
*
* LineList class is a linked-base list that represents the contents of a document
*
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class LineList{
   
   private Node head;
   
   public LineList(){   //constructor  
      head=null;
   }
   //Don't modify anything before this line. 
   //Do not add any other instance variables.
   

   // *** Student tasks: implement the following methods *** 
  
   public void empty(){
   // delete all lines in the editor
      head = null;
   }

   public void load(String fileName, boolean append){
   // append is true, read lines and add to existing list, 
   // otherwise, create new list.
	// Each line is stored in a Node.
	// You may need to handle exception within this method
	// or throw runtime exception only.

      try (Scanner scnr = new Scanner(new FileInputStream(fileName))) {
         if (append == true) {
            // If the list is empty, make a new one
            if (head == null) {
               if (scnr.hasNext()) {
                  head = new Node(scnr.nextLine());
               }
            }

            // Find the last node in the current list
            Node current = head;
            if (current != null) {
               while (current.getNext() != null) {
                  current = current.getNext();
               }
            }

            // Append the new nodes to the end of the list
            while (scnr.hasNext()) {
               Node newNode = new Node(scnr.nextLine());
               if (current == null) {
                  head = newNode; // if the list was empty make the head the newNode
               }
               else {
                  current.setNext(newNode); // link the previous node to it
               }
               current = newNode; // move the pointer forward
            }
         } 
         else {
            if (!scnr.hasNext()) {    // if the file is empty return
               System.out.println("Error: File is empty");
               return; 
            }

            head = new Node(scnr.nextLine());  // create the head node
            Node current = head;                // keep track of the last node

            while (scnr.hasNext()) {
               Node newNode = new Node(scnr.nextLine()); // create new node
               current.setNext(newNode);                 // link the previous node to it
               current = newNode;                        // move the pointer forward
            }
         }
      }
      catch (FileNotFoundException e) {
         System.out.println("File not found: " + fileName);
      }
      catch (Exception e) {
         System.out.println("Error loading file: " + e.getMessage());
      }
   }
   
   public void save(String fileName){
   // Save all lines represented with Nodes to a file. 
	// Each line (Node) occupies a line in the saved file.
	// You may need to handle exception within this method
	// or throw runtime exception only.
      try (PrintWriter outFS = new PrintWriter(new FileOutputStream(fileName))) {
         // If the list is empty, there’s nothing to save
         if (head == null) {
            System.out.println("Error: Writer is empty");
            return;
         }

         // Start at the head of the linked list
         Node current = head; 

         while (current != null) {
            // Write the text stored in each Node to the file
            outFS.print(current.getLine());
            // Move to the next Node
            if (current.getNext() != null) {
               outFS.println(); // Add newline only if this is not the last line
            }
            current = current.getNext();  
         }
      } 
      catch (FileNotFoundException e) {
         System.out.println("Error: File not found - " + e.getMessage());
      } 
      catch (Exception e) {
         System.out.println("Error saving file: " + e.getMessage());
      }
   }
   
   public void addLine(String line){
   //append the line to the end of the list
      
      // If the list is empty, make the new line the head
      if (head == null) {
         head = new Node(line);
         return;
      }

      // Find the last node in the current list
      Node current = head;
      if (current != null) {
         while (current.getNext() != null) {
            current = current.getNext();
         }
      }

      // Add the new node to the end of the list
      Node newNode = new Node(line);
      current.setNext(newNode);
   }

   public void addLine(String line, int n){
   //insert new line to nth line, if n > total number of line, 
   //append to the end of the list.

      Node newNode = new Node(line);
      
      // If the list is empty or we are insterting at the beginning (n <= 1)
      if (head == null || n <= 1) {
         newNode.setNext(head);
         head = newNode;
         return;
      }

      // Traverse to the (n-1)th node or stop at the last node
      Node current = head;
      int index = 1;
      while (current.getNext() != null && index < (n - 1)) {
            current = current.getNext();
            index++;
      }

      // Insert newNode after current
      newNode.setNext(current.getNext());
      current.setNext(newNode);
   }
   
   public int words(){
      // count number of words, word may be separated with 
      // \t,;.?!-@spaces ' * and "
      int count = 0;
      Node current = head;

      while (current != null) {
         // Split the line by the given delimiters using regex
         String[] words = current.getLine().trim().split("[\\s\\t,.;'?!*\"@\\-:]+");

         // Count the non-empty entires in the list
         for (String word : words) {
            if (!word.isEmpty()) {
               count++;
            }
         }
         // Move to the next node
         current = current.getNext();
      }

      return count;
   }

   public int lines(){ 
      // count number of lines, which is the same as the number of
      // nodes in the list.
      int count = 0;
      Node current = head;

      while (current != null) {
         count++;
         current = current.getNext();
      }

      return count;
   }

   public void delete(int n){
      //delete nth line if exists. Otherwise do nothing.

      // If the list is empty
      if (head == null) {
         System.out.println("The list is empty. Nothing deleted.");
         return;
      }

      // If deleting the first line
      if (n == 1) {
         head = head.getNext();
         return;
      }

      // Traverse to the (n-1)th node
      Node current = head;
      for (int i = 1; i < (n - 1) && current.getNext() != null; i++) {
            current = current.getNext();
      }

      // If the nth node does not exist
      if (current.getNext() == null) {
         System.out.println("There is no line at position " + n + ". Nothing deleted.");
         return;
      }

      // Skip the nth node to delete it
      current.setNext(current.getNext().getNext());
   }

   public String toString(){
      // Return all lines represented by Nodes in the list. All lines
      // are in the same order as their corresponding nodes in the list.
      // All lines are separated with \n. No newline character should be 
	   // added after the last line.

      // If the list is empty return empty string
      if (head == null) {
         return "";
      }

      Node current = head;
      String lines = "";

      while (current != null) {
         lines += current.getLine(); // Add the current line
         if (current.getNext() != null) {
            lines += "\n"; // Add newline only if not the end of the list
         }
         current = current.getNext();
      }

      return lines;
   }

   public void print(){
      // Print each line. Each line is proceeded with its corresponding line 
      // numbers. Please refer to sample output.

      // If the list is empty, print message and return
      if (head == null) {
         return;
      }

      Node current = head;
      int index = 1;
      
      while (current != null) {
         System.out.println(index + ". " + current.getLine());
         current = current.getNext();
         index++;
      }
   }
   
   public void replace(String s1, String s2){
      // Replace all occurrences of s1 with s2.

      //if the list is empty or the list
      if (head == null) {
         System.out.println("List is empty. No replacements made.");
         return;
      }

      Node current = head;

      // Traverse through the whole list
      while (current != null) {
         // Replace all occurrences of the whole word s1 in the current line with s2
         String updatedLine = current.getLine().replaceAll("\\b" + s1 + "\\b", s2);
         current.setLine(updatedLine);

         // Move to the next node
         current = current.getNext();
      }
   }
   
   public void line(int n){
      // Print nth line in the document. (The nth node in the list)
      // If nth line does not exists, print �Line n does not exist.�

      // If the list is empty, print and return
      if (head == null || n <= 0) {
         System.out.println("Line " + n + " does not exist.");
         return;
      }

      Node current = head;

      // Traverse to the nth node
      int index = 1;
      while (current != null && index < n) {
            current = current.getNext();
            index++;
      }

      // If the nth node does not exist
      if (current == null) {
         System.out.println("Line " + n + " does not exist.");
         return;
      }

      System.out.println(current.getLine());
   }
}