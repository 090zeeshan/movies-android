package com.vd.movies.ui.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.vd.movies.R
import com.vd.movies.databinding.FragmentWebViewBinding
import com.vd.movies.ui.home.HomeFragment
import com.vd.movies.ui.listing.ListingFragment
import com.vd.movies.ui.listing.ListingFragmentArgs

class WebViewFragment : Fragment() {

    private lateinit var mBinding: FragmentWebViewBinding
    private lateinit var mUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUrl = arguments?.let { WebViewFragmentArgs.fromBundle(it).url } ?: "";
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentWebViewBinding.inflate(inflater, container, false)

        mBinding.webview.settings.domStorageEnabled = true
        mBinding.webview.settings.javaScriptEnabled = true
        mBinding.webview.webChromeClient = object: WebChromeClient(){}
        mBinding.webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mBinding.layoutLoading.visibility = INVISIBLE
            }
        }
        mBinding.webview.loadUrl(mUrl)

        return mBinding.root;
    }
}