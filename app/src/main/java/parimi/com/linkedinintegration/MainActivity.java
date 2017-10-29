package parimi.com.linkedinintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import parimi.com.lintegration.activity.LinkedinIntegrationActivity;

import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN;
import static parimi.com.lintegration.constant.Constant.SHOW_KEYHASH;
import static parimi.com.lintegration.constant.Constant.SIGN_IN_LINKEDIN;

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
        startActivity(intent);
    }
}

