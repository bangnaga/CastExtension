/**
Authors: Alexander Barrios
    
Version alpha
**/

package uy.abstudios.cinelartv.mediaclient;



import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.R.drawable;

import java.awt.*;
import java.awt.event.*;
import android.view.Window;
import java.util.ArrayList;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.ElementsUtil;
import com.google.appinventor.components.runtime.util.YailList;
import su.litvak.chromecast.api.v2.*;
import org.slf4j.*;
import com.fasterxml.jackson.databind.*;








@DesignerComponent(version = 1,
 category = ComponentCategory.EXTENSION,
 description = "Chromecast Extension for CinelarTV App",
 nonVisible = true,
 iconName = "https://cdn.worldvectorlogo.com/logos/cast-icon-chromecast.svg",
 helpUrl = "https://developers.cinelartv.tk/opensource/cast.html")



@UsesLibraries(libraries = "api-v2-0.11.4-SNAPSHOT.jar,jackson-annotations-2.10.3.jar,jackson-core-2.10.3.jar,jackson-databind-2.10.3.jar,protobuf-java-2.6.0.jar,jmdns-3.5.5.jar,slf4j-api-1.7.30.jar")
@SimpleObject(external = true)
public final class CastExtension extends AndroidNonvisibleComponent implements com.google.appinventor.components.runtime.Component {


private static String APP_ID = "";









 public CastExtension(ComponentContainer container) {
  super(container.$form());
 }

@SimpleProperty(category = PropertyCategory.BEHAVIOR,
      description = "Return configured APP_ID in Designer")
    public String AppID() {
      return APP_ID;
    }

 @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING, defaultValue = "")
    @SimpleProperty
    public void AppID(String applicationID) {
      APP_ID = applicationID;
    }






    @SimpleFunction(description = "Return a list of Chromecast Devices")
    public YailList getDeviceList() {


        return makeList(java.util.List ChromeCasts.get())
    
    }


     @SimpleFunction(description = "Set Volume (0-100)")
    public void SetVolume(String deviceID, int volume) {
  
       
        
          //MyCodeHere
        }                    


        


      
      

@SimpleFunction(description = "Get all Chromecast devices")
    public void GetDevices() {
      try {
  ChromeCasts.startDiscovery();
}
catch (Exception e) {
  e.printStackTrace();
}

    }

  }