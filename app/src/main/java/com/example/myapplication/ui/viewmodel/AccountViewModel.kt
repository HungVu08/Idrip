package com.example.myapplication.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class AccountViewModel(private val application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = Firebase.auth
    private var currentUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    var _currentUser: MutableLiveData<FirebaseUser?> = currentUser
    private val signInRequest = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.your_web_client_id))
        .requestEmail().build()

    private val signInClient = GoogleSignIn.getClient(application, signInRequest)

    lateinit var callback: OnSignInStarted

    fun signInWithOneTap() {
        callback.startSignIn(signInClient)
    }

    init {
        currentUser.value = auth.currentUser
    }

    fun signOut() {
        Firebase.auth.signOut()
        currentUser.value = null
    }

    fun firebaseAuthWithToken(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { it ->
                updateData(it)
            }.addOnFailureListener {
                currentUser.value = null
                Toast.makeText(application, "SignIn Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateData(it: AuthResult?) {
        if (it != null) {
            currentUser.value = it.user
            Toast.makeText(application, "SignIn Success", Toast.LENGTH_SHORT).show()
        } else {
            currentUser.value = null
            Toast.makeText(application, "SignIn Failed", Toast.LENGTH_SHORT).show()
        }
    }


    interface OnSignInStarted {
        fun startSignIn(signInClient: GoogleSignInClient)

    }


}