package com.checkboxrecyclerselectalldynamic;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/* ---- Jana-----*/

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckBox chk_select_all;
    private Button btn_delete_all;
    List<String> liste;
    private ArrayList<Model> item_list = new ArrayList<>();
    private ModelAdapter mAdapter;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
    }

    private void initControls() {


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        chk_select_all = (CheckBox) findViewById(R.id.chk_select_all);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);
        new LoadSpinnerdata().execute();

       /* item_list.add(new Model("Alpha", false));
        item_list.add(new Model("Beta", false));
        item_list.add(new Model("Cup Cake", false));
        item_list.add(new Model("Donut", false));
        item_list.add(new Model("Eclair", false));
        item_list.add(new Model("Froyo", false));
        item_list.add(new Model("Ginger Bread", false));
        item_list.add(new Model("Honycomb", false));
        item_list.add(new Model("Icecream Sandwhich", false));
        item_list.add(new Model("Jelly Bean", false));
        item_list.add(new Model("Kitkat", false));
        item_list.add(new Model("Lolly Pop", false));
        item_list.add(new Model("Marsh Mallow", false));
        item_list.add(new Model("Nougat", false));


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ModelAdapter(item_list);
        recyclerView.setAdapter(mAdapter);
*/
        chk_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk_select_all.isChecked()) {

                    for (Model model : item_list) {
                        model.setSelected(true);
                    }
                } else {

                    for (Model model : item_list) {
                        model.setSelected(false);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }
        });

        btn_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder stringBuilder = new StringBuilder();
                liste = new ArrayList<String>();
                for (Model model : item_list) {
                    if (model.isSelected()) {
                        if (stringBuilder.length() > 0)
                            stringBuilder.append(", ");
                        stringBuilder.append(model.getItemid());




                    }

                }

                for (Model model : item_list){
                    if (model.isSelected()){
                        liste.add(model.getItemid());

                    }
                }

                // Toast.makeText(getActivity(), "["+stringBuilder.toString()+"]", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, liste.toString(), Toast.LENGTH_LONG).show();



                Log.e("testing", "stringBuilder = "+stringBuilder);
                Log.e("testing","liste = "+liste);


              /*

                if (chk_select_all.isChecked()) {
                    item_list.clear();
                    mAdapter.notifyDataSetChanged();
                    chk_select_all.setChecked(false);
                } else {
                    Snackbar.make(v, "Please click on select all check box, to delete all items.", Snackbar.LENGTH_LONG).show();
                }*/
            }
        });

    }


    class LoadSpinnerdata extends AsyncTask<String, String, String> {
        String responce;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {

            arraylist = new ArrayList<HashMap<String, String>>();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            userpramas.add(new BasicNameValuePair("type", "dryclean"));
            JSONObject json = jsonParser.makeHttpRequest("http://www.wdilaunderers.com/app/type_allcategoryandpricedetails.php", "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    // status = json.getString("status");
                    String arrayresponce = json.getString("man");
                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);

                    if (arrayresponce == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();


                            item_list.add(new Model(post.optString("id"),post.optString("name"), false));
                            //item_list.add(new Model(post.optString("name"), false));



                          /*  FeedItem item = new FeedItem();

                            item.setId(post.optString("id"));
                            item.setPurpose(post.optString("name"));
                            item.setCity(post.optString("price"));

                            feedItemList.add(item);





                            arraylist.add(map);*/
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            pDialog.dismiss();
            Log.e("testing", "result is = " + responce);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mAdapter = new ModelAdapter(item_list);
            recyclerView.setAdapter(mAdapter);






        }

    }


}
