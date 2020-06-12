/**
 * @author Alexander Barrios
 * @version Alpha
 * -------------
 * Extensión creada para la aplicación CinelarTV 
 */

package uy.abstudios.cinelartv.mediaclient.CastExtension;

import android.os.AsyncTask;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.util.YailList;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import java.util.List;
import java.awt.event.*;
import android.view.Window;
import android.support.v7.app.AppCompatActivity;
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

@DesignerComponent(
	version = 1,
	category = ComponentCategory.EXTENSION,
	description = "Chromecast Extension for CinelarTV App",
	nonVisible = true,
	iconName = "https://cdn.iconscout.com/icon/free/png-256/cast-226401.png",
	helpUrl = "https://developers.cinelartv.tk/opensource/cast.html"
)
@UsesLibraries(libraries = "api-v2-0.11.2-SNAPSHOT.jar, jackson-core-2.9.10.jar, jmdns-3.5.5.jar, slf4j-api-1.7.30.jar, jackson-annotations-2.9.10.jar ,jackson-databind-2.9.10.4.jar, protobuf-java-2.6.0.jar, android-support-v4.jar, support-compat-28.0.0.jar")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class CastExtension extends AndroidNonvisibleComponent implements Component {
	private static String APP_ID = "";
	private YailList extras;
	private List<ChromeCast> castsList;
	private List<ChromeCast> nativeList;
	private ChromeCast chromecast;
	private Context context;
	private JmDNS mDNS;
	private boolean hasListener;

	public CastExtension(ComponentContainer container) {
		super(container.$form());
		context = container.$context();
		castsList = new ArrayList<>();  
	}

	/**
	 * --------------
	 *   PROPERTIES
	 * --------------
	 */
	@DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING, defaultValue = "")
	@SimpleProperty
	public void AppID(String applicationID) {
		if(applicationID.equals("DEFAULT_MEDIA_RECEIVER_APPLICATION_ID")) {
				APP_ID = "CC1AD845";
		} else {
				APP_ID = applicationID;
		}
	}

	/**
	 * -------------
	 *   FUNCTIONS
	 * -------------
	 */
	@SimpleFunction(description = "Set Volume")
	public void SetVolume(int volume) throws IOException {
		chromecast.setVolume(volume);
	}

	@SimpleFunction(description = "")
	public void Pause() throws IOException {
		chromecast.pause();
	}

	@SimpleFunction(description = "")
	public void Play() throws IOException {
		chromecast.play();
	}
	
	@SimpleFunction(description = "")
	public void Seek(int seek) throws IOException {
		chromecast.seek(seek);
	}
	
	@SimpleFunction(description = "")
	public void SetMuted(boolean mute) throws IOException {
		chromecast.setMuted(mute);
	}

	@SimpleFunction(description = "Start devices scan")
	public void startDiscovery() {
		new StartListening().execute();
		appendListener();
	}

	@SimpleFunction(description = "Stop devices scan")
	public void stopDiscovery() {
		try {
			ChromeCasts.stopDiscovery();
			ChromeCasts.unregisterListener(listener);
			hasListener = false;
		} catch (Exception e) {
			OnError("StoppingDiscoveringDevices", e.toString());
			e.printStackTrace();
		}
	}

	@SimpleFunction(description = "Restart devices scan")
	public void RestartDiscovery() {
		try {
			castsList.clear();
			stopDiscovery();
			startDiscovery();
		} catch (Exception e) {
			OnError("RestartingDiscoveringDevices", e.toString());
			e.printStackTrace();
		}
	}
	
	@SimpleFunction(description="Returns device status")
	public String DeviceStatus() {
		String status;
		try {
			status = chromecast.getStatus().toString();
		} catch (IOException e) {
			status = e.getMessage() + " , " + e.toString();
			OnError("ObtainingDeviceStatus", status);
			e.printStackTrace();
		}

		return status;
	}

	@SimpleFunction(description = "Show list of devices detected")
	public List<ChromeCast> DevicesList() {
		return castsList;
	}

	@SimpleFunction(description = "Launch Application in Connected Device") 
	public void LaunchApp() {
		String status;
		try {
			chromecast.launchApp(APP_ID);
		} catch (IOException e) {
			status = e.getMessage() + " , " + e.toString();
			e.printStackTrace();
		}
	}
	
	@SimpleFunction
	public int DiscoveredDevicesCount() {
		return castsList.size();
	}
	
	@SimpleFunction(description = "")
	public void ConnectToDevice(int index) throws IOException, GeneralSecurityException {
		chromecast = castsList.get(index);
		chromecast.connect();
	}
	
	@SimpleFunction(description = "")
	public void ConnectToDeviceWithIP(String ipAddress) {
		chromecast = new ChromeCast(ipAddress);
		try {
			chromecast.connect();
		} catch(Exception e ){
			e.printStackTrace();
		}
	}
	
	@SimpleFunction
	public void SetMedia(String title, String thumbnail, String video) throws IOException {
		chromecast.load(title, thumbnail, video, null);
	}
	
	@SimpleEvent
	public void OnError(String process, String message) {
		ChromeCasts.unregisterListener(listener);
		hasListener = false;
		EventDispatcher.dispatchEvent(this, "OnError", process, message);
	}

	@SimpleEvent(description = "")
	public void OnChromeCastDiscovered(String deviceName,String deviceAddress, int devicePort) {
		EventDispatcher.dispatchEvent(this, "OnChromeCastDiscovered", deviceName, deviceAddress, devicePort);
	}

	@SimpleEvent(description = "")
	public void OnNativeCastDiscovered(String deviceName,String deviceAddress, int devicePort) {
		EventDispatcher.dispatchEvent(this, "OnNativeCastDiscovered", deviceName, deviceAddress, devicePort);
	}
	
	@SimpleEvent(description="Cast removed from ")
	public void OnChromeCastRemoved(String deviceName,String deviceAddress, int devicePort) {
		EventDispatcher.dispatchEvent(this, "OnChromeCastRemoved", deviceName, deviceAddress, devicePort);
	}
	
	public void appendListener() {
		if (!hasListener) {
			nativeList = ChromeCasts.get();
			ChromeCasts.registerListener(listener);
			hasListener = true;
		}
	}

	private ChromeCastsListener listener = new ChromeCastsListener() {
		@Override public void newChromeCastDiscovered(ChromeCast chromeCast) {
			castsList.add(chromeCast);
			OnChromeCastDiscovered(chromeCast.getName(), chromeCast.getAddress(), chromeCast.getPort());
		}
		
		@Override public void chromeCastRemoved(ChromeCast chromeCast) {
			if (castsList.contains(chromeCast)) {
					castsList.remove(chromeCast);
			}
			
			OnChromeCastRemoved(chromeCast.getName(), chromeCast.getAddress(), chromeCast.getPort());
		}
	};    

	class StartListening extends AsyncTask <Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... args) {
			try {
					ChromeCasts.startDiscovery(); 
			} catch (Exception e) {
					OnError("StartingDiscoveryTask", e.toString());
					e.printStackTrace();
			}

			return null;
		}
	}   
}
