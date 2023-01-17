package com.example.birthdaybuddy
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactOverview : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_overview)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val contact_list = findViewById<RecyclerView>(R.id.recycler_view)
        contact_list.layoutManager = LinearLayoutManager(this)

        val readContactsButton: Button = findViewById(R.id.readContactsButton)
        readContactsButton.setOnClickListener {
            val contactList : MutableList<ContactClass> = ArrayList()
            val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
            if (contacts != null) {
                while (contacts.moveToNext()) {
                    val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    // val birthday = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE))
                    val obj = ContactClass()
                    obj.name = name
                    // obj.birthday = birthday

                    val photo_uri = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                    if (photo_uri != null) {
                        obj.image = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photo_uri))
                    }

                    contactList.add(obj)
                }
            }
            contact_list.adapter = ContactAdapter(contactList,this)
            contacts?.close()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class ContactAdapter(items : List<ContactClass>, ctx: Context) : RecyclerView.Adapter<ContactAdapter.ViewHolder> () {
        private var list = items
        private var context = ctx

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_card,parent,false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.name.text = list[position].name
            // holder.birthday.text = list[position].birthday
            if(list[position].image != null)
                holder.profile.setImageBitmap(list[position].image)
            else
                holder.profile.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_launcher_round))
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val name: TextView = v.findViewById(R.id.contactName)
            // val birthday: TextView = v.findViewById(R.id.contactBirthday)
            val profile:ImageView = v.findViewById(R.id.profilePic)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}