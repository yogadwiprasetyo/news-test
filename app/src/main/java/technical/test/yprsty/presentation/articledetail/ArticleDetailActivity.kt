package technical.test.yprsty.presentation.articledetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import technical.test.yprsty.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}