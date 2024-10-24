package com.example.assignmentvrid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.assignmentvrid.ui.fragments.viewmodals.BlogViewModel
import com.example.assignmentvrid.activity.MainActivity
import com.example.assignmentvrid.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    lateinit var viewModel: BlogViewModel
    val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.link)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener{
            viewModel.saveArticle(args.article)
        }
        return binding.root
    }

}