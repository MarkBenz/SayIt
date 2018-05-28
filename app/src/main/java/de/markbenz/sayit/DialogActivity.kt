package de.markbenz.sayit

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.content_dialog.*

class DialogActivity : AppCompatActivity() {

    val liste = ArrayList<DataAntwort>()
    var recyclerView: RecyclerView? = null
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        setSupportActionBar(toolbar)

        val intent = getIntent()
        id = intent.getStringExtra("id")
        Log.i("Test", id)

        recyclerView = findViewById(R.id.DialogRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

/*        val antwort = DataAntwort(id!!, "Test Antwort")
        schreibeInDb(antwort)*/

        holeListe()
        imageButtonAntworten.setOnClickListener{
            val antwort = DataAntwort(id!!, editTextAntworten.text.toString())
            schreibeInDb(antwort)
        }

    }


    fun holeListe() {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("antworten").orderByChild("idDesThemas").equalTo(id)
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    liste.clear()
                    for (i in p0!!.children) {
                        var posteintrag = i.getValue(DataAntwort::class.java)
                        Log.i("Test", "Inhalt von post : ${posteintrag!!.content}")

                        //   posteintrag!!.id = i.key
                        liste.add(posteintrag)
                    }

                    val adapter = AdapterDialog(liste)
                    recyclerView?.adapter = adapter
                }
            }

        }
        ref.addValueEventListener(listener)
    }

    fun schreibeInDb(msg: DataAntwort) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("/")
        //ref.setValue(liste.get(3))
        val key = ref.child("antworten").push().key

        ref.child("antworten").child(key).setValue(msg) // it : Element um das es gerade geht
    }

}
