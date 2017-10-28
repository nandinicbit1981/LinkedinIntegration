package parimi.com.linkedinintegration;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static parimi.com.linkedinintegration.R.id.linkedin_signin_text;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hash_key_button)
    Button hashKeyButton;

    @BindView(R.id.hash_key_text)
    TextView hashKeyText;

    @BindView(R.id.linkedin_signin)
    Button linkedinSignin;

    @BindView(linkedin_signin_text)
    TextView linkedinSigninText;


    @BindView(R.id.linkedin_post_text)
    TextView linkedinPostText;



    public static String PACKAGE_NAME = "parimi.com.linkedinintegration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    @OnClick(R.id.linkedin_signin)
    public void linkedinSignin() {

        LISessionManager.getInstance(getApplicationContext()).init(MainActivity.this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.
                System.out.println("successful!");
                linkedinSigninText.setText("Signed in Successfully!");

            }

            @Override
            public void onAuthError(LIAuthError error) {
                // Handle authentication errors
                System.out.println("Error!");
            }


        }, true);

    }


    @OnClick(R.id.hash_key_button)
    public void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(PACKAGE_NAME,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                hashKeyText.setText(keyhash);
                Log.e("KeyHash:", keyhash);
            }
        } catch(PackageManager.NameNotFoundException| NoSuchAlgorithmException e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }


    @OnClick(R.id.linkedin_post_button)
    void postLinkedin() {
        String url = "https://api.linkedin.com/v1/people/~/shares";

        String payload = "{" +
                "\"comment\":\"Check out developer.linkedin.com! " +
                "http://linkd.in/1FC2PyG\"," +
                "\"visibility\":{" +
                "    \"code\":\"anyone\"}" +
                "}";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.postRequest(this, url, payload, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                linkedinPostText.setText("Successfully posted to Linkedin!");
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making POST request!

                linkedinPostText.setText("Request was unSuccessful!");
            }
        });
    }
}
