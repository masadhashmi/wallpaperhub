package wallpaperscube.pakpublishers.com.wallpapers.business_objects;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

public class XmlParser {

    SoapObject MyObject=null;
    public void setSoapObject(SoapObject ob)
    {
        MyObject=ob;
    }
    public SoapObject getDataSet()
    {
        SoapObject Datatable=(SoapObject)MyObject.getProperty(0);
        Datatable=(SoapObject)Datatable.getProperty(1);
        return Datatable;

    }
    public Boolean isContainAnyData(SoapObject obj,int Length)
    {
        Boolean isContainData=false;
        int lenght=obj.toString().length();
        if(obj.toString().length()>Length)isContainData=true;
        return isContainData;
    }


}
