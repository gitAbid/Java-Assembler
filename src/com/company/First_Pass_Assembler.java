package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Abid Hasan on 6/17/2016.
 */
public class First_Pass_Assembler {


    public void First_Pass_Assembler() {


        int lineNumber = 0;

        //Flags
        boolean label = false;
        boolean org = false;
        boolean end = false;

        int org_location = 0;
        int index = 0;
        int location = 0;
        int label_index = 0;

        String label_Value = "";

        System.out.println("------------------------::First Pass::--------------------------");


        try {
            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter output = new BufferedWriter(new FileWriter("symbolTable.txt"));
            String line = null;

            while ((line = input.readLine()) != null && !end)                 //reading from file
            {
                lineNumber++;
                System.out.println(location + " " + line + "  " + line.indexOf(" "));
                if (line.indexOf("END") == 0) {
                    end = true;
                } else if ((label_index = line.indexOf(",")) != -1) {  //check label
                    label = true;
                    label_Value = line.substring(0, label_index);


                    output.write(label_Value + " " + (location) + "\n");           //   Modified Last
                    output.flush();

                   /* System.out.print("Label Table:"+symbol_table[index].getLabel()+" ");
                    System.out.println(symbol_table[index].getLocation());*/
                    index++;
                    location++;
                } else if (org = (line.indexOf("ORG")) != -1) {         //Check ORG and Set Location
                    label = false;
                    org = true;
                    String _location = "";
                    org_location = line.length();
                    int indexS = line.lastIndexOf(" ") + 1;
                    int indexE = line.length();
                    // System.out.println(indexE);
                    //System.out.println(indexS);

                    _location = line.substring(indexS, indexE);
                    org_location = Integer.parseInt(_location);


                    location = org_location;

                    // System.out.println(org_location);
                } else {
                    org = false;
                    label = false;
                    end = false;
                    location++;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
