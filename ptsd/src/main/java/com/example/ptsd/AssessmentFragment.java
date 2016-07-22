package com.example.ptsd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

//import android.support.v4.app.Fragment;


/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AssessmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AssessmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssessmentFragment extends android.app.Fragment implements View.OnClickListener {
    private static int currentQuestion = 0;
    /*private int question_number;
    private String question_text;
    private String question_response;
    Button play, pause;
    private OnFragmentInteractionListener mListener;*/

    public AssessmentFragment() {
        // Required empty public constructor
    }

    public static AssessmentFragment newInstance(int param1, String param2) {
        AssessmentFragment fragment = new AssessmentFragment();
        Bundle args = new Bundle();
        args.putInt("q_number", currentQuestion);
        currentQuestion++;
       // question_number = param1;
        args.putString("q_text", "R.string.question"+param1);
        fragment.setArguments(args);

        return fragment;
    }

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            /*question_number = getArguments().getInt("q_number");
            question_text = "This was made in the AssessmentFragment Class";*/
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentQuestion++;
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_assessment, container, false);
        super.onViewCreated(container, savedInstanceState);
        TextView text = (TextView) v.findViewById(R.id.text);
        RadioButton button1 = (RadioButton) v.findViewById(R.id.likert1);
        button1.setOnClickListener(this);
        RadioButton button2 = (RadioButton) v.findViewById(R.id.likert2);
        button2.setOnClickListener(this);
        RadioButton button3 = (RadioButton) v.findViewById(R.id.likert3);
        button3.setOnClickListener(this);
        RadioButton button4 = (RadioButton) v.findViewById(R.id.likert4);
        button4.setOnClickListener(this);
        RadioButton button5 = (RadioButton) v.findViewById(R.id.likert5);
        button5.setOnClickListener(this);
        if(currentQuestion == 0){
            text.setText(R.string.Question0);
        }
        if(currentQuestion == 1) {
            text.setText(R.string.Question1);
        }
        if(currentQuestion == 2) {
            text.setText(R.string.Question2);
        }
        if(currentQuestion == 3) {
            text.setText(R.string.Question3);
        }
        if(currentQuestion == 4) {
            text.setText(R.string.Question4);
        }
        if(currentQuestion == 5) {
            text.setText(R.string.Question5);
        }
       if(currentQuestion == 6) {
            text.setText(R.string.Question6);
        }
        if(currentQuestion == 7) {
            text.setText(R.string.Question7);
        }
        if(currentQuestion == 8) {
            text.setText(R.string.Question8);
        }
        if(currentQuestion == 9) {
            text.setText(R.string.Question9);
        }
        if(currentQuestion == 10){
            text.setText(R.string.Question10);
        }
        return v;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        /*Check which radio button was clicked and calculate "score" accordingly
        Also should send a confirmation to activity for every question answered
        Confirmation is used to determine whether the test is finished or not
        */

        //this.getActivity().
        switch(view.getId()) {
            case R.id.likert1:
                if (checked)
                    //score+=5;
                    break;
            case R.id.likert2:
                if (checked)
                    //score+=4;
                    break;
            case R.id.likert3:
                if (checked)
                    //score+=3;
                    break;
            case R.id.likert4:
                if (checked)
                    //score+=2;
                    break;
            case R.id.likert5:
                if (checked)
                    //score+=1;
                    break;
        }
    }

    @Override
    public void onClick(View view) {
        //view.onRadioButto
    }

    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }*/
}

//text.setText("ok");
        /*play=(Button) v.findViewById(R.id.play);
        pause=(Button) v.findViewById(R.id.pause);

        final MediaPlayer sound=MediaPlayer.create(getActivity(), R.raw.rain);

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sound.start();

            }
        });
        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sound.pause();

            }
        });*/