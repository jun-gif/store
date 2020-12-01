package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.activity.DistribMainActivity;
import com.example.activity.R;
import com.example.dao.DistributorDao;
import com.example.dao.OrderDao;
import com.example.model.Order;

import java.util.ArrayList;


public class ReceiveFragment extends Fragment {
    ListView listView;
    ArrayList<Order> allorder;
    OrderDao order_dao;
    LinearLayout psy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive, container, false);
        order_dao = new OrderDao(getContext());
        psy = view.findViewById(R.id.psy);
        allorder = order_dao.allOrder();
        listView=view.findViewById(R.id.listview);
        if (allorder.size() == 0) {
            psy.setBackgroundResource(R.mipmap.wudingdan);
        } else {
            psy.setBackgroundResource(R.color.white);
        }
        ReceiveOrderAdapter phAdapter = new ReceiveOrderAdapter(getContext(), allorder);

        listView.setAdapter(phAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                intat(position);
            }
//
//            private void intat(int position) {
//            }
        });
        return view;

    }


    class ReceiveOrderAdapter extends BaseAdapter {
        Order order = null;
        LayoutInflater layoutInflater;
        ArrayList<Order> list;
        Context context;
        public ReceiveOrderAdapter(Context context, ArrayList<Order> list) {
            layoutInflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.receiveorder, parent,false);
                viewHolder.imageView = convertView.findViewById(R.id.imgss);
                viewHolder.name = convertView.findViewById(R.id.name);
                viewHolder.phone = convertView.findViewById(R.id.phone);
                viewHolder.address = convertView.findViewById(R.id.address);
                viewHolder.layout=convertView.findViewById(R.id.cs);
                viewHolder.button = convertView.findViewById(R.id.jiedain);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            order = list.get(position);
//            Log.i("pic",order.getOrder_picpath());
            if (order.getOrder_picpath()!= null &&!order.getOrder_picpath().equals("")) {
                viewHolder.imageView.setImageURI(Uri.parse(order.getOrder_picpath()));
            }else {
                viewHolder.imageView.setImageResource(R.mipmap.psy_image);
            }
            if (order.getOrder_distribut_type().equals("代发大件")||order.getOrder_distribut_type().equals("代发小件"))
            {
                viewHolder.layout.setBackgroundResource(R.mipmap.s);}
            else {
                viewHolder.layout.setBackgroundResource(R.mipmap.t);
            }
            viewHolder.name.setText(order.getOrder_receiver_name());
            viewHolder.phone.setText(order.getOrder_receiver_tel() + "");
            viewHolder.address.setText(order.getOrder_receiver_address());
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sp=context.getSharedPreferences("distribdata",Context.MODE_MULTI_PROCESS);
                    String id=sp.getString("distribuId","");
                    order_dao.receiveOrder(id, String.valueOf(order.getOrder_id()));
                        DistributorDao distributorDao=new DistributorDao(getContext());
                        distributorDao.updateSingularnum(id);

                    startActivity(new Intent(getContext(), DistribMainActivity.class));
                    getActivity().finish();
                }
            });
            return convertView;
        }

    }
    class ViewHolder {
        public LinearLayout layout;
        public Button button;
        public ImageView imageView;
        public TextView address;
        public TextView name;
        public TextView phone;
    }


}