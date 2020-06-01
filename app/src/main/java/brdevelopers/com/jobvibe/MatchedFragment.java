package brdevelopers.com.jobvibe;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.github.clans.fab.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MatchedFragment extends Fragment implements View.OnClickListener {

    private String allJob = "http://103.230.103.142/jobportalapp/job.asmx/JobSearch";
    private RecyclerView recyclerView;

    private HashSet<String> jblocation = new HashSet<>();
    private HashSet<String> jbcompany = new HashSet<>();
    private HashSet<String> jbskill = new HashSet<>();
    private ImageView nojob;
    private FloatingActionMenu floatingActionMenu;
    private int scroll=0;

    private static String course;

    private RequestQueue mRequestQueue;
    private SearchView searchView;
    boolean[] checkedItemscom;
    boolean[] checkedItemsskill;
    boolean[] checkedItemsloc;
    private Animation animShow, animHide;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matched_fragment, container, false);
        recyclerView = view.findViewById(R.id.RV_job);

        nojob=view.findViewById(R.id.IV_nojob);
        floatingActionMenu=view.findViewById(R.id.menu);

        initAnimation();
//        Hiding the bottom layout and floating button when scroll down and show when scroll up

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0) {
                    if(scroll==0) {
                        scroll = 1;
                        Home.layoutbottom.startAnimation(animHide);
                        Home.layoutbottom.setVisibility(View.GONE);
                        floatingActionMenu.hideMenu(true);
                    }
                }
                else if(dy<0){
                    if(scroll==1) {
                        scroll=0;
                        Home.layoutbottom.startAnimation(animShow);
                        Home.layoutbottom.setVisibility(View.VISIBLE);
                        floatingActionMenu.showMenu(true);
                    }

                }
            }
        });

//        closing the floating menu when touch on Parent layout(Relative Layout)

        view.findViewById(R.id.matchedRoot).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(floatingActionMenu.isOpened())
                    floatingActionMenu.close(true);
                return false;
            }
        });

// Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();



        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        String degree=Home.getdegree;
        String FOS=Home.getfos;




        if(degree.equals("B.TECH"))
        {
            degree=degree+"("+FOS+")";
        }

        course=degree;


        if (Util.isNetworkConnected(getActivity())) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                loadAlljob();
        } else {
            Toast toast = new Toast(getActivity());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);

            LayoutInflater inf = getActivity().getLayoutInflater();

            View layoutview = inf.inflate(R.layout.custom_toast, (ViewGroup) getActivity().findViewById(R.id.CustomToast_Parent));
            TextView tf = layoutview.findViewById(R.id.CustomToast);
            tf.setText("No Internet Connection " + Html.fromHtml("&#9995;"));
            toast.setView(layoutview);
            toast.show();


            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        return view;
    }

    private void initAnimation()
    {
        animShow = AnimationUtils.loadAnimation( getActivity(), R.anim.view_show);
        animHide = AnimationUtils.loadAnimation( getActivity(), R.anim.view_hide);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        inflater.inflate(R.menu.searchfile, menu);
        final MenuItem myactionmenu=menu.findItem(R.id.search);
        searchView=(SearchView)myactionmenu.getActionView();



        searchView.setQueryHint(Html.fromHtml("<font color = #000>" + getResources().getString(R.string.search_title) + "</font>"));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean bol=floatingActionMenu.isOpened();
                if(bol){
                    floatingActionMenu.close(true);
                }
                searchView.setBackgroundResource(R.drawable.searchview_bg);
                ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.BLACK);

            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                boolean bol=floatingActionMenu.isOpened();
                if(bol){
                    floatingActionMenu.close(true);
                }
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

//                Home.toolbar.setNavigationIcon(Home.drawable);
                menu.getItem(0).setVisible(true);
                boolean bol=floatingActionMenu.isOpened();
                if(bol){
                    floatingActionMenu.close(true);
                }

                if(searchView.isIconified())
                    searchView.setIconified(true);

                myactionmenu.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                boolean bol=floatingActionMenu.isOpened();
                if(bol){
                    floatingActionMenu.close(true);
                }

                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadAlljob() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, allJob, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("LogCheck", response);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("JobList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jobobject = jsonArray.getJSONObject(i);

                        String jlocation = jobobject.getString("location");

                        jblocation.add(jlocation.toLowerCase());

                    }

                    checkedItemsloc = new boolean[jblocation.size()];
                    int i = 0;
                    for (String s : jblocation) {
                        checkedItemsloc[i] = false;
                        i++;
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("LogCheck", "" + e);

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LogCheck", "" + error);
                        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> jobHash = new HashMap<>();
                jobHash.put("location", "");
                jobHash.put("skill", "");
                jobHash.put("course", "");

                return jobHash;
            }

        };

//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        mRequestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.location) {

            showLocation();
        } else if (v.getId() == R.id.skill) {
            showSkill();
        } else if (v.getId() == R.id.company) {
            showCompany();
        }
    }

    private void showCompany() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Company");

        // add a checkbox list
        final String[] company = jbcompany.toArray(new String[0]);
//        boolean[] checkedItems = new boolean[jbcompany.size()];

        final List<String> newCompany = new ArrayList<>();

        int a=0;
        for(boolean bolu:checkedItemscom){
            if(bolu)
                newCompany.add(company[a]);
            a++;
        }

        builder.setMultiChoiceItems(company, checkedItemscom, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked)
                    newCompany.add(company[which]);
                else
                    newCompany.remove(company[which]);
            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                int i = 0;
                for (String s : jblocation) {
                    checkedItemsloc[i] = false;
                    i++;
                }
                i=0;
                for(String s:jbskill){
                    checkedItemsskill[i]=false;
                    i++;
                }

                boolean bol=floatingActionMenu.isOpened();
                if(bol){
                    floatingActionMenu.close(true);
                }



            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showSkill() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Skill");

        // add a checkbox list
        final String[] skill = jbskill.toArray(new String[0]);
//        final boolean[] checkedItems = new boolean[jbskill.size()];

        final List<String> newSkill = new ArrayList<>();

        int a=0;
        for(boolean bolu:checkedItemsskill){
            if(bolu)
                newSkill.add(skill[a]);
            a++;
        }

        builder.setMultiChoiceItems(skill, checkedItemsskill, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked)
                    newSkill.add(skill[which]);

                else
                    newSkill.remove(skill[which]);

            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                int i = 0;
                for (String s : jbcompany) {
                    checkedItemscom[i] = false;
                    i++;
                }
                i=0;
                for(String s:jblocation){
                    checkedItemsloc[i]=false;
                    i++;
                }

                boolean bol=floatingActionMenu.isOpened();
                if(bol){
                    floatingActionMenu.close(true);
                }



            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showLocation() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Location");

        // add a checkbox list
        final String[] location = jblocation.toArray(new String[0]);


        final List<String> newLocation = new ArrayList<>();

        int a=0;
        for(boolean bolu:checkedItemsloc){
            if(bolu)
                newLocation.add(location[a]);
            a++;
        }

        builder.setMultiChoiceItems(location, checkedItemsloc, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // user checked or unchecked a box
                if (isChecked)
                    newLocation.add(location[which]);
                else
                    newLocation.remove(location[which]);

            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK

                int i = 0;
                for (String s : jbcompany) {
                    checkedItemscom[i] = false;
                    i++;
                }
                i=0;
                for(String s:jbskill){
                    checkedItemsskill[i]=false;
                    i++;
                }


                boolean bol=floatingActionMenu.isOpened();
                if(bol){
                    floatingActionMenu.close(true);
                }



            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }







}
