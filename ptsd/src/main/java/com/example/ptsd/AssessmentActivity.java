package com.example.ptsd;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;

//import android.support.v13.app.FragmentPagerAdapter;
/*import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;*/

public class AssessmentActivity extends AppCompatActivity
            implements AssessmentFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */

   // private android.support.wearable.view.FragmentGridPagerAdapter mFragmentGridPagerAdapter;
    private GridViewPager mGridViewPager;
    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Create the adapter that will return a fragment for each of the
        // primary sections of the activity. Uses the Fragment Manager param
        //to access fragments.

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();



        AssessmentFragment fragment = new AssessmentFragment();
        Bundle b = new Bundle();
        b.putString("s1", "This is a test. Please let me be.");
        fragment.setArguments(b);

        AssessmentFragment fragment2 = new AssessmentFragment();
        Bundle b2 = new Bundle();
        b2.putString("s2", "This is a test. Please let me be. Don't mess with me.");
        fragment2.setArguments(b2);

        fragmentTransaction.add(R.id.pager, fragment);
        fragmentTransaction.add(R.id.q2, fragment2);

        android.support.wearable.view.FragmentGridPagerAdapter mFragmentGridPagerAdapter = new android.support.wearable.view.FragmentGridPagerAdapter(getFragmentManager()) {//keep an eye on this
            @Override
            public  android.app.Fragment getFragment(int row, int column){

                //testing
                if(row == 1 && column == 1){
                    AssessmentFragment fragment = new AssessmentFragment();
                    Bundle b = new Bundle();
                    b.putString("s1", "This is a test. Please let me be.");
                    fragment.setArguments(b);
                    return fragment;

                }

               else
                    return null;
            }
            @Override
            public int getRowCount() {
                return 0;
            }

            @Override
            public int getColumnCount(int i) {
                return 0;
            }

           /*@Override
            public android.app.Fragment instantiateItem(ViewGroup viewGroup, int i, int i1) {
                return null;
            }

            @Override
            public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {

            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return false;
            }*/
        };
        final Resources res = getResources();
        setContentView(R.layout.activity_assessment);
        mGridViewPager = (GridViewPager) findViewById(R.id.pager);
        mGridViewPager.setAdapter(mFragmentGridPagerAdapter);
        mGridViewPager.setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                final boolean round = insets.isRound();
                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
                int colMargin = res.getDimensionPixelOffset(round ?
                        R.dimen.page_column_margin_round : R.dimen.page_column_margin);
                mGridViewPager.setPageMargins(rowMargin, colMargin);
                mGridViewPager.onApplyWindowInsets(insets);
                return insets;
            }
        });
        mGridViewPager.setAdapter(mFragmentGridPagerAdapter);
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(mGridViewPager);
//        fragmentTransaction.add(mFragmentGridPagerAdapter.getFragment(1,1));
//        fragmentTransaction.commit();
        ((ViewGroup)mGridViewPager.getParent()).removeView(mGridViewPager);
        setContentView(mGridViewPager);
        beginAssessment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void beginAssessment(){



    }
    public void openMain(View view){
        Intent startAssessment = new Intent(AssessmentActivity.this, MainActivity.class);
        startActivity(startAssessment);
    }
}

