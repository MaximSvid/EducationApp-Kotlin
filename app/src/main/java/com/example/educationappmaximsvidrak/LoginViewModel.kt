package com.example.educationappmaximsvidrak

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.educationappmaximsvidrak.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference


class LoginViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

     val firebaseRef: DatabaseReference = FirebaseDatabase.getInstance("https://education-app-maxim-svidrak-default-rtdb.europe-west1.firebasedatabase.app").getReference("contacts")

    private lateinit var storageReference: StorageReference



    //LiveData currentUser firebaseAuth
    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    //LiveData für result Login
    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult


    fun login(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("LoginViewModel", "Login done")
                    _currentUser.value = it.result.user
                    _loginResult.value = "Login successful"
                } else {
                    //bearbeitug Fehler
                    val exception = it.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            // Учетная запись не существует
                            _loginResult.value = "Account does not exist"
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            // Неправильный пароль
                            _loginResult.value = "Incorrect password"
                        }
                        else -> {
                            // Другие ошибки
                            _loginResult.value = "Login failed: ${exception?.message}"
                        }
                    }

                    Log.e("LoginViewModel", "Login failed: ${exception?.message}")
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
                    Log.e("LoginViewModel", "")
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = null
    }

    fun updateDataAndImage () {



    }






}

