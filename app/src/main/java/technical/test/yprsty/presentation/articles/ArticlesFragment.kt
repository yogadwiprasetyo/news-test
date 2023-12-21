package technical.test.yprsty.presentation.articles

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import technical.test.yprsty.databinding.FragmentArticlesBinding
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.presentation.articledetail.ArticleDetailActivity
import technical.test.yprsty.presentation.articles.adapter.ArticleListAdapter
import technical.test.yprsty.presentation.articles.adapter.LoadStatePagingAdapter

private const val ARG_CATEGORY = "category"

class ArticlesFragment : Fragment() {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private var category: String? = null

    private val viewModel: ArticlesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        arguments?.let { category = it.getString(ARG_CATEGORY) }

        val adapter = ArticleListAdapter(::moveToDetail)
        setupRecyclerView(adapter)
        observeViewModel(adapter)
    }

    private fun moveToDetail(article: Article) {
        val intent = Intent(requireContext(), ArticleDetailActivity::class.java).apply {
            putExtra(ArticleDetailActivity.KEY_ARTICLE, article)
        }
        activity?.startActivity(intent)
    }

    private fun setupRecyclerView(adapter: ArticleListAdapter) {
        binding.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticles.adapter = adapter.withLoadStateFooter(
            footer = LoadStatePagingAdapter {
                adapter.retry()
            }
        )
        binding.rvArticles.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun observeViewModel(adapter: ArticleListAdapter) {
        viewModel.loadArticles(category.orEmpty())
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            adapter.submitData(viewLifecycleOwner.lifecycle, uiState.items)
            binding.apply {
                rvArticles.isVisible = uiState.userMessage.isBlank()
                progressBar.isVisible = uiState.isLoading
                ivInfo.isVisible = uiState.userMessage.isNotBlank()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        category = null
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String) =
            ArticlesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}