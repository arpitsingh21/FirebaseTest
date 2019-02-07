package ne.application.com.firebasetest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()

        setTitle("Hi ! " + intent.getStringExtra("name"))

        var snapshot = db.collection("meditation").document("relax").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("Firebase", "DocumentSnapshot data: " + document.data)

            } else {
                Log.d("Firebase", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d("Firebase", "get failed with ", exception)
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_filter -> AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    val intent = Intent(this@MainActivity, SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                };

        }
        return super.onOptionsItemSelected(item)
    }
}
