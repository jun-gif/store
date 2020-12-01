package com.example.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.FeedbackActivity;
import com.example.activity.MyAddressActivity;
import com.example.activity.OrderTypeActivity;
import com.example.activity.PersonInfoActivity;
import com.example.activity.R;
import com.example.activity.RechargeActivity;
import com.example.dao.UserDao;
import com.example.model.User;
import com.makeramen.roundedimageview.RoundedImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView headImage;
    private Toolbar toolbar;
    private TextView showuser;
    private LinearLayout lin_ddxq;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_mine, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        lin_ddxq = view.findViewById(R.id.ddxq);
        headImage = view.findViewById(R.id.myicon);
        showuser = view.findViewById(R.id.showuser);
        SharedPreferences sp = getActivity().getSharedPreferences("userdata", Context.MODE_MULTI_PROCESS);
        String user_id = sp.getString("userId", "");
        UserDao user_dao = new UserDao(getContext());
        User user = user_dao.findUser(user_id);
        showuser.setText(user_id);

        String picPath = user.getUser_picPath();
        Toast.makeText(getContext(), "图片路劲"+picPath, Toast.LENGTH_LONG).show();
        if (null != picPath  && !picPath.equals("")) {
            Bitmap bitmap= BitmapFactory.decodeFile(user.getUser_picPath());
            headImage.setImageBitmap(bitmap);
            //headImage.setImageURI(Uri.parse(picPath));
        }

        view.findViewById(R.id.linheared).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PersonInfoActivity.class);
                Bundle b = new Bundle();
                b.putString("ispsy", "0");
                intent.putExtras(b);
                getActivity().startActivity(intent);
            }
        });

        lin_ddxq.setOnClickListener(new View.OnClickListener() {//订单详情
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), OrderTypeActivity.class);
                Bundle b = new Bundle();
                b.putString("ispsy", "0");
                intent.putExtras(b);
                getActivity().startActivity(intent);
            }
        });

        view.findViewById(R.id.cz).setOnClickListener(new View.OnClickListener() {//充值
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RechargeActivity.class));
            }
        });
        view.findViewById(R.id.yjfh).setOnClickListener(new View.OnClickListener() {//意见反馈
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FeedbackActivity.class));
            }
        });
        view.findViewById(R.id.myaddress).setOnClickListener(new View.OnClickListener() {//我的地址
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyAddressActivity.class));
            }
        });
        view.findViewById(R.id.wdxx).setOnClickListener(new View.OnClickListener() {//我的消息
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), MyMessageActivity.class);
//                getActivity().startActivity(intent);
            }
        });
        view.findViewById(R.id.gywm).setOnClickListener(new View.OnClickListener() {//关于我们
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getContext(), AboutUsActivity.class));
            }
        });
        view.findViewById(R.id.jcgx).setOnClickListener(new View.OnClickListener() {//检测更新
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), DetectUpdateActivity.class));
            }
        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
       return view;
    }
}
