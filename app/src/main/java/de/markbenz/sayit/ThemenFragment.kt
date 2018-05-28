package de.markbenz.sayit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThemenFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ThemenFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val liste = ArrayList<DataThema>()
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_themen, container, false)
        recyclerView = mView?.findViewById(R.id.recyclerViewThemen)

        val sendButton = mView?.findViewById<ImageButton>(R.id.imageButtonSendThema)
        val text = mView?.findViewById<EditText>(R.id.editTextThema)
        sendButton?.setOnClickListener {
            val nachricht = DataThema(text?.text.toString(),"")
            schreibeInDb(nachricht)
        }
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)


        holeListe()
        //schreibeInDb()

        return mView
    }

    fun schreibeInDb(msg : DataThema) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("/")
        val key = ref.child("post").push().key
        ref.child("post").child(key).setValue(msg)
}

    fun holeListe() {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("post")
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    liste.clear()
                    for (i in p0!!.children) {
                        var posteintrag = i.getValue(DataThema::class.java)
                        posteintrag!!.id = i.key
                        liste.add(posteintrag!!)
                    }

                    val adapter = Adapter(liste, context!!)
                    recyclerView?.adapter = adapter
                }
            }

        }
        ref.addValueEventListener(listener)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThemenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ThemenFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
