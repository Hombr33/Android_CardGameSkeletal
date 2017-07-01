package com.example.d1996e.tabs.Core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.d1996e.tabs.Networking.GameClient;
import com.example.d1996e.tabs.R;

public class JoinFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    public JoinFragment() {
    }

    public void Join(View view)
    {
        EditText edit1 = view.findViewById(R.id.port_text);
        EditText edit2 = view.findViewById(R.id.name_text);
        EditText edit3 = view.findViewById(R.id.ip_text);
        final int port = Integer.parseInt(edit1.getText().toString());
        final String name = edit2.getText().toString();
        final String ip = edit3.getText().toString();
        new Thread()
        {
            public void run() {
                try
                {
                    Activity thisActivity = getActivity();
                    new GameClient(ip, port, name, thisActivity);
                }
                catch (Throwable e) { System.out.println(e); }
            }
        }.start();
        Toast.makeText(getActivity(), "Trying to join the game...", Toast.LENGTH_SHORT).show();
    }


    public static JoinFragment newInstance(int sectionNumber) {
        JoinFragment fragment = new JoinFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_join, container, false);
        Button btn = (Button) rootView.findViewById(R.id.join_button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try { Join(rootView); }
                catch (Throwable e) { Toast.makeText(getActivity(), "Error connecting to host! Please try again.", Toast.LENGTH_SHORT).show(); }
            }
        });
        return rootView;
    }
}