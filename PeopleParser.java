package com.company;

import java.util.List;

/**
 * Creates List of Persons from lines of text
 * Interrogates Persons to output to console
 *
 */
public class PeopleParser
{

    //C
    public List<Person> CreatePersons(String[] conversation) {

        if (conversation == null)
        {
            System.out.print("File empty. Please add some conversation."
                    + "\nConversations are lines of text that follow this form:"
                    + "\n   <person>:<white space><sentence>");
            return null;
        }
        else
        {
            //counter used to place in person array
            int personCounter = 0;

            //take every line and make a new person if new name
            for (String line : conversation)
            {
                //CHECK for blank line
                if (line.length() != 0)
                {
                    boolean nameExists = false;

                    //remove name and colon, rest is person's sentence
                    String[] lineSplit = line.split(":");

                    //CHECK for too many or too little colons
                    if (lineSplit.length != 2)
                    {
                        System.out.print("Incorrect line format"
                                + "\nConversations are lines of text that follow this form:"
                                + "\n   <person>:<white space><sentence>");
                        Main.people = null;
                        return null;
                    }
                    String name = lineSplit[0];
                    String sentence = lineSplit[1];
                    Person person = new Person();

                    //if name is in use, set as existing
                    if (Main.people != null) {
                        for (Person tempPerson : Main.people)
                        {
                            if (name.equals(tempPerson.name))
                            {
                                person = tempPerson;
                                nameExists = true;
                            }
                        }


                        //create new person to have new line
                        if (nameExists == false)
                        {
                            //increase person count to add person
                            personCounter = personCounter + 1;

                            person.name = name;
                            person.sentences.add(sentence);
                            String[] words = sentence.split("\\s+");
                            for (String word : words)
                            {
                                if (word.length() > 0)
                                {
                                    person.words.add(word);
                                }
                            }
                            Main.people.add(person);
                        }
                        else //adding sentence to existing person
                        {
                            person.sentences.add(sentence);
                            String[] words = sentence.split("\\s+");
                            for (String word : words)
                            {
                                if (word.length() > 0)
                                    person.words.add(word);
                            }
                        }
                    }
                }
            }

            return Main.people;
        }
    }

    public void InterrogatePersons()
    {
        Parser parser = new Parser();

        for (Person person : Main.people)
        {
            boolean speaksDiego = true;

            for (String word : person.words)
            {
                if (parser.Parse(word) == false)
                {
                    speaksDiego = false;
                }
            }
            if (speaksDiego)
            {
                System.out.print(person.name + "   Found out Diego secrets\n");
            }
            else
            {
                System.out.print(person.name + "   Impersonator caught\n");
            }
            for (String sentence : person.sentences)
            {
                if (sentence != null)
                    System.out.println(sentence);
            }

            System.out.print("   Words:\n");
            for (String word : person.words)
            {
                System.out.print(word + "           ");
                if (parser.Parse(word))
                {
                    System.out.print("word");
                }
                else
                {
                    System.out.print("not a word");
                }
                System.out.print("\n");
            }

            System.out.print("\n");
        }
    }
}

