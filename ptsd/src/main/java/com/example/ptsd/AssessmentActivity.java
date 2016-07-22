package com.example.ptsd;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;

import java.util.List;

public class AssessmentActivity extends AppCompatActivity{
            //implements AssessmentFragment.OnFragmentInteractionListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     * The {@link ViewPager} that will host the section contents.
     */
    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        //displaySpeechRecognizer();

        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        final Resources res = getResources();
        pager.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                final boolean round = insets.isRound();
                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
                int colMargin = res.getDimensionPixelOffset(round ?
                        R.dimen.page_column_margin_round : R.dimen.page_column_margin);
                pager.setPageMargins(rowMargin, colMargin);
                pager.onApplyWindowInsets(insets);
                return insets;
            }
        });

        pager.setAdapter(new SampleGridPagerAdapter(this, getFragmentManager()));
        DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        /*@Override
            public android.app.Fragment instantiateItem(ViewGroup viewGroup, int i, int i1) {
               AssessmentFragment fragment = new AssessmentFragment();
               Bundle b = new Bundle();
               b.putInt("question_number", 1);
               b.putString("question_text", "@string/random_text");
               fragment.setArguments(b);
               return fragment;
            }
            @Override
            public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
            }
        };*/
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

   /* @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }*/

    public void finishAssessment(){
        /* Checks to see if the user has answered all questions before finishing the assessment
        If true, finishes, saves and returns to the main menu and ends AssessmentActivity
        If false, displays a notification and stays on the same activity
         */
        if(true){

        }
        else {
            Intent mainAct = new Intent(AssessmentActivity.this, MainActivity.class);
            startActivity(mainAct);
            this.finish();
        }
    }
    /*public void openMain(View view){
        Intent startAssessment = new Intent(AssessmentActivity.this, MainActivity.class);
        startActivity(startAssessment);
    }*/

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

