package com.company;


import java.io.*;

/**
 * Created by Abid Hasan on 7/16/2016.
 */
public class Second_Pass_Assembler {

    final static String psudoInsrtuction[] = {"ORG", "END", "DEC", "HEX"};
    final static String mriInstruction[] = {"AND", "ADD", "LDA", "STA", "BUN", "BSA", "ISZ"};
    final static String mriInstructionCode[] = {"000", "001", "010", "011", "100", "101", "110"};
    final static String registerReferenceInstruction[] = {"CLA", "CLE", "CMA", "CME", "CIR", "CIL", "INC", "SPA", "SNA", "SZA", "SZE", "HLT"};
    final static String registerReferenceInstructionCode[] = {"100000000000", "010000000000", "001000000000", "000100000000", "000010000000", "000001000000", "000000100000", "000000010000", "000000001000", "000000000100", "000000000010", "000000000001"};
    final static String IOinstuction[] = {"INP", "OUT", "SKI", "SKO", "ION", "IOF"};
    final static String IoinstructionCode[] = {"100000000000", "010000000000", "001000000000", "000100000000", "000010000000", "000001000000"};

    public void Second_Pass_Assembler() {

        System.out.println("------------------------::Second Pass::--------------------------");


        String I = "";
        String opcode = null;
        String address = "";
        String finalcode = null;

        String textLine;
        String tableLine;
        String AddLabel = "";
        String tableLabel = "";
        String Location = "";
        String tabelAddress = "";
        String[] tablelabel = new String[100];
        int[] addressNumber = new int[100];


        int i = 0;
        int j = 0;
        int indexS = 0;
        int indexE = 0;
        int tableCounter = 0;
        int LocationCounter = 0;


        boolean psudo = false;
        boolean mri = false;
        boolean rri = false;
        boolean io = false;
        boolean end = false;


        try {

            BufferedReader input = new BufferedReader(new FileReader("input.txt"));
            BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
            BufferedReader SymbolTable = new BufferedReader(new FileReader("symbolTable.txt"));

            while ((tableLine = SymbolTable.readLine()) != null) {
                tableLabel = tableLine.substring(0, tableLine.indexOf(" "));
                tabelAddress = tableLine.substring(tableLine.indexOf(" ") + 1, tableLine.length());
                //System.out.println(address);
                tablelabel[j] = tableLabel;
                addressNumber[j] = Integer.parseInt(tabelAddress);
                j++;
                tableCounter++;
            }


            while ((textLine = input.readLine()) != null) {                       //reading text file
                Location = Location + LocationCounter;
                LocationCounter++;

                if (textLine.indexOf(", ") != -1) {                              //separating labels
                    indexS = textLine.indexOf(", ");
                    indexE = textLine.length();
                    textLine = textLine.substring(indexS + 2, indexE);
                }


                if (!end) {                                                     //checking psudoInstruction
                    int number = 0;
                    String _number = "";
                    String ad = "";

                    if (psudo = (textLine.indexOf(psudoInsrtuction[0])) != -1) {         //checking ORG
                        String _location = "";
                        int org_Location = 0;
                        indexS = textLine.lastIndexOf(" ") + 1;
                        indexE = textLine.length();
                        _location = textLine.substring(indexS, indexE);
                        org_Location = Integer.parseInt(_location);
                        LocationCounter = org_Location - 1;
                    }
                    if (psudo = (textLine.indexOf(psudoInsrtuction[1])) != -1) {        //checking END
                        end = true;
                    }
                    if (psudo = (textLine.indexOf(psudoInsrtuction[2])) != -1) {        //checking DEC and converting


                        int addressLength;
                        indexS = textLine.indexOf(" ") + 1;
                        indexE = textLine.length();
                        _number = textLine.substring(indexS, indexE);

                        number = Integer.parseInt(_number);
                        ad = Integer.toBinaryString(number);
                        addressLength = ad.length();

                        if (addressLength > 16) {
                            address = ad.substring((addressLength - 16), addressLength);

                        } else {
                            for (i = 0; i < (16 - addressLength); i++) {
                                ad = "0" + ad;
                            }
                            address = ad;


                        }
                        finalcode = address;
                        System.out.println(finalcode);
                        output.write(LocationCounter + "\t" + "  " + finalcode + "\n");
                        output.flush();

                    }
                    if (psudo = (textLine.indexOf(psudoInsrtuction[3])) != -1) {        //checking HEX and converting
                        int addressLength;
                        indexS = textLine.indexOf(" ") + 1;
                        indexE = textLine.length();
                        _number = textLine.substring(indexS, indexE);

                        number = Integer.parseInt(_number, 16);
                        ad = Integer.toBinaryString(number);
                        addressLength = ad.length();

                        if (addressLength > 16) {
                            address = ad.substring((addressLength - 16), addressLength);

                        } else {
                            for (i = 0; i < (16 - addressLength); i++) {
                                ad = "0" + ad;
                            }
                            address = ad;


                        }
                        finalcode = address;
                        System.out.println(finalcode);
                        output.write(LocationCounter + "\t" + "  " + finalcode + "\n");
                        output.flush();

                    }

                }


                if (!psudo && !end) {

                    for (i = 0; i < 7; i++) {
                        mri = textLine.contains(mriInstruction[i]);

                        if (mri) {

                            if (textLine.indexOf(" I") != -1) {
                                I = "1";
                                textLine = textLine.substring(0, textLine.indexOf(" I"));
                                System.out.println(textLine);
                            } else {
                                I = "0";
                            }


                            int number = 0;
                            opcode = mriInstructionCode[i];
                            //System.out.println(mriInstructionCode[i]);
                            AddLabel = textLine.substring(textLine.indexOf(" ") + 1, textLine.length());
                            //  System.out.println(AddLabel);
                            j = 0;
                            while (j < tableCounter) {
                                if (tablelabel[j].contains(AddLabel)) {
                                    // System.out.println(AddLabel+"  "+addressNumber[j]);
                                    number = Integer.parseInt(String.valueOf(addressNumber[j]), 16);
                                    address = Integer.toBinaryString(number);

                                }
                                j++;
                            }
                            System.out.println("address: " + address);
                            System.out.println("addressLength: " + address.length());

                            while (address.length() < 12) {
                                address = "0" + address;
                            }
                            System.out.println("MRI Address:" + address);

                            finalcode = I + opcode + address;

                            System.out.println("finalcode " + " " + I + " " + opcode + "  " + address + " F : " + finalcode);
                            output.write(LocationCounter + "\t" + "  " + finalcode + "\n");
                            output.flush();
                        }


                    }
                }

                if (!mri && !psudo && !end) {
                    for (i = 0; i < 12; i++) {
                        rri = (textLine.indexOf(registerReferenceInstruction[i])) != -1;
                        if (rri) {
                            opcode = "111";
                            I = "0";
                            address = registerReferenceInstructionCode[i];

                            finalcode = I + opcode + address;
                            output.write(LocationCounter + "\t" + "  " + finalcode + "\n");
                            output.flush();

                        }

                    }
                }
                if (!mri && !psudo && !end && !rri) {
                    for (i = 0; i < 6; i++) {
                        io = (textLine.indexOf(IOinstuction[i])) != -1;
                        if (io) {
                            I = "1";
                            opcode = "111";
                            address = IoinstructionCode[i];

                            finalcode = I + opcode + address;

                            output.write(LocationCounter + "\t" + "  " + finalcode + "\n");
                            output.flush();

                        }

                    }

                }
//                if (!(mri || psudo || rri || end || io))
//                {
//                    output.write("Invalid Instruction at : "+LocationCounter+"\n");
//                    output.flush();
//                }

            }

            j = 0;

            String s, s1, s2, s3, s4, s5, s6;

            int assci;
            int assci2;
            int assci3;
            int assci4;

            BufferedWriter table = new BufferedWriter(new FileWriter("symbolTable.txt"));

            while (tablelabel[j] != null) {
                if (tablelabel[j].length() == 1) {
                    s = tablelabel[j];
                    s1 = ",";
                    s = s + s1;

                    assci = (int) s.charAt(0);
                    assci2 = (int) s.charAt(1);


                    s2 = Integer.toBinaryString(assci);
                    s3 = Integer.toBinaryString(assci2);
                    s6 = Integer.toBinaryString(Integer.parseInt(String.valueOf(addressNumber[j]), 16));

                    while (s2.length() < 8) {
                        s2 = "0" + s2;
                    }
                    while (s3.length() < 8) {
                        s3 = "0" + s3;
                    }
                    while (s6.length() < 16) {
                        s6 = "0" + s6;
                    }
                    table.write(s + "   \t" + s2 + "" + s3 + "\n");
                    table.write("(LC)" + "\t" + s6 + "\n");
                    table.flush();

                }
                if (tablelabel[j].length() == 2) {
                    s = tablelabel[j];
                    s1 = ",";

                    assci = (int) s.charAt(0);
                    assci2 = (int) s.charAt(1);
                    assci3 = (int) s1.charAt(0);

                    s2 = Integer.toBinaryString(assci);
                    s3 = Integer.toBinaryString(assci2);
                    s4 = Integer.toBinaryString(assci3);
                    s5 = "";
                    s6 = Integer.toBinaryString(Integer.parseInt(String.valueOf(addressNumber[j]), 16));

                    while (s2.length() < 8) {
                        s2 = "0" + s2;
                    }
                    while (s3.length() < 8) {
                        s3 = "0" + s3;
                    }
                    while (s4.length() < 8) {
                        s4 = "0" + s4;
                    }
                    while (s5.length() < 8) {
                        s5 = "0" + s5;
                    }
                    while (s6.length() < 16) {
                        s6 = "0" + s6;
                    }
                    table.write(s + "  \t" + s2 + "" + s3 + "\n" + s1 + "      \t" + s4 + "" + s5 + "\n");
                    table.write("(LC)" + "\t" + s6 + "\n");
                    table.flush();

                }
                if (tablelabel[j].length() == 3) {

                    s = tablelabel[j].substring(0, 2);
                    s1 = tablelabel[j].substring(2, tablelabel[j].length());
                    System.out.println(s);
                    System.out.println(s1);
                    s1 = s1 + ",";
                    assci = (int) s.charAt(0);
                    assci2 = (int) s.charAt(1);
                    assci3 = (int) s1.charAt(0);
                    assci4 = (int) s1.charAt(1);

                    s2 = Integer.toBinaryString(assci);
                    s3 = Integer.toBinaryString(assci2);
                    s4 = Integer.toBinaryString(assci3);
                    s5 = Integer.toBinaryString(assci4);
                    s6 = Integer.toBinaryString(Integer.parseInt(String.valueOf(addressNumber[j]), 16));


                    while (s2.length() < 8) {
                        s2 = "0" + s2;
                    }
                    while (s3.length() < 8) {
                        s3 = "0" + s3;
                    }
                    while (s4.length() < 8) {
                        s4 = "0" + s4;
                    }
                    while (s5.length() < 8) {
                        s5 = "0" + s5;
                    }
                    while (s6.length() < 16) {
                        s6 = "0" + s6;
                    }
                    table.write(s + "  \t" + s2 + "" + s3 + "\n" + s1 + "  \t" + s4 + "" + s5 + "\n");
                    table.write("(LC)" + "\t" + s6 + "\n");
                    table.flush();
                }
                j++;
            }

            input.close();
            output.close();
            SymbolTable.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
