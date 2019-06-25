# Facebook Manager
Login manager with facebook

# Usage

In your build.gradle(Project: android).
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

```

Add in your build.gradle(Module: app) 

```
dependencies {
 
   // Facebook Manager
    implementation 'com.github.soluevo:facebook-manager:1.0.0'
}

```

Include the component in your layout

```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        tools:context=".MainActivity">

        <com.facebook.login.widget.LoginButton
                    android:id="@+id/auth_facebook_button"
                    android:layout_width="160dp"
                    android:layout_height="22dp"
                   />

</LinearLayout>

```

```Java

   // Implement Facebook callback "FacebookCallBack"
    class MainFragment : Fragment(), FacebookCallBack {

    private lateinit var callbackManager: CallbackManager 

   // Call this function in onViewCreated
    private fun setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        buttonFacebookLogin = authFacebookButton // facebook button id reference
        buttonFacebookLogin.fragment = this // Your fragment instance

        FBCallbackManager(buttonFacebookLogin, this, callbackManager)
    }

  // block when facebook login is a success
   override fun fbCallbackOnSuccess(userFacebook: UserFacebook) {
        // do anything
    }

  // block when facebook login is an error
    override fun fbCallbackOnError(localizedMessage: String) {
       // do anything
    }

  // block when facebook login is canceled
    override fun fbCallbackOnCancel() {
        // do anything
    }

  // This block is required for the callback manager to work
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
   
}

