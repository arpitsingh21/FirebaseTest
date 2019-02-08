package ne.application.com.firebasetest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.ItemClickListener {
    val db = FirebaseFirestore.getInstance()
     var meditationList:MutableList<MeditationModel> = mutableListOf<MeditationModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setTitle("Hi ! " + intent.getStringExtra("name"))
        fetchMeditationDetails()

    }

    private fun fetchMeditationDetails() {
        db.collection("meditation").document("calm_down").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("Firebase", "DocumentSnapshot data: " + document.data)
                loadCalmDownData(document.data)
            } else {
                Log.d("Firebase", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("Firebase", "get failed with ", exception)
        }
        db.collection("meditation").document("destress").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("Firebase", "DocumentSnapshot data: " + document.data)
                loadDestressData(document.data)
            } else {
                Log.d("Firebase", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("Firebase", "get failed with ", exception)
        }
        db.collection("meditation").document("focus").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("Firebase", "DocumentSnapshot data: " + document.data)
                loadFocusData(document.data)
            } else {
                Log.d("Firebase", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("Firebase", "get failed with ", exception)
        }
        db.collection("meditation").document("relax").get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("Firebase", "DocumentSnapshot data: " + document.data)
                loadrelaxData(document.data)
            } else {
                Log.d("Firebase", "No such document")
            }
        }.addOnFailureListener { exception ->
            Log.d("Firebase", "get failed with ", exception)
        }
    }

    private fun loadrelaxData(data: Map<String, Any>?) {

        val doingNow = data!!.get("doing_right_now")
        val name = data!!.get("name")
        Log.d("Firebase", doingNow.toString())

        db.collection("meditation").document("relax").collection("session").document("93uqxpczvwqke9Lkxka2").get().addOnCompleteListener(
            OnCompleteListener {
                val imageLink = it.result!!.get("imageLink")
                val link = it.result!!.get("link")
                meditationList.add(MeditationModel(
                    name.toString(), imageLink.toString(), link.toString(),
                    doingNow.toString()
                ))

                gridRv.adapter.notifyDataSetChanged()

            })
    }

    private fun loadFocusData(data: Map<String, Any>?) {
        val doingNow = data!!.get("doing_right_now")
        val name = data!!.get("name")
        Log.d("Firebase", doingNow.toString())
        Log.d("Firebase", data.get("session").toString())
        db.collection("meditation").document("focus").collection("session").document("random_document_id").get().addOnCompleteListener(
            OnCompleteListener {
                val imageLink = it.result!!.get("imageLink")
                val link = it.result!!.get("link")
                meditationList.add(MeditationModel(
                    name.toString(), imageLink.toString(), link.toString(),
                    doingNow.toString()
                ))

                gridRv.adapter.notifyDataSetChanged()

            })
    }

    private fun loadDestressData(data: Map<String, Any>?) {
        val doingNow = data!!.get("doing_right_now")
        val name = data!!.get("name")
        Log.d("Firebase", doingNow.toString())
        Log.d("Firebase", data.get("session").toString())
        db.collection("meditation").document("destress").collection("session").document("F0GMXjGYE52WIDgbV5wd").get().addOnCompleteListener(
            OnCompleteListener {
                val imageLink = it.result!!.get("imageLink")
                val link = it.result!!.get("link")
                meditationList.add(MeditationModel(
                    name.toString(), imageLink.toString(), link.toString(),
                    doingNow.toString()
                ))

                gridRv.adapter.notifyDataSetChanged()

            })
    }

    private fun loadCalmDownData(data: Map<String, Any>?) {
        val doingNow = data!!.get("doing_right_now")
        val name = data!!.get("name")
        Log.d("Firebase", doingNow.toString())
        Log.d("Firebase", data.get("session").toString())
        db.collection("meditation").document("calm_down").collection("session").document("fmALHnbljymtusTKC6N5").get().addOnCompleteListener(
            OnCompleteListener {
                val imageLink = it.result!!.get("imageLink")
                val link = it.result!!.get("link")
                meditationList.add(MeditationModel(
                    name.toString(), imageLink.toString(), link.toString(),
                    doingNow.toString()
                ))

                gridRv.adapter.notifyDataSetChanged()

            })
    }


    private fun setupRecyclerView() {
        gridRv.layoutManager = GridLayoutManager(this@MainActivity, 2)
        gridRv.adapter = MyRecyclerViewAdapter(this@MainActivity, meditationList)
    }

    private fun setData(data: Map<String, Any>?) {
        hideProgress()
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

    fun hideProgress() {
        progress_circular.hide()
        gridRv.visibility = View.VISIBLE
    }

    override fun onItemClick(view: View, position: MeditationModel) {

    }
}


