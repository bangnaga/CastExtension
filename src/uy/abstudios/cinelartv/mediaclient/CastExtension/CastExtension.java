/**
Authors: Alexander Barrios, Krish Jha
    
Version alpha

Extensión creada para la aplicación CinelarTV
**/

package uy.abstudios.cinelartv.mediaclient.CastExtension;



import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.util.YailList;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import java.util.List;
import java.awt.event.*;
import android.view.Window;
import java.util.ArrayList;
import java.util.List;
import su.litvak.chromecast.api.v2.*;
import su.litvak.chromecast.api.v2.ChromeCast.*;
import org.slf4j.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.security.GeneralSecurityException;






@DesignerComponent(version = 1,
 category = ComponentCategory.EXTENSION,
 description = "Chromecast Extension for CinelarTV App",
 nonVisible = true,
 iconName = "https://cdn.iconscout.com/icon/free/png-256/cast-226401.png",
 helpUrl = "https://developers.cinelartv.tk/opensource/cast.html")
@UsesLibraries(libraries = "api-v2-0.11.2-SNAPSHOT.jar,jackson-core-2.9.10.jar,jmdns-3.5.5.jar,slf4j-api-1.7.30.jar,jackson-annotations-2.9.10.jar,jackson-databind-2.9.10.4.jar,protobuf-java-2.6.0.jar")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.INTERNET")

public final class CastExtension extends AndroidNonvisibleComponent implements Component {


private static String APP_ID = "";
private YailList extras;
private ChromeCast chromecast;
private Context context;
private JmDNS mDNS;







 public CastExtension(ComponentContainer container) {
  super(container.$form());
  context = (Context) container.$context();
 }


 // ------------------------
    //        PROPERTIES
    // ------------------------



@SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String AppID() {
      return APP_ID;
    }

 @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING, defaultValue = "")
    @SimpleProperty
    public void AppID(String applicationID) {
      APP_ID = applicationID;
    }




// ------------------------
    //        Functions
    // ------------------------



     @SimpleFunction(description = "Set Volume")
    public void SetVolume(float volume) throws IOException {
         chromecast.setVolume(volume);
        }

     @SimpleFunction(description="")
     public void Pause() throws IOException {
     chromecast.pause();
     }

     @SimpleFunction(description="")
     public void Play() throws IOException {
     chromecast.play();
     }
     
     @SimpleFunction(description="")
     public void Seek(int seek) throws IOException {
     chromecast.seek(seek);
     }
     
     @SimpleFunction(description="")
     public void SetMuted(boolean mute) throws IOException {
     chromecast.setMuted(mute);
     }

    @SimpleFunction(description = "Start devices scan")
    public void startDiscovery() {
    try {
    ChromeCasts.startDiscovery();
    } catch (Exception e) {
    e.printStackTrace();
    }
    }

    @SimpleFunction(description = "Stop devices scan")
    public void stopDiscovery() {
    try {
    ChromeCasts.stopDiscovery();
    } catch (Exception e) {
    e.printStackTrace();
    }
    }


    @SimpleFunction(description = "Show list of devices detected")
    public List<String> DevicesList() {
    List<String> listchromecasts = new ArrayList<String>();

    for (ChromeCast device : ChromeCasts.get()){
            String deviceName = device.getName();
            listchromecasts.add(deviceName);
          }
        return listchromecasts;
    }
    
    @SimpleFunction(description="")
    public void ConnectToDevice(int index) throws IOException, GeneralSecurityException {
    chromecast = ChromeCasts.get().get(index);
    chromecast.connect();
    }
    
    @SimpleFunction
    public void SetMedia(String title, String thumbnail, String video) throws IOException {
    chromecast.load(title, thumbnail, video, null);
    }
}
  

  