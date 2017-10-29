package parimi.com.lintegration.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parimi.com.androidlinkedinintegration.R;
import parimi.com.androidlinkedinintegration.R2;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkedinPostFragment extends Fragment {


    @BindView(R2.id.linkedin_post_text)
    TextView linkedinPostText;

    public LinkedinPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_linkedin_post, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R2.id.linkedin_post_button)
    void postLinkedin() {
        String url = "https://api.linkedin.com/v1/people/~/shares";

        String payload = "{" +
                "\"comment\":\"Check out developer.linkedin.com! " +
                "http://linkd.in/1FC2PyG\"," +
                "\"visibility\":{" +
                "    \"code\":\"anyone\"}" +
                "}";

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