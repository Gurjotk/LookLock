package brdevelopers.com.jobvibe;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class MatchedFragment extends Fragment  {
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    RecyclerView recyclerView,recyclerView2;
    ArrayList<EntityInternship> EntityInternshipArrayList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MatchedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_wishlist.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchedFragment newInstance(String param1, String param2) {
        MatchedFragment fragment = new MatchedFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.matched_fragment, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.RV_Intenship);
        layoutManager=new LinearLayoutManager( getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        prepare_news();

        recyclerView2=(RecyclerView) view.findViewById(R.id.RV_JobCategory);
        layoutManager2=new LinearLayoutManager( getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        prepare_news2();


        return  view;
    }

    public  void prepare_news(){

        EntityInternshipArrayList=new ArrayList<>();
        EntityInternshipArrayList.add(new EntityInternship("Android Developer","Mobile"));
        EntityInternshipArrayList.add(new EntityInternship("Accountant","Admin"));
        EntityInternshipArrayList.add(new EntityInternship("Technician","Tech"));
        EntityInternshipArrayList.add(new EntityInternship("Web Developer","Full Stack"));
        EntityInternshipArrayList.add(new EntityInternship("Graphic Designer","Animation"));

//        EntityInternshipArrayList.add(new EntityInternship("INDIA"));




        CustomAdapterInternship customAdapter= new CustomAdapterInternship(EntityInternshipArrayList,getActivity());
        recyclerView.setAdapter(customAdapter);

    }
    public  void prepare_news2(){

        EntityInternshipArrayList=new ArrayList<>();




        EntityInternshipArrayList.add(new EntityInternship("General Labour","Labour"));
        EntityInternshipArrayList.add(new EntityInternship("Graphic Designer","Animation"));
        EntityInternshipArrayList.add(new EntityInternship("Content Writter","Tech"));
        EntityInternshipArrayList.add(new EntityInternship("Cleaning","HouseKeeping"));
        EntityInternshipArrayList.add(new EntityInternship("Hospitality","HouseKeeping"));
        EntityInternshipArrayList.add(new EntityInternship("Transportation","Quality"));
        EntityInternshipArrayList.add(new EntityInternship("Marketing","communicator"));
        EntityInternshipArrayList.add(new EntityInternship("Customer Service","Speaker"));

//        EntityInternshipArrayList.add(new EntityInternship("INDIA"));




        CustomAdapterJobCategory customAdapter2= new CustomAdapterJobCategory(EntityInternshipArrayList,getActivity());
        recyclerView2.setAdapter(customAdapter2);

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
