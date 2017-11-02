package parimi.com.lintegration.fragment;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.AccessToken;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parimi.com.androidlinkedinintegration.R;
import parimi.com.androidlinkedinintegration.R2;
import parimi.com.lintegration.activity.LinkedinIntegrationActivity;

import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN;
import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN_COMMENT;
import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN_LINK;
import static parimi.com.lintegration.constant.Constant.POST_LINKEDIN_TEXT;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkedinPostFragment extends Fragment {


    @BindView(R2.id.linkedin_post_text)
    TextView linkedinPostText;

    @BindView(R2.id.linkedin_post_button)
    Button linkedinPostButton;

    public LinkedinPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_linkedin_post, container, false);
        ButterKnife.bind(this, view);
        String shareButtonText = getArguments().get(POST_LINKEDIN_TEXT).toString();
        linkedinPostButton.setText(shareButtonText);
        return view;
    }


    @OnClick(R2.id.linkedin_post_button)
    void postLinkedin() {
        AccessToken accessToken = LISessionManager.getInstance(getActivity()).getSession().getAccessToken();
        if(accessToken == null || accessToken.isExpired()) {
            ((LinkedinIntegrationActivity) getActivity()).signIn(POST_LINKEDIN, getActivity());
        }
        String url = "https://api.linkedin.com/v1/people/~/shares";

        String linkedinComment = getArguments().get(POST_LINKEDIN_COMMENT).toString();
        String linkedinCommentLink = getArguments().get(POST_LINKEDIN_LINK).toString();

        String payload = "{" +
                "\"comment\":\"" + linkedinComment.toString() + " " +
                linkedinCommentLink.toString() + "\"," +
                "\"visibility\":{" +
                "    \"code\":\"anyone\"}" +
                "}";

        try {
            PackageInfo info =
                    getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),
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

        APIHelper apiHelper = APIHelper.getInstance(getActivity().getApplicationContext());
        apiHelper.postRequest(getActivity(), url, payload, new ApiListener() {
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
