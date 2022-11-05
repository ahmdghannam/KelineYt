package fts.ahmed.kelineyt.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fts.ahmed.kelineyt.R
import fts.ahmed.kelineyt.activities.ShoppingActivity
import fts.ahmed.kelineyt.databinding.FragmentLoginBinding
import fts.ahmed.kelineyt.dialog.setupBottomSheetDialog
import fts.ahmed.kelineyt.util.Resource
import fts.ahmed.kelineyt.viewModel.LoginViewModel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvForgetPassword.setOnClickListener {
            this.setupBottomSheetDialog { email->
                viewModel.resetPassword(email)
            }
        }
        lifecycleScope.launchWhenStarted {
                viewModel.resetPassword.collect { resourceResponse->
                    when (resourceResponse) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            Snackbar.make(requireView(),"Reset Link was sent to Your email",Snackbar.LENGTH_LONG).show()

                        }
                        is Resource.Error -> {
                            Snackbar.make(requireView(),"Error: ${resourceResponse.message}",Snackbar.LENGTH_LONG).show()
                        }
                        else -> Unit
                    }
                }

        }
        binding.tvDoYouHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.apply {
            buttonLoginLogin.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString()
                viewModel.login(email, password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect { resourceResponse->
                when (resourceResponse) {
                    is Resource.Loading -> binding.buttonLoginLogin.startAnimation()
                    is Resource.Success -> {
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(),resourceResponse.message, Toast.LENGTH_LONG).show()
                        binding.buttonLoginLogin.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }
    }
}