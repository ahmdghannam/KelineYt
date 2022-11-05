package fts.ahmed.kelineyt.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import fts.ahmed.kelineyt.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) :ViewModel() {

    private val _login= MutableSharedFlow<Resource<FirebaseUser>>()
    val login=_login.asSharedFlow()

    private val _resetPassword= MutableSharedFlow<Resource<String>>()
    val resetPassword= _resetPassword.asSharedFlow()

    fun login(email:String,password:String){
        viewModelScope.launch {
            _login.emit(Resource.Loading())
        }
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener { authResult->
                viewModelScope.launch {
                    authResult.user?.let { user->
                        _login.emit(Resource.Success(user))
                    }
                }
            }
            .addOnFailureListener { ex->
                viewModelScope.launch {
                    _login.emit(Resource.Error(ex.message.toString()))
                }
            }

    }

    fun resetPassword(email:String){
        viewModelScope.launch {
            _resetPassword.emit(Resource.Loading())
        }
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        _resetPassword.emit(Resource.Success(email))
                    }
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _resetPassword.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
    }

