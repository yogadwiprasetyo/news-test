package technical.test.yprsty.presentation.articles

import android.os.Bundle
import androidx.activity.ComponentActivity
import technical.test.yprsty.databinding.ActivityArticlesBinding

class ArticlesActivity : ComponentActivity() {
    private lateinit var binding: ActivityArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}