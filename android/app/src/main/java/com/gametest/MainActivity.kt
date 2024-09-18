package com.gametest /* <---- change this with your own package name */
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import com.facebook.react.bridge.Arguments
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.ReactRootView
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.RCTNativeAppEventEmitter
import com.facebook.react.modules.core.DeviceEventManagerModule

class MainActivity : ReactActivity() {

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    override fun getMainComponentName(): String {
        return "RNUnity"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("TAG", "onActivityResult-- $requestCode   $resultCode   $data")

        if (requestCode == 1110) {
            data?.let {
                val callBackFromUnityToRN = it.getStringExtra("callBackFromUnityToRN")
                val params: WritableMap = Arguments.createMap()
                params.putString("data", callBackFromUnityToRN)
                sendEvent("onSubmit", params)
                return
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun sendEvent(eventName: String, @Nullable params: WritableMap?) {
        val reactContext: ReactContext? = reactNativeHost.reactInstanceManager.currentReactContext
        if (reactContext != null) {
            reactContext
                .getJSModule(RCTNativeAppEventEmitter::class.java)
                .emit(eventName, params)
        } else {
            Log.i("TAG", "sendEvent: react context is null --- ")
        }
    }

    /**
     * Returns the instance of the [ReactActivityDelegate]. Here we use a util class [DefaultReactActivityDelegate] which allows you to easily enable Fabric and Concurrent React
     * (aka React 18) with two boolean flags.
     */
    // override fun createReactActivityDelegate(): ReactActivityDelegate {
    //     return MainActivityDelegate(this, mainComponentName)
    // }

    // class MainActivityDelegate(activity: ReactActivity, mainComponentName: String) : ReactActivityDelegate(activity, mainComponentName) {
    //     override fun createRootView(): ReactRootView {
    //         val reactRootView = ReactRootView(context)
    //         // If you opted-in for the New Architecture, we enable the Fabric Renderer.
    //         reactRootView.isFabric = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
    //         return reactRootView
    //     }
    // }
}
