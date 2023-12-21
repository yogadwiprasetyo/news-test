package technical.test.yprsty.presentation.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import technical.test.yprsty.databinding.ActivityArticlesBinding
import technical.test.yprsty.utils.categories

class ArticlesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articlesPagerAdapter = ArticlesPagerAdapter(this)
        binding.viewPager.adapter = articlesPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = categories[position].replaceFirstChar { it.uppercase() }
        }.attach()
    }
}