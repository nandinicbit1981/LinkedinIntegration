package parimi.com.lintegration.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parimi.com.lintegration.DroidLintegrationCallback;
import parimi.com.androidlinkedinintegration.R;
import parimi.com.androidlinkedinintegration.R2;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkedinSigninFragment extends Fragment implements DroidLintegrationCallback {

    @BindView(R2.id.linkedin_signin)
    Button linkedinSignin;

    @BindView(R2.id.linkedin_signin_text)
    TextView linkedinSigninText;

    Context context;

    public LinkedinSigninFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linkedin_signin, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    @OnClick(R2.id.linkedin_signin)
    public void linkedinSignin() {

        LISessionManager.getInstance(getActivity()).init(getActivity(), buildScope(), new AuthListener() {
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




    @Override
    public void linkedInSignin() {

    }

    @Override
    public void linkedInKeyHash() {

    }

    @Override
    public void linkedinSharePost() {

    }
}
