package eduapp.examnation.com.examnation;

import android.app.ProgressDialog;
import android.content.Context;

public class PrograssBarBuilder {

    public static ProgressDialog getInstance(Context context){
        ProgressDialog progress;
        progress=new ProgressDialog(context);
        progress.setTitle("Processing...");
        progress.setMessage("Please wait.");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        return progress;
    }
}
