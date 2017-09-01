package com.kesteli.filip.eurekas2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TimActivity extends AppCompatActivity {

    private EditText etImeTima;
    private EditText etKorisnici;
    private EditText etNet;
    private EditText etVjera;
    private EditText etUlog;
    private EditText etRealno;
    private EditText etTkoUlog;
    private EditText etMotivacija;
    private EditText etPodrucja;
    private EditText etSansaUlog;
    private EditText etBrojClanova;
    private Button btnClanovi;

    private String imeTima = "", korisnici = "", tkoUlog = "", podrucja = "";
    private int net = 0, vjera = 0, ulog = 0, realno = 0, motivacija = 0, sansaUlog = 0, brojClanova = 0;

    private String ime = "", prezime = "";
    private int godine = 0, tehnoIskustvo = 0, iskustvo = 0, obrazovanje = 0, znanje = 0, dani = 0, tehnoDani = 0;

    private DatabaseReference databaseReference;
    private DatabaseReference childTimovi;
    private DatabaseReference childClanovi;
    private DatabaseReference childImeTima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim);

        initViews();
        setupListeners();
        setupFirebase();
    }

    private void initViews() {
        etImeTima = (EditText) findViewById(R.id.etImeTima);
        etKorisnici = (EditText) findViewById(R.id.etKorisnici);
        etNet = (EditText) findViewById(R.id.etNet);
        etVjera = (EditText) findViewById(R.id.etVjera);
        etUlog = (EditText) findViewById(R.id.etUlog);
        etRealno = (EditText) findViewById(R.id.etRealno);
        etTkoUlog = (EditText) findViewById(R.id.etTkoUlog);
        etMotivacija = (EditText) findViewById(R.id.etMotivacija);
        etPodrucja = (EditText) findViewById(R.id.etPodrucja);
        etSansaUlog = (EditText) findViewById(R.id.etSansaUlog);
        etBrojClanova = (EditText) findViewById(R.id.etBrojClanova);
        btnClanovi = (Button) findViewById(R.id.btnClanovi);
    }

    private void setupFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void setupListeners() {
        btnClanovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirebase();
                firebaseQueryUpdateRootClan();
                Intent intent = new Intent(TimActivity.this, ClanActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addToFirebase() {
        imeTima = etImeTima.getText().toString();
        korisnici = etKorisnici.getText().toString();
        net = Integer.parseInt(etNet.getText().toString());
        vjera = Integer.parseInt(etVjera.getText().toString());
        ulog = Integer.parseInt(etUlog.getText().toString());
        realno = Integer.parseInt(etRealno.getText().toString());
        tkoUlog = etTkoUlog.getText().toString();
        motivacija = Integer.parseInt(etMotivacija.getText().toString());
        podrucja = etPodrucja.getText().toString();
        sansaUlog = Integer.parseInt(etSansaUlog.getText().toString());
        brojClanova = Integer.parseInt(etBrojClanova.getText().toString());

        ime = "Pero";
        prezime = "Peric";
        godine = 23;
        tehnoIskustvo = 34;
        iskustvo = 25;
        obrazovanje = 23;
        znanje = 98;
        dani = 38;
        tehnoDani = 32;

        childTimovi = databaseReference.child("Timovi");
        childClanovi = childTimovi.child(imeTima).child("Clanovi");

        childTimovi.child(imeTima).child("motivacija").setValue(10);

        DatabaseReference childClan1 = childClanovi.child("Clan1Ivo");
        DatabaseReference childClan2 = childClanovi.child("Clan2Pero");
        DatabaseReference childClan3 = childClanovi.child("Clan3Bato");

        childClan1.child("ime").setValue(ime);
        childClan1.child("prezime").setValue(prezime);
        childClan1.child("godine").setValue(godine);
        childClan1.child("tehnoIskustvo").setValue(tehnoIskustvo);
        childClan1.child("iskustvo").setValue(iskustvo);
        childClan1.child("obrazovanje").setValue(obrazovanje);
        childClan1.child("znanje").setValue(znanje);
        childClan1.child("dani").setValue(dani);
        childClan1.child("tehnoDani").setValue(tehnoDani);

        childClan2.child("ime").setValue("Pero");
        childClan2.child("prezime").setValue("Ivic");
        childClan2.child("godine").setValue(2500);

        childClan3.child("ime").setValue("Bato");
        childClan3.child("prezime").setValue("Peric");
        childClan3.child("godine").setValue(3000);
    }

    private void firebaseQueryUpdateRootClan() {
        childClanovi = databaseReference.child("Timovi").child(imeTima).child("Clanovi");
        Query queryRefUpdateRootClan = childClanovi.orderByChild("prezime");

        queryRefUpdateRootClan.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Clan clan = dataSnapshot.getValue(Clan.class);
                Log.d("g2", dataSnapshot.getKey() + " " + clan.getIme() + " " + clan.getPrezime() + " " + clan.getGodine());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}



