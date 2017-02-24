package application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pc.maptest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends AppCompatActivity {

    private static final String TAG = Main4Activity.class.getSimpleName();
    List<StationMetroGare> Lesstations=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        String url1 = "https://data.ratp.fr/api/records/1.0/search/?dataset=accessibilite-des-gares-et-stations-metro-et-rer-ratp&facet=departement&facet=accessibilite_ufr&facet=annonce_sonore_prochain_passage&facet=annonce_visuelle_prochain_passage&facet=annonce_sonore_situations_perturbees&facet=annonce_visuelle_situations_perturbees";
        HttpHandler sh = new HttpHandler();
        String jsonStr1;
        url1 = "https://data.ratp.fr/api/records/1.0/search/?dataset=accessibilite-des-gares-et-stations-metro-et-rer-ratp&facet=departement&facet=accessibilite_ufr&facet=annonce_sonore_prochain_passage&facet=annonce_visuelle_prochain_passage&facet=annonce_sonore_situations_perturbees&facet=annonce_visuelle_situations_perturbees";
        jsonStr1 = sh.makeServiceCall(url1);
        Log.e(TAG, "Response from url: " + jsonStr1);

        if (jsonStr1 != null) {
            try {
                JSONObject jsonObj1 = new JSONObject(jsonStr1);
                JSONArray stations = jsonObj1.getJSONArray("records");
                String nom_station_gare, coord_geo;

                for (int i = 0; i < stations.length(); i++) {
                    JSONObject s = stations.getJSONObject(i);
                    JSONObject fields = s.getJSONObject("fields");
                    nom_station_gare = fields.optString("nom_station_gare");
                    coord_geo = fields.optString("coord");
                    //Database2.insertData(coord_geo, nom_station_gare);
                    StationMetroGare station = new StationMetroGare();
                    station.setNom(nom_station_gare);
                    station.setCoord_geo(coord_geo);
                    Lesstations.add(station);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                }
            });
        }
        Log.d("Lesstion", " " + Lesstations.size());
    }
}
