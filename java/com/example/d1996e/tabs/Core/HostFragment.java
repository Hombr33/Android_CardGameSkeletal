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

import com.example.d1996e.tabs.Networking.GameServer;
import com.example.d1996e.tabs.R;


/**
 * Created by D1996e on 6/19/2017.
 */

public class HostFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    public HostFragment() {
    }

    public void Host(View view)
    {
        EditText edit1 = view.findViewById(R.id.port_text);
        EditText edit2 = view.findViewById(R.id.name_text);
        final int port = Integer.parseInt(edit1.getText().toString());
        final String name = edit2.getText().toString();
        new Thread()
        {
            public void run() {
                try
                {
                    Activity thisActivity = getActivity();
                    new GameServer(port, name, thisActivity);
                }
                catch (Throwable e) { System.out.println(e); }
            }
        }.start();
        Toast.makeText(getActivity(), "Waiting for the client to connect", Toast.LENGTH_SHORT).show();
    }

    public static HostFragment newInstance(int sectionNumber) {
        HostFragment fragment = new HostFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_host, container, false);
        Button btn = (Button) rootView.findViewById(R.id.host_button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try { Host(rootView); }
                catch (Throwable e) { Toast.makeText(getActivity(), "Error hosting the game! Please try again.", Toast.LENGTH_SHORT).show(); }
            }
        });
        return rootView;
    }
}
