package com.example.myapplication.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.ui.viewmodel.AccountViewModel
import com.example.myapplication.utils.PermissionHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser


class AccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        binding = FragmentAccountBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel = (activity as MainActivity).accountViewModel
        accountViewModel.callback = onSignInStartedListener


        binding.btnSignInGoogle.setOnClickListener {
            accountViewModel.signInWithOneTap()
        }

        binding.btnSignOut.setOnClickListener {
            accountViewModel.signOut()
        }

        binding.btnPermission.setOnClickListener {
            PermissionHelper.askPermission(
                requestPermissionLauncher,
                activity as MainActivity,android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        accountViewModel._currentUser.observe(viewLifecycleOwner, Observer {
            updateUi(it)
        })
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback { isGranted ->
                if (isGranted) {
                    Log.d("TAG", ":requestPermissionLauncher granted ")
                } else {
                    Log.d("TAG", ":requestPermissionLauncher denied ")
                }
            })

    private fun updateUi(it: FirebaseUser?) {
        binding.user = it
    }

    private val onSignInStartedListener = object : AccountViewModel.OnSignInStarted {
        override fun startSignIn(signInClient: GoogleSignInClient) {
            signInClient.signInIntent.let {
                signInLauncher.launch(it)
            }
        }

    }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { it ->
                if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                    try {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                        val account = task.getResult(ApiException::class.java)
                        account.idToken?.let { token ->
                            accountViewModel.firebaseAuthWithToken(token)
                        }
                    } catch (e: ApiException) {
                        Toast.makeText(activity, "SignIn Failed : ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
}