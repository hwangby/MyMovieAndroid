package com.hby.mymovie;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailFragment extends Fragment {
    Button likeButton;
    Button hateButton;
    TextView likeCountView;
    TextView hateCountView;

    TextView titleTextView;


    //MainActivity activity;

    CommentAdapter adapter;
    //EditText editText;
    //EditText editText2;
    TextView commentView;

    RatingBar ratingBar;

    int likeCount = 1;
    int hateCount = 1;

    boolean likeState = false;
    boolean hateState = false;

    FragmentCallback2 callback;

    ListView listView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof  FragmentCallback2) {
            callback = (FragmentCallback2) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (callback != null) {
            callback = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);


        //commentView = (TextView) rootView.findViewById(R.id.commentView);


        //activity.toolbar.setTitle("영화 상세");


        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);

        titleTextView = (TextView) rootView.findViewById(R.id.titleTextView);

        final Button writeButton = (Button) rootView.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"작성하기 선택됨.", Toast.LENGTH_LONG).show();
                float rating = ratingBar.getRating();

                Intent intent = new Intent(getActivity(), CommentWriteActivity.class);
                intent.putExtra("rating", rating);
                startActivityForResult(intent, 101);

            }
        });

        Button allButton = (Button) rootView.findViewById(R.id.allButton);
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"모두보기 선택됨.", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(getActivity(), AllViewActivity.class);
                startActivity(intent2);
                //startActivityForResult(intent2, 102);
            }
        });

        likeButton = (Button) rootView.findViewById(R.id.likeButton);
        hateButton = (Button) rootView.findViewById(R.id.hateButton);

        likeCountView = (TextView) rootView.findViewById(R.id.likeCountView);
        hateCountView = (TextView) rootView.findViewById(R.id.hateCountView);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hateState) {
                    Toast.makeText(getActivity(), "이미 싫어요를 누르셨습니다.", Toast.LENGTH_SHORT).show();
                } else if (likeState) {
                    decrLikeCount();
                    likeState = !likeState;
                } else {
                    incrLikeCount();
                    likeState = !likeState;
                }
            }
        });

        hateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeState) {
                    Toast.makeText(getActivity(), "이미 좋아요를 누르셨습니다.", Toast.LENGTH_SHORT).show();
                } else if (hateState) {
                    decrHateCount();
                    hateState = !hateState;
                } else {
                    incrHateCount();
                    hateState = !hateState;
                }

            }
        });

        listView = (ListView) rootView.findViewById(R.id.listView);

        adapter = new CommentAdapter();
        adapter.addItem(new CommentItem("nhby****", "좋은 공연이었습니다."));


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommentItem item = (CommentItem) adapter.getItem(position);
                Toast.makeText(getActivity(),"선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });


        /* //등록 버튼
        editText = (EditText) rootView.findViewById(R.id.editText);
        editText2 = (EditText) rootView.findViewById(R.id.editText2);

        Button postButton = (Button) rootView.findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                String comment = editText2.getText().toString();

                saveComment(name, comment);

            }
        });
        */

        //String comment = commentView.getText().toString(); error

        return rootView;
    }

    public void saveComment(String name, String comment) {
        adapter.addItem(new CommentItem(name, comment));
        adapter.notifyDataSetChanged();

        Toast.makeText(getActivity(), "한줄평이 등록되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void incrLikeCount() {
        likeCount += 1;
        likeCountView.setText(String.valueOf(likeCount));

        likeButton.setBackgroundResource(R.drawable.ic_thumb_up_selected);
    }

    public void decrLikeCount() {
        likeCount -= 1;
        likeCountView.setText(String.valueOf(likeCount));

        likeButton.setBackgroundResource(R.drawable.ic_thumb_up);
    }

    public void incrHateCount() {
        hateCount += 1;
        hateCountView.setText(String.valueOf(hateCount));

        hateButton.setBackgroundResource(R.drawable.ic_thumb_down_selected);
    }

    public void decrHateCount() {
        hateCount -= 1;
        hateCountView.setText(String.valueOf(hateCount));

        hateButton.setBackgroundResource(R.drawable.ic_thumb_down);
    }


    public void onCommandFromActivity(String command, String comment) {
        adapter.addItem(new CommentItem("익명",comment));
        listView.setAdapter(adapter);
    }

    //-------------------------------------------------------------

    class CommentAdapter extends BaseAdapter {
        ArrayList<CommentItem> items = new ArrayList<CommentItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(CommentItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommentItemView view = null;
            if (convertView == null) {
                view = new CommentItemView(getActivity());
            } else {
                view = (CommentItemView) convertView;
            }

            CommentItem item = items.get(position);
            view.setName(item.getName());
            view.setComment(item.getComment());

            return view;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 101) {
            if (intent != null) {
                String comment = intent.getStringExtra("contents");
                adapter.addItem(new CommentItem("익명",comment));
                listView.setAdapter(adapter);
            }
        }
    }

}
