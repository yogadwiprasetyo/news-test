package technical.test.yprsty.presentation.articles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import technical.test.yprsty.databinding.ItemArticleBinding
import technical.test.yprsty.domain.model.Article
import technical.test.yprsty.utils.convertDateFormat
import technical.test.yprsty.utils.loadImage

class ArticleListAdapter(
    private val articleClicked: (Article) -> Unit
) : PagingDataAdapter<Article, ArticleListAdapter.ArticleViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            articleClicked
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        article?.let { holder.bind(it) }
    }

    class ArticleViewHolder(
        private val binding: ItemArticleBinding,
        private val articleClicked: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                tvTile.text = article.title
                tvAuthor.text = article.author ?: "Unknown"
                tvPublishAt.text = article.publishedAt?.convertDateFormat()
                ivArticle.loadImage(article.urlImage)
            }

            itemView.setOnClickListener { articleClicked(article) }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.urlArticle == newItem.urlArticle
            }
        }
    }
}