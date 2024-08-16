package com.example.educationappmaximsvidrak

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    fun login(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("LoginViewModel", "Login done")
                    _currentUser.value = it.result.user
                } else {
                    Log.e("LoginViewModel", "Login done")
                }
            }
    }

    fun register(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("LoginViewModel", "Login done")
                    _currentUser.value = it.result.user
                } else {
                    Log.e("LoginViewModel", "Login done")
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = null
    }
}

