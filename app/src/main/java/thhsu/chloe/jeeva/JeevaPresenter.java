package thhsu.chloe.jeeva;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

//import thhsu.chloe.jeeva.Filter.FilterFragment;
import java.util.ArrayList;

import thhsu.chloe.jeeva.Filter.FilterPresenter;
import thhsu.chloe.jeeva.Home.HomeFragment;
import thhsu.chloe.jeeva.Home.HomePresenter;
import thhsu.chloe.jeeva.JobDetails.JobDetailsFragment;
import thhsu.chloe.jeeva.JobDetails.JobDetailsPresenter;
import thhsu.chloe.jeeva.Profile.ProfileFragment;
//import thhsu.chloe.jeeva.Profile.ProfileFragmentTesting;
import thhsu.chloe.jeeva.Profile.ProfilePresenter;
import thhsu.chloe.jeeva.SavedJobs.SavedJobsFragment;
import thhsu.chloe.jeeva.SavedJobs.SavedJobsPresenter;
import thhsu.chloe.jeeva.SignInTab.SignInTabFragment;
import thhsu.chloe.jeeva.SignInTab.SignInTabPresenter;
import thhsu.chloe.jeeva.Utils.Constants;
import thhsu.chloe.jeeva.activities.JeevaActivity;
import thhsu.chloe.jeeva.api.model.Jobs;

/**
 * Created by Chloe on 4/30/2018.
 */

public class JeevaPresenter implements JeevaContract.Presenter {
    private final JeevaContract.View mJeevaContractView;
    private FragmentManager mFragmentManager;
    private MenuItem menuItem;
    public JeevaActivity mActivity;
    public BottomNavigationView mBottomNavigationView;
    public Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private Fragment mCurrentFragment;

    public static final String HOME = "HOME";
    public static final String SAVEDJOBS = "SAVEDJOBS";
    public static final String PROFILE = "PROFILE";
    public static final String SIGNIN = "SIGNIN";
    public static final String FILTER = "FILTER";
    public static final String JOBDETAILS = "JOBDETAILS";


    private SignInTabFragment mSignInTabFragment;
    private SavedJobsFragment mSavedJobsFragment;
    private HomeFragment mHomeFragment;
    private ProfileFragment mProfileFragment;
//    private FilterFragment mFilterFragment;
    private JobDetailsFragment mJobDetailsFragment;

    private SignInTabPresenter mSignInTabPresenter;
    private SavedJobsPresenter mSavedJobsPresenter;
    private HomePresenter mHomePresenter;
    private ProfilePresenter mProfilePresenter;
    private FilterPresenter mFilterPresenter;
    private JobDetailsPresenter mJobDetailsPresenter;


    public JeevaPresenter(JeevaContract.View jeevaView, FragmentManager fragmentManager, JeevaActivity activity,
                          BottomNavigationView bottomNavigationView, Toolbar toolbar, ProgressBar mProgressBar){
        this.mActivity = activity;
        mJeevaContractView = jeevaView;
        if(jeevaView != null){
            mJeevaContractView.setPresenter(this);
        }else{
            Log.d("Chloe", "jeevaView is empty");
        }
        mFragmentManager = fragmentManager;
        mBottomNavigationView = bottomNavigationView;
        mToolbar = toolbar;
        this.mProgressBar = mProgressBar;

    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.FILTER_REQUEST && resultCode == Constants.RESULT_SUCCESS){
            Bundle bundle = data.getExtras();
            ArrayList<Jobs> jobs = (ArrayList<Jobs>)  bundle.getSerializable("filterResult");  //Convert to Arraylist
            Log.d("Chloe", "filter bundle: " + jobs.size());
            mHomePresenter.updateJobs(jobs);
        }
//        else if(requestCode == Constants.CAPTURE_IMAGE_FRAGMENT_REQUEST){
//            mProfileFragment.onActivityResult(Constants.CAPTURE_IMAGE_FRAGMENT_REQUEST, Activity.RESULT_OK, data);
//        }

    }

//    public void updateJobs(ArrayList<Jobs> jobs){
//        mHomePresenter.updateJobs(jobs);
//    }

    @Override
    public void transToHome() {
        FragmentTransaction transaction
                = mFragmentManager.beginTransaction();

//        if(mFragmentManager.findFragmentByTag(HOME) != null)
//            mFragmentManager.popBackStack();
        if(mHomeFragment == null) mHomeFragment = HomeFragment.newInstance();
        if(mSavedJobsFragment != null) transaction.hide(mSavedJobsFragment);
        if(mProfileFragment != null) transaction.hide(mProfileFragment);
        if(mSignInTabFragment != null) transaction.hide(mSignInTabFragment);
        if (!mHomeFragment.isAdded()){
            transaction.add(R.id.main_container_for_fragment, mHomeFragment, HOME);
        }else{
            transaction.show(mHomeFragment);
        }
        transaction.commit();
        if(mHomePresenter == null){
            mHomePresenter = new HomePresenter(mHomeFragment, mProgressBar);
        }
        mJeevaContractView.showHomeUi();

    }

    @Override
    public void transToSavedJob() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if(mSavedJobsFragment == null) mSavedJobsFragment = SavedJobsFragment.newInstance();
        if(mHomeFragment != null) transaction.hide(mHomeFragment);
        if(mProfileFragment != null) transaction.hide(mProfileFragment);
        if(mSignInTabFragment != null) transaction.hide(mSignInTabFragment);

        if (!mSavedJobsFragment.isAdded()){
            transaction.add(R.id.main_container_for_fragment, mSavedJobsFragment, SAVEDJOBS);
        }else{
            transaction.show(mSavedJobsFragment);
        }
        transaction.commit();

        if(mSavedJobsPresenter == null){
            mSavedJobsPresenter = new SavedJobsPresenter(mSavedJobsFragment);
        }
        mJeevaContractView.showSavedJobUi();
    }

    @Override
    public void transToProfile() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if(mProfileFragment == null) mProfileFragment = ProfileFragment.newInstance();
        if(mHomeFragment != null) transaction.hide(mHomeFragment);
        if(mSavedJobsFragment != null) transaction.hide(mSavedJobsFragment);
        if(mSignInTabFragment != null) transaction.hide(mSignInTabFragment);
        if (!mProfileFragment.isAdded()){
            transaction.add(R.id.main_container_for_fragment, mProfileFragment, PROFILE);
        }else{
            transaction.show(mProfileFragment);
        }
        transaction.commit();

        if(mProfilePresenter == null){
            mProfilePresenter = new ProfilePresenter(mProfileFragment, mActivity);
        }
        mJeevaContractView.showProfileUi();
    }

    @Override
    public void transToSignInTabPage() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if(mSignInTabFragment == null) mSignInTabFragment = SignInTabFragment.newInstance(); //Create only one time
        if(mHomeFragment != null) transaction.hide(mHomeFragment);
        if(mSavedJobsFragment != null) transaction.hide(mSavedJobsFragment);
        if(mProfileFragment != null) transaction.hide(mProfileFragment);

        if (!mSignInTabFragment.isAdded()){
            transaction.add(R.id.main_container_for_fragment, mSignInTabFragment, SIGNIN);
        }else{
            transaction.show(mSignInTabFragment);
        }
        transaction.commit();

        if(mSignInTabPresenter == null){
            mSignInTabPresenter = new SignInTabPresenter(mSignInTabFragment);
        }
        mJeevaContractView.showSignInTabPageUi();
    }

    @Override
    public void transToJobDetails(Jobs job) {
//        mCurrentFragment = mFragmentManager.findFragmentById(R.id.main_container_for_fragment);
        int currentNavItemId = mBottomNavigationView.getSelectedItemId();
        final FragmentTransaction transaction =
                mFragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right, R.animator.slide_out_right); //smooth animator while switching the fragment
        mJobDetailsFragment = JobDetailsFragment.newInstance();
      if(currentNavItemId == R.id.action_home){
          if(mHomeFragment != null) {
              transaction.hide(mHomeFragment);
              transaction.addToBackStack(HOME);
          }
          mBottomNavigationView.setVisibility(View.GONE);
          mToolbar.findViewById(R.id.home_filter).setVisibility(View.GONE);
          mToolbar.findViewById(R.id.tool_bar_back_btn).setVisibility(View.VISIBLE);
          mToolbar.findViewById(R.id.tool_bar_back_btn).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  mJobDetailsFragment.getFragmentManager().popBackStack(); //Back to previous fragment (not new fragment)
              }
          });

      }else if(currentNavItemId == R.id.action_saved_job){
          if(mSavedJobsFragment != null){
              transaction.hide(mSavedJobsFragment);
              transaction.addToBackStack(SAVEDJOBS);
          }
          mBottomNavigationView.setVisibility(View.GONE);
          mToolbar.findViewById(R.id.tool_bar_back_btn).setVisibility(View.VISIBLE);
          mToolbar.findViewById(R.id.tool_bar_back_btn).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  mJobDetailsFragment.getFragmentManager().popBackStack(); //Back to previous fragment (not new fragment)
              }
          });
      }

        transaction.add(R.id.main_container_for_fragment, mJobDetailsFragment, JOBDETAILS);
        transaction.commit();
        mJobDetailsPresenter = new JobDetailsPresenter(mJobDetailsFragment, job, mBottomNavigationView); //Create presenter instrance
        Log.d("Chloe", "JeevaPresenter job: " + job);

        mJeevaContractView.showJobDetailsUi();

    }

    @Override
    public void refreshSavedJobsItem() {
        if(Jeeva.getJeevaSQLHelper().isSavedJobsChanged()){
            if(mSavedJobsPresenter != null){
                mSavedJobsPresenter.refreshJobs();
            }

            if(mHomePresenter != null){
                mHomePresenter.refresh();
            }
        }
    }

    @Override
    public void start() {
        transToHome();
    }

//    @Override
//    public void transToFilter() {
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        if(mFragmentManager.findFragmentByTag(FILTER) != null)
//            mFragmentManager.popBackStack();
//        if(mFilterFragment == null) mFilterFragment = FilterFragment.newInstance();
//        if(mSavedJobsFragment != null) {
//            transaction.hide(mSavedJobsFragment);
//            transaction.addToBackStack(SAVEDJOBS);
//        }
//        if(mHomeFragment != null) {
//            transaction.remove(mHomeFragment);
//            transaction.addToBackStack(HOME);
//        }
//        if(mSignInTabFragment != null) {
//            transaction.remove(mSignInTabFragment);
//            transaction.addToBackStack(SIGNIN);
//        }
//        if(mProfileFragment != null) {
//            transaction.remove(mProfileFragment);
//        }
//
//        if (!mFilterFragment.isAdded()){
//            transaction.add(R.id.main_container_for_fragment, mFilterFragment, FILTER);
//        }else{
//            transaction.show(mFilterFragment);
//        }
//        transaction.commit();
//
//        if(mFilterPresenter == null){
//            mFilterPresenter = new FilterPresenter(mFilterFragment);
//        }
//        mActivity.invalidateOptionsMenu();
//        mJeevaContractView.showFilterPageUi();
//    }

}
