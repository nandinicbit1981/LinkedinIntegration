package parimi.com.lintegration.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.AccessToken;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import parimi.com.androidlinkedinintegration.R;

import static android.content.ContentValues.TAG;
import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN_COMMENT;
import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN_LINK;

public class PostLinkedinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_linkedin);

        AccessToken accessToken = LISessionManager.getInstance(this).getSession().getAccessToken();
        if(accessToken == null || accessToken.isExpired()) {
            signIn();
        } else {
            postLinkedin();
        }

    }

    public void postLinkedin() {
        String url = "https://api.linkedin.com/v1/people/~/shares";
        final Bundle extras = getIntent().getExtras();
        String linkedinComment =  extras.get(POST_LINKEDIN_COMMENT).toString();
        String linkedinCommentLink = extras.get(POST_LINKEDIN_LINK).toString();

        String payload = "{" +
                "\"comment\":\"" + linkedinComment.toString() + " " +
                linkedinCommentLink.toString() + "\"," +
                "\"visibility\":{" +
                "    \"code\":\"anyone\"}" +
                "}";

        try {
            PackageInfo info =
                    this.getPackageManager().getPackageInfo(this.getPackageName(),
                            PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("KeyHash:", keyhash);
            }
        } catch(PackageManager.NameNotFoundException| NoSuchAlgorithmException e) {
            Log.e("something", e.getMessage());
        }

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.postRequest(getBaseContext(), url, payload, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                System.out.println("Successfully posted to Linkedin!");
                //linkedinPostText.setText("Successfully posted to Linkedin!");
                finish();

            }

            @Override
            public void onApiError(LIApiError liApiError) {
                System.out.println(liApiError.getLocalizedMessage());
                System.out.println(liApiError.getApiErrorResponse().getMessage());
                // Error making POST request!
                finish();
                //linkedinPostText.setText("Request was unSuccessful!");
            }
        });
    }
    public void signIn() {

        LISessionManager.getInstance(this).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.
            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                Log.e(TAG, error.toString());
            }


        }, true);
    }


    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        postLinkedin();
    }

}
