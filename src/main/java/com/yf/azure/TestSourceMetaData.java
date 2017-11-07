package com.yf.azure;



import com.hof.pool.DBType;
import com.hof.pool.JDBCMetaData;
import com.yf.utils.Timezone;
import java.util.ArrayList;


public class TestSourceMetaData
  extends JDBCMetaData
{
  ArrayList<String> sortedZones = Timezone.getZone();
  
  public TestSourceMetaData()
  {
    this.sourceName = "Azure";
    this.sourceCode = "TEST_DATA_SOURCE";
    this.driverName = "com.yf.azure.TestDataSource";
    this.sourceType = DBType.THIRD_PARTY;
  }
  
  public void initialiseParameters()
  {
    super.initialiseParameters();
    
    Parameter pq = new Parameter("URL", "Generate Authentication Code", "Click here to generate Authcode", 0, 15, null, true);
    
    pq.addOption("BUTTONTEXT", "Authorize Azure");
    pq.addOption("BUTTONURL", "https://login.microsoftonline.com/common/OAuth2/Authorize?client_id=b3144fe1-6338-43a0-8918-c05291f51170&response_mode=query&response_type=code&redirect_uri=https://tpconnect.yellowfin.bi/getToken.jsp&resource=https://management.core.windows.net/&domain_hint=consumers&prompt=consent&state=123456");
    
    addParameter(pq);
    addParameter(new Parameter("CODE", "Enter Authentication Code", "Enter the Authentication Code recieved from Azure", 2, 17, null, true));
    
    addParameter(pq);
    
/*    Parameter zones = new Parameter("SELECTOR", "Timezone Selector", "Select the timezone", 2, 6, null, true);
    for (String zone : this.sortedZones) {
      zones.addOption(zone);
    }
    addParameter(zones);*/
   
/*    Parameter offer = new Parameter("SELECTOR5", "Subscription Offer Selector", "Select the subscription", 2, 6, null, true);
    offer.addOption("0003P");
    offer.addOption("0041P");
    offer.addOption("0042P");
    offer.addOption("0043P");
    offer.addOption("0044P");
    offer.addOption("0059P");
    offer.addOption("0042P");
    offer.addOption("0060P");
    offer.addOption("0062P");
    offer.addOption("0063P");
    offer.addOption("0064P");
    offer.addOption("0029P");
    offer.addOption("0022P");
    offer.addOption("0023P");
    offer.addOption("0148P");
    offer.addOption("0025P");
    offer.addOption("0036P");
    offer.addOption("0120P-0130P");
    offer.addOption("0144P");
    offer.addOption("0149P");
    offer.addOption("0145P");
    offer.addOption("DE-0145P");
    offer.addOption("0044P");
    offer.addOption("DE-0003P");
    offer.addOption("DE-0041P");
    offer.addOption("DE-0042P");
    offer.addOption("DE-0043P");
    
    addParameter(offer);
    
    
    Parameter currency = new Parameter("SELECTOR1", "Currency Selector", "Select the currency", 2, 6, null, true);
    currency.addOption("USD");
    currency.addOption("AUD");
    currency.addOption("INR");
    
    addParameter(currency);
    
    Parameter Locale = new Parameter("SELECTOR2", "Locale Selector", "Select the locale", 2, 6, null, true);
    Locale.addOption("en-US");
    Locale.addOption("en-AU");
    
    addParameter(Locale);
    
    Parameter Region = new Parameter("SELECTOR3", "Region Selector", "Select the region", 2, 6, null, true);
    Region.addOption("UK");
    Region.addOption("AU");
    Region.addOption("US");
    
    addParameter(Region);*/
    
    Parameter months = new Parameter("SELECTOR4", "Select No of Billing Cycles Required", "Select the months", 2, 6, null, true);
     for(int j=1;j<=3;j++){
    	 months.addOption(""+j+"");
     } 
    addParameter(months);
   
  }
  
  public byte[] getDatasourceIcon()
  {
    String str = "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";
    return str.getBytes();
  }
  
  public String getDatasourceShortDescription()
  {
    return "Azure Connector";
  }
  
  public String getDatasourceLongDescription()
  {
    return "Microsoft Azure is a cloud computing service created by Microsoft for building, testing, deploying, and managing applications.";
  }
}
