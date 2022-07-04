/**#####################################
  * Sets up server-side HTML environment     steve terry
  * Routes requests for pages to appropiate
  * html page.
  */
function doGet(e) {
   Logger.log("Entered web app");
  if(e.parameter != null || e.parameter != undefined ){
     Logger.log("parameters supplied "+e.parameter);
  }
  var params = JSON.stringify(e);
  Logger.log("Parameters : \n"+params);

  // Route the request to a function call for each MG-calc
  // There must be a pageName parameter or we Error out.

  if (e.parameter.pageName == "mDisk") {
    Logger.log("    calling mDisk")
    return HtmlService.createHtmlOutputFromFile('mDisk').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  }  
  else if (e.parameter.pageName == "mSlab") {
        Logger.log("    calling mSlab")
   return HtmlService.createHtmlOutputFromFile('mSlab').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  } 
  else if (e.parameter.pageName == "mCustom") {
        Logger.log("    calling mCustom")
   return HtmlService.createHtmlOutputFromFile('mCustom').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  } 
  else if (e.parameter.pageName == "iDisk") {
        Logger.log("    calling iDisk")
   return HtmlService.createHtmlOutputFromFile('iDisk').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  } 
  else if (e.parameter.pageName == "iSlab") {
        Logger.log("    calling iSlab")
   return HtmlService.createHtmlOutputFromFile('iSlab').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  } 
  else if (e.parameter.pageName == "iCustom") {
        Logger.log("    calling iCustom")
   return HtmlService.createHtmlOutputFromFile('iCustom').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  } 
  else if (e.parameter.pageName == "tSlab") {
        Logger.log("    calling tSlab")
         var template = HtmlService.createTemplateFromFile('tSlab');
           return template.evaluate()
              .setTitle('Slab Calculate')
              .setSandboxMode(HtmlService.SandboxMode.IFRAME);
   // return HtmlService.createHtmlOutputFromFile('tSlab').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  } 
  else {
    // No page defined so throw and error page
      Logger.log("    No page defined - Big old ERROR")
    return HtmlService.createHtmlOutput("No page defined - Big old ERROR"); 
  }
  // return HtmlService.createHtmlOutputFromFile('Cuboid').setSandboxMode(HtmlService.SandboxMode.IFRAME);
  //.setXFrameOptionsMode(HtmlService.XFrameOptionsMode.ALLOWALL);
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
  console.log("in Code.gs : createBaseVolume ***********************");
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

  console.log("out Code.gs : createBaseVolume ***********************");

  return volumeObject;

};

