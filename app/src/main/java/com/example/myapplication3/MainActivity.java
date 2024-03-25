package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import org.bson.Document;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

//Rishi Sivaraju
public class MainActivity extends AppCompatActivity {
    private App app;
//    private EditText dataEditText;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    EditText username;
    EditText password;
    EditText phone;
    Button loginButton;
    String AppId="application-1-kvwha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        loginButton = findViewById(R.id.loginButton);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
//                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        Realm.init(this);
        app =new App(new AppConfiguration.Builder(AppId).build());



        Credentials credentials=Credentials.emailPassword("sivarajurishi@gmail.com","rishi123");

        app.loginAsync(Credentials.anonymous(),new App.Callback<User>(){

            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    Log.v("User","logged in ");
                }
                else{
                    Log.v("User","Filed to login");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = app.currentUser();
                assert user != null;
                mongoClient = user.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("botha");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("login");
//                Document document = new Document("username", UsernameValue)
//                        .append("password", passwordValue)
//                        .append("data", dataEditText.getText().toString());
                mongoCollection.insertOne(new Document("userid",user.getId()).append("username",username.getText().toString()).append("password",password.getText().toString()).append("phone",phone.getText().toString())).getAsync(result -> {
                    if(result.isSuccess())
                    {
                        Log.v("Data","Data Inserted Successfully");
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.v("Data","Error:"+result.getError().toString());
                    }
                });
            }
        });
    }
}
//Rishi Sivaraju