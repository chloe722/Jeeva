package thhsu.chloe.jeeva.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import thhsu.chloe.jeeva.Filter.FilterFragment;
import thhsu.chloe.jeeva.JeevaContract;
import thhsu.chloe.jeeva.JeevaPresenter;
import thhsu.chloe.jeeva.R;

import static thhsu.chloe.jeeva.JeevaPresenter.FILTER;

/**
 * Created by Chloe on 4/30/2018.
 */

public class JeevaActivity extends BaseActivity implements JeevaContract.View, BottomNavigationView.OnNavigationItemSelectedListener{
    private JeevaContract.Presenter mPresenter;
    private TextView mToolbarTitle;
    private Toolbar mToolbar;
    private ImageButton mFilterIcn;
    private BottomNavigationView mBottomNavigationView;
    private MenuItem mFilterItem;
    private FilterFragment filterFragment;
    private boolean isFilterInHome = true;
    private boolean shouldShowFilter = false;
    private Fragment currentFragment;
    JeevaActivity jeevaActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init(){
        setContentView(R.layout.activity_main);
        setBottomNavigationView();
        setToolbar();

        mPresenter = new JeevaPresenter(this, getFragmentManager(), this);
        mPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        jeevaActivity = this;
        int currentItem = mBottomNavigationView.getSelectedItemId();
        currentFragment = getFragmentManager().findFragmentById(R.id.main_container_for_fragment);
        MenuInflater inflater = getMenuInflater();
        if (currentItem == R.id.action_home){
            inflater.inflate(R.menu.menu_filter, menu);
                mFilterItem = menu.findItem(R.id.home_filter).setVisible(true);
                Log.d("Chloe", "currentItemId: " + currentItem);
                mFilterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(jeevaActivity,FilterActivity.class);
                        startActivity(intent);
                        return true;
                    }
                });
        }else if(currentItem == R.id.action_profile){
            inflater.inflate(R.menu.menu_more_member, menu);

        }

        if(currentFragment == getFragmentManager().findFragmentByTag(FILTER)){
            menu.clear();
//            mBottomNavigationView.;

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        init();
    }

    private void setBottomNavigationView(){
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    private void setToolbar() {
        // Retrieve the AppCompact Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("Home");


    }

    private void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }

//    public void setToolbarVisibility(boolean isVisible){
//        if(mToolbar != null){
//            mToolbar.setVisibility(isVisible) ? View.VISIBLE: View.GONE;
//        }
//    }

//    public void hideFilter(){
//        shouldShowFilter = false;
//        invalidateOptionsMenu();
//    }
//
//    public void showFilter(){
//        shouldShowFilter = true;
//        invalidateOptionsMenu();
//    }

    @Override
    public void setPresenter(JeevaContract.Presenter presenter) {
        mPresenter = presenter;

    }

    @Override
    public void showHomeUi() {
        setToolbarTitle("Home");
        isFilterInHome = true;
//        showFilter();

    }

    @Override
    public void showSavedJobUi() {
        setToolbarTitle("Saved Jobs");
        isFilterInHome = false;
//        hideFilter();
    }

    @Override
    public void showProfileUi() {
        setToolbarTitle("Profile");
        isFilterInHome = false;
//        hideFilter();
    }

    @Override
    public void showSignInTabPageUi() {
        setToolbarTitle("Sign Up or Sign In");
        isFilterInHome = false;
//        hideFilter();
    }

    @Override
    public void showFilterPageUi() {
        invalidateOptionsMenu();
        setToolbarTitle("Filter");
        isFilterInHome = false;
//        hideFilter();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        invalidateOptionsMenu();
        switch (item.getItemId()) {
            case R.id.action_home:

                mPresenter.transToHome();
                break;

            case R.id.action_saved_job:
                mPresenter.transToSavedJob();
                break;

//            case R.id.action_profile:
//                mPresenter.transToSignInTabPage();
//                return true;

            case R.id.action_profile:
                mPresenter.transToProfile();

                break;
        }
        return true;
    }
}
