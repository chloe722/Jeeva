package thhsu.chloe.jeeva.AboutPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thhsu.chloe.jeeva.R;

/**
 * Created by Chloe on 5/15/2018.
 */

public class AboutPrivacyPolicyFragment extends Fragment{

    public static AboutPrivacyPolicyFragment newInstance () { return new AboutPrivacyPolicyFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aboutpage_privacy_policy, container, false);
    }
}
