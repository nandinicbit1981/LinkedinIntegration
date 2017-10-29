package parimi.com.lintegration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parimi.com.androidlinkedinintegration.R;
import parimi.com.androidlinkedinintegration.R2;
import parimi.com.lintegration.fragment.LinkedinPostFragment;

import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN;

public class LinkedinIntegrationActivity extends AppCompatActivity {


    @BindView(R2.id.linkedin_signin)
    Button linkedinSignin;

    @BindView(R2.id.linkedin_signin_text)
    TextView linkedinSigninText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedin_integration);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle extras = getIntent().getExtras();
//        if(extras.get(SHOW_KEYHASH).equals(true)) {
//            fragmentManager.beginTransaction()
//                    .add(R.id.fl_linkedin_hash, new LinkedInHashFragment())
//                    .commit();
//        }

        if(extras.get(POST_LINKEDIN).equals(true)) {
            fragmentManager.beginTransaction()
                    .add(R.id.fl_linkedin_post, new LinkedinPostFragment())
                    .commit();
        }
    }

    @OnClick(R2.id.linkedin_signin)
    public void linkedinSignin() {

        LISessionManager.getInstance(this).init(this, buildScope(), new AuthListener() {
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

    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }
}
