package com.k4ench.pswrd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText e1;

    ImageButton ib;
    Button b3;
    ArrayList<File> list;
    private ProgressDialog dialog;
    ListView listv;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e1=(EditText)findViewById(R.id.editText2);
        ib=(ImageButton)findViewById(R.id.imageButton2);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        listv=(ListView)findViewById(R.id.listv1);
        String url="http://kiran0407.tk/list.php";

        new JSONTask().execute(url);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().equals("")){

                    Toast.makeText(DetailsActivity.this,"Please enter a keyword",Toast.LENGTH_LONG).show();

                }
                else{

                    String t3=e1.getText().toString();
                    String url="http://kiran0407.tk/search2.php?search="+t3;
                    // Toast.makeText(Nav.this,url,Toast.LENGTH_LONG).show();
                    new JSONTask().execute(url);



                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public class JSONTask extends AsyncTask<String,String, List<Categorieslist> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Categorieslist> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("success");
                List<Categorieslist> movieModelList = new ArrayList<>();
                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Categorieslist categorieslist = gson.fromJson(finalObject.toString(), Categorieslist.class);
                    movieModelList.add(categorieslist);


                }

                return movieModelList;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;

        }

        @Override
        protected void onPostExecute(final List<Categorieslist> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList != null) {
                final MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.list_item, movieModelList);
                listv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Categorieslist category=movieModelList.get(position);
                     /*   String nam=category.getTopic_name();
                        String id1=Integer.toString(category.getCatg_id());
                        Toast.makeText(Nav.this,id1,Toast.LENGTH_LONG).show();*/
                        Intent i1=new Intent(DetailsActivity.this,Desc.class);
                        i1.putExtra("name",category.getName());
                        i1.putExtra("invoice",category.getInvoice());
                        i1.putExtra("desc",category.getDescription());
                        startActivity(i1);
                    }
                });
            }
            else {
                Toast.makeText(DetailsActivity.this,"error please try again...",Toast.LENGTH_SHORT).show();
            }


        }

    }
    public class MovieAdapter extends ArrayAdapter {

        private List<Categorieslist> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<Categorieslist> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getViewTypeCount() {

            return 1;
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder  ;

            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();
                //  holder.menuimage = (ImageView)convertView.findViewById(R.id.cato_ima);
                holder.menuname=(TextView) convertView.findViewById(R.id.name);
                holder.invoice=(TextView) convertView.findViewById(R.id.invoc);
                holder.desc=(TextView) convertView.findViewById(R.id.desc);
                // holder.idt=(TextView) convertView.findViewById(R.id.idtt);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Categorieslist categorieslist= movieModelList.get(position);
            holder.menuname.setText(categorieslist.getName());
            holder.invoice.setText(categorieslist.getInvoice());
            holder.desc.setText(categorieslist.getDescription());
            return convertView;

        }

        class ViewHolder{
            // private ImageView menuimage;
            private TextView menuname,invoice,desc;
        }



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(DetailsActivity.this,Accept.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(DetailsActivity.this,Reject.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
