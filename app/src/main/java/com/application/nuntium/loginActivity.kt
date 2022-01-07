package com.application.nuntium

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.application.nuntium.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class loginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth:FirebaseAuth
    private val Req_Code:Int=123
    private lateinit var binding:ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
        val googleBtn = findViewById<ImageView>(R.id.googleBtn)
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
         mGoogleSignInClient =GoogleSignIn.getClient(this,gso)
         firebaseAuth= FirebaseAuth.getInstance()

//        btn2.setOnClickListener {
//            startActivity(Intent(this,ChoicesActivity::class.java))
//        }
        googleBtn.setOnClickListener {
            sinInGoogle()
        }

    }

    private fun sinInGoogle(){
        val signInIntent:Intent=mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task :Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult( completeTask : Task<GoogleSignInAccount> ){
        try {
            val account = completeTask.getResult(ApiException::class.java)
            if(account != null){
                UpdateUI(account)
            }
        }catch (e:Exception){
            Log.i("TAG","${e.message}")
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val cred = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(cred).addOnCompleteListener {
            if(it.isSuccessful){
                Log.i("message","success")
                SavedPreference.setUsername(this,account.displayName?.toString()?:"")
                SavedPreference.setEmail(this,account.email?.toString()?:"")
                SavedPreference.setProfileUrl(this,account.photoUrl?.toString()?:"")
                constants.setUserTime(this,"OK")
                val intent = Intent(this, ChoicesActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Log.i("message", it.exception?.localizedMessage.toString())
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, ChoicesActivity::class.java))
            finish()
        }
    }
}
object SavedPreference {

    const val EMAIL= "email"
    const val USERNAME="username"
    const val IMGURL ="imgUrl"

    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun  editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }
    fun setProfileUrl(context: Context,imgurl:String){
        editor(
            context,
            IMGURL,
            imgurl
        )
    }

    fun getImgUrl(context: Context) = getSharedPreference(
        context
    )?.getString(IMGURL,"")

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")

}