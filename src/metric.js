const GLASS   = .614;
const WATER   = .326;
const BINDER  = .032;
const LMEDIUM = .027;
const CUBICCM = 'cm<sup>3</sup>';
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


/**#####################################
 * Gets a json values from server-side input.
 * Sends it to updateNumber to be updated.
 * Result will be returned server-side.
 *
 * @param {string} element - json as text from server-side
 * @return {string} as html for MG batch recipe.
 */
function mGetDiskRecipeFromWebAPP(element) {
    var inputUnits = "metric";
    console.log("in  metric.js:mGetDiskRecipeFromWebAPP:*******************");
    console.log(element);
    //Just a quick proof that the returned number value will be a text. Remove on production.
    console.log(`Returned json is: ${element}`, typeof element);

    // Turn json text into json object
    var parsed_obj = JSON.parse(element);
    console.log("json object parsed from serverside: radius=" + parsed_obj.radius + " height="
        + parsed_obj.height + " inputUnits=" + parsed_obj.inputUnits + " outputUnits=" + parsed_obj.outputUnits);

    var volumeObject = diskCalcMetric(parsed_obj.radius, parsed_obj.height, inputUnits, parsed_obj.outputUnits);
    var resultHtml;

    if (volumeObject.outputUnits == "metric") {
        // produce the metric version of recipe
        resultHtml = mCreateRecipeTableHtml(volumeObject);
    } else if (volumeObject.outputUnits == "imperial") {
        // produce the imperial version of recipe
        // need to take the volumeObject down the Imperial path.
        resultHtml = iCreateRecipeTableHtml(volumeObject);
    } else {
        console.log("We should never get here 'Big old Error'");
        resultHtml = "<h3>Failed to create recipe...  ERROR</h3>";
    }

    console.log("out  metric.js:getDiskRecipeFromWebAPP:*******************");

    return resultHtml;
};

/**
 * This function will calculate a disk volume based on
 * centimeter inputs, generating a volume in cubic centimeters.
 *
 */
function diskCalcMetric(radius, height, inputUnits, outputUnits) {
    console.log("in metric.js:diskCalcMetric:*******************");
    console.log("value parameters on call radius=" + radius + " height=" + height + " inputUnits=" + inputUnits + " outputUnits=" + outputUnits);
    var volumeObject = new Object();
    volumeObject.volume = PI * radius * radius * height;
    volumeObject.radius = radius;
    volumeObject.height = height;
    volumeObject.inputUnits = inputUnits;
    volumeObject.outputUnits = outputUnits;

    console.log("raw volume = " + volumeObject.volume);
    console.log("out metric.js:diskCalcMetric:*******************");

    return volumeObject

}

/**#####################################
 * Gets a json values from server-side input.
 * Sends it to updateNumber to be updated.
 * Result will be returned server-side.
 *
 * @param {object} dataObject  - data object from input
 * @return {string} as html for MG batch recipe.
 */
function getSlabRecipe(dataObject) {
    console.log("in metric.js:getSlabRecipe")
    console.log(dataObject);

    var volumeObject = slabCalcMetric(dataObject.length, dataObject.width, dataObject.height, dataObject.outputUnits, dataObject.inputUnits);
    var resultHtml;

    if (volumeObject.outputUnits == "metric") {
        // produce the metric version of recipe
        resultHtml = mCreateRecipeTableHtml(volumeObject);
    } else if (volumeObject.outputUnits == "imperial") {
        // produce the imperial version of recipe
        // need to take the volumeObject down the Imperial path.
        resultHtml = iCreateRecipeTableHtml(volumeObject);
    } else {
        console.log("We should never get here 'Big old Error'");
        resultHtml = "Failed to create recipe...  ERROR";
    }
    console.log("out metric.js:getSlabRecipe")
    return resultHtml;
}

/**
 * This function will calculate a disk volume based on
 * centimeter inputs, generating a volume in cubic centimeters.
 *  @param {string} length - number as text.
 *  @param {string} width - number as text.
 *  @param {string} height - number as text.
 *  @return {object} contains input values and calculated volume.
 */
function slabCalcMetric(length, width, height, outputUnits, inputUnits) {
    console.log("in metric.js:slabCalcMetric");
    console.log("metric.js:slabCalcMetric: value parameters on call length=" + length + " width=" + width + " height=" + height + " outputUnits=" + outputUnits + " inputUnits=" + inputUnits);
    var volumeObject = new Object();
    volumeObject.volume = length * width * height;
    volumeObject.length = length;
    volumeObject.width = width;
    volumeObject.height = height;
    volumeObject.outputUnits = outputUnits;
    volumeObject.inputUnits = inputUnits;

    console.log("metric.js:slabCalcMetric: raw volume in " + outputUnits + "= " + volumeObject.volume);
    console.log("out metric.js:slabCalcMetric");
    return volumeObject
}


/**
 * this method takes json and turns it into
 * an html table of the MG recipe to be displayed to the user
 * json should have the 4 fields of glass,water,binder and liquid medium.
 */
function mCreateRecipeTableHtml(volumeObject) {
    console.log("metric.js:mCreateRecipeTableHtml: object passed to createRecipe " + JSON.stringify(volumeObject));

    volumeObject = createBaseVolume(volumeObject);

    let recipe = "";

    if (volumeObject.outputUnits != "imperial") {
        volumeObject.units = "grams";
        // set this functionality up in the metric script space
        recipe = "<b>MG total volume:</b> " + volumeObject.volume.toFixed(1) + " cm<sup>3</sup><br> <b>MG total weight:</b> " + volumeObject.total_weight.toFixed(1) + " grams\n" +
            "                <table class=\"table table-hover\">\n" +
            "                    <thead>\n" +
            "                    <tr><th scope=\"col\">Ingredient</th><th scope=\"col\">Amount</th><th scope=\"col\">Units</th></tr>\n" +
            "                    </thead>\n" +
            "                    <tbody>\n" +
            "                    <tr><th scope=\"row\">Glass</th><td>" + volumeObject.glass + "</td><td>" + volumeObject.units + "</td></tr>\n" +
            "                    <tr><th scope=\"row\">H2O</th><td>" + volumeObject.water + "</td><td>" + volumeObject.units + "</td></tr>\n" +
            "                    <tr><th scope=\"row\">Binder</th><td>" + volumeObject.binder + "</td><td>" + volumeObject.units + "</td></tr>\n" +
            "                    <tr><th scope=\"row\">Liquid Medium</th><td>" + volumeObject.liquid_medium + "</td><td>" + volumeObject.units + "</td></tr>\n" +
            "                    </tbody>\n" +
            "                </table>\n";

    } else {
        console.log("metric.js:mCreateRecipeTableHtml: needs to call function for Imperial transform");
        recipe = iCreateRecipeTableHtml(volumeObject);
    }
    return recipe;
};


/**
 * this method takes json and turns it into
 * an html table of the MG recipe to be displayed to the user
 * json should have the 4 fields of glass,water,binder and liquid medium.
 * @param {Object} volumeObject - volume DTO.
 * @param {string} volumeObject.volume -
 * @param {string} employee.department - The employee's department.
 */
function createBaseVolume(volumeObject) {
    console.log("in metric.js:createBaseVolume ***********************");
    console.log("createBaseVolume: object passed to createRecipe " + JSON.stringify(volumeObject));
    // The total volume multiplied by 2 (the sg of MG) will give you the weight
    //  of the MG needed to fill the volume.
    // Now multiply the total weight by the ratio for each ingredient.
    // That we give you the weight amount for each ingredient.
    volumeObject.total_weight = volumeObject.volume * 2;
    volumeObject.glass = (volumeObject.total_weight * GLASS).toFixed(1); // grams of glass powder to use
    volumeObject.water = (volumeObject.total_weight * WATER).toFixed(1);
    volumeObject.binder = (volumeObject.total_weight * BINDER).toFixed(1);
    volumeObject.liquid_medium = (volumeObject.total_weight * LMEDIUM).toFixed(1);

    console.log("out metric.js:createBaseVolume ***********************");

    return volumeObject;

};

/********************************************************************************************************
 *
 *   Imperial output code.
 *
 ********************************************************************************************************/

/**
 * this method takes an object and turns it into
 * an html table of the MG recipe with Imperial units to be displayed to the user
 * The volumeObject should be complete should and
 * have the 4 fields of glass,water,binder and liquid medium.
 */
function iCreateRecipeTableHtml(volumeObject) {     // a portion of this is duplicate code for metric and imperial
    console.log("in imperial.gs : iCreateRecipeTableHtml ***********************");
    console.log("iCreateRecipeTableHtml: object passed to createRecipe " + JSON.stringify(volumeObject));

    volumeObject = createBaseVolume(volumeObject);

    let text = "";

    // We need to translate from grams to imperial measurements of teaspoons ect.
    // for each ingredient. Then output the amount to table
    console.log("glass .................");
    var glassAmount = formatIngredientList(convert(volumeObject.glass, GLASSCONV));
    console.log("water .................");
    var waterAmount = formatIngredientList(convert(volumeObject.water, WATERCONV));
    console.log("binder .................");
    var binderAmount = formatIngredientList(convert(volumeObject.binder, BINDERCONV));
    console.log("medium .................");
    var mediumAmount = formatIngredientList(convert(volumeObject.liquid_medium, LMEDIUMCONV));

    var cubicInches = "in<sup>3</sup>";
    var oz = "oz";
    var impVolume;
    var impWeight;
    if(volumeObject.outputUnits == "imperial"){
        // convert volume from cc to ci and weight from gm to oz.
        impVolume=(volumeObject.volume/CITOCC).toFixed(2);
        impWeight=(volumeObject.total_weight/OZTOGM).toFixed(2);
    }

    // Logger.log("glassAmount : " + glassAmount.toString());

    text = "<p><b>MG total volume:</b> " + impVolume +" "+ cubicInches +", <b>MG total weight:</b> " + impWeight + " " + oz + "</p>"
        + "<h4> Batch Recipe</h4>"
        + "<table>"
        + "<tr><th>Ingredient</th> <th></th><th>Amount</th></tr>"
        + "<tr><td>Glass</td><td> </td><td>&nbsp; " + glassAmount.toString() + "</td></tr>"
        + "<tr><td>Water</td><td> </td><td>&nbsp; " + waterAmount.toString() + "</td></tr>"
        + "<tr><td>Binder</td><td> </td><td>&nbsp; " + binderAmount.toString() + "</td></tr>"
        + "<tr><td>Liquid Medium</td><td> </td><td>&nbsp; " + mediumAmount.toString() + "</td></tr>"
        + "</table>";
    console.log("out imperial.gs : iCreateRecipeTableHtml ***********************");
    return text;
};

// finalObject has collected values from conversion
function formatIngredientList(formatObject) {
    console.log("in imperial.gs : formatIngredientList ***********************");
    // Walk the possible values and format for
    // html template.

    var cup = false;
    var tsp = false;
    var result = "";
    var glassAmountFormatted;
    if (formatObject.cups > 0) {
        result = result + formatObject.cups + " ";
        cup = true;
    }
    if (formatObject.threeqtrCup > 0) {
        result = result + "3/4";
        cup = true;
    }
    if (formatObject.halfCup > 0) {
        result = result + "1/2";
        cup = true;
    }
    if (formatObject.qtrCup > 0) {
        result = result + "1/4";
        cup = true;
    }
    if (cup == true) {
        result = result + " cup + ";
    }
    if (formatObject.tblsp > 0) {
        result = result + formatObject.tblsp + " tblsp + ";
    }
    if (formatObject.tsp > 0) {
        result = result + formatObject.tsp + " ";
        tsp = true;
    }
    if (formatObject.threeqtrTsp > 0) {
        result = result + "3/4";
        tsp = true;
    }
    if (formatObject.halfTsp > 0) {
        result = result + "1/2";
        tsp = true;
    }
    if (formatObject.qtrTsp > 0) {
        result = result + "1/4";
        tsp = true;
    }
    if (tsp == true) {
        result = result + " tsp.";
    }

    console.log("out imperial.gs : formatIngredientList ***********************");

    return result;
}

/**
 * Take the converted input in json document and
 * create an object for ingredient amounts.
 * This will then be translated into html
 *
 */
function collectIngredientAmounts(convertedObj) {
    console.log("in imperial.gs : collectIngredientAmounts ***********************");
    // console.log("input object value " + convertedObj);
    var finalObject = new Object();
    for (const property in convertedObj) {
        // console.log(`${property}: ${convertedObj[property]}`);
        var name = property;
        var value = convertedObj[property];
        if (value > 0) {
            console.log("name: " + name + " value: " + value);
            finalObject[name] = value;
        }
    }
    console.log("out imperial.gs : collectIngredientAmounts ***********************");
    return finalObject;
};

/**
 * convert ingredient weight in grams to imperial measurements.
 * @param {number} ingredientWeightInGrams
 */
function convert(ingredientWeightInGrams, ingredientConversionFactor) {
    console.log("in imperial.gs : convert ***********************");
    console.log("input value in grams " + ingredientWeightInGrams + " input conversion factor " + ingredientConversionFactor);
    var tsp = ingredientWeightInGrams * ingredientConversionFactor;
    console.log("teaspoons from weight: " + tsp.toFixed(2));
    var conObj = new Object();
    conObj.cups = 0;         // 48 tsps
    conObj.threeqtrCup = 0;  // 36 tsps
    // conObj.twoThirdsCup = 0; // 32 tsps
    conObj.halfCup = 0;      // 24 tsps
    conObj.qtrCup = 0;       // 12 tsps
    conObj.tblsp = 0;        //  3 tsps
    conObj.threeqtrTsp = 0;
    conObj.halfTsp = 0;
    conObj.qtrTsp = 0;
    conObj.eightTsp = 0;
    conObj.tsp = 0;

    while (tsp > 48) {
        conObj.cups = conObj.cups + 1;
        tsp = tsp - 48;
    };

    if (tsp <= 47.99 && tsp >= 36) {
        conObj.threeqtrCup = 1;
        tsp = tsp - 36;
    };

    if (tsp <= 35.99 && tsp >= 24) {
        conObj.halfCup = 1;
        tsp = tsp - 24;
    };

    if (tsp <= 23.99 && tsp >= 12) {
        conObj.qtrCup = 1;
        tsp = tsp - 12;
    };

    while (tsp <= 11.99 && tsp >= 3) {
        conObj.tblsp = conObj.tblsp + 1;
        tsp = tsp - 3;
    };

    while (tsp < 2.99 && tsp >= 1) {
        tsp = tsp.toFixed(2);
        conObj.tsp = conObj.tsp + 1;
        tsp = tsp - 1;
    };

    console.log("after here there is less than 1 teaspoon left. " + tsp.toFixed(2));

    if (tsp < 0.99 && tsp >= .75) {
        conObj.threeqtrTsp = conObj.threeqtrTsp + 1;
        tsp = 0;
    }

    if (tsp < .749 && tsp >= .50) {
        conObj.halfTsp = conObj.halfTsp + 1;
        tsp = 0;
    }

    if (tsp < .499 && tsp >= .25) {
        conObj.qtrTsp = conObj.qtrTsp + 1;
        tsp = 0;
    }

    if (tsp < .249 && tsp >= .125) {
        conObj.eightTsp = conObj.eightTsp + 1;
        tsp = 0;
    }

    console.log("out imperial.gs : convert ***********************");
    return conObj;
};





