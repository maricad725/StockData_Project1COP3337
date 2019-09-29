//=============================================================================
// PROGRAMMER: Alex Lopez  
// PANTHER ID:  6132200
//
// CLASS: COP3337      
// SECTION: U10   
// SEMESTER: Fall
// CLASSTIME: 7:50-9:05 p.m.
//
// Project #1    
// DUE:  9/22/18       
//
// CERTIFICATION: I certify that this work is my own and that
//                 none of it is the work of any other person.
//=============================================================================

package project1;

//--------------------------------------------------
// Imports
//--------------------------------------------------
import java.io.*;
import java.util.*;
import static java.lang.Math.sqrt; // square root for standard deviation func
import java.text.DecimalFormat;    // Formating the correlation values


public class Lopez_Project1 {
    
    public static void main(String[] args) {
        
        // The name of the file to open.
        // notice that the StockPrice_X_Data.txt is in the data package
        String fileName = "src/data/Stock_Data.txt";

        // This will reference one line at a time
        String line = null;
        
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =  new FileReader(fileName);

            // Always wrap FileReader in BufferedReader to deal with buffers
            BufferedReader bufferedReader =  new BufferedReader(fileReader);
            
            // Creating Arraylists to store stock prices of each company 
            ArrayList<Double> x_stockPrices = new ArrayList<>();    // stock data for x 
            ArrayList<Double> GE_stockPrices = new ArrayList<>();   // stock data for GE 
            ArrayList<Double> APPL_stockPrices = new ArrayList<>(); // stock data for Apple
            ArrayList<Double> GOOG_stockPrices = new ArrayList<>(); // stock data for Google
            ArrayList<Double> F_stockPrices = new ArrayList<>();    // stock data for F
           
            //------------------------------------------------------------------
            // Inputing the stock data into their ArrayLists
            //------------------------------------------------------------------ 
  
            int lineCount = 1;    // line counter that  
            String comma = "[,]"; // string delimeter using commmas for reading stock values
            
            // 2d array list to store all stock data
            ArrayList<ArrayList<Double>> stockData = new ArrayList<>();
            Collections.addAll(stockData, x_stockPrices, GE_stockPrices, APPL_stockPrices, 
                                          GOOG_stockPrices, F_stockPrices);
            
            // data from file in bufferedReader is assigned to line and read each line of 
            while ((line = bufferedReader.readLine()) != null){
                // to store data from data below file header
                if (lineCount != 1 ) {
                    String [] Tokens = line.split(comma); // stock tokens separated by commas 
                    
                    for (int s = 0; s < stockData.size(); s++ ){
                        stockData.get(s).add(Double.parseDouble(Tokens[s + 1]));
                    }// end for
                    
                } // ends if                    
                else {
                    lineCount++; // to avoid reading the header of the data  
                } // ends else 
            
            }// ends while 
            
            //close buffer file reader.
            bufferedReader.close(); 

            //------------------------------------------------------------------
            // Doing the stock calculations 
            //------------------------------------------------------------------ 
               
            // 2D Array list to store all correlation values of the stock data and to print them out in rows
            ArrayList<ArrayList<Double>> correlationMatrix = new ArrayList<ArrayList<Double>>();
            
            // correlation arraylists to store correlations of stock data
            ArrayList<Double> x_Correlations = new ArrayList<>();
            ArrayList<Double> GE_Correlations = new ArrayList<>();  
            ArrayList<Double> APPL_Correlations = new ArrayList<>();
            ArrayList<Double> GOOG_Correlations = new ArrayList<>();
            ArrayList<Double> F_Correlations = new ArrayList<>();
            
            // 2d arraylist that contains all correlation data Arraylists
            ArrayList<ArrayList<Double>> cData = new ArrayList<>();
            Collections.addAll(cData, x_Correlations, GE_Correlations, APPL_Correlations,
                                      GOOG_Correlations, F_Correlations);
            
            // Arraylist that holds all correlation data
            int StockList_Counter = 0;
            while (StockList_Counter < stockData.size()){    
                // storing correlation data into each correlation stock arraylist 
                double calcCorrelation = 0;
                for (int s = 0; s < stockData.size(); s++){
                    calcCorrelation = findCorrelation(stockData.get(StockList_Counter), stockData.get(s)); 
                    cData.get(StockList_Counter).add(calcCorrelation);
                }
                
                correlationMatrix.add(cData.get(StockList_Counter)); // stores all the data into matrix
                StockList_Counter++;                                 // moves to next stock 
            }
                
            // Print out rows of matrix 
            for (int c = 0; c < correlationMatrix.size(); c++){
                System.out.println(correlationMatrix.get(c));
            }	
            
        } // end try 
        
        // designed to handle errors if they arise
        catch(FileNotFoundException ex) {
             System.out.println("Unable to open file '" + fileName + "'");                
        } 
        catch(IOException ex) {
             System.out.println("Error reading file '" + fileName + "'");                  
        }//end try catch 
  
    }// end main()
        
//------------------------------------------------------------------------------
// helper functions/
//------------------------------------------------------------------------------
    
    public static double findAverage(ArrayList<Double> prices) {
        
        double average = 0.0;
        double totalsum = 0.0;
	
        for (double currentDouble: prices){
            totalsum += currentDouble;        
        }
        
        average = totalsum / prices.size();
        return average;
    }//end findAverage()
    
    //--------------------------------------------------------------------------
    public static double findStandardDeviation(ArrayList<Double> prices){
        
        double stdDev = 0.0;
        double average = findAverage(prices);
 	double totalsum = 0.0; 
	   
        for(double currentDouble: prices){      
            totalsum += ((currentDouble - average) * (currentDouble - average)); 
        }
        
        stdDev = sqrt(totalsum/ (prices.size()-1) );
        return stdDev;
    }

    //--------------------------------------------------------------------------
    
    public static double findCorrelation(ArrayList<Double> firstPrices, ArrayList<Double> secondPrices ) {
        
        DecimalFormat dRound = new DecimalFormat("#.00"); // text format that gets string of correlation  
        
        double correlation = 0.0 ; // main variable for this helper method 
        
        // variables used for the calculation of the correlation values for any given stock arraylist 
        double stdDev1 = findStandardDeviation(firstPrices); 
        double stdDev2 = findStandardDeviation(secondPrices); 
        double average1 = findAverage(firstPrices);  
        double average2 = findAverage(secondPrices); 
        double totalsum = 0.0;
        
        for (int x = 0; x < firstPrices.size(); x++) {
            totalsum += (firstPrices.get(x) - average1) * (secondPrices.get(x) - average2);
        }
        
       correlation = totalsum /((stdDev1 * stdDev2) * (firstPrices.size() - 1));
       
       // rounds and returns the correlation value as a double
       return Double.parseDouble(dRound.format(correlation)); 
    
    }//end findCorrelation()
      
} // end class 
