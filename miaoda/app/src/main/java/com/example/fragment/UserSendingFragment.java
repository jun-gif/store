package com.example.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.activity.R;
import com.example.adapter.OrderListAdapter;
import com.example.dao.OrderDao;
import com.example.model.Order;

import java.util.ArrayList;



public class UserSendingFragment extends Fragment {


    ListView listView;
    SharedPreferences sp=null;

    public UserSendingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_sending, container, false);
        sp=getActivity().getSharedPreferences("userdata", Context.MODE_MULTI_PROCESS);
        listView=view.findViewById(R.id.sendlistview);
        OrderDao order_dao = new OrderDao(getContext());
        ArrayList<Order> allorder=order_dao.userOrderpsz(sp.getString("userId",""));
        OrderListAdapter phAdapter = new OrderListAdapter(getContext(),allorder);
        listView.setAdapter(phAdapter);
        return view;
    }

}