package com.application.nuntium.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.application.nuntium.R
import com.application.nuntium.SavedPreference
import com.application.nuntium.constants
import com.application.nuntium.databinding.FragmentUserBinding
import com.application.nuntium.loginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class UserFragment : Fragment() {
    private lateinit var binding:FragmentUserBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(requireContext(),gso)
        binding = FragmentUserBinding.bind(LayoutInflater.from(context).inflate(R.layout.fragment_user, container, false))
        binding.userName.text = SavedPreference.getUsername(requireContext())
        binding.userEmail.text = SavedPreference.getEmail(requireContext())

        Picasso.get().load(SavedPreference.getImgUrl(requireContext())).into(binding.userImg)
        binding.logoutBtn.setOnClickListener {
            constants.setUserTime(requireContext(),"NO")
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(requireContext(), loginActivity::class.java)
                startActivity(intent)
            }
        }
//        binding.mswitch.setOnCheckedChangeListener { buttonView, isChecked ->
//
//            if(isChecked){
//                AppCompatDelegate
//                    .setDefaultNightMode(
//                        AppCompatDelegate
//                            .MODE_NIGHT_YES)
//                Toast.makeText(requireContext(),"Noght Mode",Toast.LENGTH_SHORT).show()
//            }
//            else {
//                AppCompatDelegate
//                    .setDefaultNightMode(
//                        AppCompatDelegate
//                            .MODE_NIGHT_NO)
//                Toast.makeText(requireContext(),"light mode",Toast.LENGTH_SHORT).show()
//            }
//        }
        return binding.root
    }
}