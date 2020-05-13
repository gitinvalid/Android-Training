package com.example.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.application.BannerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
    }

    private fun initFragment() {
        val chatsFragment = ChatsFragment()
        val contactsFragment = ContactsFragment()
        val discoverFragment = DiscoverFragment()
        val meFragment = MeFragment()
        val replaceFragment = ReplaceFragment()

        supportFragmentManager.beginTransaction().add(R.id.banner_container, BannerFragment()).commit()

        chats.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            if (!chatsFragment.isAdded) {
                transaction
                    .add(R.id.fragment_container, chatsFragment)
            }
            transaction.show(chatsFragment)
            transaction.commit()
        }

        contacts.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            if (!contactsFragment.isAdded) {
                transaction
                    .add(R.id.fragment_container, contactsFragment)
            }
            transaction.show(contactsFragment)
            transaction.commit()
        }

        discover.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            if (!discoverFragment.isAdded) {
                transaction
                    .add(R.id.fragment_container, discoverFragment)
            }
            transaction.show(discoverFragment)
            transaction.commit()
        }

        me.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            if (!meFragment.isAdded) {
                transaction
                    .add(R.id.fragment_container, meFragment)
            }
            transaction.show(meFragment)
            transaction.commit()
        }

        remove_fragments.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .remove(chatsFragment)
                .remove(contactsFragment)
                .remove(discoverFragment)
                .remove(meFragment)
                .remove(replaceFragment)
                .commit()
        }

        replace.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            if (!replaceFragment.isAdded) {
                transaction
                    .replace(R.id.fragment_container, replaceFragment)
            }
            transaction.show(replaceFragment)
            transaction.commit()
        }
    }
}
