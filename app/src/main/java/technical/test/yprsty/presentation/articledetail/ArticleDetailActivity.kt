package technical.test.yprsty.presentation.articledetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import technical.test.yprsty.databinding.ActivityArticleDetailBinding
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.utils.convertDateFormat
import technical.test.yprsty.utils.loadImage

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbarWebView()

        val article: Article? = intent.getParcelableExtra(KEY_ARTICLE)
        setupViews(article)

        binding.btnOpenArticle.setOnClickListener {
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(article?.urlArticle.orEmpty())
            binding.webView.isVisible = true
            binding.toolbar.isVisible = true
        }
    }

    private fun setupViews(article: Article?) {
        val defaultDescription = "To see description, click \"Buka selengkapnya\""
        val defaultContent = "Too read full of content, click \"Buka selengkapnya\""
        binding.apply {
            tvTitle.text = article?.title
            tvAuthor.text = article?.author ?: "Unknown"
            tvDate.text = article?.publishedAt?.convertDateFormat()
            tvDescription.text = article?.description ?: defaultDescription
            tvContent.text = article?.contentPreview ?: defaultContent
            ivArticle.loadImage(article?.urlImage)
        }
    }

    private fun setupToolbarWebView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            binding.webView.isVisible = false
            binding.toolbar.isVisible = false
        }
    }

    companion object {
        const val KEY_ARTICLE = "key_article"
    }
}