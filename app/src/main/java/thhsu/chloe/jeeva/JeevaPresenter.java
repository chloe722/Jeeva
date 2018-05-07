package thhsu.chloe.jeeva;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import thhsu.chloe.jeeva.Filter.FilterFragment;
import thhsu.chloe.jeeva.Filter.FilterPresenter;
import thhsu.chloe.jeeva.Home.HomeFragment;
import thhsu.chloe.jeeva.Home.HomePresenter;
import thhsu.chloe.jeeva.JobDetails.JobDetailsFragment;
import thhsu.chloe.jeeva.JobDetails.JobDetailsPresenter;
import thhsu.chloe.jeeva.Profile.ProfileFragment;
import thhsu.chloe.jeeva.Profile.ProfilePresenter;
import thhsu.chloe.jeeva.SavedJobs.SavedJobsFragment;
import thhsu.chloe.jeeva.SavedJobs.SavedJobsPresenter;
import thhsu.chloe.jeeva.SignInTab.SignInTabFragment;
import thhsu.chloe.jeeva.SignInTab.SignInTabPresenter;
import thhsu.chloe.jeeva.activities.JeevaActivity;

/**
 * Created by Chloe on 4/30/2018.
 */

public class JeevaPresenter implements JeevaContract.Presenter {
    private final JeevaContract.View mJeevaContractView;
    private FragmentManager mFragmentManager;
    private MenuItem menuItem;
    public JeevaActivity mActivity;

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
    private FilterFragment mFilterFragment;
    private JobDetailsFragment mJobDetailsFragment;

    private SignInTabPresenter mSignInTabPresenter;
    private SavedJobsPresenter mSavedJobsPresenter;
    private HomePresenter mHomePresenter;
    private ProfilePresenter mProfilePresenter;
    private FilterPresenter mFilterPresenter;
    private JobDetailsPresenter mJobDetailsPresenter;

    public JeevaPresenter(JeevaContract.View jeevaView, FragmentManager fragmentManager, JeevaActivity activity){
        this.mActivity = activity;
        mJeevaContractView = jeevaView;
        if(jeevaView != null){
            mJeevaContractView.setPresenter(this);
        }else{
            Log.d("Chloe", "jeevaView is empty");
        }
        mFragmentManager = fragmentManager;

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void transToHome() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

//        if(mFragmentManager.findFragmentByTag(HOME) != null)
//            mFragmentManager.popBackStack();
        if(mHomeFragment == null) mHomeFragment = HomeFragment.newInstance();
        if(mSavedJobsFragment != null) transaction.hide(mSavedJobsFragment);
        if(mProfileFragment != null) transaction.hide(mProfileFragment);
//        if(mSignInTabFragment != null) transaction.remove(mSignInTabFragment);
        if (!mHomeFragment.isAdded()){
            transaction.add(R.id.main_container_for_fragment, mHomeFragment, HOME);
        }else{
            transaction.show(mHomeFragment);
        }
        transaction.commit();

        if(mHomePresenter == null){
            mHomePresenter = new HomePresenter(mHomeFragment);
        }
        mJeevaContractView.showHomeUi();


    }



    @Override
    public void transToSavedJob() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

//        if(mFragmentManager.findFragmentByTag(SAVEDJOBS) != null)
//            mFragmentManager.popBackStack();
        if(mSavedJobsFragment == null) mSavedJobsFragment = SavedJobsFragment.newInstance();
        if(mHomeFragment != null) transaction.hide(mHomeFragment);
        if(mProfileFragment != null) transaction.hide(mProfileFragment);
//        if(mSignInTabFragment != null) transaction.remove(mSignInTabFragment);
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

//        if(mFragmentManager.findFragmentByTag(PROFILE) != null)
//            mFragmentManager.popBackStack();
        if(mProfileFragment == null) mProfileFragment = ProfileFragment.newInstance();
        if(mHomeFragment != null) {
            transaction.hide(mHomeFragment);
//            transaction.addToBackStack(HOME);
        }
        if(mSavedJobsFragment != null) {
            transaction.hide(mSavedJobsFragment);
//            transaction.addToBackStack(SAVEDJOBS);
        }
        if (!mProfileFragment.isAdded()){
            transaction.add(R.id.main_container_for_fragment, mProfileFragment, PROFILE);
        }else{
            transaction.show(mProfileFragment);
        }
        transaction.commit();

        if(mProfilePresenter == null){
            mProfilePresenter = new ProfilePresenter(mProfileFragment);
        }
        mJeevaContractView.showProfileUi();

    }

    @Override
    public void transToSignInTabPage() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if(mFragmentManager.findFragmentByTag(SIGNIN) != null)
            mFragmentManager.popBackStack();
        if(mSignInTabFragment == null) mSignInTabFragment = SignInTabFragment.newInstance();
        if(mSavedJobsFragment != null) transaction.hide(mSavedJobsFragment);
        if(mHomeFragment != null) transaction.remove(mHomeFragment);
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
    public void transToJobDetails() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        mJobDetailsFragment = JobDetailsFragment.newInstance();
        if(mHomeFragment != null) {
            transaction.hide(mHomeFragment);
            transaction.addToBackStack(HOME);


        }
//        if(mSavedJobsFragment != null ) {
//            transaction.hide(mSavedJobsFragment);
////            transaction.addToBackStack(SAVEDJOBS);
//        }
//
//        if(mProfileFragment != null){
//            transaction.hide(mProfileFragment);
////            transaction.addToBackStack(PROFILE);
//        }
//
//        if(mSignInTabFragment != null ){
//            transaction.hide(mSignInTabFragment);
////            transaction.addToBackStack(SIGNIN);
//        }

        transaction.add(R.id.main_container_for_fragment, mJobDetailsFragment, JOBDETAILS);
//        if(!mJobDetailsFragment.isAdded()){
//
//
//        }else{
//            transaction.show(mJobDetailsFragment);
//        }

        transaction.commit();
        if(mJobDetailsPresenter == null){
            mJobDetailsPresenter = new JobDetailsPresenter(mJobDetailsFragment);
        }
        mJeevaContractView.showJobDetailsUi();


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

    @Override
    public void start() {
        transToHome();
    }

}
