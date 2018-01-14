package wallpaperscube.pakpublishers.com.wallpapers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebConnection extends Thread {

    String SOAP_Action = "";
    String Methoud_Name = "";
    String NAMESPACE = "";

    public static boolean isWCFService=false;
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String URL="http://192.168.1.40/pp/services/wallpaperservice.svc";

    public static  int ErrorWhat = -1;
    public  static   int SuccessWhat = 1;

    Handler ReferenceHandler = null;
    SoapObject request = null;



    public WebConnection(Handler h) {
        ReferenceHandler = h;

    }

    public void addProperty(String PropertyName, Object PropertyValue) {
        request.addProperty(PropertyName, PropertyValue);
    }

    public void setHeaderDetails(String SoapAction, String MethoudName, String NameSpace) {
        SOAP_Action = SoapAction;
        Methoud_Name = MethoudName;
        NAMESPACE = NameSpace;
        request = new SoapObject(NAMESPACE, Methoud_Name);
    }

    public void setWhats(int Error, int Success) {
        ErrorWhat = Error;
        SuccessWhat = Success;
    }

    public void Start() {
        start();
    }


    @Override
    public void run() {
        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelop.dotNet = true;
        envelop.setOutputSoapObject(request);
        HttpTransportSE transprt = new HttpTransportSE(URL, 10000);
        if(!isWCFService) {
            transprt.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            transprt.debug = true;
        }
        try {

            Log.e("SOAP Action",SOAP_Action);
            Log.e("NameSpace",NAMESPACE);
            Log.e("Methoud Name",Methoud_Name);
            Log.e("URL",URL);


            transprt.call(SOAP_Action, envelop);
            Object s = envelop.getResponse();

            SoapObject result = (SoapObject) envelop.bodyIn;
            String data = result.toString();

            Message m = new Message();
            m.obj = (Object) result;
            m.what = SuccessWhat;
            Bundle b = new Bundle();
            b.putString("message", data);
            m.setData(b);
            ReferenceHandler.sendMessage(m);
        } catch (ClassCastException ej) {
            Message m = new Message();
            m.what = ErrorWhat;
            Bundle b = new Bundle();
            b.putString("message", ej.getMessage());
            m.setData(b);
            ReferenceHandler.sendMessage(m);
        } catch (Exception k) {
            Log.e("WebConnection",""+k);
            Message m = new Message();
            m.what = ErrorWhat;
            Bundle b = new Bundle();
            b.putString("message", k.getMessage());
            m.setData(b);
            ReferenceHandler.sendMessage(m);
        }


    }
}
