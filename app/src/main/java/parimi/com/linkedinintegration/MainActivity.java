package parimi.com.linkedinintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import parimi.com.lintegration.activity.LinkedinIntegrationActivity;

import static parimi.com.lintegration.constant.Constant.*;

/**
 * This is an example activity to demo the use of androidlinkedinintegration library
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, LinkedinIntegrationActivity.class);

        intent.putExtra(SHOW_KEYHASH, true);
        intent.putExtra(POST_LINKEDIN, true);
        intent.putExtra(SIGN_IN_LINKEDIN, true);
        intent.putExtra(POST_LINKEDIN_TEXT, "Share");
        intent.putExtra(POST_LINKEDIN_COMMENT, "I received a new rating from one of my mentees on UMentor App");
        intent.putExtra(POST_LINKEDIN_LINK, "https://play.google.com/store/apps/details?id=parimi.com.umentor&ah=NbcuyPJk-gNdxj-c8CVL4NH7YLk");
        intent.putExtra(SIGN_IN_LINKEDIN, true);
        startActivity(intent);
    }
}

