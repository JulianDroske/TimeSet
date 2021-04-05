package jurt.systime;

import android.app.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import android.util.*;
import java.io.*;

public class MainActivity extends Activity{
	
	public MainActivity This = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		
		This = this;
		
		final TextView tv = new TextView(this);
		setContentView(tv);

		
		/* SntpClient client = new SntpClient();
		if (client.requestTime("time.foo.com")) {
			long now = client.getNtpTime() + SystemClock.elapsedRealtime() - 
				client.getNtpTimeReference();
			Date current = new Date(now);
			Log.i("NTP tag", current.toString());
		}*/
		
		// Toast.makeText(This, "Init...", Toast.LENGTH_SHORT).show();
		
		// Calendar.getInstance().;
		
		final Handler h = new Handler();
		
		SNTPClient.getDate(TimeZone.getTimeZone("Atlantic/Cape Verde"), new SNTPClient.Listener() {
				@Override
				public void onTimeReceived(String rawDate, final long time) {
					// rawDate -> 2019-11-05T17:51:01+0530
					// Log.e(SNTPClient.TAG, rawDate);
					
					String form = rawDate.substring(0,rawDate.indexOf("+"));
					form = form.replace("-",".").replace("T","-")/*.replace(":","")*/;
					final String v = form;
					
					h.post(new Runnable(){

							@Override
							public void run(){
								/* Calendar.getInstance().setTimeInMillis(time); */
								
								// Toast.makeText(This, v, Toast.LENGTH_LONG).show();
								
								String o="";
								try{
									ProcessBuilder psb = new ProcessBuilder(new String[]{"su","-c","busybox date -u -s "+v});
									psb.redirectErrorStream(true);
									java.lang.Process ps = psb.start();
									// ps.waitFor();
									/* OutputStreamWriter osw = new OutputStreamWriter(ps.getOutputStream());
									osw.write(o);
									osw.close();*/
									
									InputStream is = ps.getInputStream();
									byte[] bs = new byte[1024];
									while(is.read(bs) != -1){
										o += new String(bs);
									}
									is.close();
									
									
								}catch(Exception e){}

								Toast.makeText(This, "Time set.\n", Toast.LENGTH_LONG).show();
								tv.setText("Program output:\n" + o);
								// This.finish();
							}
							
						
					});
					
					
				}

				@Override
				public void onError(final Exception ex) {
					// Log.e(SNTPClient.TAG, ex.getMessage());
					h.post(new Runnable(){
						@Override
						public void run(){
							Toast.makeText(This, Log.getStackTraceString(ex), Toast.LENGTH_LONG).show();
						}
					});
				}
			});
    }
}
