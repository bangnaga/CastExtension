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


@UsesLibraries(libraries = "Chromecast.jar")


@DesignerComponent(version = 1,
 category = ComponentCategory.EXTENSION,
 description = "Chromecast Extension for CinelarTV App",
 nonVisible = true,
 iconName = "https://cdn.worldvectorlogo.com/logos/cast-icon-chromecast.svg",
 helpUrl = "https://developers.cinelartv.tk/opensource/cast.html")




//@UsesLibraries(libraries = "kawa-1.11-modified.jar")
@SimpleObject(external = true)
public final class CastExtension extends AndroidNonvisibleComponent implements Component {


 private ComponentContainer container;
 private Context context;
 private final Activity activity;
 private final Handler handler;
 final ArrayList seletedItems = null;








 public CastExtension(ComponentContainer container) {
  super(container.$form());
  //NMD UPDATE >>>
  this.container = container;
  context = (Context) container.$context();
  //NMD UPDATE <<<<
  activity = container.$context();
  handler = new Handler();

 }

 @SimpleFunction(description = "Return a list of Chromecast Devices")
    public String DeviceList() {
        return "Hola, Que, Tal";
    }

    @SimpleFunction(description = "Return a list of Chromecast Devices")
    public String DeviceList2() {
        return "Soy, Una, Taza";
    }

     @SimpleFunction(description = "Set Volume (0-100)")
    public void SetVolume(String deviceID, Integer volume) {
  
       
        
          //MyCodeHere
        }                         
        

      }
