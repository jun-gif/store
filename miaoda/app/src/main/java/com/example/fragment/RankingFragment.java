package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.activity.R;
import com.example.dao.DistributorDao;
import com.example.model.Distributor;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    ArrayList<Distributor> list;

    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *4
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ranking, container, false);
        listView=view.findViewById(R.id.paihanglist);
        DistributorDao distributor_dao=new DistributorDao(getContext());
        list =distributor_dao.distributorDesc();
        MyListAdapter myListAdapter=new MyListAdapter(getContext(),list);//实例化适配器
        listView.setAdapter(myListAdapter);
        return view;
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        ArrayList<Distributor> list;
        public MyListAdapter(Context context, ArrayList<Distributor>temlist){
            layoutInflater=LayoutInflater.from(context);
            list=temlist;
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {】
            ViewHolder viewHolder = new ViewHolder();
            if (convertView==null) {

                convertView=layoutInflater.inflate(R.layout.list_item,parent,false);
                viewHolder.imageViews=(ImageView)convertView.findViewById(R.id.psy_image);
                viewHolder.name=(TextView) convertView.findViewById(R.id.psy_name);
                viewHolder.phcs=(TextView) convertView.findViewById(R.id.psy_jdcs);
                viewHolder.ph=(TextView) convertView.findViewById(R.id.mingci);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            Distributor distributor=list.get(position);
            viewHolder.ph.setText(position+1+"");
            Bitmap bitmap= BitmapFactory.decodeFile(distributor.getDistributor_picPath());
            viewHolder.imageViews.setImageBitmap(bitmap);
            viewHolder.name.setText(distributor.getDistributor_name());
            viewHolder.phcs.setText(distributor.getDistributor_singularnum()+"");
            return convertView;
        }
    }

    class ViewHolder{
        public ImageView imageViews;
        public  TextView ph;
        public TextView name;
        public TextView phcs;
    }

}