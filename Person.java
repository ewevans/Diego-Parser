package com.company;
import java.util.ArrayList;
import java.util.List;

/** Person
 *   Data structure to pair person with speech
 */
public class Person
{
    //name of speaker who may or may not be a Diego secret keeper
    String name;

    //every line of text from the person
    List<String> sentences = new ArrayList<>();

    //An in-order list of words found in all sentences
    List<String> words = new ArrayList<>();
}
