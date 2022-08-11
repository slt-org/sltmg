const GLASS   = .614;
const WATER   = .326;
const BINDER  = .032;
const LMEDIUM = .027;
// proportion values for coe96
const GLASS96   = .579;
const WATER96   = .359;
const BINDER96  = .036;
const LMEDIUM96 = .026;
const CUBICCM = 'cm<sup>3</sup>';
const CUBICIN = 'in<sup>3</sup>';
const PI = 3.1416;
const GLASSCONV   = 0.232;
const WATERCONV   = 0.236;
const BINDERCONV  = 0.416;
const LMEDIUMCONV = 0.2;
const CUP   = "cup";  
const a3QTR = "3/4";
const aHALF = "1/2";
const a1QTR = "1/4" 
const TBSP  = "tbsp";
const TSP   = "tsp";
const IMPERIAL = "imperial";
const METRIC = "metric";
const CITOCC = 16.387;
const INTOCM = 2.54;
const OZTOGM = 28.3495;
const AUTHOR = "Steve Terry";
const VERSION = "1.0";
const REL = "15";
const ver="slt-test. 1.0";


/**
 * 113g frit powder             (½ cup plus 2 level teaspoons ) 26.3 tsps in 113g   0.232 grams per tsp.
    60g H₂O                      (¼ cup plus 2 teaspoons water)  14.17 tsps in 60g   0.236 grams per tsp
     6g powdered binder         (2 ½ level teaspoons)            2.5 tsps in 6g      0.416 grams per tsp  
     5g liquid medium           (1 teaspoon)                     1 tsp in 5g         0.2   grams per tsp


   3 tsp  in 1 tbsp
   16.2 tbsp in 1 cup
   48.7 tsp  in 1 cup  
   
   SRA
   glass   

    I have a weight of MG calculated from a volume.
    I want to create a recipe in imperial units.

    conversion factors for 
    glass         0.232g/tsp.
    h2o           0.236g/tsp  
    binder        0.416g/tsp
    lmedium       0.2g/tsp

    let's say I have 100g of MG from my volume calculation 
    multiplying each ingredient by it's conversion factor should give tsps for each.

    glass  61.4   * 0.232 = 14.24 
    h2o    32.6   * 0.236 =  7.69
    binder  3.2   * 0.416 =  1.33
    lmedium 2.7   * 0.2   =  0.54 
 */