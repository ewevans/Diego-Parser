package com.company;

/**
 * Parser
 *  The recursive-descent parser for the Diego language
 *
 *  The Diego Language
 *
 *  <stop>  → b
 *            |d
 *  <plosive>  → <stop>a
 *  <syllable> → <plosive>
 *               |<plosive><stop>
 *               |a<plosive>
 *               |a<stop>
 *  <word> → <syllable>
 *           |<syllable><word><syllable>
 *  <sentence> → <word>
 *               |<sentence>s<word>

 */
public class Parser {
    //location of the cursor during the parse
    //Used in pair with savedCursor to control backtracking
    int cursor = 0;

    //word being parsed
    String word;

    //Parses word in Diego and returns true if correct Diego, false otherwise
    // letters not in Diego alphabet return false
    public boolean Parse(String wordInput) {
        cursor = 0;
        word = wordInput;
        return Word();
    }

    //Gives the next token in the word
    // returns 'n' if end of the word
    char lex() {
        if (cursor > word.length() - 1)
        {
            return 'n';
        }
        char nextToken = word.charAt(cursor);
        cursor = cursor + 1;
        return nextToken;
    }

    //Checks for terminal
    //returns a true if the char in parameter is next token
    boolean term(char expected) {
        //System.out.println("Checking for term: " + expected);
        return (lex() == expected);
    }

    //Begins the parse of each word
     //  <word> → <syllable>
     //           |<syllable><word><syllable>
    boolean Word()
    {
        //System.out.println("Entering Word");

        //cursor location saved for backtrack
        int savedCursor = cursor;

        //Calls Word1() to check for 1-syllable word.
        if (Word1())
        {
            //System.out.println("Exiting Word");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }

        //Calls Word2() to check for
        if (Word2())
        {
            //System.out.println("Exiting Word");
            return true;
        }
        else
        {
            //System.out.println("Exiting Word");
            return false;
        }

    }


    //Returns true if 1 syllable word is located at cursor
    boolean Word1()
    {
        //System.out.println("Entering Word1");
        if (Syllable() && (term('n')))
        {
            //System.out.println("Exiting Word1");
            return true;
        }
        else
        {
            //System.out.println("Exiting Word1");
            return false;
        }
    }


    // Due to <plosive> and <plosive><stop> both being valid
    // must check different combinations of word lengths for
    //       <word> → <syllable><word><syllable>
    //   is
    //       <word> →<2-char syllable><2-char syllable><word>
    //                |<2-char syllable><3-char  syllable><word>
    //                |<3-char syllable><2-char syllable><word>
    //                |<3-char syllable><3-char syllable><word>
    boolean Word2()
    {
        //System.out.println("Entering Word2");
        int savedCursor = cursor;

        //2,2,word
        if (Syllable2char() && Syllable2char() && Word())
        {
            //System.out.println("Exiting Word2");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }

        //2,3,word
        if (Syllable2char() && Syllable3char() && Word())
        {
            //System.out.println("Exiting Word2");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }

        //3,2,word
        if (Syllable3char() && Syllable2char() && Word())
        {
            //System.out.println("Exiting Word2");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }

        //3,3,word
        if (Syllable3char() && Syllable3char() && Word())
        {
            //System.out.println("Exiting Word2");
            return true;
        }
        else
        {
            //backtrack
            return false;
        }

    }

    // Returns true if 2 character syllable is located at cursor
    //   <syllable> → <plosive>          <--------
    //                |<plosive><stop>
    //                |a<plosive>
    //                |a<stop>           <--------
    boolean Syllable2char()
    {
        int savedCursor = cursor;
        if (term('a') && Stop())
        {
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }
        if (Plosive())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Returns true if 3 character syllable is located at cursor
    //   <syllable> → <plosive>
    //                |<plosive><stop>      <--------
    //                |a<plosive>           <--------
    //                |a<stop>
    boolean Syllable3char()
    {
        int savedCursor = cursor;
        if (term('a') && Plosive())
        {
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }

        if (Plosive() && Stop())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

     // syllable parsing for single syllables
     //
     //  <syllable> → <plosive>
     //               |<plosive><stop>
     //               |a<plosive>
     //               |a<stop>
    boolean Syllable()
    {
        //System.out.println("Entering Syllable");

        //SaveCursor();

        int savedCursor = cursor;


        if (Syllable2())
        {
            //System.out.println("Exiting Syllable");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }
        if (Syllable1())
        {
            //System.out.println("Exiting Syllable");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }
        if (Syllable3())
        {
            //System.out.println("Exiting Syllable");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }
        if (Syllable4())
        {
            //System.out.println("Exiting Syllable");
            return true;
        }
        else
        {
            //System.out.println("Exiting Syllable");
            return false;
        }
    }

    boolean Syllable1()
    {
        //System.out.println("Entering Syllable1");
        if (Plosive())
        {
            //System.out.println("Exiting Syllable1");
            return true;
        }
        else
        {
            //System.out.println("Exiting Syllable1");
            return false;
        }
    }

    boolean Syllable2()
    {
        //System.out.println("Entering Syllable2");
        if (Plosive() == false)
        {
            //System.out.println("Exiting Syllable2");
            return false;
        }
        if (Stop())
        {
            //System.out.println("Exiting Syllable2");
            return true;
        }
        else
        {
            //System.out.println("Exiting Syllable2");
            return false;
        }
    }

    boolean Syllable3()
    {
        //System.out.println("Entering Syllable3");
        if (term('a') == false)
        {
            //System.out.println("Exiting Syllable3");
            return false;
        }
        if (Plosive())
        {
            //System.out.println("Exiting Syllable3");
            return true;
        }
        else
        {
            //System.out.println("Exiting Syllable3");
            return false;
        }
    }

    boolean Syllable4()
    {
        //System.out.println("Entering Syllable4");
        if (term('a') == false)
        {
           // System.out.println("Exiting Syllable4");
            return false;
        }
        if (Stop())
        {
            //System.out.println("Exiting Syllable4");
            return true;
        }
        else
        {
            //System.out.println("Exiting Syllable4");
            return false;
        }
    }

    //
    //<plosive>  → <stop>a
    //
    boolean Plosive()
    {
        //System.out.println("Entering Plosive");
        if (Stop() == false)
        {
            //System.out.println("Exiting Plosive");
            return false;
        }
        if (term('a'))
        {
            //System.out.println("Exiting Plosive");
            return true;
        }
        else
        {
            //System.out.println("Exiting Plosive");
            return false;
        }
    }

    //   <stop>  → b
    //             |d
    boolean Stop()
    {
        //System.out.println("Entering Stop");
        //SaveCursor();

        int savedCursor = cursor;

        //b
        if (Stop1())
        {
            //System.out.println("Exiting Stop");
            return true;
        }
        else
        {
            //backtrack
            cursor = savedCursor;
        }

        //d
        if (Stop2())
        {
            //System.out.println("Exiting Stop");
            return true;
        }
        else
        {
            //System.out.println("Exiting Stop");
            return false;
        }
    }

    boolean Stop1()
    {
        //System.out.println("Entering Stop1");
        if (term('b'))
        {
            //System.out.println("Exiting Stop1");
            return true;
        }
        else
        {
            //System.out.println("Exiting Stop1");
            return false;
        }
    }

    boolean Stop2()
    {
       // System.out.println("Entering Stop2");
        if (term('d'))
        {
            //System.out.println("Exiting Stop2");
            return true;
        }
        else
        {
            //System.out.println("Exiting Stop2");
            return false;
        }
    }
}
