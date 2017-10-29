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
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import parimi.com.androidlinkedinintegration.R;
import parimi.com.androidlinkedinintegration.R2;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkedInHashFragment extends Fragment {

    @BindView(R2.id.hash_key_text)
    TextView hashKeyText;

    public LinkedInHashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linked_in_hash, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R2.id.hash_key_button)
    public void getHashKey() {
        try {
            PackageInfo info =
                    getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                hashKeyText.setText(keyhash);
                Log.e("KeyHash:", keyhash);
            }
        } catch(PackageManager.NameNotFoundException| NoSuchAlgorithmException e) {
            Log.e("something", e.getMessage());
        }
    }


}
