package cg.vsu.survey.view.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cg.vsu.survey.R
import cg.vsu.survey.app.DEFAULT_SURVEYS_WHEN_TO_LOAD
import cg.vsu.survey.viewmodel.SurveyListViewModel


class FeedSurveyFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SurveyAdapter
    private lateinit var viewModel: SurveyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        recyclerView = view.findViewById(R.id.RV)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(this).get(SurveyListViewModel::class.java)
        adapter = SurveyAdapter(viewModel, this.viewLifecycleOwner)
        recyclerView.adapter = adapter

        viewModel.loadData()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (viewModel.isEnded() || viewModel.isLoading()) {
                    return
                }

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= (totalItemCount - DEFAULT_SURVEYS_WHEN_TO_LOAD) && firstVisibleItemPosition >= 0) {
                    viewModel.loadData()
                }
            }
        })

        viewModel.surveys.observe(viewLifecycleOwner) { surveys ->
            adapter.notifyDataSetChanged()
        }

        return view
    }
}