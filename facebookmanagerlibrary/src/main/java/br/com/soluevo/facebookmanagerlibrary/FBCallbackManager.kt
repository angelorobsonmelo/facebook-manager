package br.com.soluevo.facebookmanagerlibrary

import br.com.soluevo.cobrei.application.commom.utils.facebookmanager.FacebookCallBack
import br.com.soluevo.facebookmanagerlibrary.handlerstatuscode.HandlerErrorStatusCode
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class FBCallbackManager(private val mButtonFacebookLogin: LoginButton, private val mFacebookCallBack: FacebookCallBack, private val mCallbackManager: CallbackManager) {

    private var mAuth: FirebaseAuth         = FirebaseAuth.getInstance()
    private var mLoginManager: LoginManager = LoginManager.getInstance()

    init {
        setupFacebookCallback()
    }

    private fun setupFacebookCallback() {
        mButtonFacebookLogin.setReadPermissions("email", "public_profile")
        mButtonFacebookLogin.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
               mFacebookCallBack.fbCallbackOnCancel()
            }

            override fun onError(error: FacebookException) {
                val messageError = run {
                    HandlerErrorStatusCode.NO_INTERNET_CONNECTION_ERROR.getMessageFromResourceString().toString()
                }

                mFacebookCallBack.fbCallbackOnError(messageError)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    val userFacebook =
                        UserFacebook(
                            token.applicationId,
                            mAuth.currentUser?.email!!,
                            mAuth.currentUser?.displayName!!,
                            token.token
                        )
                    mAuth.signOut()
                    mLoginManager.logOut()
                    mFacebookCallBack.fbCallbackOnSuccess(userFacebook)
                } else -> mFacebookCallBack.fbCallbackOnError(it.exception?.localizedMessage!!)
            }
        }

    }

}