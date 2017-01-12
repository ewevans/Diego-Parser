package com.company;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
//Ethan Evans
//CIS 400
// Assignment 2


//Pre: Input via CIS400A1.dat in format of lines of
//          <name> : <sentence>
//
//Post: If valid data,
//   <Persons Name>   Found out Diego secrets or Impersonator Caught
//   <List of sentences spoken>
//   <List of words>   <word or not a word>

public class Main {

    //List of Persons that hold the words and sentences they speak
    static List<Person> people = new ArrayList<>();

    public static void main(String[] args) {


        // write your code here
        String[] conversation;

        //Get lines of text from file
        conversation = QueryFile();


        if (conversation == null) {
            //file error
            //error output already occurred
        } else {
            PeopleParser peopleParser = new PeopleParser();
            peopleParser.CreatePersons(conversation);


            //error output already occurred
            if (people == null) {
                //System.out.print("ERROR: people is null");
                //people is null if invalid format is found

            } //If no lines of text
            else if (people.size() == 0)
            {
                System.out.print("Input file CIS400A1.dat is empty."
                        + "\nConversations are lines of text that follow this form:"
                        + "\n   <person>:<white space><sentence>");
            }
            else
            {   //output to console
                peopleParser.InterrogatePersons();
            }
        }
    }

    //Opens data file and returns lines of text as array
    private static String[] QueryFile ()
    {
        //create file path for CIS400A1.dat
        Path path = Paths.get("CIS400A1.dat");

        try {
            //Get list of lines from text file
            List<String> lines = Files.readAllLines(path);

            //Convert to string array
            String[] arr = lines.toArray(new String[lines.size()]);

            return arr;
        }
        catch (IOException ex)
        {
            System.out.print("File 'CIS400A1.dat' not found.\n");

            return null;
        }
    }
}