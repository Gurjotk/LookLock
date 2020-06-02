package brdevelopers.com.jobvibe;


import android.os.Bundle;
;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Viewed_Fragment extends Fragment {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private ImageView iv_nojob;

    private RequestQueue mRequestQueue;
    private Animation animShow, animHide;
    private int scroll=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewed,container,false);
        recyclerView = view.findViewById(R.id.RV_job);
        progressBar=view.findViewById(R.id.progressbar);
        iv_nojob=view.findViewById(R.id.IV_nojob);

// Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        initAnimation();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0) {
                    if(scroll==0) {
                        scroll=1;
                        Home.layoutbottom.startAnimation(animHide);
                        Home.layoutbottom.setVisibility(View.GONE);
                    }
                }
                else if(dy<0){
                    if(scroll==1) {
                        scroll=0;
                        Home.layoutbottom.startAnimation(animShow);
                        Home.layoutbottom.setVisibility(View.VISIBLE);
                    }

                }
            }
        });


        DBManager dbManager=new DBManager(getActivity());


        return view;
    }

    private void initAnimation()
    {
        animShow = AnimationUtils.loadAnimation( getActivity(), R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( getActivity(), R.anim.view_hide);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:

//                progressBar.setVisibility(View.VISIBLE);
                DBManager dbManager=new DBManager(getActivity());

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
