package brdevelopers.com.jobvibe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import brdevelopers.com.jobvibe.ui.main.SectionsPagerAdapter;

public class Admin extends AppCompatActivity {
private Button AdminGoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AdminGoBack= findViewById(R.id.AdminGoBack);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setterViewPagerActivity(viewPager);

        AdminGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(Admin.this, Login.class);
                startActivity(profile);
                finish();
            }
        });

    }
    private void setterViewPagerActivity(ViewPager viewPager) {

        ViewAdapter viewAdapter=new ViewAdapter(getSupportFragmentManager());
        //  viewAdapter.addFragment(new Viewed_Fragment(),"Viewed");
        viewAdapter.addFragment(new AdminAddJob(),"New");
        viewAdapter.addFragment(new AdminPostedJob(),"Posted");
        viewAdapter.addFragment(new AdminJobAppliedByUser(),"Applied");
        viewPager.setAdapter(viewAdapter);
    }

    public class ViewAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> toplist=new ArrayList<>();
        private List<String>titlelist=new ArrayList<>();

        public ViewAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return toplist.get(position);
        }

        @Override
        public int getCount() {
            return toplist.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titlelist.get(position);
        }


        public void addFragment(Fragment fragment, String string){

            toplist.add(fragment);
            titlelist.add(string);
        }

    }
}